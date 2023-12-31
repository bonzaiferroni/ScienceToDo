package com.bonsai.sciencetodo.ui.observation

import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.model.Enumerator
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
        VariableType.Integer -> initInteger(variable)
        VariableType.String -> initString(variable)
        VariableType.Float -> initFloat(variable)
        VariableType.Boolean -> NewBoolean(variable)
        VariableType.Enum -> initEnum(variable)
    }

    private suspend fun initInteger(variable: Variable): NewInteger {
        val previousValues = dataRepository.intValueDao.getDistinctByVariableId(variable.id, 10)
            .first()
            .map { it.toString() }
        return NewInteger(
            variable = variable,
            suggestions = listOf(pickerNullText) + previousValues
        )
    }

    private suspend fun initFloat(variable: Variable): NewFloat {
        val previousValues = dataRepository.floatValueDao.getDistinctByVariableId(variable.id, 10)
            .first()
            .map { it.toString() }
        return NewFloat(
            variable = variable,
            suggestions = listOf(pickerNullText) + previousValues
        )
    }

    private suspend fun initString(variable: Variable): NewString {
        val previousValues = dataRepository.stringValueDao
            .getDistinctValuesByVariableId(variable.id, 10)
            .first()
        return NewString(
            variable = variable,
            suggestions = listOf(pickerNullText) + previousValues
        )
    }

    private suspend fun initEnum(variable: Variable): NewEnum {
        val enumeration = enumRepository.enumVarJoinDao.getEnumerationByVariableId(variable.id)
            .first()
        val enumerators = enumRepository.enumeratorDao.getByEnumerationId(enumeration.id).first()
        return NewEnum(
            variable = variable,
            enumerators = enumerators,
            suggestions = listOf(pickerNullText) + enumerators.map { it.name }
        )
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

    private fun setInteger(newInteger: NewInteger, text: String): NewInteger {
        val newValue = text.filter { it.isDigit() }
        return newInteger.copy(
            value = newValue.toIntOrNull(),
            text = if (text == pickerNullText) newInteger.text else newValue,
            pickerState = if (text == pickerNullText) text
            else if (newInteger.suggestions.contains(newValue)) newValue
            else newInteger.pickerState
        )
    }

    private fun setString(newString: NewString, text: String): NewString {
        return newString.copy(
            text = if (text == pickerNullText) newString.text else text,
            pickerState = if (newString.suggestions.contains(text)) text
            else newString.pickerState
        )
    }

    private fun setFloat(newFloat: NewFloat, text: String): NewFloat {
        return newFloat.copy(
            value = text.toFloatOrNull(),
            text = if (text == pickerNullText) newFloat.text else text,
            pickerState = if (newFloat.suggestions.contains(text)) text else
                newFloat.pickerState
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
            text = if (text == pickerNullText) newBoolean.text else newValue,
            pickerState = if (NewBoolean.pickerList.contains(newValue)) newValue else
                newBoolean.pickerState
        )
    }

    private fun setEnum(newEnum: NewEnum, text: String): NewEnum {
        return newEnum.copy(
            text = if (text == pickerNullText) newEnum.text else text,
            value = newEnum.enumerators.findEnumerator(text)?.id,
            pickerState = if (text == pickerNullText) text else
                newEnum.enumerators.findEnumerator(text)?.name ?: newEnum.pickerState
        )
    }
}

fun List<Enumerator>.filterNames(text: String): List<String> = listOf(pickerNullText) + this
    .filter { it.name.uppercase() == text.uppercase() }
    .map { it.name }

fun List<Enumerator>.findEnumerator(text: String): Enumerator? = this
    .firstOrNull { it.name.uppercase() == text.uppercase() }

fun Map<Int, NewValue>.isValid(): Boolean = this.all { it.value.isValid() }

const val pickerNullText = "?"