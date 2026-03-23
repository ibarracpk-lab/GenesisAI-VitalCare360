package com.pamelaibarra.genesisai.di

import android.content.Context
import com.pamelaibarra.genesisai.data.local.db.VitalCareDatabase
import com.pamelaibarra.genesisai.data.repository.CreeieRepositoryImpl
import com.pamelaibarra.genesisai.data.repository.ReferenceDataRepositoryImpl
import com.pamelaibarra.genesisai.CreeieEngine
import com.pamelaibarra.genesisai.domain.usecase.CalculateNptUseCase
import com.pamelaibarra.genesisai.domain.usecase.CalculatePediatricDoseUseCase
import com.pamelaibarra.genesisai.domain.usecase.ObserveRcpTimerUseCase
import com.pamelaibarra.genesisai.domain.usecase.ResetRcpTimerUseCase
import com.pamelaibarra.genesisai.domain.usecase.StartRcpTimerUseCase
import com.pamelaibarra.genesisai.domain.usecase.StopRcpTimerUseCase

data class MainUseCases(
    val observeRcpTimer: ObserveRcpTimerUseCase,
    val startRcpTimer: StartRcpTimerUseCase,
    val stopRcpTimer: StopRcpTimerUseCase,
    val resetRcpTimer: ResetRcpTimerUseCase,
    val calculateNpt: CalculateNptUseCase,
    val calculatePediatricDose: CalculatePediatricDoseUseCase,
)

class AppContainer(context: Context) {
    private val database = VitalCareDatabase.getInstance(context)
    private val creeieRepository = CreeieRepositoryImpl(CreeieEngine())
    val referenceDataRepository = ReferenceDataRepositoryImpl(database)

    val mainUseCases = MainUseCases(
        observeRcpTimer = ObserveRcpTimerUseCase(creeieRepository),
        startRcpTimer = StartRcpTimerUseCase(creeieRepository),
        stopRcpTimer = StopRcpTimerUseCase(creeieRepository),
        resetRcpTimer = ResetRcpTimerUseCase(creeieRepository),
        calculateNpt = CalculateNptUseCase(creeieRepository),
        calculatePediatricDose = CalculatePediatricDoseUseCase(creeieRepository),
    )
}


