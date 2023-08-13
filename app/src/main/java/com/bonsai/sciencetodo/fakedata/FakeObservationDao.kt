package com.bonsai.sciencetodo.fakedata

import com.bonsai.sciencetodo.data.ObservationDao
import com.bonsai.sciencetodo.fakedata.FakeData.fakeObservations
import com.bonsai.sciencetodo.model.Observation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeObservationDao : ObservationDao {
    override fun getAll(): Flow<List<Observation>> {
        return flowOf(fakeObservations)
    }

    override fun getById(id: Int): Flow<Observation> {
        return flowOf(fakeObservations.first { it.id == id })
    }

    override fun getByFlowId(id: Int): Flow<List<Observation>> {
        return flowOf(fakeObservations.filter { it.dataFlowId == id })
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