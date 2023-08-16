package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.data.dao.BooleanValueDao
import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.dao.FloatValueDao
import com.bonsai.sciencetodo.data.dao.IntValueDao
import com.bonsai.sciencetodo.data.dao.ObservationDao
import com.bonsai.sciencetodo.data.dao.StringValueDao
import com.bonsai.sciencetodo.data.fake.FakeBooleanValueDao
import com.bonsai.sciencetodo.data.fake.FakeEnumValueDao
import com.bonsai.sciencetodo.data.fake.FakeFloatValueDao
import com.bonsai.sciencetodo.data.fake.FakeIntValueDao
import com.bonsai.sciencetodo.data.fake.FakeObservationDao
import com.bonsai.sciencetodo.data.fake.FakeStringValueDao
import com.bonsai.sciencetodo.model.BooleanValue
import com.bonsai.sciencetodo.model.EnumValue
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import kotlinx.coroutines.flow.Flow
import java.time.Instant

class ObservationRepository(
    private val observationDao: ObservationDao,
    private val stringValueDao: StringValueDao,
    private val intValueDao: IntValueDao,
    private val floatValueDao: FloatValueDao,
    private val booleanValueDao: BooleanValueDao,
    private val enumValueDao: EnumValueDao,
) {
    suspend fun createObservation(dataFlowId: Int, newValueBoxes: List<NewValueBox>) {
        val observation = createObservation(dataFlowId)

        newValueBoxes.forEach {
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

    private suspend fun insertNewDataValue(observationId: Int, newValueBox: NewValueBox) {
        if (!newValueBox.isValid()) throw IllegalArgumentException("Invalid data")
        val variable = newValueBox.variable

        when (newValueBox) {
            is NewInteger -> {
                val value = checkNotNull(newValueBox.value)
                val dataValue = IntValue(0, variable.id, observationId, value)
                intValueDao.insert(dataValue)
            }
            is NewString -> {
                val value = checkNotNull(newValueBox.value)
                val dataValue = StringValue(0, variable.id, observationId, value)
                stringValueDao.insert(dataValue)
            }
            is NewFloat -> {
                val value = checkNotNull(newValueBox.value)
                val dataValue = FloatValue(0, variable.id, observationId, value)
                floatValueDao.insert(dataValue)
            }
            is NewBoolean -> {
                val value = checkNotNull(newValueBox.value)
                val dataValue = BooleanValue(0, variable.id, observationId, value)
                booleanValueDao.insert(dataValue)
            }
            is NewEnum -> {
                val value = checkNotNull(newValueBox.value)
                val dataValue = EnumValue(0, variable.id, observationId, value)
                enumValueDao.insert(dataValue)
            }
        }
    }

    companion object {
        fun getFake(): ObservationRepository {
            return ObservationRepository(
                FakeObservationDao(),
                FakeStringValueDao(),
                FakeIntValueDao(),
                FakeFloatValueDao(),
                FakeBooleanValueDao(),
                FakeEnumValueDao()
            )
        }
    }
}