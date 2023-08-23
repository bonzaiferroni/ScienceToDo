package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.dao.EnumVarJoinDao
import com.bonsai.sciencetodo.data.dao.EnumerationDao
import com.bonsai.sciencetodo.data.dao.EnumeratorDao
import com.bonsai.sciencetodo.data.fake.FakeEnumValueDao
import com.bonsai.sciencetodo.data.fake.FakeEnumVarJoinDao
import com.bonsai.sciencetodo.data.fake.FakeEnumerationDao
import com.bonsai.sciencetodo.data.fake.FakeEnumeratorDao

class EnumRepository(
    val enumerationDao: EnumerationDao,
    val enumeratorDao: EnumeratorDao,
    val enumValueDao: EnumValueDao,
    val enumVarJoinDao: EnumVarJoinDao,
) {
    companion object {
        fun getFake(): EnumRepository {
            return EnumRepository(
                FakeEnumerationDao(),
                FakeEnumeratorDao(),
                FakeEnumValueDao(),
                FakeEnumVarJoinDao(),
            )
        }
    }
}