package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.ui.datavalues.NewDataValue
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.util.Date

class ObservationManager(
    private val observationDao: ObservationDao,
    private val stringValueDao: StringValueDao,
    private val intValueDao: IntValueDao,
) {
    suspend fun createObservation(dataFlowId: Int, newDataValues: List<NewDataValue>) {
        val observation = createObservation(dataFlowId)

        newDataValues.forEach {
            insertNewDataValue(observation.id, it)
        }
    }

    fun getObservationCount(dataFlowId: Int): Flow<Int> {
        return observationDao.getCountByDataFlowId(dataFlowId)
    }

    private suspend fun createObservation(dataFlowId: Int): Observation {
        val date = Date.from(Instant.now())
        val observation = Observation(0, date, dataFlowId)
        val id = observationDao.insert(observation)
        return observation.copy(id = id.toInt())
    }

    private suspend fun insertNewDataValue(observationId: Int, newDataValue: NewDataValue) {
        val variable = newDataValue.variable
        val value = newDataValue.value ?: throw NullPointerException("new value is null")

        when (value) {
            is Int -> {
                val dataValue = IntValue(0, value, variable.id, observationId)
                intValueDao.insert(dataValue)
            }

            is String -> {
                val dataValue = StringValue(0, value, variable.id, observationId)
                stringValueDao.insert(dataValue)
            }
        }
    }
}