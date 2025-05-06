package com.example.amphibians.fake

import com.example.amphibians.network.AmphibianPhoto
import com.example.amphibians.network.AmphibiansApiService

class FakeAmphibiansApiService : AmphibiansApiService {
    override suspend fun getAmphibians(): List<AmphibianPhoto> {
        return FakeDataSource.photosList
    }
}
