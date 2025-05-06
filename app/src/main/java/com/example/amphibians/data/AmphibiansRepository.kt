package com.example.amphibians.data

import com.example.amphibians.network.AmphibianPhoto
//import com.example.amphibians.network.AmphibiansApi
import com.example.amphibians.network.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<AmphibianPhoto>
}

class NetworkAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansRepository {
//    override suspend fun getAmphibians(): List<AmphibianPhoto> {
//        return AmphibiansApi.retrofitService.getAmphibians()
//    }
    // 17th re-factoring: use the dependency injection API service
    override suspend fun getAmphibians(): List<AmphibianPhoto> {
        return amphibiansApiService.getAmphibians()
    }
}
