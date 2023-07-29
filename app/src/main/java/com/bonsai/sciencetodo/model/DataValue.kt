package com.bonsai.sciencetodo.model

interface DataValue<T> {
    val id: Int
    val name : String
    val value: T
    val dataFlowId: Int
    val variableId: Int
}
