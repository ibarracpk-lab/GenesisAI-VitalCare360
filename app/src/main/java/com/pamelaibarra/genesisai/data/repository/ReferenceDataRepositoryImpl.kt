package com.pamelaibarra.genesisai.data.repository

import com.pamelaibarra.genesisai.data.local.db.VitalCareDatabase
import com.pamelaibarra.genesisai.data.local.entity.ClinicalFormulaEntity
import com.pamelaibarra.genesisai.data.local.entity.VademecumEntity
import com.pamelaibarra.genesisai.domain.repository.ReferenceDataRepository
import kotlinx.coroutines.flow.Flow

class ReferenceDataRepositoryImpl(
    database: VitalCareDatabase,
) : ReferenceDataRepository {

    private val vademecumDao = database.vademecumDao()
    private val formulaDao = database.clinicalFormulaDao()

    override fun observeVademecum(): Flow<List<VademecumEntity>> = vademecumDao.observeAll()

    override fun observeClinicalFormulas(): Flow<List<ClinicalFormulaEntity>> = formulaDao.observeAll()
}


