package com.bonsai.sciencetodo.ui.dataflowprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataDaoManager
import com.bonsai.sciencetodo.data.DataFlowDao
import com.bonsai.sciencetodo.data.VariableDao
import com.bonsai.sciencetodo.data.VariableType
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.datavalues.NewDataValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataFlowProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataFlowDao: DataFlowDao,
    private val variableDao: VariableDao,
    private val dataDaoManager: DataDaoManager,
) : ViewModel() {
    private val dataFlowId: Int =
        checkNotNull(savedStateHandle[AppScreens.DataFlowProfile.dataFlowIdArg])

    private val _uiState = MutableStateFlow(DataFlowProfileUiState())
    val uiState: StateFlow<DataFlowProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            dataFlowDao.getById(dataFlowId).collect {
                _uiState.value = _uiState.value.copy(dataFlow = it)
            }
        }

        viewModelScope.launch {
            variableDao.getByFlowId(dataFlowId).collect {
                _uiState.value = _uiState.value.copy(variables = it)
            }
        }
    }

    fun addVariable() {
        if (_uiState.value.newVariableType == VariableType.Undefined ||
            _uiState.value.newVariableName == ""
        ) return

        val variable = Variable(
            id = 0,
            dataFlowId = dataFlowId,
            name = uiState.value.newVariableName,
            type = uiState.value.newVariableType
        )
        viewModelScope.launch {
            variableDao.insert(variable)
        }

        // reset values
        _uiState.value = _uiState.value.copy(
            newVariableName = "",
            newVariableType = VariableType.Undefined
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
        val newDataValues = _uiState.value.variables.map { variable ->
            NewDataValue(variable)
        }
        _uiState.value = _uiState.value.copy(newDataValues = newDataValues)
    }

    fun saveDataDialog() {
        val newDataValues = _uiState.value.newDataValues
            ?: throw NullPointerException("newDataValues is null")

        viewModelScope.launch {
            val observation = dataDaoManager.createObservation(dataFlowId)

            newDataValues.forEach {
                dataDaoManager.insertNewDataValue(observation, it)
            }
        }

        _uiState.value = _uiState.value.copy(newDataValues = null)
    }

    fun cancelDataDialog() {
        _uiState.value = _uiState.value.copy(newDataValues = null)
    }
}

data class DataFlowProfileUiState(
    val dataFlow: DataFlow = DataFlow(0, "404"),
    val variables: List<Variable> = emptyList(),
    val newVariableName: String = "",
    val newVariableType: VariableType = VariableType.Undefined,
    val newDataValues: List<NewDataValue>? = null
)
