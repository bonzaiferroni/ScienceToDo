package com.bonsai.sciencetodo.ui.dataview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.model.BaseValue
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DataViewVm(
    savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    val datasetId: Int =
        checkNotNull(savedStateHandle[AppScreens.datasetIdArg])

    private val _uiState = MutableStateFlow(DataViewState())
    val uiState: StateFlow<DataViewState> = _uiState

    init {
        viewModelScope.launch {
            dataRepository.datasetDao.getById(datasetId).collect {
                _uiState.value = _uiState.value.copy(dataset = it)
            }
        }
        viewModelScope.launch {
            val dataTableContent = dataRepository.getTableContent(datasetId)
            _uiState.value = _uiState.value.copy(
                dataTableContent = dataTableContent
            )
        }
    }

    fun editCell(column: Int, row: Int) {
        val tableContent = uiState.value.dataTableContent
        if (column == 0 || row == 0 || tableContent == null) return

        val baseValue = tableContent.getBaseValue(column, row)
        _uiState.value = _uiState.value.copy(
            editingBaseValue = baseValue
        )
    }
}

data class DataViewState(
    val dataset: Dataset = Dataset(0, "404"),
    val dataTableContent: DataTableContent? = null,
    val editingBaseValue: BaseValue? = null
)
