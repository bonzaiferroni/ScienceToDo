package com.bonsai.sciencetodo.ui.observation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.model.Variable
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
    private val variableDao: VariableDao,
) : ViewModel() {
    private val datasetId: Int = checkNotNull(savedStateHandle[AppScreens.datasetIdArg])

    private val _uiState = MutableStateFlow(ObservationState())
    val uiState: StateFlow<ObservationState> = _uiState

    init {
        viewModelScope.launch {
            variableDao.getByFlowId(datasetId).collect { variables ->
                _uiState.value = _uiState.value.copy(
                    variables = variables,
                    newValueBoxes = variables.map { NewValueBox.getBox(it) }
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
}

data class ObservationState(
    val newValueBoxes: List<NewValueBox> = emptyList(),
    val variables: List<Variable> = emptyList(),
    val enableAccept: Boolean = false,
)