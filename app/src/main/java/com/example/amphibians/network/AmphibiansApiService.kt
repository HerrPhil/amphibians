package com.example.amphibians.network

import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET

// 16th re-factoring: move code for variables BASE_URL, retrofit, and retrofitService
// to DefaultAppContainer

// Here is the base URL of the amphibians RESTful API
// Note that the course instructors re-used the Mars server, from the previous codelabs,
// to deploy this endpoint, as well.
//private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// Add the retrofit builder that will create the generated implementation of the API service.
// Retrofit needs the base URI for the web service and a converter factory to build a web services API.
// The converter tells Retrofit what to do with the data it gets back from the web service.
// 12th re-factoring: use Kotlinx serialization converter
//private val retrofit = Retrofit.Builder()
////    .addConverterFactory(ScalarsConverterFactory.create())
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
//    .baseUrl(BASE_URL)
//    .build()

// Define an interface (AmphibiansApiService) that defines how Retrofit talks to the web server
// using HTTP requests.
interface AmphibiansApiService {
    // Annotation @GET is the http annotation
    // 1st re-factoring: make this a suspend function to service the view model from launch()
    // 13th re-factoring: return a list of photos
    @GET("amphibians")
    suspend fun getAmphibians(): List<AmphibianPhoto>
//    suspend fun getAmphibians(): String
}

// Object declarations are used to declare singleton objects.

// 18th re-factoring: remove this API object/singleton; no longer used
// Use this pattern to expose the API
//object AmphibiansApi {
//    // lazily instantiate retrofit object property. Note: it's a property
//    val retrofitService : AmphibiansApiService by lazy {
//        retrofit.create(AmphibiansApiService::class.java)
//    }
//}
