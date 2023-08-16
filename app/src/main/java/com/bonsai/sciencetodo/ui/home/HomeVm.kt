package com.bonsai.sciencetodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.dao.DataFlowDao
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.dao.VariableDao
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.datavalues.NewValueBox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeVm(
    private val dataFlowDao: DataFlowDao,
    private val variableDao: VariableDao,
    private val observationRepository: ObservationRepository,
) : ViewModel() {

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
        _uiState.value = _uiState.value.copy(showDialog = false, newDataFlowName = "")
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(newDataFlowName = name)
    }

    fun addDataFlow() = viewModelScope.launch {
        val newFlow = DataFlow(id = 0, name = _uiState.value.newDataFlowName)
        dataFlowDao.insert(newFlow)
        _uiState.value = _uiState.value.copy(showDialog = false, newDataFlowName = "")
    }

    fun openDataDialog(dataFlowId: Int) {
        viewModelScope.launch {
            variableDao.getByFlowId(dataFlowId)
                .map { variables ->
                    variables.map { NewValueBox.getBox(it) }
                }
                .collect {
                    _uiState.value = _uiState.value.copy(
                        newValueBoxes = it,
                        newDataTargetId = dataFlowId
                    )
                }
        }
    }

    fun saveDataDialog() {
        val newValueBoxes = _uiState.value.newValueBoxes
            ?: throw NullPointerException("newDataValues is null")
        val dataFlowId = _uiState.value.newDataTargetId
            ?: throw NullPointerException("newDataTargetId is null")

        viewModelScope.launch {
            observationRepository.createObservation(dataFlowId, newValueBoxes)
        }

        clearNewDataState()
    }

    fun cancelDataDialog() {
        clearNewDataState()
    }

    private fun clearNewDataState() {
        _uiState.value = _uiState.value.copy(
            newValueBoxes = null,
            newDataTargetId = null
        )
    }
}

data class HomeUiState(
    val dataFlows: List<DataFlow> = emptyList(),
    val showDialog: Boolean = false,
    val newDataFlowName: String = "",
    val newValueBoxes: List<NewValueBox>? = null,
    val newDataTargetId: Int? = null
)