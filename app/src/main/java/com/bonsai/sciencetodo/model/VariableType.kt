package com.bonsai.sciencetodo.model

enum class VariableType(val dbValue: Int) {
    Undefined(0),
    Integer(1),
    String(2),
    Float(3),
    Boolean(4);

    companion object {
        fun fromInt(value: Int): VariableType = VariableType.values()
            .firstOrNull { value == it.dbValue } ?:
            throw IllegalArgumentException("no VariableType with intValue: $value")
    }
}