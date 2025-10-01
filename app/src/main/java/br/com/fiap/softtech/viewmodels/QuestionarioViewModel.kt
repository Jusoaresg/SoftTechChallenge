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

    // --- DEFINIÇÃO DOS 3 TIPOS DE RESPOSTAS PARA O QUESTIONÁRIO MENSAL ---

    // Mapeamento para a pergunta 1
    private val opcoesCargaTrabalho = mapOf(
        "Muito Leve" to 1,
        "Leve" to 2,
        "Média" to 3,
        "Alta" to 4,
        "Muito Alta" to 5
    )

    // Mapeamento para as perguntas 2 a 5
    private val opcoesFrequencia = mapOf(
        "Não" to 1,
        "Raramente" to 2,
        "Às vezes" to 3,
        "Frequentemente" to 4,
        "Sempre" to 5
    )

    // Mapeamento para as perguntas 6 a 12 (escala de 1 a 5)
    private val opcoesEscalaNumerica = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5
    )

    // Função pública para a UI buscar as opções corretas para cada pergunta
    fun getOpcoesParaPergunta(pergunta: Pergunta): Map<String, Int> {
        return when (pergunta.order) {
            1 -> opcoesCargaTrabalho
            in 2..5 -> opcoesFrequencia
            in 6..21 -> opcoesEscalaNumerica
            else -> emptyMap()
        }
    }
    // --- FIM DA DEFINIÇÃO DAS RESPOSTAS ---

    init {
        carregarPerguntas()
    }

    private fun carregarPerguntas() {
        viewModelScope.launch {
            try {
                val perguntasDoBanco = repositorio.buscarPerguntasMensais()
                _uiState.update { estadoAtual ->
                    estadoAtual.copy(carregando = false, perguntas = perguntasDoBanco)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(carregando = false, erro = "Falha ao carregar perguntas.") }
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