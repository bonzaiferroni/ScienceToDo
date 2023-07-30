package com.bonsai.sciencetodo.ui.dataflowprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataFlowDao
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataFlowProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataFlowDao: DataFlowDao
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
    }
}

data class DataFlowProfileUiState(
    val dataFlow: DataFlow? = null
)
