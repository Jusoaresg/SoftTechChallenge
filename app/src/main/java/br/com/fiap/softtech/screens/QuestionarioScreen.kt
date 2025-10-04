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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softtech.database.SaudeRepository
import br.com.fiap.softtech.viewmodel.QuestionarioViewModel
import br.com.fiap.softtech.viewmodels.QuestionarioViewModelFactory

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
        }
        // --- ALTERAÇÃO 3: Exibe a mensagem de bloqueio se o usuário já respondeu ---
        else if (uiState.jaRespondeuEsteMes) {
            Text(
                text = "Você já respondeu o questionário este mês!\nVolte no próximo.",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
        // -------------------------------------------------------------------------
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Questionário Mensal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                uiState.perguntas.forEach { pergunta ->
                    val idDaPergunta = pergunta.id.toString()
                    val opcoesMap = viewModel.getOpcoesParaPergunta(pergunta)
                    val opcoesTexto = opcoesMap.keys.toList()
                    val valorSelecionado = uiState.respostasSelecionadas[idDaPergunta]
                    val textoSelecionado = opcoesMap.entries
                        .find { it.value == valorSelecionado }?.key ?: ""

                    PerguntaCard(
                        numero = pergunta.order.toString(),
                        texto = pergunta.text,
                        opcoes = opcoesTexto,
                        selecionado = textoSelecionado,
                        aoSelecionar = { opcaoSelecionada ->
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