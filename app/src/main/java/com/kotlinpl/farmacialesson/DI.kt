package com.kotlinpl.farmacialesson

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kotlinpl.farmacialesson.data.network.ApiService
import com.kotlinpl.farmacialesson.data.network.NetworkClient
import com.kotlinpl.farmacialesson.data.repository.DrugstoreRepository
import com.kotlinpl.farmacialesson.data.repository.DrugstoreRepositoryImpl
import com.kotlinpl.farmacialesson.view_model.LocationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Dependency Injection
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideHttpClient(): HttpClient {
        return NetworkClient().httpClientAndroid
    }

    @Provides
    fun provideApiService(
        client: HttpClient
    ): ApiService {
        return ApiService(client)
    }

    @Provides
    fun provideDrugstore(
        apiService: ApiService
    ): DrugstoreRepository {
        return DrugstoreRepositoryImpl(apiService)
    }


    @Provides
    fun provideLocationViewModel(
        @ApplicationContext context: Context,
        fusedLocationClient: FusedLocationProviderClient
    ): LocationViewModel = LocationViewModel(
        context,
        fusedLocationClient
    )

    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

}