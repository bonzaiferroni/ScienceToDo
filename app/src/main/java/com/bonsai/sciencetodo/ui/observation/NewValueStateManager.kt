package com.bonsai.sciencetodo.ui.observation

import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import kotlinx.coroutines.flow.first

class NewValueStateManager(
    private val observationRepository: ObservationRepository,
    private val dataRepository: DataRepository,
    private val enumRepository: EnumRepository,
) {
    suspend fun getBox(variable: Variable): NewValue = when (variable.type) {
        VariableType.Undefined -> throw IllegalArgumentException("undefined variableType")
        VariableType.Integer -> NewInteger(variable)
        VariableType.String -> NewString(variable)
        VariableType.Float -> NewFloat(variable)
        VariableType.Boolean -> NewBoolean(variable)
        VariableType.Enum -> initEnum(variable)
    }

    fun setValue(newValue: NewValue, text: String): NewValue {
        return when (newValue) {
            is NewBoolean -> setBoolean(newValue, text)
            is NewEnum -> setEnum(newValue, text)
            is NewFloat -> setFloat(newValue, text)
            is NewInteger -> setInteger(newValue, text)
            is NewString -> setString(newValue, text)
        }
    }

    private suspend fun initEnum(variable: Variable): NewEnum {
        val enumeration = enumRepository.enumVarJoinDao.getEnumerationByVariableId(variable.id)
            .first()
        val enumerators = enumRepository.enumeratorDao.getByEnumerationId(enumeration.id).first()
        return NewEnum(
            variable = variable,
            enumerators = enumerators,
            suggestions = enumerators.map { it.name }
        )
    }

    private fun setInteger(newInteger: NewInteger, text: String): NewInteger {
        val newValue = text.filter { it.isDigit() }
        return newInteger.copy(
            value = newValue.toIntOrNull(),
            text = newValue
        )
    }

    private fun setString(newString: NewString, text: String): NewString {
        return newString.copy(
            text = text,
        )
    }

    private fun setFloat(newFloat: NewFloat, text: String): NewFloat {
        return newFloat.copy(
            value = text.toFloatOrNull(),
            text = text
        )
    }

    private fun setBoolean(newBoolean: NewBoolean, text: String): NewBoolean {
        val newValue = if (text.length > newBoolean.text.length) {
            if ("false".startsWith(text)) {
                "false"
            } else if ("true".startsWith(text)) {
                "true"
            } else {
                text
            }
        } else {
            text
        }
        return newBoolean.copy(
            value = newValue.toBooleanStrictOrNull(),
            text = newValue
        )
    }

    private fun setEnum(newEnum: NewEnum, text: String): NewEnum {
        return newEnum.copy(
            text = text,
        )
    }
}