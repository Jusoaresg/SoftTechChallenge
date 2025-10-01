package br.com.fiap.softtech.database

import br.com.fiap.softtech.data.* // Importa tudo do pacote data, incluindo o Enum
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SaudeRepository {
    private val perguntasCollection = MongoDbManager.perguntasCollection
    private val respostasCollection = MongoDbManager.respostasCollection
    private val humorDiarioCollection = MongoDbManager.humorDiarioCollection

    // Formatter para o questionário MENSAL
    private val formatadorDataMensal = DateTimeFormatter.ofPattern("yyyy-MM")
    // Formatter para o humor DIÁRIO
    private val formatadorDataDiaria = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    // --- MÉTODOS DO QUESTIONÁRIO MENSAL ---

    /**
     * Busca no banco de dados apenas as perguntas do tipo MENSAL.
     */
    suspend fun buscarPerguntasMensais(): List<Pergunta> {
        return perguntasCollection
            .find(Filters.eq("tipo", TipoQuestionario.MENSAL.name)) // Filtra pelo tipo MENSAL
            .sort(Sorts.ascending("order"))
            .toList()
    }

    /**
     * Verifica se já existe um registro de respostas para o MÊS ATUAL.
     */
    suspend fun verificarRespostaMesAtual(): Boolean {
        val mesAtualString = LocalDate.now().format(formatadorDataMensal)
        val resposta = respostasCollection.find(Filters.eq("date", mesAtualString)).firstOrNull()
        return resposta != null
    }

    /**
     * Salva uma lista de respostas para o MÊS ATUAL.
     */
    suspend fun salvarRespostasDoMes(respostas: List<Resposta>): Boolean {
        if (verificarRespostaMesAtual()) {
            println("Erro: O usuário já respondeu este mês.")
            return false
        }
        val mesAtualString = LocalDate.now().format(formatadorDataMensal)
        val respostaMensal = RespostaMensal(date = mesAtualString, answers = respostas)
        respostasCollection.insertOne(respostaMensal)
        println("Respostas salvas com sucesso para o mês $mesAtualString.")
        return true
    }

    /**
     * Busca todo o histórico de respostas mensais, ordenado do mais recente para o mais antigo.
     */
    suspend fun buscarHistoricoRespostas(): List<RespostaMensal> {
        return respostasCollection
            .find()
            .sort(Sorts.descending("date"))
            .toList()
    }


    // --- MÉTODOS DO DIÁRIO DE HUMOR ---

    /**
     * Busca no banco de dados apenas as perguntas do tipo DIARIO.
     */
    suspend fun buscarPerguntasDiarias(): List<Pergunta> {
        return perguntasCollection
            .find(Filters.eq("tipo", TipoQuestionario.DIARIO.name)) // Filtra pelo tipo DIARIO
            .sort(Sorts.ascending("order"))
            .toList()
    }

    /**
     * Verifica se o usuário já respondeu o questionário de humor no dia de hoje.
     */
    suspend fun verificarHumorDiarioHoje(): Boolean {
        val hojeString = LocalDate.now().format(formatadorDataDiaria)
        val resposta = humorDiarioCollection.find(Filters.eq("date", hojeString)).firstOrNull()
        return resposta != null
    }

    /**
     * Salva as respostas de humor do dia.
     */
    suspend fun salvarHumorDiario(humor: HumorDiario): Boolean {
        if (verificarHumorDiarioHoje()) {
            println("Erro: O usuário já respondeu o humor diário hoje.")
            return false
        }
        val hojeString = LocalDate.now().format(formatadorDataDiaria)
        // Garante que a data correta está sendo usada ao salvar
        val humorComDataCorreta = humor.copy(date = hojeString)
        humorDiarioCollection.insertOne(humorComDataCorreta)
        println("Humor diário de ${humorComDataCorreta.date} salvo com sucesso.")
        return true
    }
}