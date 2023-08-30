package com.bonsai.sciencetodo.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.RoutineDao
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.Routine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineVm @Inject constructor(
    private val routineDao: RoutineDao,
    private val datasetDao: DatasetDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutineState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            routineDao.getAll().collect { routines ->
                _uiState.value = uiState.value.copy(
                    routines = routines.map { routine ->
                        val dataset = datasetDao.getById(routine.datasetId).first()
                        RoutineObjects(
                            routine = routine,
                            dataset = dataset
                        )
                    }
                )
            }
        }
    }
}

data class RoutineState(
    val routines: List<RoutineObjects> = emptyList(),
)

data class RoutineObjects(
    val routine: Routine,
    val dataset: Dataset,
)
