package com.kotlinpl.farmacialesson.data.repository

import com.kotlinpl.farmacialesson.data.model.Drugstore
import com.kotlinpl.farmacialesson.data.model.ResponseDrugstore
import com.kotlinpl.farmacialesson.data.network.ApiService
import io.ktor.client.call.body

class DrugstoreRepositoryImpl(
    private val apiService: ApiService = ApiService(),
    // Otras dependencias SQLite
) : DrugstoreRepository {
    override suspend fun getDrugstores(): Result<List<Drugstore>> {
        return runCatching {
            apiService.getDrugstores().body<ResponseDrugstore>().data
        }
    }
}