package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.fakedata.FakeIntValueDao
import com.bonsai.sciencetodo.fakedata.FakeObservationDao
import com.bonsai.sciencetodo.fakedata.FakeStringValueDao
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.ui.datavalues.NewDataBox
import com.bonsai.sciencetodo.ui.datavalues.NewInteger
import com.bonsai.sciencetodo.ui.datavalues.NewString
import kotlinx.coroutines.flow.Flow
import java.time.Instant

class ObservationRepository(
    private val observationDao: ObservationDao,
    private val stringValueDao: StringValueDao,
    private val intValueDao: IntValueDao,
) {
    suspend fun createObservation(dataFlowId: Int, newDataBoxes: List<NewDataBox>) {
        val observation = createObservation(dataFlowId)

        newDataBoxes.forEach {
            insertNewDataValue(observation.id, it)
        }
    }

    fun getObservationCount(dataFlowId: Int): Flow<Int> {
        return observationDao.getCountByDataFlowId(dataFlowId)
    }

    private suspend fun createObservation(dataFlowId: Int): Observation {
        val observation = Observation(0, dataFlowId, Instant.now())
        val id = observationDao.insert(observation)
        return observation.copy(id = id.toInt())
    }

    private suspend fun insertNewDataValue(observationId: Int, newDataBox: NewDataBox) {
        if (!newDataBox.isValid()) throw IllegalArgumentException("Invalid data")
        val variable = newDataBox.variable

        when (newDataBox) {
            is NewInteger -> {
                val value = checkNotNull(newDataBox.value)
                val dataValue = IntValue(0, variable.id, observationId, value)
                intValueDao.insert(dataValue)
            }

            is NewString -> {
                val value = checkNotNull(newDataBox.value)
                val dataValue = StringValue(0, variable.id, observationId, value)
                stringValueDao.insert(dataValue)
            }
        }
    }

    companion object {
        fun getFake(): ObservationRepository {
            return ObservationRepository(
                FakeObservationDao(),
                FakeStringValueDao(),
                FakeIntValueDao()
            )
        }
    }
}