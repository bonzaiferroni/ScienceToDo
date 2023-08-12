package com.bonsai.sciencetodo.ui.datavalues

import com.bonsai.sciencetodo.model.Variable

open class NewDataValue(
    val variable: Variable,
) {
    var value: Any? = null
}