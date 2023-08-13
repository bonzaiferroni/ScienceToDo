package com.bonsai.sciencetodo.fakedata

import com.bonsai.sciencetodo.data.StringValueDao
import com.bonsai.sciencetodo.fakedata.FakeData.fakeStringValues
import com.bonsai.sciencetodo.model.StringValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeStringValueDao : StringValueDao {
    override fun getAll(): Flow<List<StringValue>> {
        return flowOf(fakeStringValues)
    }

    override fun getById(id: Int): Flow<StringValue> {
        return flowOf(fakeStringValues.first { it.id == id })
    }

    override fun getByObservationId(id: Int): Flow<List<StringValue>> {
        return flowOf(fakeStringValues.filter { it.observationId == id })
    }

    override fun getByVariableId(id: Int): Flow<List<StringValue>> {
        return flowOf(fakeStringValues.filter { it.variableId == id })
    }

    override fun getCountByVariableId(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(stringValue: StringValue) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(stringValues: List<StringValue>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(stringValue: StringValue) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(stringValue: StringValue) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}