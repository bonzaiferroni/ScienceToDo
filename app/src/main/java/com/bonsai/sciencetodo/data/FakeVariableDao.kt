package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.Variable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeVariableDao : VariableDao {
    private val fakeVariables = listOf(
        Variable(1, "Calories", 1),
        Variable(2, "Meal", 1),
        Variable(3, "Rating", 1)
    )

    override fun getAll(): Flow<List<Variable>> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Int): Flow<Variable> {
        TODO("Not yet implemented")
    }

    override fun getByFlowId(id: Int): Flow<List<Variable>> {
        return flowOf(fakeVariables)
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