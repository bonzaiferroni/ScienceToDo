package com.bonsai.sciencetodo.ui.dataview

import com.bonsai.sciencetodo.model.BaseValue
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DataTableContent(
    val variables: List<Variable>,
    val observations: List<Observation>,
    val dataMatrix: Array<Array<BaseValue?>>
) {
    val columnCount = variables.count() + 1 // additional column for observation date
    val rowCount = observations.count() + 1 // additional row for variable names

    private val formatter = DateTimeFormatter
        .ofPattern("HH:mm:ss") // yyyy-MM-dd
        .withZone(ZoneId.systemDefault())

    fun getMatrixValue(column: Int, row: Int): String {
        if (row == 0 && column == 0)
            return "Date"
        if (row == 0) {
            return variables[column - 1].name
        }
        if (column == 0) {
            return formatter.format(observations[row - 1].recordedAt)
        }
        return when (val dataValue = dataMatrix[column - 1][row - 1]) {
            is IntValue -> dataValue.value.toString()
            is StringValue -> dataValue.value
            is FloatValue -> dataValue.value.toString()
            else -> ""
        }
    }

    fun getBaseValue(column: Int, row: Int): BaseValue? {
        if (column == 0) throw IllegalArgumentException("column 0 is outside of dataMatrix")
        if (row == 0) throw IllegalArgumentException("row 0 is outside of dataMatrix")
        return dataMatrix[column - 1][row - 1]
    }
}