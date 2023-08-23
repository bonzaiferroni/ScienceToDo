package com.bonsai.sciencetodo.ui.enums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Enumerator
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
        viewModelScope.launch {
            enumRepository.enumeratorDao.getAll().collect { enumerators ->
                _uiState.value = _uiState.value.copy(
                    enumeratorMap = enumerators.groupBy { it.enumerationId }
                )
            }
        }
    }

    // New Enum
    fun createNewEnum() {
        _uiState.value = _uiState.value.copy(
            newEnumName = "",
            isValidEnumName = false,
        )
    }

    fun clearNewEnum() {
        updateNewEnum(null)
    }

    fun saveNewEnum() {
        val name = _uiState.value.newEnumName
            ?: throw IllegalStateException("No new enum name to save")
        if (name.isBlank())
            throw IllegalStateException("New enum name cannot be blank")

        val enumeration = Enumeration(0, name)
        viewModelScope.launch {
            enumRepository.enumerationDao.insert(enumeration)
            clearNewEnum()
        }
    }

    fun updateNewEnum(name: String?) {
        _uiState.value = _uiState.value.copy(
            newEnumName = name,
            isValidEnumName = !name.isNullOrBlank()
        )
    }

    // edit enumeration
    fun editEnumeration(enumeration: Enumeration) {
        _uiState.value = _uiState.value.copy(
            editingEnumeration = Pair(enumeration.id, enumeration.name)
        )
    }

    fun editEnumerationName(name: String) {
        val (id, _) = uiState.value.editingEnumeration
            ?: throw IllegalStateException("No edited enumeration")
        _uiState.value = _uiState.value.copy(
            editingEnumerator = Pair(id, name)
        )
    }

    fun saveEditEnumeration() {
        val (id, name) = uiState.value.editingEnumeration
            ?: throw IllegalStateException("No edited enumeration to save")
        var enumeration = uiState.value.enumerations
            .firstOrNull { it.id == id }
            ?: throw IllegalStateException("No enumeration with id: $id")
        if (name.isBlank())
            throw IllegalStateException("New name cannot be blank")

        enumeration = enumeration.copy(name = name)

        viewModelScope.launch {
            enumRepository.enumerationDao.update(enumeration)
            clearEditEnumeration()
        }
    }

    fun clearEditEnumeration() {
        _uiState.value = uiState.value.copy(
            editingEnumeration = null
        )
    }

    // edit Enumerator
    fun editEnumerator(enumerator: Enumerator) {
        _uiState.value = _uiState.value.copy(
            editingEnumerator = Pair(enumerator.id, enumerator.name)
        )
    }

    fun editEnumeratorName(name: String) {
        val (id, _) = uiState.value.editingEnumerator
            ?: throw IllegalStateException("No editing enumerator")

        _uiState.value = _uiState.value.copy(
            editingEnumerator = Pair(id, name)
        )
    }

    fun saveEditEnumerator() {
        val (id, name) = uiState.value.editingEnumerator
            ?: throw IllegalStateException("No editing enumerator to save")
        var enumerator = uiState.value.enumeratorMap.values
            .flatten()
            .firstOrNull { it.id == id }
            ?: throw IllegalStateException("No enumerator with id: $id")
        if (name.isBlank())
            throw IllegalStateException("New name cannot be blank")

        enumerator = enumerator.copy(name = name)

        viewModelScope.launch {
            enumRepository.enumeratorDao.update(enumerator)
            clearEditEnumerator()
        }
    }

    fun clearEditEnumerator() {
        _uiState.value = uiState.value.copy(
            editingEnumerator = null
        )
    }

    fun moveEnumerator(enumerator: Enumerator, index: Int) {
    }

    fun deleteEnumerator(enumerator: Enumerator) {

    }
}

data class EnumState(
    val enumerations: List<Enumeration> = emptyList(),
    val enumeratorMap: Map<Int, List<Enumerator>> = emptyMap(),
    val newEnumName: String? = null,
    val isValidEnumName: Boolean = false,
    val editingEnumeration: Pair<Int, String>? = null,
    val editingEnumerator: Pair<Int, String>? = null,
)