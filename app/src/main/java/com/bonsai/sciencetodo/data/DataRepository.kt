package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.dao.BooleanValueDao
import com.bonsai.sciencetodo.dao.DataFlowDao
import com.bonsai.sciencetodo.dao.FloatValueDao
import com.bonsai.sciencetodo.dao.IntValueDao
import com.bonsai.sciencetodo.dao.ObservationDao
import com.bonsai.sciencetodo.dao.StringValueDao
import com.bonsai.sciencetodo.dao.VariableDao
import com.bonsai.sciencetodo.fakedata.FakeBooleanValueDao
import com.bonsai.sciencetodo.fakedata.FakeDataFlowDao
import com.bonsai.sciencetodo.fakedata.FakeFloatValueDao
import com.bonsai.sciencetodo.fakedata.FakeIntValueDao
import com.bonsai.sciencetodo.fakedata.FakeObservationDao
import com.bonsai.sciencetodo.fakedata.FakeStringValueDao
import com.bonsai.sciencetodo.fakedata.FakeVariableDao
import com.bonsai.sciencetodo.model.BaseValue
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.dataview.DataTableContent
import kotlinx.coroutines.flow.first

class DataRepository(
    val dataFlowDao: DataFlowDao,
    val observationDao: ObservationDao,
    val variableDao: VariableDao,
    val stringValueDao: StringValueDao,
    val intValueDao: IntValueDao,
    val floatValueDao: FloatValueDao,
    val booleanValueDao: BooleanValueDao,
) {
    suspend fun getTableContent(dataFlowId: Int): DataTableContent {
        val variables = variableDao.getByFlowId(dataFlowId).first()
        val observations = observationDao.getByFlowId(dataFlowId).first()

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
        }
    }

    companion object {
        fun getFake(): DataRepository {
            return DataRepository(
                FakeDataFlowDao(),
                FakeObservationDao(),
                FakeVariableDao(),
                FakeStringValueDao(),
                FakeIntValueDao(),
                FakeFloatValueDao(),
                FakeBooleanValueDao()
            )
        }
    }
}