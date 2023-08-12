package com.bonsai.sciencetodo.model

interface DataValue<T> {
    val id: Int
    val value: T
    val variableId: Int
}
