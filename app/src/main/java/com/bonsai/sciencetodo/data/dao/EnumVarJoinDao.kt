package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bonsai.sciencetodo.model.EnumVarJoin
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Variable
import kotlinx.coroutines.flow.Flow

@Dao
interface EnumVarJoinDao {
    @Query("SELECT * FROM enumeration INNER JOIN enum_var_join " +
            "ON enumeration.id=enum_var_join.enumeration_id " +
            "WHERE enum_var_join.variable_id=:variableId " +
            "LIMIT 1")
    fun getEnumerationByVariableId(variableId: Int): Flow<Enumeration>

    @Query("SELECT * FROM variable INNER JOIN enum_var_join " +
            "ON variable.id=enum_var_join.variable_id " +
            "WHERE enum_var_join.enumeration_id=:enumerationId")
    fun getVariablesByEnumerationId(enumerationId: Int): Flow<List<Variable>>

    @Insert
    suspend fun insert(enumVarJoin: EnumVarJoin)
}