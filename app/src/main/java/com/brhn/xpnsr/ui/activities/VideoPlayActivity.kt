package com.brhn.xpnsr.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.brhn.xpnsr.ui.components.VideoScreen

class VideoPlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Replace with your actual video URL
            val videoUrl =
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            VideoScreen(videoUri = videoUrl)
        }
    }
}