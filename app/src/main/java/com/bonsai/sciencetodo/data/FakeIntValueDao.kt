package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.IntValue
import kotlinx.coroutines.flow.Flow

class FakeIntValueDao : IntValueDao {
    override fun getAll(): Flow<List<IntValue>> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Flow<IntValue> {
        TODO("Not yet implemented")
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