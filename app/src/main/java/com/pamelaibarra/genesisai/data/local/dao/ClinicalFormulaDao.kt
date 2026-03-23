package com.pamelaibarra.genesisai.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pamelaibarra.genesisai.data.local.entity.ClinicalFormulaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClinicalFormulaDao {
    @Query("SELECT * FROM clinical_formulas ORDER BY module ASC, formulaName ASC")
    fun observeAll(): Flow<List<ClinicalFormulaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<ClinicalFormulaEntity>)
}


