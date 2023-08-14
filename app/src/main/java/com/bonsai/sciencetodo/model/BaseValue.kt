package com.bonsai.sciencetodo.model

interface BaseValue {
    val id: Int
    val variableId: Int
    val observationId: Int
}

interface DataValue<T> : BaseValue {
    val value: T
}
