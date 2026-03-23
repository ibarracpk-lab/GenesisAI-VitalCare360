package com.pamelaibarra.genesisai.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pamelaibarra.genesisai.di.AppContainer
import com.pamelaibarra.genesisai.NptRequest
import com.pamelaibarra.genesisai.NptResult
import com.pamelaibarra.genesisai.PediatricDoseResult
import com.pamelaibarra.genesisai.RcpTimerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainViewModel(
    private val appContainer: AppContainer,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    val rcpTimerState: StateFlow<RcpTimerState> = appContainer.mainUseCases.observeRcpTimer()

    init {
        viewModelScope.launch {
            delay(900)
            _uiState.value = _uiState.value.copy(keepSystemSplash = false)
            delay(1_300)
            _uiState.value = _uiState.value.copy(showBrandSplash = false)
        }

        appContainer.mainUseCases.resetRcpTimer()
        appContainer.mainUseCases.startRcpTimer()
    }

    override fun onCleared() {
        appContainer.mainUseCases.stopRcpTimer()
        super.onCleared()
    }

    fun buildEasterEggMessage(): String {
        val currentHour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        return "Son las $currentHour, te hace falta un cafe o algo asi. \u00A1Animo con el turno! \u2615"
    }

    fun previewNpt(): NptResult {
        return appContainer.mainUseCases.calculateNpt(
            NptRequest(
                dextroseGrams = 140.0,
                aminoAcidsGrams = 42.0,
                sodiumMeq = 48.0,
                potassiumMeq = 24.0,
                calciumMeq = 8.0,
                magnesiumMeq = 6.0,
                phosphorusMmol = 12.0,
                finalVolumeMl = 1500.0,
            ),
        )
    }

    fun previewPediatricDose(): PediatricDoseResult {
        return appContainer.mainUseCases.calculatePediatricDose(
            weightKg = 12.0,
            mgPerKgPerDose = 15.0,
            concentrationMgPerMl = 100.0,
            maxDoseMg = 250.0,
        )
    }

    companion object {
        fun provideFactory(appContainer: AppContainer): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(appContainer) as T
                }
            }
    }
}


