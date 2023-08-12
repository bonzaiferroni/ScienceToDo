package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.ui.datavalues.NewDataValue

class DataDaoManager(
    private val stringValueDao: StringValueDao,
    private val intValueDao: IntValueDao,
) {
    suspend fun insertNewDataValue(newDataValue: NewDataValue) {
        val variable = newDataValue.variable
        val value = newDataValue.value ?: throw NullPointerException("new value is null")

        if (value is Int) {
            val dataValue = IntValue(0, value, variable.id)
            intValueDao.insert(dataValue)
        }

        if (value is String) {
            val dataValue = StringValue(0, value, variable.id)
            stringValueDao.insert(dataValue)
        }
    }
}