package com.bonsai.sciencetodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.NewValueBox
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.model.Dataset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVm @Inject constructor(
    private val datasetDao: DatasetDao,
    private val variableDao: VariableDao,
    private val observationRepository: ObservationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            datasetDao.getAll().collect {
                _uiState.value = _uiState.value.copy(datasets = it)
            }
        }
    }

    fun showDialog() {
        _uiState.value = _uiState.value.copy(showDialog = true)
    }

    fun hideDialog() {
        _uiState.value = _uiState.value.copy(showDialog = false, newDatasetName = "")
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(newDatasetName = name)
    }

    fun addDataset() = viewModelScope.launch {
        val newFlow = Dataset(id = 0, name = _uiState.value.newDatasetName)
        datasetDao.insert(newFlow)
        _uiState.value = _uiState.value.copy(showDialog = false, newDatasetName = "")
    }

    fun openDataDialog(datasetId: Int) {
        viewModelScope.launch {
            variableDao.getByFlowId(datasetId)
                .map { variables ->
                    variables.map { NewValueBox.getBox(it) }
                }
                .collect {
                    _uiState.value = _uiState.value.copy(
                        newValueBoxes = it,
                        newDataTargetId = datasetId
                    )
                }
        }
    }

    fun saveDataDialog() {
        val newValueBoxes = _uiState.value.newValueBoxes
            ?: throw NullPointerException("newDataValues is null")
        val datasetId = _uiState.value.newDataTargetId
            ?: throw NullPointerException("newDataTargetId is null")

        viewModelScope.launch {
            observationRepository.createObservation(datasetId, newValueBoxes)
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
    val datasets: List<Dataset> = emptyList(),
    val showDialog: Boolean = false,
    val newDatasetName: String = "",
    val newValueBoxes: List<NewValueBox>? = null,
    val newDataTargetId: Int? = null,
    val showCreateEnum: Boolean = false,
)