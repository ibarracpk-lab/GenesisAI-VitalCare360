package com.pamelaibarra.genesisai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clinical_formulas")
data class ClinicalFormulaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val module: String,
    val formulaName: String,
    val formulaExpression: String,
    val notes: String,
)


