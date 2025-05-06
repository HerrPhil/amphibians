package com.example.amphibians.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.data.NetworkAmphibiansRepository
import com.example.amphibians.network.AmphibianPhoto
//import com.example.amphibians.network.AmphibiansApi
import kotlinx.coroutines.launch
import java.io.IOException

// Sealing this interface makes it exhaustive to check only these 3 values in a when statement.
// 4th re-factoring: define the UI state of amphibians, relative to status of the web request
sealed interface AmphibiansUiState {
//    data class Success(val amphibians: String) : AmphibiansUiState
    // 27th re-factoring: allow Success to accept a photo
//    data class Success(val amphibians: AmphibianPhoto) : AmphibiansUiState
    // 30th re-factoring: allow Success to accept the list of photos
    data class Success(val amphibians: List<AmphibianPhoto>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}

// 20th re-factoring: add private constructor parameter for dependency injection repository
class AmphibiansViewModel(private var amphibiansRepository: AmphibiansRepository) : ViewModel() {

    /**
     * The mutable State that stores the status of the most recent request.
     */
//    var amphibiansUiState: String by mutableStateOf("")
    // 5th re-factoring: change the type to AmphibiansUiState and default as Loading
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    /**
     * Call getAmphibians() on init so we can display immediately
     */
    init {
        getAmphibians()
    }

    /**
     * Get Amphibians information from the Amphibians API Retrofit service and
     * updates the [Amphibians] [List] [MutableList].
     */
    fun getAmphibians() {
//        amphibiansUiState = "Set the Amphibians API status response here!"
        // 2nd re-factoring: replace with getAmphibians() of the API service
        viewModelScope.launch {
            // 3rd re-factoring: handle network unavailability and errors; try/catch
            // 8th re-factoring: lift amphibiansUiState assignment to here
            amphibiansUiState = try {
//                val listResult = AmphibiansApi.retrofitService.getAmphibians()
                // 15th re-factoring: access API service using repository
//                val amphibiansRepository = NetworkAmphibiansRepository()
                // 23th re-factoring: remove the previous line; repository set in constructor using dependency injection
                val listResult = amphibiansRepository.getAmphibians()
                // 25th re-factoring: add processing the image with COIL; start with 1 one image
                val result = amphibiansRepository.getAmphibians()[0]
//                amphibiansUiState = listResult
                // 6th re-factoring: set to value of Success
//                amphibiansUiState = AmphibiansUiState.Success(listResult)
//                AmphibiansUiState.Success(listResult)
                // 14th re-factoring: use size of the list in a String result
//                AmphibiansUiState.Success("${listResult.size} Amphibian photos retrieved")
//                AmphibiansUiState.Success("First amphibian image URL to process: ${result.imgSrc}")
                // 28th re-factoring: set the newly accepted one photo of Success
//                AmphibiansUiState.Success(result)
                // 31st re-factoring: set the newly accepted many photos of Success
                AmphibiansUiState.Success(listResult)
            } catch (e: IOException) {
                // 7th re-factoring: handle the error state
//                amphibiansUiState = AmphibiansUiState.Error
                AmphibiansUiState.Error
            }
        }
    }

    // 21st re-factoring: use factory pattern to pass values to the view model constructor
    // The Android framework does not allow a "ViewModel" to be passed values in the constructor when created.
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }

}
