package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeVariables
import com.bonsai.sciencetodo.model.Variable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeVariableDao : VariableDao {

    override fun getAll(): Flow<List<Variable>> {
        return flowOf(fakeVariables)
    }

    override fun getById(id: Int): Flow<Variable> {
        return flowOf(fakeVariables.first { it.id == id})
    }

    override fun getByFlowId(id: Int): Flow<List<Variable>> {
        return flowOf(fakeVariables.filter { it.dataFlowId == id })
    }

    override fun getByName(name: String): Flow<Variable> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(variable: Variable) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(variables: List<Variable>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(variable: Variable) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(variable: Variable) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}