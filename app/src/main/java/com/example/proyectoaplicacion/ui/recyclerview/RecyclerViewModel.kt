package com.example.proyectoaplicacion.ui.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoaplicacion.domain.model.Review
import com.example.proyectoaplicacion.domain.usecase.AddPOJOUseCase
import com.example.proyectoaplicacion.domain.usecase.DeletePOJOUseCase
import com.example.proyectoaplicacion.domain.usecase.EditPOJOUseCase
import com.example.proyectoaplicacion.domain.usecase.GetPOJOsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecyclerViewModel @Inject constructor(
    private val getPOJOsUseCase: GetPOJOsUseCase,
    private val addPOJOUseCase: AddPOJOUseCase,
    private val deletePOJOUseCase: DeletePOJOUseCase,
    private val editPOJOUseCase: EditPOJOUseCase
) : ViewModel() {

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    init {
        loadReviews()
    }

    private fun loadReviews() {
        viewModelScope.launch {
            _reviews.value = getPOJOsUseCase.execute()
        }
    }

    fun addReview(review: Review) {
        viewModelScope.launch {
            addPOJOUseCase.execute(review)
            loadReviews()
        }
    }

    fun editReview(id: Int, review: Review) {
        viewModelScope.launch {
            editPOJOUseCase.execute(id, review)
            loadReviews()
        }
    }

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            deletePOJOUseCase.execute(id)
            loadReviews()
        }
    }
}