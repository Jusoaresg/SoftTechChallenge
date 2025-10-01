package br.com.fiap.softtech.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softtech.data.HumorDiario
import br.com.fiap.softtech.data.Pergunta
import br.com.fiap.softtech.database.SaudeRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class DiarioHumorUiState(
    val carregando: Boolean = true,
    val perguntas: List<Pergunta> = emptyList(),
    val respostaHumor: String = "",
    val respostaTrabalho: String = "",
    val podeEnviar: Boolean = false,
    val envioConcluido: Boolean = false,
    val erro: String? = null,
    val jaRespondeuHoje: Boolean = false
)

class DiarioHumorViewModel(private val repositorio: SaudeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DiarioHumorUiState())
    val uiState: StateFlow<DiarioHumorUiState> = _uiState.asStateFlow()

    // Opções de resposta fixas para cada pergunta
    val opcoesHumor = listOf("Triste", "Alegre", "Cansado", "Ansioso", "Estressado")
    val opcoesTrabalho = listOf("Motivado", "Cansado", "Preocupado", "Estressado", "Animado", "Satisfeito")

    init {
        carregarDadosIniciais()
    }

    private fun carregarDadosIniciais() {
        viewModelScope.launch {
            val jaRespondeu = repositorio.verificarHumorDiarioHoje()
            if (jaRespondeu) {
                _uiState.update { it.copy(carregando = false, jaRespondeuHoje = true) }
            } else {
                val perguntas = repositorio.buscarPerguntasDiarias()
                _uiState.update { it.copy(carregando = false, perguntas = perguntas) }
            }
        }
    }

    fun onRespostaSelecionada(orderDaPergunta: Int, resposta: String) {
        _uiState.update { estadoAtual ->
            val novoEstado = if (orderDaPergunta == 22) {
                estadoAtual.copy(respostaHumor = resposta)
            } else {
                estadoAtual.copy(respostaTrabalho = resposta)
            }
            // Habilita o botão de enviar apenas se ambas as respostas foram selecionadas
            novoEstado.copy(podeEnviar = novoEstado.respostaHumor.isNotBlank() && novoEstado.respostaTrabalho.isNotBlank())
        }
    }

    fun salvarHumor() {
        if (!_uiState.value.podeEnviar) return

        val humorDiario = HumorDiario(
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            respostaHumor = _uiState.value.respostaHumor,
            respostaTrabalho = _uiState.value.respostaTrabalho
        )

        viewModelScope.launch {
            val sucesso = repositorio.salvarHumorDiario(humorDiario)
            if (sucesso) {
                _uiState.update { it.copy(envioConcluido = true) }
            } else {
                _uiState.update { it.copy(erro = "Ocorreu um erro ao salvar.") }
            }
        }
    }
}