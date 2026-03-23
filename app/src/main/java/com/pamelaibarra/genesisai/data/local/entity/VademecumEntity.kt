package com.pamelaibarra.genesisai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vademecum")
data class VademecumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val drugName: String,
    val presentation: String,
    val concentration: String,
    val doseReference: String,
    val precautions: String,
    val lastUpdatedEpochMs: Long,
)


