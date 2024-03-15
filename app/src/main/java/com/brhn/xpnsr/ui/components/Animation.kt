package com.brhn.xpnsr.ui.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap

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