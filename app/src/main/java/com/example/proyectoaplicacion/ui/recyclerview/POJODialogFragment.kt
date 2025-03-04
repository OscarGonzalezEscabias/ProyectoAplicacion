package com.example.proyectoaplicacion.ui.recyclerview

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.proyectoaplicacion.R
import com.example.proyectoaplicacion.databinding.FragmentPojoDialogBinding
import com.example.proyectoaplicacion.domain.model.Review
import java.io.ByteArrayOutputStream

class POJODialogFragment(
    private val review: Review?,
    private val onSave: (Review) -> Unit
) : DialogFragment() {

    private var _binding: FragmentPojoDialogBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPojoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        review?.let {
            binding.titleEditText.setText(it.title)
            binding.descriptionEditText.setText(it.description)
            binding.imageView.setImageResource(it.id)
        }

        binding.selectImageButton.setOnClickListener {
            checkMediaPermission()
        }

        binding.captureImageButton.setOnClickListener {
            checkCameraPermission()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val description = binding.descriptionEditText.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val imageBase64 = selectedImageUri?.let { uri ->
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                        bitmapToBase64(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

                val newReview = review?.copy(
                    title = title,
                    description = description,
                    id = selectedImageUri?.let { R.drawable.custom_placeholder } ?: review.id,
                    image = imageBase64
                ) ?: Review(R.drawable.imagen_placeholder, title, description, imageBase64)

                onSave(newReview)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                binding.imageView.setImageURI(uri)
            }
        }
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        imagePickerLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestMediaPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openImageSelector()
        } else {
            Toast.makeText(requireContext(), "Permiso de acceso a medios denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openImageSelector()
        } else {
            Toast.makeText(requireContext(), "Permiso de acceso a medios denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            openCamera()
        }
    }

    private fun checkMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openImageSelector()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Permiso requerido")
                        .setMessage("Necesitamos acceso a tus imágenes para que puedas seleccionar una.")
                        .setPositiveButton("Aceptar") { _, _ ->
                            requestMediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                } else {
                    requestMediaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImageSelector()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Permiso requerido")
                        .setMessage("Necesitamos acceso a tus imágenes para que puedas seleccionar una.")
                        .setPositiveButton("Aceptar") { _, _ ->
                            requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                } else {
                    requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(imageBitmap)
            selectedImageUri = saveImageToGallery(imageBitmap)
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun saveImageToGallery(bitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            val outputStream = requireContext().contentResolver.openOutputStream(it)
            outputStream?.use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
        }
        return uri
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}