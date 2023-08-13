package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.StringValue
import kotlinx.coroutines.flow.Flow

class FakeStringValueDao : StringValueDao {
    override fun getAll(): Flow<List<StringValue>> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Flow<StringValue> {
        TODO("Not yet implemented")
    }

    override fun getByObservationId(id: Int): Flow<List<StringValue>> {
        TODO("Not yet implemented")
    }

    override fun getByVariableId(id: Int): Flow<List<StringValue>> {
        TODO("Not yet implemented")
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