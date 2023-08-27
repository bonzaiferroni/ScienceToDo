package com.bonsai.sciencetodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.ui.observation.NewValueBox
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
}

data class HomeUiState(
    val datasets: List<Dataset> = emptyList(),
    val showDialog: Boolean = false,
    val newDatasetName: String = "",
    val newDataTargetId: Int? = null,
    val showCreateEnum: Boolean = false,
)