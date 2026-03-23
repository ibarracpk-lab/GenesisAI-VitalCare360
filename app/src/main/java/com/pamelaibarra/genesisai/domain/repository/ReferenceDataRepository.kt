package com.pamelaibarra.genesisai.domain.repository

import com.pamelaibarra.genesisai.data.local.entity.ClinicalFormulaEntity
import com.pamelaibarra.genesisai.data.local.entity.VademecumEntity
import kotlinx.coroutines.flow.Flow

interface ReferenceDataRepository {
    fun observeVademecum(): Flow<List<VademecumEntity>>
    fun observeClinicalFormulas(): Flow<List<ClinicalFormulaEntity>>
}


