package ru.test.exovc.ui.views

import android.view.View
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

class AppPlayer(private val view: StyledPlayerView) {

    private val exoPlayer = ExoPlayer.Builder(view.context).build()

    init {
        view.player = exoPlayer.apply {
            repeatMode = Player.REPEAT_MODE_ONE
            addListener(Listener())
        }
    }

    fun play(url: String?) = url?.let {
        exoPlayer.apply {
            setMediaItem(MediaItem.fromUri(it))
            pause()
            prepare()
        }
    }

    inner class Listener : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            if (playbackState == Player.STATE_READY) {
                exoPlayer.play()
                (view.parent as View).isVisible = true
            }
        }
    }
}
