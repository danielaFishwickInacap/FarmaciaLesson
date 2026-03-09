package com.kotlinpl.farmacialesson.ui.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotlinpl.farmacialesson.domain.Drugstore

@Composable
fun DrugstoreList(
    drugstores: List<Drugstore>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(drugstores.size) { index ->
            Drugstore(drugstore = drugstores[index])
        }
    }

    Log.d("DrugstoreList", "Drugstores: $drugstores")
}
