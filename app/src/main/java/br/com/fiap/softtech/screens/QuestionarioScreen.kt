package br.com.fiap.softtech.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softtech.database.SaudeRepository
import br.com.fiap.softtech.viewmodel.QuestionarioViewModelFactory
import br.com.fiap.softtech.viewmodels.QuestionarioViewModel

@Composable
fun QuestionarioScreen(modifier: Modifier = Modifier, navController: NavController) {
    // Instancia o ViewModel de forma correta, passando o repositório
    val repositorio = remember { SaudeRepository() }
    val viewModel: QuestionarioViewModel = viewModel(factory = QuestionarioViewModelFactory(repositorio))

    // Coleta o estado da UI a partir do ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    // Efeito para navegar para trás quando o envio for concluído
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

                // Loop para criar um Card para cada pergunta vinda do banco
                uiState.perguntas.forEach { pergunta ->
                    // As opções são as mesmas para todas as perguntas
                    val opcoes = viewModel.opcoesDeResposta.keys.toList()
                    val idDaPergunta = pergunta.id.toString()
                    val valorSelecionado = uiState.respostasSelecionadas[idDaPergunta]

                    // Converte o valor numérico de volta para o texto da opção para o RadioButton
                    val textoSelecionado = viewModel.opcoesDeResposta.entries
                        .find { it.value == valorSelecionado }?.key ?: ""

                    PerguntaCard(
                        numero = pergunta.order.toString(),
                        texto = pergunta.text,
                        opcoes = opcoes,
                        selecionado = textoSelecionado,
                        aoSelecionar = { opcaoSelecionada ->
                            viewModel.onRespostaSelecionada(idDaPergunta, opcaoSelecionada)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.salvarRespostas() },
                    enabled = uiState.podeEnviar, // Botão só é clicável quando todas as perguntas forem respondidas
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SALVAR RESPOSTAS")
                }

                // Exibe mensagem de erro, se houver
                uiState.erro?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

// O Composable PerguntaCard continua exatamente o mesmo!
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
