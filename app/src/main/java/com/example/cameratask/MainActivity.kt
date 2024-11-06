package com.example.cameratask

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cameratask.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageURL: Uri

    // Intent to open Camera
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        binding.imageView.setImageURI(imageURL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageURL = createImageUri()
        binding.cameraButton.setOnClickListener {
            openCamera()
        }
        binding.shareButton.setOnClickListener {
            sharePicture()
        }
    }

    private fun createImageUri(): Uri {
        val image = File(filesDir, "camera_photos.png")
        return FileProvider.getUriForFile(
            this,
            "com.example.cameratask.MainActivity",
            image
        )
    }

    private fun openCamera() {
        binding.imageView.setImageURI(null)
        contract.launch(imageURL)
    }

    private fun sharePicture() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_SUBJECT, "DigiJED Bahno Oleksandr")
            putExtra(Intent.EXTRA_TEXT, "Link to github - https://github.com/AlexBahno/CameraTask")
            putExtra(Intent.EXTRA_STREAM, imageURL)
        }
        startActivity(intent)
    }
}