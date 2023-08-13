package com.bonsai.sciencetodo.ui.dataview

import com.bonsai.sciencetodo.model.BaseDataValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.Variable

class DataTableContent(
    val variables: List<Variable>,
    val observations: List<Observation>,
    val values: List<BaseDataValue>
) {
}