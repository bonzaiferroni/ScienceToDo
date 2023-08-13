package com.bonsai.sciencetodo.model

interface BaseDataValue {
    val id: Int
    val variableId: Int
    val observationId: Int
}

interface DataValue<T> : BaseDataValue {
    val value: T
}
