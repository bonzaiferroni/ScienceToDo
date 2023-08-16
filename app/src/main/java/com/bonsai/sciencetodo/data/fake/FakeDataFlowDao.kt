package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.DataFlowDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeDataFlows
import com.bonsai.sciencetodo.model.DataFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataFlowDao : DataFlowDao {

    override fun getAll(): Flow<List<DataFlow>> {
        return flowOf(fakeDataFlows)
    }

    override fun getById(id: Int): Flow<DataFlow> {
        return flowOf(fakeDataFlows[0])
    }

    override fun getByName(name: String): Flow<DataFlow> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(dataFlow: DataFlow) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(dataFlows: List<DataFlow>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(dataFlow: DataFlow) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(dataFlow: DataFlow) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}