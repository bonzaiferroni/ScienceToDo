package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.BooleanValueDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeBooleanValues
import com.bonsai.sciencetodo.model.BooleanValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBooleanValueDao : BooleanValueDao {
    override fun getAll(): Flow<List<BooleanValue>> {
        return flowOf(fakeBooleanValues)
    }

    override fun getById(id: Int): Flow<BooleanValue> {
        return flowOf(fakeBooleanValues.first { it.id == id })
    }

    override fun getByObservationId(id: Int): Flow<List<BooleanValue>> {
        return flowOf(fakeBooleanValues.filter { it.observationId == id })
    }

    override fun getByVariableId(id: Int): Flow<List<BooleanValue>> {
        return flowOf(fakeBooleanValues.filter { it.variableId == id })
    }

    override fun getCountByVariableId(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(booleanValue: BooleanValue) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(booleanValues: List<BooleanValue>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(booleanValue: BooleanValue) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(booleanValue: BooleanValue) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}