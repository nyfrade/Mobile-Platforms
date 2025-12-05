package com.example.fruitcatcher

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(@ApplicationContext private val context: Context) {

    private var soundPool: SoundPool
    private var mediaPlayer: MediaPlayer? = null
    private var currentMusicResId: Int? = null

    private var buttonClickSoundId: Int = 0
    private var collectFruitSoundId: Int = 0

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        loadSounds()
    }

    private fun loadSounds() {
        buttonClickSoundId = soundPool.load(context, R.raw.buttonclick, 1)
        collectFruitSoundId = soundPool.load(context, R.raw.collectfruit, 1)
    }

    fun playButtonClick() {
        soundPool.play(buttonClickSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun playCollectFruit() {
        soundPool.play(collectFruitSoundId, 1f, 1f, 0, 0, 1f)
    }

    fun playMenuMusic() {
        playMusic(R.raw.mainmenu)
    }

    fun playGameMusic() {
        playMusic(R.raw.gamemusic)
    }

    private fun playMusic(resId: Int) {
        if (currentMusicResId == resId && mediaPlayer?.isPlaying == true) return

        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, resId).apply {
            isLooping = true
            start()
        }
        currentMusicResId = resId
    }

    fun stopMusic() {
        mediaPlayer?.stop()
    }

    fun release() {
        soundPool.release()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
