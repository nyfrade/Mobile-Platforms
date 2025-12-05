package com.example.trabalhoapi.sound

import android.content.Context
import android.media.MediaPlayer
import com.example.trabalhoapi.R

class SoundManager(private val context: Context) {

    private var backgroundMusicPlayer: MediaPlayer? = null

    fun playBackgroundMusic() {
        if (backgroundMusicPlayer == null) {
            backgroundMusicPlayer = MediaPlayer.create(context, R.raw.main).apply {
                isLooping = true
                start()
            }
        } else if (!backgroundMusicPlayer!!.isPlaying) {
            backgroundMusicPlayer!!.start()
        }
    }

    fun playButtonClickSound() {
        MediaPlayer.create(context, R.raw.buttonclick).start()
    }

    fun playFavoriteSound() {
        MediaPlayer.create(context, R.raw.fav).start()
    }

    fun onPause() {
        backgroundMusicPlayer?.pause()
    }

    fun onResume() {
        backgroundMusicPlayer?.start()
    }

    fun onDestroy() {
        backgroundMusicPlayer?.stop()
        backgroundMusicPlayer?.release()
        backgroundMusicPlayer = null
    }
}