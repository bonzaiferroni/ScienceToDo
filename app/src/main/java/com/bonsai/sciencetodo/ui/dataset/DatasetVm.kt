package com.bonsai.sciencetodo.ui.dataset

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.data.NewValueBox
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.EnumVarJoin
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DatasetVm(
    savedStateHandle: SavedStateHandle,
    private val datasetDao: DatasetDao,
    private val variableDao: VariableDao,
    private val observationRepository: ObservationRepository,
    private val enumRepository: EnumRepository,
) : ViewModel() {
    private val datasetId: Int =
        checkNotNull(savedStateHandle[AppScreens.datasetIdArg])

    private val _uiState = MutableStateFlow(DataProfileState())
    val uiState: StateFlow<DataProfileState> = _uiState

    init {
        viewModelScope.launch {
            datasetDao.getById(datasetId).collect {
                _uiState.value = _uiState.value.copy(dataset = it)
            }
        }

        viewModelScope.launch {
            variableDao.getByFlowId(datasetId).collect {
                _uiState.value = _uiState.value.copy(variables = it)
            }
        }

        viewModelScope.launch {
            observationRepository.getObservationCount(datasetId).collect {
                _uiState.value = _uiState.value.copy(observationCount = it)
            }
        }

        viewModelScope.launch {
            enumRepository.enumerationDao.getAll().collect {
                _uiState.value = _uiState.value.copy(enumerations = it)
            }
        }
    }

    fun removeVariable(variable: Variable) {
        viewModelScope.launch {
            variableDao.delete(variable)
        }
    }

    fun openDataDialog() {
        val newValueBoxes = _uiState.value.variables.map { variable ->
            NewValueBox.getBox(variable)
        }
        _uiState.value = _uiState.value.copy(newValueBoxes = newValueBoxes)
    }

    fun saveDataDialog() {
        val newDataValues = _uiState.value.newValueBoxes
            ?: throw NullPointerException("newDataValues is null")

        viewModelScope.launch {
            observationRepository.createObservation(datasetId, newDataValues)
        }

        _uiState.value = _uiState.value.copy(newValueBoxes = null)
    }

    fun cancelDataDialog() {
        _uiState.value = _uiState.value.copy(newValueBoxes = null)
    }

    // new variable dialog
    private var newVariableState: NewVariable?
        get() = uiState.value.newVariable
        set(newVariable) {
            _uiState.value = uiState.value.copy(newVariable = newVariable)
        }

    inner class NewVariableFunctions {

        fun start() { newVariableState = NewVariable() }

        fun clear() { newVariableState = null }

        fun updateName(name: String) {
            newVariableState = newVariableState?.copy(name = name)
        }

        fun updateType(variableType: VariableType) {
            newVariableState = newVariableState?.copy(variableType = variableType)
        }

        fun updateEnumId(enumerationId: Int?) {
            newVariableState = newVariableState?.copy(enumerationId = enumerationId)
        }

        fun save() {
            val newVariable = newVariableState
                ?: throw IllegalStateException("state cached newVariable is null")

            if (!newVariable.isValid) throw IllegalStateException("newVariable.isValid returned false")

            val (name, variableType, enumerationId) = newVariable

            val variable = Variable(
                id = 0,
                datasetId = datasetId,
                name = name,
                type = variableType,
            )
            viewModelScope.launch {
                val id = variableDao.insert(variable)
                if (enumerationId != null) {
                    val enumVarJoin = EnumVarJoin(id.toInt(), enumerationId)
                    enumRepository.enumVarJoinDao.insert(enumVarJoin)
                }
                clear()
            }
        }
    }
}

data class DataProfileState(
    val dataset: Dataset = Dataset(0, "404"),
    val variables: List<Variable> = emptyList(),
    val newValueBoxes: List<NewValueBox>? = null,
    val newVariable: NewVariable? = null,
    val observationCount: Int = 0,
    val enumerations: List<Enumeration> = emptyList(),
)

data class NewVariable(
    val name: String = "",
    val variableType: VariableType = VariableType.Undefined,
    val enumerationId: Int? = null,
) {
    val isValid: Boolean
        get() {
            if (name.isBlank() || variableType == VariableType.Undefined) return false
            return !(variableType == VariableType.Enum && enumerationId == null)
        }
}
