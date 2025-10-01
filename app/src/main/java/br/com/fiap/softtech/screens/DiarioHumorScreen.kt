package br.com.fiap.fineduca.screens // ou br.com.fiap.softtech.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softtech.database.SaudeRepository
import br.com.fiap.softtech.screens.PerguntaCard
import br.com.fiap.softtech.viewmodel.DiarioHumorViewModel
import br.com.fiap.softtech.viewmodel.DiarioHumorViewModelFactory

@Composable
fun DiarioHumorScreen(modifier: Modifier = Modifier, navController: NavController) {
    val repositorio = remember { SaudeRepository() }
    val factory = remember { DiarioHumorViewModelFactory(repositorio) }
    val viewModel: DiarioHumorViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.envioConcluido) {
        if (uiState.envioConcluido) {
            navController.popBackStack()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE8F0FF)),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.carregando) {
            CircularProgressIndicator()
        } else if (uiState.jaRespondeuHoje) {
            Text(
                text = "Você já registrou seu humor hoje!\nVolte amanhã.",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Humor Diário",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Pergunta 1
                uiState.perguntas.getOrNull(0)?.let { pergunta ->
                    PerguntaCard(
                        numero = "1",
                        texto = pergunta.text,
                        opcoes = viewModel.opcoesHumor,
                        selecionado = uiState.respostaHumor,
                        aoSelecionar = { resposta -> viewModel.onRespostaSelecionada(pergunta.order, resposta) }
                    )
                }

                // Pergunta 2
                uiState.perguntas.getOrNull(1)?.let { pergunta ->
                    PerguntaCard(
                        numero = "2",
                        texto = pergunta.text,
                        opcoes = viewModel.opcoesTrabalho,
                        selecionado = uiState.respostaTrabalho,
                        aoSelecionar = { resposta -> viewModel.onRespostaSelecionada(pergunta.order, resposta) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.salvarHumor() },
                    enabled = uiState.podeEnviar,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SALVAR HUMOR")
                }

                uiState.erro?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}
// NOTA: A sua função PerguntaCard() pode ser reutilizada aqui sem nenhuma alteração.
// Certifique-se de que ela esteja acessível a partir deste arquivo.