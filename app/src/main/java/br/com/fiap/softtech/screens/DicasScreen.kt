package br.com.fiap.fineduca.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DicasScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val primaryColor = Color(0xFF4F46E5)
        val warningColor = Color(0xFFFFD700)
        val backgroundColor = Color(0xFFE6EDFF)

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Icone headset
                    //Icon(Icons.Default.CalendarToday, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Dicas", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Dicas Comprovadas para melhorar a Ansiedade e o Estresse", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "1. Pratique a respiração profunda e consciente", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "A respiração profunda ativa o sistema nervoso parassimpático, responsável pela sensação de calma e relaxamente. Técnicas como a respiração 4-7-8 (inspire por 4 segundos, segure por 7, expire por 8) ajudam a reduzir a frequência cardíaca e os níveis de cortisol, o hormônio do estresse.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Fonte: Harvard Medical School - Relaxation techniques", fontSize = 14.sp, textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(thickness = 2.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "2. Exercite-se regularmente", textAlign = TextAlign.Left, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "A atividade física libera endorfinas e outros neurotransmissores como a dopamina e a serotonina, que têm efeito antidepressivo e ansiolítico natural. Caminhadas diárias de 30 minutos, por exemplo, já mostram efeito significativo")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Fonte: Harvard Medical School - Relaxation techniques", fontSize = 14.sp, textAlign = TextAlign.Center)
                }


            }
        }
    }
}