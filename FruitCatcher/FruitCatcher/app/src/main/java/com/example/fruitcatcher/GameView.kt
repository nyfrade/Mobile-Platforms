package com.example.fruitcatcher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    private var isPlaying = false
    private var gameThread: Thread? = null
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private lateinit var surfaceHolder: SurfaceHolder

    private lateinit var catcher: Catcher
    private val fruits = ArrayList<Fruit>()
    private var score = 0
    private var lives = 3
    private var touchX = 0f

    private lateinit var backgroundBitmap: Bitmap
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    var onGameOver: ((Int) -> Unit)? = null

    private fun init(context: Context, width: Int, height: Int) {
        this.screenWidth = width
        this.screenHeight = height
        this.touchX = screenWidth / 2f

        surfaceHolder = holder
        paint = Paint()

        catcher = Catcher(context, screenWidth, screenHeight)
        for (i in 0..4) {
            fruits.add(Fruit(context, screenWidth, screenHeight))
        }

        val originalBackground = BitmapFactory.decodeResource(context.resources, R.drawable.fundo)
        backgroundBitmap = Bitmap.createScaledBitmap(originalBackground, screenWidth, screenHeight, false)
    }

    constructor(context: Context, width: Int, height: Int, onGameOver: (Int) -> Unit) : super(context) {
        this.onGameOver = onGameOver
        init(context, width, height)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            control()
        }
    }

    fun resume() {
        isPlaying = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    fun pause() {
        isPlaying = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun update() {
        if (lives <= 0) {
            return
        }
        catcher.update(touchX)
        for (fruit in fruits) {
            if (fruit.update()) {
                lives--
                if (lives <= 0) {
                    isPlaying = false
                    post { onGameOver?.invoke(score) }
                }
                fruit.reset()
            }
            if (Rect.intersects(fruit.collisionBox, catcher.collisionBox)) {
                score++
                fruit.reset()
            }
        }
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas() ?: return
            try {
                canvas.drawBitmap(backgroundBitmap, 0f, 0f, null)
                canvas.drawBitmap(catcher.bitmap, catcher.x.toFloat(), catcher.y.toFloat(), paint)
                for (fruit in fruits) {
                    canvas.drawBitmap(fruit.bitmap, fruit.x.toFloat(), fruit.y.toFloat(), paint)
                }
                paint.color = Color.WHITE
                paint.textSize = 50f
                canvas.drawText("Pontos: $score", 50f, 100f, paint)
                canvas.drawText("Vidas: $lives", screenWidth - 250f, 100f, paint)
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun control() {
        try {
            Thread.sleep(17)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                touchX = event.x
            }
        }
        return true
    }
}
