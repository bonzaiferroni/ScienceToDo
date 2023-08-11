package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.DataFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataFlowDao : DataFlowDao {

    private val dataFlows = listOf(
        DataFlow(id = 1, name = "Food"),
        DataFlow(id = 2, name = "Sleep")
    )

    override fun getAll(): Flow<List<DataFlow>> {
        return flowOf(dataFlows)
    }

    override fun getById(id: Int): Flow<DataFlow> {
        return flowOf(dataFlows[0])
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