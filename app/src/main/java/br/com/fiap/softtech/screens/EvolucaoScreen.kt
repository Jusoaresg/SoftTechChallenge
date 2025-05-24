package br.com.fiap.softtech.screens

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun EvolucaoScreen(navController: NavController) {
    val chartData = mapOf(
        "Feliz" to 9f,
        "Ansioso" to 12f,
        "Deprimido" to 9f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        PieChartView(data = chartData)

        // Textos com os dados
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            chartData.forEach { (label, value) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = label)
                    Text(text = "${value.toInt()} dias")
                }
            }
        }

        Text(
            text = "Acesse nossa aba de \"Dicas\" para receber conteúdos e melhorar o seu desempenho!",
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = { navController.navigate("dicas") }) {
            Text(text = "Ver Dicas")
        }
    }
}

@Composable
fun PieChartView(data: Map<String, Float>) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            description.isEnabled = false
            isRotationEnabled = true
            setUsePercentValues(false)
            setEntryLabelColor(AndroidColor.BLACK)
            setHoleColor(AndroidColor.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 40f
            setCenterText("NOS ÚLTIMOS 30 DIAS\nVOCÊ SE DEFINIU COMO:")
            setCenterTextSize(12f)
        }
    }, update = { chart ->
        val entries = data.map { (label, value) ->
            PieEntry(value, label)
        }

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                AndroidColor.rgb(144, 238, 144), // Verde claro para "Feliz"
                AndroidColor.rgb(255, 165, 0),   // Laranja para "Ansioso"
                AndroidColor.rgb(255, 105, 180)  // Rosa para "Deprimido"
            )
            valueTextColor = AndroidColor.BLACK
            valueTextSize = 12f
            sliceSpace = 3f
        }

        chart.data = PieData(dataSet)
        chart.invalidate()
    },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp))
}
