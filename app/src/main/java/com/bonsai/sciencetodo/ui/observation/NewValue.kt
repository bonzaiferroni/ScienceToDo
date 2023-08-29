package com.bonsai.sciencetodo.ui.observation

import com.bonsai.sciencetodo.model.Enumerator
import com.bonsai.sciencetodo.model.Variable

sealed interface NewValue {
    val variable: Variable
    fun isValid(): Boolean
}

data class NewInteger(
    override val variable: Variable,
    val text: String = "",
    val value: Int? = null,
) : NewValue {
    override fun isValid() = value != null
}

data class NewString(
    override val variable: Variable,
    val text: String = "",
    val pickerState: String = "?",
    val suggestions: List<String>,
) : NewValue {
    override fun isValid() = text.isNotBlank()
}

data class NewFloat(
    override val variable: Variable,
    val text: String = "",
    val pickerState: String = "?",
    val suggestions: List<String>,
    val value: Float? = null,
) : NewValue {
    override fun isValid() = value != null
}

data class NewBoolean(
    override val variable: Variable,
    val text: String = "",
    val pickerState: String = "?",
    val value: Boolean? = null,
) : NewValue {

    override fun isValid() = value != null

    companion object {
        val pickerList = listOf("false", "?", "true")
    }
}

data class NewEnum(
    override val variable: Variable,
    val text: String = "",
    val pickerState: String = "?",
    val value: Int? = null,
    val suggestions: List<String> = emptyList(),
    val enumerators: List<Enumerator> = emptyList(),
) : NewValue {


    override fun isValid() = value != null
}