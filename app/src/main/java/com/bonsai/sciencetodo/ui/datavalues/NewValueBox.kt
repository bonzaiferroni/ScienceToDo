package com.bonsai.sciencetodo.ui.datavalues

import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType

abstract class NewValueBox (
    val variable: Variable,
) {
    abstract fun isValid(): Boolean

    companion object {
        fun getBox(variable: Variable): NewValueBox = when (variable.type) {
            VariableType.Undefined -> throw IllegalArgumentException("undefined variableType")
            VariableType.Integer -> NewInteger(variable)
            VariableType.String -> NewString(variable)
        }
    }
}

abstract class NewValue<T>(
    variable: Variable,
    var value: T
) : NewValueBox(variable)

class NewInteger(
    variable: Variable
) : NewValue<Int?>(variable, null) {
    override fun isValid() = value != null
}

class NewString(
    variable: Variable
) : NewValue<String?>(variable, null) {
    override fun isValid() = value != null
}