package com.pamelaibarra.genesisai.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Healing
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector

data class MainUiState(
    val keepSystemSplash: Boolean = true,
    val showBrandSplash: Boolean = true,
    val modules: List<ClinicalModule> = clinicalModules,
)

data class ClinicalModule(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
)

val clinicalModules = listOf(
    ClinicalModule("Cardio", "UCI, RCP y soporte hemodinamico", Icons.Outlined.MonitorHeart),
    ClinicalModule("Nefro", "Balance, depuracion y accesos", Icons.Outlined.WaterDrop),
    ClinicalModule("Pediatria", "Escalas y dosificacion por peso", Icons.Outlined.ChildCare),
    ClinicalModule("Neonatologia", "Reanimacion y nutricion temprana", Icons.Outlined.Healing),
    ClinicalModule("Urgencias", "Atencion critica y triage", Icons.Outlined.LocalHospital),
    ClinicalModule("Infectologia", "Antibioticos y aislamiento", Icons.Outlined.Medication),
    ClinicalModule("Endocrino", "Insulina, crisis y ajustes", Icons.Outlined.Science),
    ClinicalModule("Neuro", "Sedacion, Glasgow y monitoreo", Icons.Outlined.Favorite),
    ClinicalModule("Respiratorio", "Ventilacion y gasometria", Icons.Outlined.MonitorHeart),
    ClinicalModule("NPT", "Osmolaridad y relacion cal-nitrogeno", Icons.Outlined.Science),
    ClinicalModule("Farmacos", "Vademecum offline", Icons.Outlined.Medication),
    ClinicalModule("Gastro", "Sondas, sangrado y soporte", Icons.Outlined.Healing),
    ClinicalModule("Trauma", "Protocolos ABCDE", Icons.Outlined.LocalHospital),
    ClinicalModule("Obstetricia", "Urgencias maternas", Icons.Outlined.Favorite),
    ClinicalModule("Laboratorio", "Interpretacion rapida", Icons.Outlined.Science),
)


