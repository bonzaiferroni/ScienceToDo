package com.bonsai.sciencetodo.fakedata

import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import java.time.Instant

object FakeData {
    val fakeDataFlows = listOf(
        DataFlow(id = 1, name = "Food"),
        DataFlow(id = 2, name = "Sleep")
    )

    val fakeVariables = listOf(
        Variable(1, fakeDataFlows[0].id, "description", VariableType.String),
        Variable(2, fakeDataFlows[0].id, "calories", VariableType.Integer),
        Variable(3, fakeDataFlows[0].id, "rating", VariableType.Float),
        Variable(4, fakeDataFlows[1].id, "hours", VariableType.Integer),
        Variable(5, fakeDataFlows[1].id, "location", VariableType.String),
        Variable(6, fakeDataFlows[1].id, "comfy_rating", VariableType.Integer),
    )

    val fakeObservations = listOf(
        Observation(1, fakeDataFlows[0].id, Instant.now().minusSeconds(6)),
        Observation(2, fakeDataFlows[0].id, Instant.now().minusSeconds(5)),
        Observation(3, fakeDataFlows[0].id, Instant.now().minusSeconds(4)),
        Observation(4, fakeDataFlows[1].id, Instant.now().minusSeconds(3)),
        Observation(5, fakeDataFlows[1].id, Instant.now().minusSeconds(2)),
        Observation(6, fakeDataFlows[1].id, Instant.now().minusSeconds(1)),
    )

    val fakeIntValues = listOf(
        IntValue(1, fakeVariables[1].id, fakeObservations[0].id, value = 100),
        IntValue(2, fakeVariables[1].id, fakeObservations[1].id, value = 500),
        IntValue(3, fakeVariables[1].id, fakeObservations[2].id, value = 400),
        IntValue(7, fakeVariables[3].id, fakeObservations[3].id, value = 7),
        IntValue(8, fakeVariables[3].id, fakeObservations[4].id, value = 9),
        IntValue(9, fakeVariables[3].id, fakeObservations[5].id, value = 5),
        IntValue(10, fakeVariables[5].id, fakeObservations[3].id, value = 6),
        IntValue(11, fakeVariables[5].id, fakeObservations[4].id, value = 7),
        IntValue(12, fakeVariables[5].id, fakeObservations[5].id, value = 6),
    )

    val fakeStringValues = listOf(
        StringValue(1, fakeVariables[0].id, fakeObservations[0].id, "Salad"),
        StringValue(2, fakeVariables[0].id, fakeObservations[1].id, "Pizza"),
        StringValue(3, fakeVariables[0].id, fakeObservations[2].id, "Chicken"),
        StringValue(4, fakeVariables[4].id, fakeObservations[3].id, "Couch"),
        StringValue(5, fakeVariables[4].id, fakeObservations[4].id, "Bed"),
        StringValue(6, fakeVariables[4].id, fakeObservations[5].id, "Couch"),
    )

    val fakeFloatValues = listOf(
        FloatValue(1, fakeVariables[2].id, fakeObservations[0].id, 5.5f),
        FloatValue(2, fakeVariables[2].id, fakeObservations[1].id, 6.5f),
        FloatValue(3, fakeVariables[2].id, fakeObservations[2].id, 7.5f),
    )
}