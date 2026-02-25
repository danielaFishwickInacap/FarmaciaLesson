package com.kotlinpl.farmacialesson.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get


class ApiService(private val client: HttpClient = NetworkClient().httpClientAndroid) {
    companion object {
        // Elementos static
        private const val END_POINT = "https://midas.minsal.cl/farmacia_v2/WS/getLocalesTurnos.php"
    }

    suspend fun getDrugstores() = client.get(END_POINT)
}
