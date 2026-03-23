package com.pamelaibarra.genesisai.data.repository

import com.pamelaibarra.genesisai.CreeieEngine
import com.pamelaibarra.genesisai.NptRequest
import com.pamelaibarra.genesisai.NptResult
import com.pamelaibarra.genesisai.PediatricDoseResult
import com.pamelaibarra.genesisai.RcpTimerState
import com.pamelaibarra.genesisai.domain.repository.CreeieRepository
import kotlinx.coroutines.flow.StateFlow

class CreeieRepositoryImpl(
    private val engine: CreeieEngine,
) : CreeieRepository {
    override val rcpTimerState: StateFlow<RcpTimerState> = engine.rcpTimerState

    override fun startRcpTimer(cycleDurationSeconds: Int, adrenalineIntervalSeconds: Int) {
        engine.startRcpTimer(cycleDurationSeconds, adrenalineIntervalSeconds)
    }

    override fun stopRcpTimer() {
        engine.stopRcpTimer()
    }

    override fun resetRcpTimer(cycleDurationSeconds: Int, adrenalineIntervalSeconds: Int) {
        engine.resetRcpTimer(cycleDurationSeconds, adrenalineIntervalSeconds)
    }

    override fun acknowledgeAlerts() {
        engine.acknowledgeAlerts()
    }

    override fun calculateNpt(request: NptRequest): NptResult = engine.calculateNpt(request)

    override fun calculatePediatricDose(
        weightKg: Double,
        mgPerKgPerDose: Double,
        concentrationMgPerMl: Double?,
        maxDoseMg: Double?,
    ): PediatricDoseResult = engine.calculatePediatricDose(weightKg, mgPerKgPerDose, concentrationMgPerMl, maxDoseMg)
}


