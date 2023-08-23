package com.bonsai.sciencetodo.ui.dataset

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.NewValueBox
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatasetVm(
    savedStateHandle: SavedStateHandle,
    private val datasetDao: DatasetDao,
    private val variableDao: VariableDao,
    private val observationRepository: ObservationRepository,
) : ViewModel() {
    private val datasetId: Int =
        checkNotNull(savedStateHandle[AppScreens.datasetIdArg])

    private val _uiState = MutableStateFlow(DataProfileState())
    val uiState: StateFlow<DataProfileState> = _uiState

    init {
        viewModelScope.launch {
            datasetDao.getById(datasetId).collect {
                _uiState.value = _uiState.value.copy(dataset = it)
            }
        }

        viewModelScope.launch {
            variableDao.getByFlowId(datasetId).collect {
                _uiState.value = _uiState.value.copy(variables = it)
            }
        }

        viewModelScope.launch {
            observationRepository.getObservationCount(datasetId).collect {
                _uiState.value = _uiState.value.copy(observationCount = it)
            }
        }
    }

    fun addVariable() {
        if (_uiState.value.newVariableType == VariableType.Undefined ||
            _uiState.value.newVariableName == ""
        ) return

        val variable = Variable(
            id = 0,
            datasetId = datasetId,
            name = uiState.value.newVariableName,
            type = uiState.value.newVariableType
        )
        viewModelScope.launch {
            variableDao.insert(variable)
        }

        // reset values
        _uiState.value = _uiState.value.copy(
            newVariableName = "",
            newVariableType = VariableType.Undefined,
            showAddVariableDialog = false
        )
    }

    fun updateVariableName(name: String) {
        _uiState.value = _uiState.value.copy(newVariableName = name)
    }

    fun updateVariableType(variableType: VariableType) {
        _uiState.value = _uiState.value.copy(newVariableType = variableType)
    }

    fun removeVariable(variable: Variable) {
        viewModelScope.launch {
            variableDao.delete(variable)
        }
    }

    fun openDataDialog() {
        val newValueBoxes = _uiState.value.variables.map { variable ->
            NewValueBox.getBox(variable)
        }
        _uiState.value = _uiState.value.copy(newValueBoxes = newValueBoxes)
    }

    fun saveDataDialog() {
        val newDataValues = _uiState.value.newValueBoxes
            ?: throw NullPointerException("newDataValues is null")

        viewModelScope.launch {
            observationRepository.createObservation(datasetId, newDataValues)
        }

        _uiState.value = _uiState.value.copy(newValueBoxes = null)
    }

    fun cancelDataDialog() {
        _uiState.value = _uiState.value.copy(newValueBoxes = null)
    }

    fun openAddVariableDialog() {
        _uiState.value = _uiState.value.copy(showAddVariableDialog = true)
    }

    fun cancelAddVariableDialog() {
        _uiState.value = _uiState.value.copy(
            showAddVariableDialog = false,
            newVariableName = "",
            newVariableType = VariableType.Undefined
        )
    }
}

data class DataProfileState(
    val dataset: Dataset = Dataset(0, "404"),
    val variables: List<Variable> = emptyList(),
    val newValueBoxes: List<NewValueBox>? = null,
    val newVariableName: String = "",
    val newVariableType: VariableType = VariableType.Undefined,
    val observationCount: Int = 0,
    val showAddVariableDialog: Boolean = false,
)
