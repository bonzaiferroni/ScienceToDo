package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.data.dao.EnumVarJoinDao
import com.bonsai.sciencetodo.data.fake.FakeData.fakeEnumVarJoinValues
import com.bonsai.sciencetodo.data.fake.FakeData.fakeEnumerations
import com.bonsai.sciencetodo.data.fake.FakeData.fakeVariables
import com.bonsai.sciencetodo.model.EnumVarJoin
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Variable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeEnumVarJoinDao : EnumVarJoinDao {
    override fun getEnumerationByVariableId(variableId: Int): Flow<Enumeration> {
        return flowOf(fakeEnumerations
            .first { enumeration -> enumeration.id == fakeEnumVarJoinValues
                .first { it.variableId == variableId}.enumerationId }
        )
    }

    override fun getVariablesByEnumerationId(enumerationId: Int): Flow<List<Variable>> {
        return flowOf(fakeEnumVarJoinValues
            .filter { it.enumerationId == enumerationId }
            .map { enumVarJoin -> fakeVariables.first { it.id == enumVarJoin.variableId } }
        )
    }

    override suspend fun insert(enumVarJoin: EnumVarJoin) {
        TODO("Not yet implemented")
    }
}