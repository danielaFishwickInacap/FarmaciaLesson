package com.kotlinpl.farmacialesson.domain

import com.kotlinpl.farmacialesson.data.model.DrugstoreResponse
import com.kotlinpl.farmacialesson.util.Location

data class Drugstore(
    val id: String,
    val fecha: String,
    val name: String,
    val location: Location
)

fun DrugstoreResponse.toDrugstore(): Drugstore = Drugstore(
    fecha = this.fecha,
    id = this.local_id,
    name = this.local_nombre,
    location = Location(
        lat = this.local_lat.toDouble(),
        long = this.local_lng.toDouble()
    )
)