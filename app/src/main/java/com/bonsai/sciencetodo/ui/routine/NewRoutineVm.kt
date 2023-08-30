package com.bonsai.sciencetodo.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.IntervalUnit
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.RoutineDao
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.Routine
import com.bonsai.sciencetodo.ui.observation.pickerNullText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NewRoutineVm @Inject constructor(
    private val datasetDao: DatasetDao,
    private val routineDao: RoutineDao,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewRoutineState())
    val uiState = _uiState.asStateFlow()

    private var _datasets: List<Dataset> = emptyList()

    init {
        viewModelScope.launch {
            datasetDao.getAll().collect { datasets ->
                _datasets = datasets
                _uiState.value = uiState.value.copy(
                    datasetSuggestions = listOf(pickerNullText) + datasets.map { it.name },
                )
            }
        }
    }

    fun setDatasetText(text: String) {
        val currentState = uiState.value
        val dataset = _datasets.firstOrNull { it.name.equals(text, true)}
        _uiState.value = uiState.value.copy(
            dataset = dataset,
            datasetText = if (text == pickerNullText) currentState.datasetText else text,
            datasetPickerState = dataset?.name ?: currentState.datasetPickerState,
            enableAccept = dataset != null,
        )
    }

    fun setBaseTime(localTime: LocalTime) {
        _uiState.value = uiState.value.copy(
            baseTime = localTime
        )
    }

    fun setInterval(interval: Int) {
        _uiState.value = uiState.value.copy(
            intervalPickerState = interval
        )
    }

    fun setIntervalUnit(intervalUnit: IntervalUnit) {
        _uiState.value = uiState.value.copy(
            unitPickerState = intervalUnit
        )
    }

    fun saveObservation(onSuccess: () -> Boolean?) {
        val dataset = uiState.value.dataset ?: throw IllegalStateException("dataset is null")
        val baseTime = uiState.value.baseTime
        val intervalSeconds = uiState.value.intervalPickerState

        val routine = Routine(
            0, dataset.id, baseTime, intervalSeconds
        )

        viewModelScope.launch {
            routineDao.insert(routine)
            onSuccess()
        }
    }
}

data class NewRoutineState(
    val datasetText: String = "",
    val datasetPickerState: String = pickerNullText,
    val datasetSuggestions: List<String> = listOf(pickerNullText),
    val dataset: Dataset? = null,
    val baseTime: LocalTime = LocalTime.of(0, 0),
    val intervalPickerState: Int = 1,
    val unitPickerState: IntervalUnit = IntervalUnit.Hour,
    val enableAccept: Boolean = false,
)
