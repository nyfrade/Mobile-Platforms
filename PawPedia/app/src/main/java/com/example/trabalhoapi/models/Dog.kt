package com.example.trabalhoapi.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import org.json.JSONObject

@Entity(tableName = "dogs")
data class Dog(
    @PrimaryKey
    var breedName: String,
    var imageUrl: String? = null,
    var isFavorite: Boolean = false
) {
    companion object {
        fun fromJSON(breedName: String): Dog {
            return Dog(breedName, "https://dog.ceo/api/breed/$breedName/images/random")
        }

        fun fromJSON(json: JSONObject): Dog {
            val imageUrl = json.getString("message")
            return Dog("", imageUrl)
        }
    }
}

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs")
    suspend fun getAll(): List<Dog>

    @Delete
    suspend fun delete(dog: Dog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dog: Dog)
}