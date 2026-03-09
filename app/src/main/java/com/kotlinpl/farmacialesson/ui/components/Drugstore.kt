package com.kotlinpl.farmacialesson.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kotlinpl.farmacialesson.domain.Drugstore

@Composable
fun Drugstore(
    drugstore: Drugstore,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = drugstore.name)
        Text(text = drugstore.fecha)

        Text(text = """
            Location:
            Lat: ${drugstore.location.lat}
            Long: ${drugstore.location.long}
        """.trimIndent())
    }
}