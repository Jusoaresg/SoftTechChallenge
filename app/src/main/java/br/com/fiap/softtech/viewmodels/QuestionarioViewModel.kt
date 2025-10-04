package br.com.fiap.softtech.viewmodel

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

data class QuestionarioUiState(
    val carregando: Boolean = true,
    val perguntas: List<Pergunta> = emptyList(),
    val respostasSelecionadas: Map<String, Int> = emptyMap(),
    val podeEnviar: Boolean = false,
    val envioConcluido: Boolean = false,
    val erro: String? = null,
    val jaRespondeuEsteMes: Boolean = false
)

class QuestionarioViewModel(private val repositorio: SaudeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionarioUiState())
    val uiState: StateFlow<QuestionarioUiState> = _uiState.asStateFlow()

    private val opcoesCargaTrabalho = mapOf(
        "Muito Leve" to 1, "Leve" to 2, "Média" to 3, "Alta" to 4, "Muito Alta" to 5
    )
    private val opcoesFrequencia = mapOf(
        "Não" to 1, "Raramente" to 2, "Às vezes" to 3, "Frequentemente" to 4, "Sempre" to 5
    )
    private val opcoesEscalaNumerica = mapOf(
        "1" to 1, "2" to 2, "3" to 3, "4" to 4, "5" to 5
    )

    fun getOpcoesParaPergunta(pergunta: Pergunta): Map<String, Int> {
        return when (pergunta.order) {
            1 -> opcoesCargaTrabalho
            in 2..5 -> opcoesFrequencia
            in 6..21 -> opcoesEscalaNumerica
            else -> emptyMap()
        }
    }

    init {
        carregarDadosIniciais()
    }

    private fun carregarDadosIniciais() {
        viewModelScope.launch {
            try {
                val jaRespondeu = repositorio.verificarRespostaMesAtual()
                if (jaRespondeu) {
                    _uiState.update { it.copy(carregando = false, jaRespondeuEsteMes = true) }
                } else {
                    val perguntasDoBanco = repositorio.buscarPerguntasMensais()
                    _uiState.update { estadoAtual ->
                        estadoAtual.copy(carregando = false, perguntas = perguntasDoBanco)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(carregando = false, erro = "Falha ao carregar dados.") }
            }
        }
    }

    fun onRespostaSelecionada(perguntaId: String, valorResposta: Int) {
        val respostasAtuais = _uiState.value.respostasSelecionadas.toMutableMap()
        respostasAtuais[perguntaId] = valorResposta

        _uiState.update { estadoAtual ->
            val todasRespondidas = estadoAtual.perguntas.size == respostasAtuais.size && estadoAtual.perguntas.isNotEmpty()
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
            val sucesso = repositorio.salvarRespostasDoMes(respostasParaSalvar)
            if (sucesso) {
                _uiState.update { it.copy(envioConcluido = true) }
            } else {
                _uiState.update { it.copy(erro = "Você já respondeu o questionário este mês!") }
            }
        }
    }
}