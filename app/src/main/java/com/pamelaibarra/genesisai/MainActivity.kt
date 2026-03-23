package com.pamelaibarra.genesisai

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.awaitEachGesture
import androidx.compose.ui.input.pointer.awaitFirstDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.waitForUpOrCancellation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pamelaibarra.genesisai.ads.AdMobPlaceholders
import com.pamelaibarra.genesisai.RcpTimerState
import com.pamelaibarra.genesisai.presentation.main.ClinicalModule
import com.pamelaibarra.genesisai.presentation.main.MainViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        (application as VitalCareApp).appContainer.let { MainViewModel.provideFactory(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.keepSystemSplash }

        enableEdgeToEdge()

        setContent {
            VitalCare360Theme {
                VitalCare360App(viewModel = viewModel)
            }
        }
    }
}

private val VitalCareColors = darkColorScheme(
    primary = Color(0xFF00FFFF),
    secondary = Color(0xFFFF00FF),
    tertiary = Color(0xFF32CD32),
    background = Color(0xFF000000),
    surface = Color(0xFF06080F),
    onPrimary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
)

@Composable
private fun VitalCare360Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = VitalCareColors,
        content = content,
    )
}

@Composable
private fun VitalCare360App(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val timerState by viewModel.rcpTimerState.collectAsState()
    val context = LocalContext.current
    val nptPreview = remember { viewModel.previewNpt() }
    val dosePreview = remember { viewModel.previewPediatricDose() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
    ) {
        if (uiState.showBrandSplash) {
            NeonSplashScreen()
        } else {
            DashboardScreen(
                modules = uiState.modules,
                timerState = timerState,
                previewSummary = "NPT ${nptPreview.totalOsmolarityMsmPerL} mOsm/L | Pediatria ${dosePreview.doseMg} mg",
                onEasterEgg = {
                    Toast.makeText(
                        context,
                        viewModel.buildEasterEggMessage(),
                        Toast.LENGTH_LONG,
                    ).show()
                },
            )
        }
    }
}

@Composable
private fun NeonSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "E.R.C.E.E.I.",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 5.sp,
                color = Color(0xFF00FFFF),
                modifier = Modifier.blur(1.5.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "GenesisAI Vital Care 360",
                fontSize = 16.sp,
                color = Color(0xFFFF00FF),
                letterSpacing = 2.sp,
            )
        }
    }
}

@Composable
private fun DashboardScreen(
    modules: List<ClinicalModule>,
    timerState: RcpTimerState,
    previewSummary: String,
    onEasterEgg: () -> Unit,
) {
    val gradient = remember {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF00FFFF),
                Color(0xFFFF00FF),
                Color(0xFF32CD32),
            ),
        )
    }

    Scaffold(
        containerColor = Color.Black,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 18.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        brush = gradient,
                        shape = RoundedCornerShape(24.dp),
                    )
                    .background(Color(0xFF05070B), RoundedCornerShape(24.dp))
                    .padding(20.dp),
            ) {
                Column {
                    HoldToCheerText(onEasterEgg = onEasterEgg)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Dashboard clinico profesional con calculo offline y acceso rapido a 15 modulos criticos.",
                        color = Color(0xFFD6E4FF),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = previewSummary,
                        color = Color(0xFF32CD32),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    RcpStatusCard(timerState = timerState)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            AdMobBannerPlaceholder()
            Spacer(modifier = Modifier.height(18.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                items(modules) { module ->
                    ModuleCard(module = module, gradient = gradient)
                }
            }
        }
    }
}

@Composable
private fun RcpStatusCard(timerState: RcpTimerState) {
    val minutes = timerState.secondsRemainingInCycle / 60
    val seconds = timerState.secondsRemainingInCycle % 60

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0A1320), RoundedCornerShape(18.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = "RCP activa | Ciclo ${timerState.currentCycle}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Tiempo restante ${String.format("%02d:%02d", minutes, seconds)} | Adrenalina a los ${timerState.nextAdrenalineAtElapsedSeconds}s",
                color = Color(0xFFB9C6E2),
                fontSize = 12.sp,
            )
        }
        Text(
            text = if (timerState.isRunning) "LIVE" else "STOP",
            color = Color(0xFF32CD32),
            fontWeight = FontWeight.ExtraBold,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HoldToCheerText(onEasterEgg: () -> Unit) {
    Text(
        text = "CREEIE",
        fontSize = 30.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = 6.sp,
        color = Color(0xFF32CD32),
        modifier = Modifier
            .combinedClickable(
                onClick = {},
                onLongClick = {},
            )
            .pointerInput(Unit) {
                awaitEachGesture {
                    coroutineScope {
                        awaitFirstDown(requireUnconsumed = false)
                        val triggerJob = launch {
                            delay(5_000)
                            onEasterEgg()
                        }
                        waitForUpOrCancellation()
                        triggerJob.cancel()
                    }
                }
            },
    )
}

@Composable
private fun AdMobBannerPlaceholder() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111)),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = AdMobPlaceholders.BannerSlot,
                color = Color(0xFF00FFFF),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "SDK integrado y listo para conectar con un AdView real y Rewarded Ads en modulos premium.",
                color = Color(0xFFB9C6E2),
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
private fun ModuleCard(
    module: ClinicalModule,
    gradient: Brush,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B101A)),
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(158.dp)
            .border(1.dp, gradient, RoundedCornerShape(22.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                imageVector = module.icon,
                contentDescription = module.title,
                tint = Color(0xFF00FFFF),
                modifier = Modifier.size(30.dp),
            )
            Column {
                Text(
                    text = module.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = module.subtitle,
                    color = Color(0xFFB9C6E2),
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}


