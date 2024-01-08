package com.dlyagoogleplay.speed

import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dlyagoogleplay.speed.ui.theme.SpeedTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val videoUri =
                Uri.parse("android.resource://com.dlyagoogleplay.speed/raw/video")

            MaterialTheme {
                Column() {
                    YouTubePlayer(
                        youTubeVideoId = "Aey3_l-nyV0",
                        lifecycleOwner = LocalLifecycleOwner.current
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    VideoPlayer(videoUri = videoUri)
                }


            }
        }
    }
}

