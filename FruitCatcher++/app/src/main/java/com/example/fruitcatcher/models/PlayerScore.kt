package com.example.fruitcatcher.models

import java.util.Date

data class PlayerScore(
    var docId: String? = null,
    var userId: String? = null,
    var playerName: String? = null, 
    var score: Int = 0,
    var timestamp: Date? = null
)
