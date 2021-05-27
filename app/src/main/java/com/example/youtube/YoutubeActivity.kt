package com.example.youtube

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import java.util.*

const val YOUTUBE_VIDEO_ID="yTZObC3UzAk"
const val YOUTUBE_PLAYLIST="PLgqzPZMnAlw6k3Sk8l7PwOavUi1goxrSu"

class YoutubeActivity : YouTubeBaseActivity(),YouTubePlayer.OnInitializedListener {
    private val TAG = "YouTubeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)
        val layout=findViewById<ConstraintLayout>(R.id.activity_youtube)

//        val layout=layoutInflater.inflate(R.layout.activity_youtube,null) as ConstraintLayout
//        setContentView(layout)

        val playerView= YouTubePlayerView(this)
        playerView.layoutParams=ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT ,ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY),this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG,"onInitialisationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG,"onInitialisationSuccess: youTubePlayer is ${youTubePlayer?.javaClass}")
        Toast.makeText(this,"Initialised youtube player success",Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)
        if (!wasRestored){
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        }else{
            youTubePlayer?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE=0
        if (youTubeInitializationResult?.isUserRecoverableError==true){
            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE).show()
        }else{
            val errorMessage= "There was an error initialising youtube player ($youTubeInitializationResult)"
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show()
        }
    }
    private val playbackEventListener=object: YouTubePlayer.PlaybackEventListener{
        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity,"Video Played", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity,"Video Paused", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity,"Video Stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onBuffering(p0: Boolean) {
            Toast.makeText(this@YoutubeActivity,"Hold On...........", Toast.LENGTH_SHORT).show()
        }

        override fun onSeekTo(p0: Int) {
        }
    }
    private val playerStateChangeListener= object : YouTubePlayer.PlayerStateChangeListener{
        override fun onLoading() {
            Toast.makeText(this@YoutubeActivity,"Wait for a while", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {
            TODO("Not yet implemented")
        }

        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity,"Ad is here!!", Toast.LENGTH_SHORT).show()
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity,"Video Started", Toast.LENGTH_SHORT).show()
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity,"Video Completed", Toast.LENGTH_SHORT).show()        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            TODO("Not yet implemented")
        }
    }
}