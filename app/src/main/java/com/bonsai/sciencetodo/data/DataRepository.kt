package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.data.dao.BooleanValueDao
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.dao.FloatValueDao
import com.bonsai.sciencetodo.data.dao.IntValueDao
import com.bonsai.sciencetodo.data.dao.ObservationDao
import com.bonsai.sciencetodo.data.dao.StringValueDao
import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.data.fake.FakeBooleanValueDao
import com.bonsai.sciencetodo.data.fake.FakeDatasetDao
import com.bonsai.sciencetodo.data.fake.FakeEnumValueDao
import com.bonsai.sciencetodo.data.fake.FakeFloatValueDao
import com.bonsai.sciencetodo.data.fake.FakeIntValueDao
import com.bonsai.sciencetodo.data.fake.FakeObservationDao
import com.bonsai.sciencetodo.data.fake.FakeStringValueDao
import com.bonsai.sciencetodo.data.fake.FakeVariableDao
import com.bonsai.sciencetodo.model.BaseValue
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.dataview.DataTableContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DataRepository(
    val datasetDao: DatasetDao,
    val observationDao: ObservationDao,
    val variableDao: VariableDao,
    val stringValueDao: StringValueDao,
    val intValueDao: IntValueDao,
    val floatValueDao: FloatValueDao,
    val booleanValueDao: BooleanValueDao,
    val enumValueDao: EnumValueDao,
) {
    fun getDataset(id: Int): Flow<Dataset> {
        return datasetDao.getById(id)
    }

    fun getVariablesByDatasetId(id: Int): Flow<List<Variable>> {
        return variableDao.getByDatasetId(id)
    }

    suspend fun getTableContent(datasetId: Int): DataTableContent {
        val variables = variableDao.getByDatasetId(datasetId).first()
        val observations = observationDao.getByFlowId(datasetId).first()

        val variableIds = variables.map { v -> v.id }
        val observationIds = observations.map { o -> o.id }

        val matrix: Array<Array<BaseValue?>> = Array(variables.size) {
            Array(observations.size) { null }
        }
        for (variable in variables) {
            val dataValues = getDataByVariable(variable)
            for (dataValue in dataValues) {
                val column = variableIds.indexOf(dataValue.variableId)
                val row = observationIds.indexOf(dataValue.observationId)
                matrix[column][row] = dataValue
            }
        }
        return DataTableContent(variables, observations, matrix)
    }

    private suspend fun getDataByVariable(variable: Variable): List<BaseValue> {
        return when (variable.type) {
            VariableType.Undefined ->
                throw IllegalArgumentException("variable type not initialized")
            VariableType.Integer ->
                intValueDao.getByVariableId(variable.id).first()
            VariableType.String ->
                stringValueDao.getByVariableId(variable.id).first()
            VariableType.Float ->
                floatValueDao.getByVariableId(variable.id).first()
            VariableType.Boolean ->
                booleanValueDao.getByVariableId(variable.id).first()
            VariableType.Enum ->
                enumValueDao.getByVariableId(variable.id).first()
        }
    }

    companion object {
        fun getFake(): DataRepository {
            return DataRepository(
                FakeDatasetDao(),
                FakeObservationDao(),
                FakeVariableDao(),
                FakeStringValueDao(),
                FakeIntValueDao(),
                FakeFloatValueDao(),
                FakeBooleanValueDao(),
                FakeEnumValueDao()
            )
        }
    }
}