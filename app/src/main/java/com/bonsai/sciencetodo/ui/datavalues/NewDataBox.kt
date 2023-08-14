package com.bonsai.sciencetodo.ui.datavalues

import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType

abstract class NewDataBox (
    val variable: Variable,
) {
    abstract fun isValid(): Boolean

    companion object {
        fun getBox(variable: Variable): NewDataBox = when (variable.type) {
            VariableType.Undefined -> throw IllegalArgumentException("undefined variableType")
            VariableType.Integer -> NewInteger(variable)
            VariableType.String -> NewString(variable)
        }
    }
}

abstract class NewData<T>(
    variable: Variable,
    var value: T
) : NewDataBox(variable)

class NewInteger(
    variable: Variable
) : NewData<Int?>(variable, null) {
    override fun isValid() = value != null
}

class NewString(
    variable: Variable
) : NewData<String?>(variable, null) {
    override fun isValid() = value != null
}