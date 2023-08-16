package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.EnumeratorDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeEnumerators
import com.bonsai.sciencetodo.model.Enumerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeEnumeratorDao : EnumeratorDao {

    override fun getAll(): Flow<List<Enumerator>> {
        return flowOf(fakeEnumerators)
    }

    override fun getById(id: Int): Flow<Enumerator> {
        return flowOf(fakeEnumerators[0])
    }

    override fun getByName(name: String): Flow<Enumerator> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(enumerator: Enumerator) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(enumerators: List<Enumerator>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(enumerator: Enumerator) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(enumerator: Enumerator) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}