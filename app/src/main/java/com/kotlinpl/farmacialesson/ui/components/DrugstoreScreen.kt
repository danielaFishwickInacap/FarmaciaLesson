package com.kotlinpl.farmacialesson.ui.components

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kotlinpl.farmacialesson.R
import com.kotlinpl.farmacialesson.view_model.DrugstoreViewModel
import com.kotlinpl.farmacialesson.view_model.DrugstoresErrors
import com.kotlinpl.farmacialesson.view_model.LocationViewModel
import com.kotlinpl.farmacialesson.ui.components.DrugstoreList


@Composable
fun DrugstoreScreen(
    modifier: Modifier = Modifier,
    drugstoreViewModel: DrugstoreViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel()
    ) {
    val uiState = drugstoreViewModel.uiState.collectAsState()
    val locationState = locationViewModel.location.collectAsState()

    var listOfPremissions by remember { mutableStateOf(listOf("")) }

    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            locationViewModel.getCurrentLocation()
        } else {
            Log.e("DrugstoreScreen", "Location settings not enabled")
        }
    }

    LaunchedEffect(locationViewModel.location) {
        if (locationViewModel.location.value != null) {
            drugstoreViewModel.sortDrugstores(locationViewModel.location.value!!)
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        listOfPremissions = permissions.entries.mapNotNull {
            if (it.value) it.key else null
        }
    }

    Column(
        modifier = modifier
    ) {
        if(locationViewModel.hasLocationPermission()) {
            LaunchedEffect(Unit) {
                locationViewModel.checkLocationSettings(
                    onSettingDisable = { intentRequestSender ->
                        settingResultRequest.launch(intentRequestSender)
                    },
                    onSettingEnable = {
                        locationViewModel.getCurrentLocation()
                    }
                )
            }
        }




        if(uiState.value.isLoading) {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingIndicator()
            }
        }

        if(uiState.value.error != null) {
            when(uiState.value.error) {
                DrugstoresErrors.SerializationError -> Text(text = stringResource(R.string.serialization_error))
                DrugstoresErrors.UnknownError -> Text(text = stringResource(R.string.unknown_error))
                else -> Text(text = stringResource(R.string.unknown_error))
            }

            Log.e("DrugstoreScreen", "Error: ${uiState.value.error}")
        } else {
            if (locationState.value != null) {
                Text(text = "Lat: ${locationState.value!!.lat}")
                Text(text = "Long: ${locationState.value!!.long}")
            }

            DrugstoreList(
                drugstores = uiState.value.drugstores,
                modifier = modifier
            )
        }
    }


}