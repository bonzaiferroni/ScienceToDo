package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.ui.datavalues.NewDataValue
import kotlinx.coroutines.flow.Flow
import java.io.InvalidObjectException
import java.time.Instant
import java.util.Date

class DataDaoManager(
    private val observationDao: ObservationDao,
    private val stringValueDao: StringValueDao,
    private val intValueDao: IntValueDao,
) {
    suspend fun createObservation(dataFlowId: Int): Observation {
        val date = Date.from(Instant.now())
        val observation = Observation(0, date, dataFlowId)
        val id = observationDao.insert(observation)
        return observation.copy(id = id)
    }

    suspend fun insertNewDataValue(observation: Observation, newDataValue: NewDataValue) {
        val variable = newDataValue.variable
        val value = newDataValue.value ?: throw NullPointerException("new value is null")

        when (value) {
            is Int -> {
                val dataValue = IntValue(0, value, variable.id, observation.id)
                intValueDao.insert(dataValue)
            }

            is String -> {
                val dataValue = StringValue(0, value, variable.id, observation.id)
                stringValueDao.insert(dataValue)
            }
        }
    }

    fun getDataValueCount(variable: Variable): Flow<Int> {
        return when (variable.type) {
            VariableType.Undefined -> throw InvalidObjectException("no VariableType defined")
            VariableType.Integer -> intValueDao.getCountByVariableId(variable.id)
            VariableType.String -> stringValueDao.getCountByVariableId(variable.id)
        }
    }
}