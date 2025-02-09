package com.example.proyectoaplicacion.ui.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectoaplicacion.domain.model.POJO
import com.example.proyectoaplicacion.domain.usecase.AddPOJOUseCase
import com.example.proyectoaplicacion.domain.usecase.DeletePOJOUseCase
import com.example.proyectoaplicacion.domain.usecase.EditPOJOUseCase
import com.example.proyectoaplicacion.domain.usecase.GetPOJOsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecyclerViewModel @Inject constructor(
    private val getPOJOsUseCase: GetPOJOsUseCase,
    private val addPOJOUseCase: AddPOJOUseCase,
    private val deletePOJOUseCase: DeletePOJOUseCase,
    private val editPOJOUseCase: EditPOJOUseCase
) : ViewModel() {

    private val _pojos = MutableLiveData<List<POJO>>()
    val pojos: LiveData<List<POJO>> get() = _pojos

    init {
        loadPOJOs()
    }

    private fun loadPOJOs() {
        _pojos.value = getPOJOsUseCase.execute()
    }

    fun addPOJO(pojo: POJO) {
        addPOJOUseCase.execute(pojo)
        loadPOJOs()
    }

    fun deletePOJO(pojo: POJO) {
        deletePOJOUseCase.execute(pojo)
        loadPOJOs()
    }

    fun editPOJO(pojo: POJO) {
        editPOJOUseCase.execute(pojo)
        loadPOJOs()
    }
}