package com.brhn.xpnsr.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap


class AnimationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimatedScreen()
        }
    }
}

enum class AnimationType {
    FadeInOut,
    Rotate,
    SlideInOut
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun AnimatedIcon(animationType: AnimationType) {
    val context = LocalContext.current
    val appIconBitmap =
        context.packageManager.getApplicationIcon(context.packageName).toBitmap().asImageBitmap()

    // For rotation
    val rotateAnimation by animateFloatAsState(
        targetValue = if (animationType == AnimationType.Rotate) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // For slide in/out
    val slideAnimation by animateDpAsState(
        targetValue = if (animationType == AnimationType.SlideInOut) 100.dp else 0.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val alpha by if (animationType == AnimationType.FadeInOut) {
        rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    } else mutableFloatStateOf(1f)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = appIconBitmap,
            contentDescription = "App Icon",
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(alpha)
                .rotate(rotateAnimation)
                .offset(y = slideAnimation)
        )
    }
}

@Composable
fun AnimatedScreen() {
    var animationType by remember { mutableStateOf<AnimationType?>(null) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { animationType = AnimationType.FadeInOut }) {
                Text("Fade In/Out")
            }
            Button(onClick = { animationType = AnimationType.Rotate }) {
                Text("Rotate")
            }
            Button(onClick = { animationType = AnimationType.SlideInOut }) {
                Text("Slide In/Out")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        animationType?.let {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedIcon(animationType = it)
            }
        }
    }
}