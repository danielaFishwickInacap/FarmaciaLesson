package com.kotlinpl.farmacialesson.data.repository

import com.kotlinpl.farmacialesson.data.model.Drugstore

interface DrugstoreRepository {
    suspend fun getDrugstores(): Result<List<Drugstore>>
}
