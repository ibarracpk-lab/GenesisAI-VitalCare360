package com.pamelaibarra.genesisai

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class RcpTimerState(
    val isRunning: Boolean = false,
    val currentCycle: Int = 1,
    val secondsRemainingInCycle: Int = 120,
    val totalElapsedSeconds: Int = 0,
    val nextAdrenalineAtElapsedSeconds: Int = 240,
    val cycleAlert: Boolean = false,
    val adrenalineAlert: Boolean = false,
)

data class NptRequest(
    val dextroseGrams: Double,
    val aminoAcidsGrams: Double,
    val sodiumMeq: Double,
    val potassiumMeq: Double,
    val calciumMeq: Double,
    val magnesiumMeq: Double,
    val phosphorusMmol: Double,
    val finalVolumeMl: Double,
    val nonProteinCalories: Double? = null,
)

data class NptResult(
    val totalOsmolarityMsmPerL: Double,
    val caloriesNitrogenRatio: Double,
    val nitrogenGrams: Double,
)

data class PediatricDoseResult(
    val doseMg: Double,
    val doseMl: Double?,
    val roundedDoseMg: Int,
)

class CreeieEngine(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) {

    private val engineScope = CoroutineScope(SupervisorJob() + dispatcher)
    private val _rcpTimerState = MutableStateFlow(RcpTimerState())
    val rcpTimerState: StateFlow<RcpTimerState> = _rcpTimerState.asStateFlow()

    private var timerJob: Job? = null

    fun startRcpTimer(
        cycleDurationSeconds: Int = 120,
        adrenalineIntervalSeconds: Int = 240,
    ) {
        if (timerJob?.isActive == true) return

        timerJob = engineScope.launch {
            _rcpTimerState.value = _rcpTimerState.value.copy(isRunning = true)

            while (true) {
                delay(1_000)
                val previous = _rcpTimerState.value
                val nextElapsed = previous.totalElapsedSeconds + 1
                val nextRemaining = previous.secondsRemainingInCycle - 1

                val cycleFinished = nextRemaining <= 0
                val adrenalineDue = nextElapsed >= previous.nextAdrenalineAtElapsedSeconds

                _rcpTimerState.value = previous.copy(
                    isRunning = true,
                    currentCycle = if (cycleFinished) previous.currentCycle + 1 else previous.currentCycle,
                    secondsRemainingInCycle = if (cycleFinished) cycleDurationSeconds else nextRemaining,
                    totalElapsedSeconds = nextElapsed,
                    nextAdrenalineAtElapsedSeconds = if (adrenalineDue) {
                        previous.nextAdrenalineAtElapsedSeconds + adrenalineIntervalSeconds
                    } else {
                        previous.nextAdrenalineAtElapsedSeconds
                    },
                    cycleAlert = cycleFinished,
                    adrenalineAlert = adrenalineDue,
                )
            }
        }
    }

    fun acknowledgeAlerts() {
        _rcpTimerState.value = _rcpTimerState.value.copy(
            cycleAlert = false,
            adrenalineAlert = false,
        )
    }

    fun stopRcpTimer() {
        timerJob?.cancel()
        timerJob = null
        _rcpTimerState.value = _rcpTimerState.value.copy(isRunning = false)
    }

    fun resetRcpTimer(
        cycleDurationSeconds: Int = 120,
        adrenalineIntervalSeconds: Int = 240,
    ) {
        stopRcpTimer()
        _rcpTimerState.value = RcpTimerState(
            secondsRemainingInCycle = cycleDurationSeconds,
            nextAdrenalineAtElapsedSeconds = adrenalineIntervalSeconds,
        )
    }

    fun calculateNpt(request: NptRequest): NptResult {
        require(request.finalVolumeMl > 0) { "El volumen final debe ser mayor a cero." }

        val nitrogenGrams = request.aminoAcidsGrams / 6.25
        val nonProteinCalories = request.nonProteinCalories ?: (request.dextroseGrams * 3.4)

        val estimatedMsm =
            (request.dextroseGrams * 5.0) +
                (request.aminoAcidsGrams * 10.0) +
                request.sodiumMeq +
                request.potassiumMeq +
                request.calciumMeq +
                request.magnesiumMeq +
                (request.phosphorusMmol * 2.0)

        val osmolarity = estimatedMsm / (request.finalVolumeMl / 1_000.0)
        val calNitrogenRatio = if (nitrogenGrams == 0.0) 0.0 else nonProteinCalories / nitrogenGrams

        return NptResult(
            totalOsmolarityMsmPerL = osmolarity.roundTo(2),
            caloriesNitrogenRatio = calNitrogenRatio.roundTo(2),
            nitrogenGrams = nitrogenGrams.roundTo(2),
        )
    }

    fun calculatePediatricDose(
        weightKg: Double,
        mgPerKgPerDose: Double,
        concentrationMgPerMl: Double? = null,
        maxDoseMg: Double? = null,
    ): PediatricDoseResult {
        require(weightKg > 0) { "El peso debe ser mayor a cero." }
        require(mgPerKgPerDose > 0) { "La dosis por kilogramo debe ser mayor a cero." }

        val rawDose = weightKg * mgPerKgPerDose
        val cappedDose = maxDoseMg?.let { minOf(rawDose, it) } ?: rawDose
        val doseMl = concentrationMgPerMl?.takeIf { it > 0 }?.let { cappedDose / it }

        return PediatricDoseResult(
            doseMg = cappedDose.roundTo(2),
            doseMl = doseMl?.roundTo(2),
            roundedDoseMg = cappedDose.roundToInt(),
        )
    }

    private fun Double.roundTo(decimals: Int): Double {
        val factor = 10.0.pow(decimals)
        return (this * factor).roundToInt() / factor
    }

    private fun Double.pow(exponent: Int): Double {
        var result = 1.0
        repeat(exponent) { result *= this }
        return result
    }
}


