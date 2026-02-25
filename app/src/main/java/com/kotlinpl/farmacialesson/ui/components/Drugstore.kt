package com.kotlinpl.farmacialesson.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotlinpl.farmacialesson.data.model.Drugstore

@Composable
fun Drugstore(
    drugstore: Drugstore,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = drugstore.local_nombre)
        Text(text = drugstore.fecha)
    }
}