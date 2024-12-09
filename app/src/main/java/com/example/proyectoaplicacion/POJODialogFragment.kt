package com.example.proyectoaplicacion

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.proyectoaplicacion.databinding.FragmentPojoDialogBinding

class POJODialogFragment(
    private val pojo: POJO?,
    private val onSave: (POJO) -> Unit
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
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.imageView.setImageURI(selectedImageUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1001
    }
}

