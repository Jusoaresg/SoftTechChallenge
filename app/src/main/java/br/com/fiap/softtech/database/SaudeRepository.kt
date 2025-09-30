package br.com.fiap.softtech.database

import br.com.fiap.softtech.data.Pergunta
import br.com.fiap.softtech.data.Resposta
import br.com.fiap.softtech.data.RespostaDiaria
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class SaudeRepository {
    private val perguntasCollection = MongoDbManager.perguntasCollection
    private val respostasCollection = MongoDbManager.respostasCollection
    private val formatadorDeData = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * Busca no banco de dados todas as perguntas ativas, ordenadas pela propriedade 'order'.
     */
    suspend fun buscarPerguntasAtivas(): List<Pergunta> {
        return perguntasCollection
            .find(Filters.eq("active", true))
            .sort(Sorts.ascending("order"))
            .toList()
    }

    /**
     * Verifica se já existe um registro de respostas para a data de hoje.
     */
    suspend fun verificarRespostaHoje(): Boolean {
        val hojeString = LocalDate.now().format(formatadorDeData)
        // CORREÇÃO: Usamos find() para criar a busca e .firstOrNull() para pegar o primeiro item ou null
        val resposta = respostasCollection.find(Filters.eq("date", hojeString)).firstOrNull()
        return resposta != null
    }

    /**
     * Salva uma lista de respostas para o dia atual.
     */
    suspend fun salvarRespostasDoDia(respostas: List<Resposta>): Boolean {
        if (verificarRespostaHoje()) {
            println("Erro: O usuário já respondeu hoje.")
            return false
        }
        val hojeString = LocalDate.now().format(formatadorDeData)
        val respostaDiaria = RespostaDiaria(date = hojeString, answers = respostas)
        respostasCollection.insertOne(respostaDiaria)
        println("Respostas salvas com sucesso para o dia $hojeString.")
        return true
    }

    /**
     * Busca os dados dos últimos 7 dias para o relatório semanal.
     */
    suspend fun buscarDadosRelatorioSemanal(): List<RespostaDiaria> {
        val dataFinal = LocalDate.now()
        val dataInicial = dataFinal.minusDays(6)

        val filtroDeData = Filters.and(
            Filters.gte("date", dataInicial.format(formatadorDeData)),
            Filters.lte("date", dataFinal.format(formatadorDeData))
        )

        return respostasCollection.find(filtroDeData).toList()
    }
}