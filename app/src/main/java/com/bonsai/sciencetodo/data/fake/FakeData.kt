package com.bonsai.sciencetodo.data.fake

import com.bonsai.sciencetodo.model.BooleanValue
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.EnumValue
import com.bonsai.sciencetodo.model.EnumVarJoin
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Enumerator
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import java.time.Instant

object FakeData {

    val fakeDatasets by lazy {
        listOf(
            Dataset(id = 1, name = "Food"),
            Dataset(id = 2, name = "Rest")
        )
    }

    val fakeVariables by lazy {
        val foodData = fakeDatasets.first { it.name == "Food" }
        val restData = fakeDatasets.first { it.name == "Rest" }
        var id = 0
        listOf(
            Variable(++id, foodData.id, "description", VariableType.String),
            Variable(++id, foodData.id, "calories", VariableType.Integer),
            Variable(++id, foodData.id, "rating", VariableType.Float),
            Variable(++id, foodData.id, "hot", VariableType.Boolean),
            Variable(++id, foodData.id, "meal", VariableType.Enum),
            Variable(++id, restData.id, "hours", VariableType.Integer),
            Variable(++id, restData.id, "location", VariableType.String),
        )
    }

    val fakeEnumerations by lazy {
        listOf(
            Enumeration(id = 1, name = "Meal")
        )
    }

    val fakeEnumerators by lazy {
        val enumeration = fakeEnumerations.first { it.name == "Meal"}
        var id = 0
        var orderIndex = 0
        listOf(
            Enumerator(++id, enumeration.id, "Breakfast", orderIndex++),
            Enumerator(++id, enumeration.id, "Lunch", orderIndex++),
            Enumerator(++id, enumeration.id, "Dinner", orderIndex),
        )
    }

    val fakeObservations by lazy {
        val foodData = fakeDatasets.first { it.name == "Food" }
        listOf(0, 1, 2).map { id ->
            val instant = Instant.now().minusSeconds(id.toLong())
            Observation(id, foodData.id, instant, instant)
        }
    }

    val fakeIntValues by lazy {
        val variable = fakeVariables.first { it.name == "calories"}
        var id = 0
        listOf(
            IntValue(++id, variable.id, fakeObservations[0].id, value = 150),
            IntValue(++id, variable.id, fakeObservations[1].id, value = 500),
            IntValue(++id, variable.id, fakeObservations[2].id, value = 400),
        )
    }

    val fakeStringValues by lazy {
        val variable = fakeVariables.first { it.name == "description"}
        var id = 0
        listOf(
            StringValue(++id, variable.id, fakeObservations[0].id, "Salad"),
            StringValue(++id, variable.id, fakeObservations[1].id, "Pizza"),
            StringValue(++id, variable.id, fakeObservations[2].id, "Chicken"),
        )
    }

    val fakeFloatValues by lazy {
        val variable = fakeVariables.first { it.name == "rating"}
        var id = 0
        listOf(
            FloatValue(++id, variable.id, fakeObservations[0].id, 5.5f),
            FloatValue(++id, variable.id, fakeObservations[1].id, 6.5f),
            FloatValue(++id, variable.id, fakeObservations[2].id, 7.5f),
        )
    }

    val fakeBooleanValues by lazy {
        val variable = fakeVariables.first { it.name == "hot"}
        var id = 0
        listOf(
            BooleanValue(++id, variable.id, fakeObservations[0].id, false),
            BooleanValue(++id, variable.id, fakeObservations[1].id, true),
            BooleanValue(++id, variable.id, fakeObservations[2].id, true),
        )
    }

    val fakeEnumValues by lazy {
        val variable = fakeVariables.first { it.name == "meal"}
        val lunch = fakeEnumerators.first { it.name == "Lunch"}
        val dinner = fakeEnumerators.first { it.name == "Dinner"}
        var id = 0
        listOf(
            EnumValue(++id, variable.id, fakeObservations[0].id, lunch.id),
            EnumValue(++id, variable.id, fakeObservations[1].id, dinner.id),
            EnumValue(++id, variable.id, fakeObservations[2].id, dinner.id)
        )
    }

    val fakeEnumVarJoinValues by lazy {
        val variable = fakeVariables.first { it.name == "meal"}
        val enumeration = fakeEnumerations.first { it.name == "Meal"}
        listOf(
            EnumVarJoin(variable.id, enumeration.id)
        )
    }
}