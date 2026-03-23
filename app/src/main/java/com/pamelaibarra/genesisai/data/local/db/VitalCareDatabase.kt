package com.pamelaibarra.genesisai.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pamelaibarra.genesisai.data.local.dao.ClinicalFormulaDao
import com.pamelaibarra.genesisai.data.local.dao.DrugDao
import com.pamelaibarra.genesisai.data.local.dao.VademecumDao
import com.pamelaibarra.genesisai.data.local.entity.ClinicalFormulaEntity
import com.pamelaibarra.genesisai.data.local.entity.DrugEntity
import com.pamelaibarra.genesisai.data.local.entity.VademecumEntity

@Database(
    entities = [VademecumEntity::class, ClinicalFormulaEntity::class, DrugEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class VitalCareDatabase : RoomDatabase() {

    abstract fun vademecumDao(): VademecumDao
    abstract fun clinicalFormulaDao(): ClinicalFormulaDao
    abstract fun drugDao(): DrugDao

    companion object {
        @Volatile
        private var INSTANCE: VitalCareDatabase? = null

        fun getInstance(context: Context): VitalCareDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    VitalCareDatabase::class.java,
                    "vital_care_360.db",
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
