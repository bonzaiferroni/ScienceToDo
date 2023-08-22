package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.dao.EnumVarJoinDao
import com.bonsai.sciencetodo.data.dao.EnumerationDao
import com.bonsai.sciencetodo.data.dao.EnumeratorDao

class EnumRepository(
    val enumerationDao: EnumerationDao,
    val enumeratorDao: EnumeratorDao,
    val enumValueDao: EnumValueDao,
    val enumVarJoinDao: EnumVarJoinDao,
) {
}