package com.bonsai.sciencetodo.ui.enums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.model.Enumeration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EnumVm(
    private val enumRepository: EnumRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EnumState())
    val uiState: StateFlow<EnumState> = _uiState

    init {
        viewModelScope.launch {
            enumRepository.enumerationDao.getAll().collect {
                _uiState.value = _uiState.value.copy(
                    enumerations = it
                )
            }
        }
    }

    fun openCreateDialog() {
        _uiState.value = _uiState.value.copy(
            newEnumName = ""
        )
    }

    fun closeCreateDialog() {
        _uiState.value = _uiState.value.copy(
            newEnumName = null
        )
    }
}

data class EnumState(
    val enumerations: List<Enumeration> = emptyList(),
    val newEnumName: String? = null
)