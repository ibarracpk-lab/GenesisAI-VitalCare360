package com.pamelaibarra.genesisai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drugs")
data class DrugEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val concentration: String,
    val dosePerKg: Double,
    val maxDoseMg: Double?,
    val route: String,
    val notes: String = "",
)


