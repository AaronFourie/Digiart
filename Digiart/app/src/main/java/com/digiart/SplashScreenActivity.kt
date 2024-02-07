package com.digiart

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.digiart.ui.login.LoginActivity

// SplashScreenActivity.java

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }

        // Set navigation bar color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.black, theme)
        }
        setContentView(R.layout.activity_splash_screen)
        // Set status bar color to black

        val videoView = findViewById<VideoView>(R.id.videoView)

        // Set the path of the video file (replace "your_video.mp4" with your actual video file)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.digiart_splash_screen

        // Parse the URI and set it to the video view
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Start playing the video
        videoView.start()

        // Delay the transition to the login activity after 1 second
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 3500 milliseconds (3.5 seconds)
    }
}