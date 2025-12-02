package com.example.trabalhoapi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trabalhoapi.models.Dog
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class DogDetailState(
    val dog: Dog? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DogDetailViewModel : ViewModel() {

    var uiState = mutableStateOf(DogDetailState())
        private set

    fun fetch(breedName: String) {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://dog.ceo/api/breed/$breedName/images/random")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { r ->
                    if (!r.isSuccessful) {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Error: ${r.code}"
                        )
                        return
                    }

                    val result = response.body!!.string()
                    val jsonResult = JSONObject(result)

                    val dog = Dog.fromJSON(jsonResult)
                    dog.breedName = breedName

                    uiState.value = uiState.value.copy(
                        dog = dog,
                        isLoading = false,
                        error = null
                    )
                }
            }
        })
    }
}