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
import com.bonsai.sciencetodo.ui.observation.NewBoolean
import com.bonsai.sciencetodo.ui.observation.NewEnum
import com.bonsai.sciencetodo.ui.observation.NewFloat
import com.bonsai.sciencetodo.ui.observation.NewInteger
import com.bonsai.sciencetodo.ui.observation.NewString
import com.bonsai.sciencetodo.ui.observation.NewValue
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
    suspend fun createObservation(datasetId: Int, newValues: List<NewValue>) {
        val observation = createObservation(datasetId)

        newValues.forEach {
            insertNewDataValue(observation.id, it)
        }
    }

    fun getObservationCount(datasetId: Int): Flow<Int> {
        return observationDao.getCountByDatasetId(datasetId)
    }

    private suspend fun createObservation(datasetId: Int): Observation {
        val observation = Observation(0, datasetId, Instant.now())
        val id = observationDao.insert(observation)
        return observation.copy(id = id.toInt())
    }

    private suspend fun insertNewDataValue(observationId: Int, newValue: NewValue) {
        if (!newValue.isValid()) throw IllegalArgumentException("Invalid data")
        val variable = newValue.variable

        when (newValue) {
            is NewInteger -> {
                val value = checkNotNull(newValue.value)
                val dataValue = IntValue(0, variable.id, observationId, value)
                intValueDao.insert(dataValue)
            }
            is NewString -> {
                val value = checkNotNull(newValue.text)
                val dataValue = StringValue(0, variable.id, observationId, value)
                stringValueDao.insert(dataValue)
            }
            is NewFloat -> {
                val value = checkNotNull(newValue.value)
                val dataValue = FloatValue(0, variable.id, observationId, value)
                floatValueDao.insert(dataValue)
            }
            is NewBoolean -> {
                val value = checkNotNull(newValue.value)
                val dataValue = BooleanValue(0, variable.id, observationId, value)
                booleanValueDao.insert(dataValue)
            }
            is NewEnum -> {
                val value = checkNotNull(newValue.value)
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