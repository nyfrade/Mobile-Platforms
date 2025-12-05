package com.example.fruitcatcher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

public class Catcher {

    lateinit var bitmap: Bitmap
    var x = 0
    var y = 0
    var width = 0
    var height = 0
    lateinit var collisionBox: Rect

    private var screenWidth = 0
    private var screenHeight = 0

    constructor(context: Context, with: Int, height: Int) {
        this.screenWidth = with
        this.screenHeight = height

        val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.catcher)
        val newWidth = 350
        val newHeight = (originalBitmap.height.toFloat() / originalBitmap.width.toFloat() * newWidth).toInt()
        bitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false)

        this.width = bitmap.width
        this.height = bitmap.height

        this.x = screenWidth / 2 - this.width / 2
        this.y = screenHeight - this.height

        collisionBox = Rect(x, y, x + this.width, y + this.height)
    }

    fun update(touchX: Float) {
        x = (touchX - width / 2).toInt()

        if (x < 0) {
            x = 0
        }
        if (x > screenWidth - width) {
            x = screenWidth - width
        }

        collisionBox.left = x
        collisionBox.top = y
        collisionBox.right = x + width
        collisionBox.bottom = y + height
    }
}
