package com.brhn.xpnsr.ui.activities

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfilePhotoActivity : BaseActivity() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @Composable
    override fun ScreenContent(modifier: Modifier) {
        //ProfilePhotoScreen(selectedImageUri)
        //ImagePicker(){}

        ImagePickerActivity()
    }

    override fun getAppBarTitle(): String {
        return "Profile Photo"
    }

//    private fun pickImageFromGallery() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, PICK_IMAGE_REQUEST)
//    }

//    @Composable
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImageUri = data.data
//            //imageView.setImageURI(selectedImageUri)
//
//            ProfilePhotoScreen(selectedImageUri)
//        }
//    }

//    @Composable
//    fun ProfilePhotoScreen(selectedImageUri: Uri?) {
//        val context = LocalContext.current
//        val profileImageUri = "" //viewModel.profileImageUri.collectAsState().value
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Button(onClick = { (context as? ProfilePhotoActivity)?.selectImage() }) {
//                Text("Select Profile Picture", fontSize = 16.sp)
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//            profileImageUri?.let { uri ->
//                Image(
//                    painter = rememberImagePainter(selectedImageUri),
//                    contentDescription = "Profile picture",
//                    modifier = Modifier
//                        .size(150.dp)
//                        .clip(CircleShape)
//                        .border(2.dp, Color.Black, CircleShape)
//                )
//            }
//        }
//    }


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

//
//@Composable
//fun ImagePickerActivity() {
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) } // Update this line to use mutable state
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        val context = LocalContext.current
//        val uriHandler = LocalUriHandler.current
//
//        val launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.GetContent(),
//            onResult = { uri ->
//                if (uri != null) {
//                    selectedImageUri = uri // This will now trigger recomposition
//                    println("Photo path: $uri")
//                }
//            }
//        )
//
//        // Display Button to pick image
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text("Pick Image")
//        }
//
//        // Display the picked image
//        selectedImageUri?.let { uri ->
//            Image(
//                painter = rememberImagePainter(uri),
//                contentDescription = "Picked Image",
//                modifier = Modifier.size(200.dp) // You can adjust the size as needed
//            )
//        }
//    }
//}


@Composable
fun ImagePickerActivity() {
    // This state is used to trigger recompositions when the image URI changes
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Remember a launcher for selecting images
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            selectedImageUri = uri
            uri?.let { println("Gallery photo path: $uri") }
        }
    )

    // Remember a launcher for taking pictures
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success: Boolean ->
            if (success) {
                // Photo was saved to the URI provided
                selectedImageUri?.let { uri ->
                    println("Camera photo path: $uri")
                }
            }
        }
    )

    // This function creates a temporary file and returns its URI to be used with camera intents
    fun createImageUri(): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.externalCacheDir
        return try {
            val file = File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir).apply {
                // Save a file path for use with ACTION_VIEW intents
                currentPhotoPath = absolutePath
            }
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } catch (e: IOException) {
            // Error occurred while creating the File
            null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to pick an image from the gallery
        Button(onClick = { galleryLauncher.launch("image/*") }) {
            Text("Pick Image from Gallery")
        }

        // Button to take a photo with the camera
        Button(onClick = {
            // Create a URI where the photo will be saved
            createImageUri()?.let { uri ->
                selectedImageUri = uri // Update the URI where the camera will save the photo
                cameraLauncher.launch(uri) // Launch the camera activity
            }
        }) {
            Text("Take Photo with Camera")
        }

        // Display the selected or taken image
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = "Selected or Taken Image",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}




// Make sure to define a variable to hold the current photo path if needed
private var currentPhotoPath: String? = null


//
//@Composable
//fun CameraCaptureView(modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//    var imageUri by remember { mutableStateOf<Uri?>(null)} // Stores captured image Uri
//
//        val cameraProviderFuture = context.cameraProviderFuture
//        val cameraProvider = cameraProviderFuture.await()
//
//        val previewUseCase = Preview.Builder()
//            .setTargetAspectRatio(4f / 3f) // Adjust aspect ratio as needed
//            .build()
//        val imageCaptureUseCase = ImageCapture.Builder()
//            .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
//            .build()
//
//        CameraPreview(
//            previewUseCase.outputEvents,
//            modifier = modifier,
//            onSizeAvailable = { size ->
//                // Handle preview size changes (optional)
//            }
//        )
//
//        if (imageUri != null) {
//            // Display captured image
//            Image(
//                modifier = Modifier.fillMaxSize(),
//                contentDescription = "Captured Image",
//                contentScale = ContentScale.Crop,
//                bitmap = ImageBitmap.createFromContentResolver(context.contentResolver, imageUri!!)
//            )
//        } else {
//            // Display camera preview or placeholder
//            Button(onClick = {
//                val outputFile = File(
//                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                    "captured_image.jpg"
//                )
//                val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
//                imageCaptureUseCase.takePicture(outputOptions, context.mainExecutor,
//                    object : ImageCapture.OnImageSavedCallback {
//                        override fun onError(exc: ImageCaptureException) {
//                            // Handle capture error
//                            Log.e("CameraCaptureView", "Capture failed:", exc)
//                        }
//
//                        override fun onImageSaved(outputFile: File) {
//                            imageUri = Uri.fromFile(outputFile)
//                        }
//                    })
//            }) {
//                Text("Capture Image")
//            }
//        }
//    }