package com.bonsai.sciencetodo.ui.dataflowprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataFlowDao
import com.bonsai.sciencetodo.data.VariableDao
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataFlowProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataFlowDao: DataFlowDao,
    private val variableDao: VariableDao
) : ViewModel() {
    private val dataFlowId: Int = checkNotNull(savedStateHandle[AppScreens.DataFlowProfile.dataFlowIdArg])

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
        val variable = Variable(
            id = 0,
            dataFlowId = dataFlowId,
            name = uiState.value.newVariableName
        )
        viewModelScope.launch {
            variableDao.insert(variable)
        }
        _uiState.value = _uiState.value.copy(newVariableName = "")
    }

    fun updateVariableName(name: String) {
        _uiState.value = _uiState.value.copy(newVariableName = name)
    }
}

data class DataFlowProfileUiState(
    val dataFlow: DataFlow = DataFlow(0, "404"),
    val variables: List<Variable> = emptyList(),
    val newVariableName: String = ""
)
