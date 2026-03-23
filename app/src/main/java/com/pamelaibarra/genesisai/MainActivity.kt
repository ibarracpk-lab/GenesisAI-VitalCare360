package com.pamelaibarra.genesisai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF000B18)) { // Fondo azul oscuro neón
                DashboardEspecialidades()
            }
        }
    }
}

@Composable
fun DashboardEspecialidades() {
    val categorias = listOf(
        "🩸 Unidad Crítica (Intensivistas)",
        "🔪 Especialidades Quirúrgicas",
        "💧 Unidad Renal (Nefrólogos)",
        "🧠 Neuro y Medicina Interna",
        "👶 Pediatría y Metabólica",
        "🩺 Enfermería General"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "GénesisAI: Vital Care 360",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF00FF00) // Verde Neón
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categorias.size) { index ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
                    modifier = Modifier.height(100.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text(text = categorias[index], color = Color.White, textAlign = androidx.compose.ui.PaddingValues(8.dp))
                    }
                }
            }
        }
        
        // Espacio para tu anuncio de AdMob al final
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Anuncio AdMob aquí", color = Color.Gray)
    }
}