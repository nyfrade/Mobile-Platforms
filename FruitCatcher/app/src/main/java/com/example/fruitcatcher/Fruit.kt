package com.example.fruitcatcher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.Random

public class Fruit {

    lateinit var bitmap: Bitmap
    var x = 0
    var y = 0
    private var speed = 10
    lateinit var collisionBox: Rect

    private var screenWidth = 0
    private var screenHeight = 0

    var generator = Random()

    private val fruitImages = arrayOf(
        R.drawable.apple,
        R.drawable.peach,
        R.drawable.strawberry,
        R.drawable.watermelon
    )

    constructor(context: Context, with: Int, height: Int) {
        this.screenWidth = with
        this.screenHeight = height

        val randomFruit = fruitImages[generator.nextInt(fruitImages.size)]
        val originalBitmap = BitmapFactory.decodeResource(context.resources, randomFruit)
        bitmap = Bitmap.createScaledBitmap(originalBitmap, 150, 150, false)

        collisionBox = Rect(x, y, bitmap.width, bitmap.height)

        reset()
    }

    fun update(): Boolean {
        y += speed
        if (y > screenHeight) {
            return true
        }

        collisionBox.left = x
        collisionBox.top = y
        collisionBox.right = x + bitmap.width
        collisionBox.bottom = y + bitmap.height

        return false
    }

    fun reset() {
        x = generator.nextInt(screenWidth - bitmap.width)
        y = -bitmap.height
        speed = generator.nextInt(10) + 10
    }
}
