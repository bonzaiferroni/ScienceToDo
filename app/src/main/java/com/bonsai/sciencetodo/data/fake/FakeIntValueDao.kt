package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.IntValueDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeIntValues
import com.bonsai.sciencetodo.model.IntValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeIntValueDao : IntValueDao {
    override fun getAll(): Flow<List<IntValue>> {
        return flowOf(fakeIntValues)
    }

    override fun getById(id: Int): Flow<IntValue> {
        return flowOf(fakeIntValues.first { it.id == id })
    }

    override fun getByObservationId(id: Int): Flow<List<IntValue>> {
        return flowOf(fakeIntValues.filter { it.observationId == id })
    }

    override fun getByVariableId(id: Int): Flow<List<IntValue>> {
        return flowOf(fakeIntValues.filter { it.variableId == id })
    }

    override fun getByVariableId(id: Int, count: Int): Flow<List<IntValue>> {
        TODO("Not yet implemented")
    }

    override fun getCountByVariableId(id: Int): Flow<Int> {
        return flowOf(fakeIntValues.filter { it.variableId == id }.size)
    }

    override suspend fun insert(intValue: IntValue) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(intValues: List<IntValue>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(intValue: IntValue) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(intValue: IntValue) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}