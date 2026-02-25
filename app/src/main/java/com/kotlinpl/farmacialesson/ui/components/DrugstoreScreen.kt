package com.kotlinpl.farmacialesson.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlinpl.farmacialesson.view_model.DrugstoreViewModel

@Composable
fun DrugstoreScreen(
    modifier: Modifier = Modifier,
    viewModel: DrugstoreViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    DrugstoreList(
        drugstores = uiState.value.drugstores,
        modifier = modifier
    )
}