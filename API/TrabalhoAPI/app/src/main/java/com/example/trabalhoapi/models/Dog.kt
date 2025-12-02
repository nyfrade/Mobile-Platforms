package com.example.trabalhoapi.models

import org.json.JSONObject

data class Dog(
    var breedName: String,
    var imageUrl: String? = null
) {
    companion object {
        fun fromJSON(breedName: String): Dog {
            return Dog(breedName, null)
        }

        fun fromJSON(json: JSONObject): Dog {
            val imageUrl = json.getString("message")
            return Dog("", imageUrl)
        }
    }
}
