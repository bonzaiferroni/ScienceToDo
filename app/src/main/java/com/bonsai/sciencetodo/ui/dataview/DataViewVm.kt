package com.bonsai.sciencetodo.ui.dataview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataViewVm(
    savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    val dataFlowId: Int =
        checkNotNull(savedStateHandle[AppScreens.dataFlowIdArg])

    private val _uiState = MutableStateFlow(DataViewState())
    val uiState: StateFlow<DataViewState> = _uiState

    init {
        viewModelScope.launch {
            dataRepository.dataFlowDao.getById(dataFlowId).collect {
                _uiState.value = _uiState.value.copy(dataFlow = it)
            }
        }
        viewModelScope.launch {
            val dataTableContent = dataRepository.getTableContent(dataFlowId)
            _uiState.value = _uiState.value.copy(
                dataTableContent = dataTableContent
            )
        }
    }
}

data class DataViewState(
    val dataFlow: DataFlow = DataFlow(0, "404"),
    val dataTableContent: DataTableContent? = null
)
