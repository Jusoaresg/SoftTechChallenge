package br.com.fiap.fineduca.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun QuestionarioScreen(modifier: Modifier = Modifier, navController: NavController) {
    var resposta1 by remember { mutableStateOf("Ansioso") }
    var resposta2 by remember { mutableStateOf("Disposto") }
    var resposta3 by remember { mutableStateOf("7-9h") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .background(Color(0xFFE8F0FF)), // azul claro
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Humor Diário",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        PerguntaCard(
            numero = "1",
            texto = "Como você se definiria hoje?",
            opcoes = listOf("Animado!", "Ansioso", "Triste", "Calmo", "Feliz"),
            selecionado = resposta1,
            aoSelecionar = { resposta1 = it }
        )

        PerguntaCard(
            numero = "2",
            texto = "Como você se sentiu ao acordar?",
            opcoes = listOf("Disposto", "Indisposto"),
            selecionado = resposta2,
            aoSelecionar = { resposta2 = it }
        )

        PerguntaCard(
            numero = "3",
            texto = "Quantas horas de sono você teve?",
            opcoes = listOf("7-9h", "4-7h", "Menos de 4h", "Mais de 9h"),
            selecionado = resposta3,
            aoSelecionar = { resposta3 = it }
        )
    }
}

@Composable
fun PerguntaCard(
    numero: String,
    texto: String,
    opcoes: List<String>,
    selecionado: String,
    aoSelecionar: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "$numero. $texto",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            opcoes.forEach { opcao ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selecionado == opcao,
                        onClick = { aoSelecionar(opcao) }
                    )
                    Text(text = opcao)
                }
            }
        }
    }
}
