package com.bonsai.sciencetodo.data

import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType

sealed interface NewValueBox {
    val variable: Variable

    fun isValid(): Boolean

    companion object {
        fun getBox(variable: Variable): NewValueBox = when (variable.type) {
            VariableType.Undefined -> throw IllegalArgumentException("undefined variableType")
            VariableType.Integer -> NewInteger(variable)
            VariableType.String -> NewString(variable)
            VariableType.Float -> NewFloat(variable)
            VariableType.Boolean -> NewBoolean(variable)
        }
    }
}

sealed interface NewValue<T> : NewValueBox {
    var value: T
}

class NewInteger(
    override val variable: Variable,
    override var value: Int? = null
) : NewValue<Int?> {
    override fun isValid() = value != null
}

class NewString(
    override val variable: Variable,
    override var value: String? = null
) : NewValue<String?> {
    override fun isValid() = value != null
}

class NewFloat(
    override val variable: Variable,
    override var value: Float? = null
) : NewValue<Float?> {
    override fun isValid() = value != null
}

class NewBoolean(
    override val variable: Variable,
    override var value: Boolean? = null
) : NewValue<Boolean?> {
    override fun isValid() = value != null
}

fun List<NewValueBox>.isValid(): Boolean = this.all { it.isValid() }