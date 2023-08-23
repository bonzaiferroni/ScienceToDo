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
                    enumeratorMap = enumerators
                        .groupBy { it.enumerationId }
                        .mapValues { (_, list) -> list.sortedBy { it.orderIndex } }
                )
            }
        }
    }

    fun deleteEnumeration(enumeration: Enumeration) {
        viewModelScope.launch {
            enumRepository.enumerationDao.delete(enumeration)
        }
    }

    // New Enum
    val newEnumerationFunctions = EditFunctions(
        start = this::startNewEnumeration,
        update = this::updateNewEnumeration,
        clear = this::clearNewEnumeration,
        save = this::saveNewEnumeration,
    )

    private fun startNewEnumeration(unit: Unit) {
        _uiState.value = _uiState.value.copy(
            newEnumeration = NewEnumeration(""),
        )
    }

    private fun clearNewEnumeration() {
        _uiState.value = _uiState.value.copy(
            newEnumeration = null
        )
    }

    private fun saveNewEnumeration() {
        val newEnumeration = _uiState.value.newEnumeration
            ?: throw IllegalStateException("No new enum name to save")
        if (!newEnumeration.isValid)
            throw IllegalStateException("New enum name cannot be blank")

        val enumeration = Enumeration(0, newEnumeration.name)
        viewModelScope.launch {
            enumRepository.enumerationDao.insert(enumeration)
            clearNewEnumeration()
        }
    }

    private fun updateNewEnumeration(name: String) {
        _uiState.value = _uiState.value.copy(
            newEnumeration = NewEnumeration(name),
        )
    }

    // enumeration name edit
    val enumerationNameEditFunctions = EditFunctions(
        start = this::startEnumerationNameEdit,
        update = this::updateEnumerationNameEdit,
        clear = this::clearEnumerationNameEdit,
        save = this::saveEnumerationNameEdit,
    )

    private fun startEnumerationNameEdit(enumeration: Enumeration) {
        _uiState.value = _uiState.value.copy(
            enumerationNameEdit = EnumerationNameEdit(enumeration.id, enumeration.name)
        )
    }

    private fun updateEnumerationNameEdit(name: String) {
        val (id, _) = uiState.value.enumerationNameEdit
            ?: throw IllegalStateException("No edited enumeration")
        _uiState.value = _uiState.value.copy(
            enumerationNameEdit = EnumerationNameEdit(id, name)
        )
    }

    private fun saveEnumerationNameEdit() {
        val editEnumerationName = uiState.value.enumerationNameEdit
            ?: throw IllegalStateException("editEnumerationName is null")
        if (!editEnumerationName.isValid)
            throw IllegalStateException("Invalid EnumerationNameEdit")
        val (id, name) = editEnumerationName
        var enumeration = uiState.value.enumerations
            .firstOrNull { it.id == id }
            ?: throw IllegalStateException("No enumeration with id: $id")
        if (name.isBlank())
            throw IllegalStateException("New name cannot be blank")

        enumeration = enumeration.copy(name = name)

        viewModelScope.launch {
            enumRepository.enumerationDao.update(enumeration)
            clearEnumerationNameEdit()
        }
    }

    private fun clearEnumerationNameEdit() {
        _uiState.value = uiState.value.copy(
            enumerationNameEdit = null
        )
    }

    // enumerator name edit
    val enumeratorNameEditFunctions = EditFunctions(
        start = this::startEnumeratorNameEdit,
        update = this::updateEnumeratorNameEdit,
        clear = this::clearEnumeratorNameEdit,
        save = this::saveEnumeratorNameEdit
    )

    private fun startEnumeratorNameEdit(enumerator: Enumerator) {
        _uiState.value = _uiState.value.copy(
            enumeratorNameEdit = EnumeratorNameEdit(enumerator.id, enumerator.name)
        )
    }

    private fun updateEnumeratorNameEdit(name: String) {
        val (id, _) = uiState.value.enumeratorNameEdit
            ?: throw IllegalStateException("No editing enumerator")

        _uiState.value = _uiState.value.copy(
            enumeratorNameEdit = EnumeratorNameEdit(id, name)
        )
    }

    private fun saveEnumeratorNameEdit() {
        val editEnumeratorName = uiState.value.enumeratorNameEdit
        if (editEnumeratorName?.isValid != true)
            throw IllegalStateException("EditEnumerationName is invalid")
        val (id, name) = editEnumeratorName
        var enumerator = uiState.value.enumeratorMap.values
            .flatten()
            .firstOrNull { it.id == id }
            ?: throw IllegalStateException("No enumerator with id: $id")
        if (name.isBlank())
            throw IllegalStateException("New name cannot be blank")

        enumerator = enumerator.copy(name = name)

        viewModelScope.launch {
            enumRepository.enumeratorDao.update(enumerator)
            clearEnumeratorNameEdit()
        }
    }

    private fun clearEnumeratorNameEdit() {
        _uiState.value = uiState.value.copy(
            enumeratorNameEdit = null
        )
    }

    fun moveEnumerator(enumerator: Enumerator, newOrderIndex: Int) {
        val enumerators = uiState.value.enumeratorMap[enumerator.enumerationId]
            ?: throw IllegalStateException("Enumeration Id ${enumerator.enumerationId} not found")

        val moveUp = newOrderIndex < enumerator.orderIndex

        val updatedEnumerators = enumerators.map { otherEnumerator ->
            val (id, enumerationId, name, orderIndex) = otherEnumerator
            val updatedOrderIndex = if (id == enumerator.id) {
                newOrderIndex
            } else if (orderIndex == newOrderIndex) {
                if (moveUp) {
                    orderIndex + 1
                } else {
                    orderIndex - 1
                }
            } else {
                orderIndex
            }
            Enumerator(id, enumerationId, name, updatedOrderIndex)
        }

        viewModelScope.launch {
            enumRepository.enumeratorDao.updateAll(updatedEnumerators)
        }
    }

    fun deleteEnumerator(enumerator: Enumerator) {
        viewModelScope.launch {
            enumRepository.enumeratorDao.delete(enumerator)
        }
    }

    // new enumerator functions
    val newEnumeratorFunctions = EditFunctions(
        start = this::startNewEnumerator,
        update = this::updateNewEnumerator,
        save = this::saveNewEnumerator,
        clear = this::clearNewEnumerator,
    )

    private fun startNewEnumerator(enumerationId: Int) {
        _uiState.value = _uiState.value.copy(
            newEnumerator = NewEnumerator(enumerationId, "")
        )
    }

    private fun clearNewEnumerator() {
        _uiState.value = _uiState.value.copy(
            newEnumerator = null
        )
    }

    private fun saveNewEnumerator() {
        val newEnumerator = _uiState.value.newEnumerator
        if (newEnumerator?.isValid != true)
            throw IllegalStateException("newEnumerator name is not valid")
        val (enumerationId, name) = newEnumerator
        val enumeratorListSize = uiState.value.enumeratorMap[enumerationId]?.size
            ?: 0

        val enumerator = Enumerator(0, enumerationId, name, enumeratorListSize)

        viewModelScope.launch {
            enumRepository.enumeratorDao.insert(enumerator)
            clearNewEnumerator()
        }
    }

    private fun updateNewEnumerator(name: String) {
        val newEnumerator = _uiState.value.newEnumerator
            ?: throw IllegalStateException("newEnumerator data is null")

        _uiState.value = _uiState.value.copy(
            newEnumerator = newEnumerator.copy(
                name = name,
            )
        )
    }
}

data class EnumState(
    val enumerations: List<Enumeration> = emptyList(),
    val enumeratorMap: Map<Int, List<Enumerator>> = emptyMap(),
    val newEnumeration: NewEnumeration? = null,
    val enumerationNameEdit: EnumerationNameEdit? = null,
    val newEnumerator: NewEnumerator? = null,
    val enumeratorNameEdit: EnumeratorNameEdit? = null,
)

data class NewEnumeration(
    override val name: String
) : ValidateName

data class EnumerationNameEdit(
    val id: Int,
    override val name: String
) : ValidateName

data class NewEnumerator(
    val enumerationId: Int,
    override val name: String,
) : ValidateName

data class EnumeratorNameEdit(
    val id: Int,
    override val name: String
) : ValidateName

data class EditFunctions<T>(
    val start: (T) -> Unit = { },
    val update: (String) -> Unit = { },
    val clear: () -> Unit = { },
    val save: () -> Unit = { },
)

interface ValidateName {
    val name: String

    val isValid: Boolean
        get() = name.isNotBlank()
}