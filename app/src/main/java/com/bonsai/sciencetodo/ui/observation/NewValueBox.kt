package com.bonsai.sciencetodo.ui.observation

import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface NewValueBox {
    val variable: Variable

    fun isValid(): Boolean

    val textState: StateFlow<String>

    companion object {
        fun getBox(variable: Variable, onSetValue: () -> Unit): NewValueBox = when (variable.type) {
            VariableType.Undefined -> throw IllegalArgumentException("undefined variableType")
            VariableType.Integer -> NewInteger(variable, onSetValue)
            VariableType.String -> NewString(variable, onSetValue)
            VariableType.Float -> NewFloat(variable, onSetValue)
            VariableType.Boolean -> NewBoolean(variable, onSetValue)
            VariableType.Enum -> NewEnum(variable, onSetValue)
        }
    }
}

abstract class NewValue<T>(
    var value: T,
    val onSetValue: () -> Unit,
) {
    private val _textState = MutableStateFlow("")
    val textState: StateFlow<String> = _textState

    protected abstract fun setValue(text: String): String

    fun setText(text: String) {
        _textState.value = setValue(text)
        onSetValue()
    }
}

class NewInteger(
    override val variable: Variable,
    onSetValue: () -> Unit,
) : NewValue<Int?>(null, onSetValue), NewValueBox {
    override fun isValid() = value != null
    override fun setValue(text: String): String {
        if (text.endsWith('.')) {
            return textState.value
        }
        value = text.toIntOrNull()
        return text
    }
}

class NewString(
    override val variable: Variable,
    onSetValue: () -> Unit,
) : NewValue<String?>(null, onSetValue), NewValueBox {
    override fun isValid() = value != null
    override fun setValue(text: String): String {
        value = text
        return text
    }
}

class NewFloat(
    override val variable: Variable,
    onSetValue: () -> Unit,
) : NewValue<Float?>(null, onSetValue), NewValueBox {
    override fun isValid() = value != null
    override fun setValue(text: String): String {
        value = text.toFloatOrNull()
        return text
    }
}

class NewBoolean(
    override val variable: Variable,
    onSetValue: () -> Unit,
) : NewValue<Boolean?>(null, onSetValue), NewValueBox {
    override fun isValid() = value != null
    override fun setValue(text: String): String {
        value = text.toBooleanStrictOrNull()
        return text
    }
}

class NewEnum(
    override val variable: Variable,
    onSetValue: () -> Unit,
) : NewValue<Int?>(null, onSetValue), NewValueBox {
    override fun isValid() = value != null
    override fun setValue(text: String): String {
        value = text.toIntOrNull()
        return text
    }
}

fun List<NewValueBox>.isValid(): Boolean = this.all { it.isValid() }