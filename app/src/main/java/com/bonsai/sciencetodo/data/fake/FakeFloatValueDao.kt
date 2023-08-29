package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.FloatValueDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeFloatValues
import com.bonsai.sciencetodo.model.FloatValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFloatValueDao : FloatValueDao {
    override fun getAll(): Flow<List<FloatValue>> {
        return flowOf(fakeFloatValues)
    }

    override fun getById(id: Int): Flow<FloatValue> {
        return flowOf(fakeFloatValues.first { it.id == id })
    }

    override fun getByObservationId(id: Int): Flow<List<FloatValue>> {
        return flowOf(fakeFloatValues.filter { it.observationId == id })
    }

    override fun getByVariableId(id: Int): Flow<List<FloatValue>> {
        return flowOf(fakeFloatValues.filter { it.variableId == id })
    }

    override fun getDistinctByVariableId(id: Int, count: Int): Flow<List<Float>> {
        TODO("Not yet implemented")
    }

    override fun getCountByVariableId(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(floatValue: FloatValue) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(floatValues: List<FloatValue>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(floatValue: FloatValue) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(floatValue: FloatValue) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}