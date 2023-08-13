package com.bonsai.sciencetodo.ui.dataview

import com.bonsai.sciencetodo.model.BaseDataValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable
import java.text.SimpleDateFormat
import java.util.Locale

class DataTableContent(
    val variables: List<Variable>,
    val observations: List<Observation>,
    val dataMatrix: Array<Array<BaseDataValue?>>
) {
    val columnCount = variables.count() + 1 // additional column for observation date
    val rowCount = observations.count() + 1 // additional row for variable names

    fun getMatrixValue(column: Int, row: Int): String {
        if (row == 0 && column == 0)
            return "Date"
        if (row == 0) {
            return variables[column - 1].name
        }
        if (column == 0) {
            val date = observations[row - 1].instant
            return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
        }
        return when (val dataValue = dataMatrix[column - 1][row - 1]) {
            is IntValue -> dataValue.value.toString()
            is StringValue -> dataValue.value
            else -> ""
        }
    }
}