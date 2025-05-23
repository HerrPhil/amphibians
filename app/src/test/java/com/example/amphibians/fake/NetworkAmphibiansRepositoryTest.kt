package com.example.amphibians.fake

import com.example.amphibians.data.NetworkAmphibiansRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkAmphibiansRepositoryTest {

    @Test
    fun networkMarsPhotosRepository_getMarsPhotos_verifyPhotoList() = runTest {
        val repository = NetworkAmphibiansRepository(
            amphibiansApiService = FakeAmphibiansApiService()
        )
        assertEquals(FakeDataSource.photosList, repository.getAmphibians())
    }
}