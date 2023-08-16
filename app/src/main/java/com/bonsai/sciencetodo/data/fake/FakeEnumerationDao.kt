package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.EnumerationDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeEnumerations
import com.bonsai.sciencetodo.model.Enumeration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeEnumerationDao : EnumerationDao {

    override fun getAll(): Flow<List<Enumeration>> {
        return flowOf(fakeEnumerations)
    }

    override fun getById(id: Int): Flow<Enumeration> {
        return flowOf(fakeEnumerations[0])
    }

    override fun getByName(name: String): Flow<Enumeration> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(enumeration: Enumeration) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(enumerations: List<Enumeration>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(enumeration: Enumeration) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(enumeration: Enumeration) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}