package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.Observation
import kotlinx.coroutines.flow.Flow

class FakeObservationDao : ObservationDao {
    override fun getAll(): Flow<List<Observation>> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Flow<Observation> {
        TODO("Not yet implemented")
    }

    override fun getByFlowId(id: Int): Flow<List<Observation>> {
        TODO("Not yet implemented")
    }

    override fun getCountByDataFlowId(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(observation: Observation): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(observations: List<Observation>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(observation: Observation) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(observation: Observation) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}