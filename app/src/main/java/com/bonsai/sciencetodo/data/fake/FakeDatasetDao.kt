package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeDatasets
import com.bonsai.sciencetodo.model.Dataset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDatasetDao : DatasetDao {

    override fun getAll(): Flow<List<Dataset>> {
        return flowOf(fakeDatasets)
    }

    override fun getById(id: Int): Flow<Dataset> {
        return flowOf(fakeDatasets[0])
    }

    override fun getByName(name: String): Flow<Dataset> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(dataset: Dataset) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(datasets: List<Dataset>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(dataset: Dataset) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(dataset: Dataset) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}