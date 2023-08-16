package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeEnumValues
import com.bonsai.sciencetodo.model.EnumValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeEnumValueDao : EnumValueDao {
    override fun getAll(): Flow<List<EnumValue>> {
        return flowOf(fakeEnumValues)
    }

    override fun getById(id: Int): Flow<EnumValue> {
        return flowOf(fakeEnumValues.first { it.id == id })
    }

    override fun getByObservationId(id: Int): Flow<List<EnumValue>> {
        return flowOf(fakeEnumValues.filter { it.observationId == id })
    }

    override fun getByVariableId(id: Int): Flow<List<EnumValue>> {
        return flowOf(fakeEnumValues.filter { it.variableId == id })
    }

    override fun getCountByVariableId(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(enumValue: EnumValue) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(enumValues: List<EnumValue>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(enumValue: EnumValue) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(enumValue: EnumValue) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }
}