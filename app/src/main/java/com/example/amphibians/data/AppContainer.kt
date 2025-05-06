package com.example.amphibians.data

import com.example.amphibians.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibiansRepository: AmphibiansRepository
}

class DefaultAppContainer : AppContainer {

    // Here is the base URL of the amphibians RESTful API
    // Note that the course instructors re-used the Mars server, from the previous codelabs,
    // to deploy this endpoint, as well.
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

    // Add the retrofit builder that will create the generated implementation of the API service.
    // Retrofit needs the base URI for the web service and a converter factory to build a web services API.
    // The converter tells Retrofit what to do with the data it gets back from the web service.
    // 12th re-factoring: use Kotlinx serialization converter
    private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    // lazily instantiate retrofit object property. Note: it's a property
    private val retrofitService : AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java)
    }

    override val amphibiansRepository: AmphibiansRepository by lazy {
        NetworkAmphibiansRepository(retrofitService)
    }

}