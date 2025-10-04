package br.com.fiap.softtech.screens

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background // <-- NOVO IMPORT
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // <-- NOVO IMPORT
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun EvolucaoScreen(navController: NavController, modifier: Modifier = Modifier) {
    val chartData = mapOf(
        "Feliz" to 9f,
        "Ansioso" to 12f,
        "Deprimido" to 9f
    )

    val corDeFundo = Color(0xFF00008B)

    Column(

        modifier = modifier
            .fillMaxSize()
            .background(corDeFundo)
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
                    Text(
                        text = label,
                        color = Color.White
                    )
                    Text(
                        text = "${value.toInt()} dias",
                        color = Color.White
                    )
                }
            }
        }

        Text(
            text = "Acesse nossa aba de \"Dicas\" para receber conteúdos e melhorar o seu desempenho!",
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { navController.navigate("dicas") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = corDeFundo
            )
        ) {
            Text(text = "Ver Dicas")
        }
    }
}

@Composable
fun PieChartView(data: Map<String, Float>) {
    AndroidView(factory = { context ->
        PieChart(context).apply {
            description.isEnabled = false
            legend.isEnabled = false
            isRotationEnabled = true
            setUsePercentValues(false)
            setEntryLabelColor(AndroidColor.TRANSPARENT)
            setHoleColor(0xFF00008B.toInt())
            setTransparentCircleAlpha(0)
            holeRadius = 58f
            setCenterTextColor(AndroidColor.WHITE)
            setCenterText("NOS ÚLTIMOS 30 DIAS\nVOCÊ SE DEFINIU COMO:")
            setCenterTextSize(12f)
        }
    }, update = { chart ->
        val entries = data.map { (label, value) ->
            PieEntry(value, label)
        }

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                AndroidColor.rgb(152, 251, 152),
                AndroidColor.rgb(255, 165, 0),
                AndroidColor.rgb(255, 110, 180)
            )
            valueTextColor = AndroidColor.WHITE
            valueTextSize = 14f
            sliceSpace = 3f
        }

        dataSet.setDrawValues(true)
        chart.data = PieData(dataSet)
        chart.invalidate()
    },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp))
}
