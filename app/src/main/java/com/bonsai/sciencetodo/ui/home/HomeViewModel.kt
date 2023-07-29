package com.bonsai.sciencetodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataFlowDao
import com.bonsai.sciencetodo.model.DataFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val dataFlowDao: DataFlowDao) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            dataFlowDao.getAll().collect {
                _uiState.value = _uiState.value.copy(dataFlows = it)
            }
        }
    }

    fun showDialog() {
        _uiState.value = _uiState.value.copy(showDialog = true)
    }

    fun hideDialog() {
        _uiState.value = _uiState.value.copy(showDialog = false, dataFlowName = "")
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(dataFlowName = name)
    }

    fun addDataFlow() = viewModelScope.launch {
        val newFlow = DataFlow(id = 0, name = _uiState.value.dataFlowName)
        dataFlowDao.insert(newFlow)
        _uiState.value = _uiState.value.copy(showDialog = false, dataFlowName = "")
    }
}

data class HomeUiState(
    val dataFlows: List<DataFlow> = emptyList(),
    val showDialog: Boolean = false,
    val dataFlowName: String = ""
)