package com.bonsai.sciencetodo.ui.observation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.ui.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObservationVm @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val observationRepository: ObservationRepository,
    private val dataRepository: DataRepository,
) : ViewModel() {
    private val datasetId: Int = checkNotNull(savedStateHandle[AppScreens.datasetIdArg])

    private val _uiState = MutableStateFlow(ObservationState())
    val uiState: StateFlow<ObservationState> = _uiState

    init {
        val onSetValue = this::validateBoxes

        viewModelScope.launch {
            dataRepository.getVariablesByDatasetId(datasetId).collect { variables ->
                _uiState.value = _uiState.value.copy(
                    newValueBoxes = variables.map { variable ->
                        NewValueBox.getBox(variable, onSetValue)
                    }
                )
            }
        }
        viewModelScope.launch {
            dataRepository.getDataset(datasetId).collect {
                _uiState.value = _uiState.value.copy(
                    dataset = it
                )
            }
        }
    }

    fun saveObservation(callback: () -> Unit) {
        val newDataValues = _uiState.value.newValueBoxes

        viewModelScope.launch {
            observationRepository.createObservation(datasetId, newDataValues)
            callback()
        }
    }

    private fun validateBoxes() {
        val uiState = uiState.value
        _uiState.value = uiState.copy (
            enableAccept = uiState.newValueBoxes.isValid(),
        )
    }
}

data class ObservationState(
    val dataset: Dataset = Dataset(0, ""),
    val newValueBoxes: List<NewValueBox> = emptyList(),
    val enableAccept: Boolean = false,
)