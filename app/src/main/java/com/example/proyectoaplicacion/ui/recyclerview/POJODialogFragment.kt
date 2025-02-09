package com.example.proyectoaplicacion.ui.recyclerview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.proyectoaplicacion.R
import com.example.proyectoaplicacion.databinding.FragmentPojoDialogBinding
import com.example.proyectoaplicacion.domain.model.POJO

class POJODialogFragment(
    private val pojo: POJO?,
    private val onSave: (POJO) -> Unit
) : DialogFragment() {

    private var _binding: FragmentPojoDialogBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.imageView.setImageURI(selectedImageUri)
        }
    }

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
        pojo?.let {
            binding.titleEditText.setText(it.title)
            binding.descriptionEditText.setText(it.description)
            binding.imageView.setImageResource(it.imageResId)
        }

        binding.selectImageButton.setOnClickListener {
            openImageSelector()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val description = binding.descriptionEditText.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val newPOJO = pojo?.copy(
                    title = title,
                    description = description,
                    imageResId = selectedImageUri?.let { R.drawable.custom_placeholder } ?: pojo.imageResId
                ) ?: POJO(R.drawable.imagen_placeholder, title, description)

                onSave(newPOJO)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
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
}