package com.example.amphibians.fake

import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.network.AmphibianPhoto

class FakeNetworkAmphibiansRepository : AmphibiansRepository {

    override suspend fun getAmphibians(): List<AmphibianPhoto> {
        return FakeDataSource.photosList
    }

}
