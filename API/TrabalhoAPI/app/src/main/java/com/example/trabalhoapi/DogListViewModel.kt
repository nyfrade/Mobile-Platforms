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

data class DogListState(
    val dogs: List<Dog> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class DogListViewModel : ViewModel() {

    var uiState = mutableStateOf(DogListState())
        private set

    fun fetch() {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://dog.ceo/api/breeds/list/all")
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

                    val result = r.body!!.string()
                    val jsonResult = JSONObject(result)
                    val message = jsonResult.getJSONObject("message")

                    val dogsResult = arrayListOf<Dog>()
                    val breeds = message.keys()
                    for (breedName in breeds) {
                        dogsResult.add(Dog.fromJSON(breedName))
                    }

                    uiState.value = uiState.value.copy(
                        dogs = dogsResult,
                        isLoading = false,
                        error = null
                    )
                }
            }
        })
    }
}