package com.pamelaibarra.genesisai.domain.usecase

import com.pamelaibarra.genesisai.NptRequest
import com.pamelaibarra.genesisai.NptResult
import com.pamelaibarra.genesisai.PediatricDoseResult
import com.pamelaibarra.genesisai.RcpTimerState
import com.pamelaibarra.genesisai.domain.repository.CreeieRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveRcpTimerUseCase(
    private val repository: CreeieRepository,
) {
    operator fun invoke(): StateFlow<RcpTimerState> = repository.rcpTimerState
}

class StartRcpTimerUseCase(
    private val repository: CreeieRepository,
) {
    operator fun invoke(cycleDurationSeconds: Int = 120, adrenalineIntervalSeconds: Int = 240) {
        repository.startRcpTimer(cycleDurationSeconds, adrenalineIntervalSeconds)
    }
}

class StopRcpTimerUseCase(
    private val repository: CreeieRepository,
) {
    operator fun invoke() {
        repository.stopRcpTimer()
    }
}

class ResetRcpTimerUseCase(
    private val repository: CreeieRepository,
) {
    operator fun invoke(cycleDurationSeconds: Int = 120, adrenalineIntervalSeconds: Int = 240) {
        repository.resetRcpTimer(cycleDurationSeconds, adrenalineIntervalSeconds)
    }
}

class CalculateNptUseCase(
    private val repository: CreeieRepository,
) {
    operator fun invoke(request: NptRequest): NptResult = repository.calculateNpt(request)
}

class CalculatePediatricDoseUseCase(
    private val repository: CreeieRepository,
) {
    operator fun invoke(
        weightKg: Double,
        mgPerKgPerDose: Double,
        concentrationMgPerMl: Double? = null,
        maxDoseMg: Double? = null,
    ): PediatricDoseResult = repository.calculatePediatricDose(weightKg, mgPerKgPerDose, concentrationMgPerMl, maxDoseMg)
}


