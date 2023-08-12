package com.bonsai.sciencetodo.model

enum class VariableType(val intValue: Int) {
    Undefined(0),
    Integer(1),
    String(2);

    companion object {
        fun fromInt(value: Int): VariableType = when (value) {
            1 -> Integer
            2 -> String
            else -> Undefined
        }
    }
}