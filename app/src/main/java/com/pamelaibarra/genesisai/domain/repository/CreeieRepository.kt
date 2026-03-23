package com.pamelaibarra.genesisai.domain.repository

import com.pamelaibarra.genesisai.NptRequest
import com.pamelaibarra.genesisai.NptResult
import com.pamelaibarra.genesisai.PediatricDoseResult
import com.pamelaibarra.genesisai.RcpTimerState
import kotlinx.coroutines.flow.StateFlow

interface CreeieRepository {
    val rcpTimerState: StateFlow<RcpTimerState>

    fun startRcpTimer(cycleDurationSeconds: Int = 120, adrenalineIntervalSeconds: Int = 240)
    fun stopRcpTimer()
    fun resetRcpTimer(cycleDurationSeconds: Int = 120, adrenalineIntervalSeconds: Int = 240)
    fun acknowledgeAlerts()
    fun calculateNpt(request: NptRequest): NptResult
    fun calculatePediatricDose(
        weightKg: Double,
        mgPerKgPerDose: Double,
        concentrationMgPerMl: Double? = null,
        maxDoseMg: Double? = null,
    ): PediatricDoseResult
}


