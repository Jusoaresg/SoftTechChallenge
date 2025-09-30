package br.com.fiap.softtech.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softtech.data.Pergunta
import br.com.fiap.softtech.data.Resposta
import br.com.fiap.softtech.database.SaudeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Data class para representar todo o estado da nossa tela
data class QuestionarioUiState(
    val carregando: Boolean = true,
    val perguntas: List<Pergunta> = emptyList(),
    val respostasSelecionadas: Map<String, Int> = emptyMap(), // Map<ID_da_Pergunta, Valor_da_Resposta>
    val podeEnviar: Boolean = false,
    val envioConcluido: Boolean = false,
    val erro: String? = null
)

class QuestionarioViewModel(private val repositorio: SaudeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionarioUiState())
    val uiState: StateFlow<QuestionarioUiState> = _uiState.asStateFlow()

    // Mapeamento fixo das opções para valores numéricos
    val opcoesDeResposta = mapOf(
        "Muito Ruim" to 1,
        "Ruim" to 2,
        "Normal" to 3,
        "Bom" to 4,
        "Muito Bom" to 5
    )

    init {
        carregarPerguntas()
    }

    private fun carregarPerguntas() {
        viewModelScope.launch {
            try {
                val perguntasDoBanco = repositorio.buscarPerguntasAtivas()
                _uiState.update { estadoAtual ->
                    estadoAtual.copy(carregando = false, perguntas = perguntasDoBanco)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(carregando = false, erro = "Falha ao carregar perguntas.") }
            }
        }
    }

    fun onRespostaSelecionada(perguntaId: String, opcaoTexto: String) {
        val valorResposta = opcoesDeResposta[opcaoTexto] ?: return // Pega o valor numérico da opção

        val respostasAtuais = _uiState.value.respostasSelecionadas.toMutableMap()
        respostasAtuais[perguntaId] = valorResposta

        _uiState.update { estadoAtual ->
            val todasRespondidas = estadoAtual.perguntas.size == respostasAtuais.size
            estadoAtual.copy(
                respostasSelecionadas = respostasAtuais,
                podeEnviar = todasRespondidas
            )
        }
    }

    fun salvarRespostas() {
        viewModelScope.launch {
            val respostasParaSalvar = _uiState.value.respostasSelecionadas.map { (perguntaId, valor) ->
                Resposta(questionId = perguntaId, responseValue = valor)
            }
            val sucesso = repositorio.salvarRespostasDoDia(respostasParaSalvar)
            if (sucesso) {
                _uiState.update { it.copy(envioConcluido = true) }
            } else {
                _uiState.update { it.copy(erro = "Você já respondeu o questionário hoje!") }
            }
        }
    }
}