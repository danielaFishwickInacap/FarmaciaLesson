package com.kotlinpl.farmacialesson.ui.components

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

@Composable
fun DrugstoreScreen(
    modifier: Modifier = Modifier,
    drugstoreViewModel: DrugstoreViewModel = hiltViewModel(),
//    locationViewModel: LocationViewModel = hiltViewModel()
    ) {
    val uiState = drugstoreViewModel.uiState.collectAsState()
    var listOfPremissions by remember { mutableStateOf(listOf("")) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        listOfPremissions = permissions.entries.mapNotNull {
            if (it.value) it.key else null
        }
    }

//    val locationState = locationViewModel.location.collectAsState()

    Column(

    ) {
        Text("Get permissions")
        Button(onClick = {
            permissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }) {
            Text("Get permissions")
            Log.d("DrugstoreScreen", "Permissions: $listOfPremissions")
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
            DrugstoreList(
                drugstores = uiState.value.drugstores,
                modifier = modifier
            )
        }
    }


}