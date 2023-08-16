package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.model.BooleanValue
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import java.time.Instant

object FakeData {

    val fakeDataFlows by lazy {
        listOf(
            DataFlow(id = 1, name = "Food"),
            DataFlow(id = 2, name = "Rest")
        )
    }

    val fakeVariables by lazy {
        val foodData = fakeDataFlows.first { it.name == "Food" }
        val restData = fakeDataFlows.first { it.name == "Rest" }
        var i = 0
        listOf(
            Variable(++i, foodData.id, "description", VariableType.String),
            Variable(++i, foodData.id, "calories", VariableType.Integer),
            Variable(++i, foodData.id, "rating", VariableType.Float),
            Variable(++i, foodData.id, "hot", VariableType.Boolean),
            Variable(++i, restData.id, "hours", VariableType.Integer),
            Variable(++i, restData.id, "location", VariableType.String),
        )
    }

    val fakeObservations by lazy {
        val foodData = fakeDataFlows.first { it.name == "Food" }
        var i = 0
        var minusSeconds = 100L
        listOf(
            Observation(++i, foodData.id, Instant.now().minusSeconds(--minusSeconds)),
            Observation(++i, foodData.id, Instant.now().minusSeconds(--minusSeconds)),
            Observation(++i, foodData.id, Instant.now().minusSeconds(--minusSeconds)),
        )
    }

    val fakeIntValues by lazy {
        val variable = fakeVariables.first { it.name == "calories"}
        var i = 0
        listOf(
            IntValue(++i, variable.id, fakeObservations[0].id, value = 150),
            IntValue(++i, variable.id, fakeObservations[1].id, value = 500),
            IntValue(++i, variable.id, fakeObservations[2].id, value = 400),
        )
    }

    val fakeStringValues by lazy {
        val variable = fakeVariables.first { it.name == "description"}
        var i = 0
        listOf(
            StringValue(++i, variable.id, fakeObservations[0].id, "Salad"),
            StringValue(++i, variable.id, fakeObservations[1].id, "Pizza"),
            StringValue(++i, variable.id, fakeObservations[2].id, "Chicken"),
        )
    }

    val fakeFloatValues by lazy {
        val variable = fakeVariables.first { it.name == "rating"}
        var i = 0
        listOf(
            FloatValue(++i, variable.id, fakeObservations[0].id, 5.5f),
            FloatValue(++i, variable.id, fakeObservations[1].id, 6.5f),
            FloatValue(++i, variable.id, fakeObservations[2].id, 7.5f),
        )
    }

    val fakeBooleanValues by lazy {
        val variable = fakeVariables.first { it.name == "hot"}
        var i = 0
        listOf(
            BooleanValue(++i, variable.id, fakeObservations[0].id, false),
            BooleanValue(++i, variable.id, fakeObservations[1].id, true),
            BooleanValue(++i, variable.id, fakeObservations[2].id, true),
        )
    }
}