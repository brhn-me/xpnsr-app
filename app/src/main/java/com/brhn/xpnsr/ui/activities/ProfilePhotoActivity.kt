package com.brhn.xpnsr.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.brhn.xpnsr.models.ProfileViewModel
import com.brhn.xpnsr.ui.components.ProfilePhotoScreen
import java.io.File
import java.io.FileOutputStream

class ProfilePhotoActivity : ComponentActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var pickImageResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        // Define the launcher here, after viewModel has been initialized
        pickImageResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    // This now safely uses viewModel
                    result.data?.data?.let { uri ->
                        val savedUri = saveImageToInternalStorage(uri)
                        savedUri?.let { viewModel.updateProfileImageUri(it) }
                    }
                }
            }

//        setContent {
//            ProfilePhotoScreen(viewModel) {
//                val intent = Intent(Intent.ACTION_PICK)
//                intent.type = "image/*"
//                pickImageResultLauncher.launch(intent)
//            }
//        }
    }

    private fun saveImageToInternalStorage(uri: Uri): Uri? {
        return try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val filename = "profile_picture.png"
            val file = File(getFilesDir(), filename)
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
