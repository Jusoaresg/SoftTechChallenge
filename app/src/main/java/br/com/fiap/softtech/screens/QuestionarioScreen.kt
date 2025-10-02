package br.com.fiap.softtech.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softtech.database.SaudeRepository
import br.com.fiap.softtech.viewmodels.QuestionarioViewModel
import br.com.fiap.softtech.viewmodel.QuestionarioViewModelFactory

@Composable
fun QuestionarioScreen(modifier: Modifier = Modifier, navController: NavController) {
    val repositorio = remember { SaudeRepository() }
    val factory = remember { QuestionarioViewModelFactory(repositorio) }
    val viewModel: QuestionarioViewModel = viewModel(factory = factory)

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
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Questionário Diário",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                uiState.perguntas.forEach { pergunta ->
                    val idDaPergunta = pergunta.id.toString()

                    // Pega o mapa de opções correto para a pergunta atual
                    val opcoesMap = viewModel.getOpcoesParaPergunta(pergunta)
                    val opcoesTexto = opcoesMap.keys.toList()

                    // Encontra o texto da opção selecionada com base no valor numérico salvo
                    val valorSelecionado = uiState.respostasSelecionadas[idDaPergunta]
                    val textoSelecionado = opcoesMap.entries
                        .find { it.value == valorSelecionado }?.key ?: ""

                    PerguntaCard(
                        numero = pergunta.order.toString(),
                        texto = pergunta.text,
                        opcoes = opcoesTexto,
                        selecionado = textoSelecionado,
                        aoSelecionar = { opcaoSelecionada ->
                            // Pega o valor numérico correspondente e envia para o ViewModel
                            val valor = opcoesMap[opcaoSelecionada]
                            if (valor != null) {
                                viewModel.onRespostaSelecionada(idDaPergunta, valor)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.salvarRespostas() },
                    enabled = uiState.podeEnviar,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SALVAR RESPOSTAS")
                }

                uiState.erro?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

// O Composable PerguntaCard continua exatamente o mesmo
@Composable
fun PerguntaCard(
    numero: String,
    texto: String,
    opcoes: List<String>,
    selecionado: String,
    aoSelecionar: (String) -> Unit
) {

}