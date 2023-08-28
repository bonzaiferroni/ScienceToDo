package com.bonsai.sciencetodo.ui.observation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.data.EnumRepository
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
    private val enumRepository: EnumRepository,
) : ViewModel() {
    private val datasetId: Int = checkNotNull(savedStateHandle[AppScreens.datasetIdArg])

    private val _uiState = MutableStateFlow(ObservationState())
    val uiState: StateFlow<ObservationState> = _uiState

    private val newValues: MutableMap<Int, NewValue> = mutableMapOf()

    private val newValueStateManager = NewValueStateManager(
        observationRepository = observationRepository,
        dataRepository = dataRepository,
        enumRepository = enumRepository
    )

    init {
        viewModelScope.launch {
            dataRepository.getVariablesByDatasetId(datasetId).collect { variables ->
                newValues.clear()
                variables.forEach { variable ->
                    newValues[variable.id] = newValueStateManager.getBox(variable)
                }
                _uiState.value = _uiState.value.copy(
                    newValues = newValues,
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

    fun setValue(newValue: NewValue, text: String) {
        val updatedNewValue = newValueStateManager.setValue(newValue, text)
        newValues[newValue.variable.id] = updatedNewValue
        _uiState.value = uiState.value.copy(
            newValues = newValues,
            enableAccept = newValues.isValid()
        )
    }

    fun saveObservation(callback: () -> Unit) {
        val newValues = _uiState.value.newValues

        viewModelScope.launch {
            observationRepository.createObservation(datasetId, newValues.values.toList())
            callback()
        }
    }
}

data class ObservationState(
    val dataset: Dataset = Dataset(0, ""),
    val newValues: Map<Int, NewValue> = emptyMap(),
    val enableAccept: Boolean = false,
)