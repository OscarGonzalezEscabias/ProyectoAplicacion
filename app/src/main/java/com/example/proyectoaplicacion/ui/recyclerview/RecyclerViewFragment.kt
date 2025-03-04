package com.example.proyectoaplicacion.ui.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoaplicacion.databinding.FragmentRecyclerViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecyclerViewFragment : Fragment() {

    private lateinit var binding: FragmentRecyclerViewBinding
    private val viewModel: RecyclerViewModel by viewModels()
    private lateinit var adapter: POJOAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePOJOs()
    }

    private fun setupRecyclerView() {
        adapter = POJOAdapter(
            onDeleteClick = { pojo ->
                viewModel.deleteReview(pojo.id)
            },
            onEditClick = { pojo ->
                val dialog = POJODialogFragment(pojo) { updatedPojo ->
                    viewModel.editReview(updatedPojo.id, updatedPojo)
                }
                dialog.show(parentFragmentManager, "EditDialog")
            }
        )
        binding.recyclerViewFragment.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFragment.adapter = adapter

        binding.addButton.setOnClickListener {
            val dialog = POJODialogFragment(null) { newPojo ->
                viewModel.addReview(newPojo)
            }
            dialog.show(parentFragmentManager, "AddDialog")
        }
    }

    private fun observePOJOs() {
        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            adapter.updatePOJOs(reviews)
        }
    }
}