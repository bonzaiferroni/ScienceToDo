package com.bonsai.sciencetodo.model

sealed interface BaseValue {
    val id: Int
    val variableId: Int
    val observationId: Int
}

sealed interface DataValue<T> : BaseValue {
    val value: T
}
