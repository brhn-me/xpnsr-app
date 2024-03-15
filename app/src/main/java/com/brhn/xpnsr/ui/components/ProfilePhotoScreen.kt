package com.brhn.xpnsr.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.brhn.xpnsr.models.ProfileViewModel
import com.brhn.xpnsr.ui.activities.ProfilePhotoActivity

@Composable
fun ProfilePhotoScreen(viewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val profileImageUri = viewModel.profileImageUri.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { (context as? ProfilePhotoActivity)?.selectImage() }) {
            Text("Select Profile Picture", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        profileImageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Black, CircleShape)
            )
        }
    }
}
