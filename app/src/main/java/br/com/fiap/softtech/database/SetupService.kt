package br.com.fiap.softtech.database

import br.com.fiap.softtech.data.Pergunta
import com.mongodb.client.model.Filters
import kotlinx.coroutines.runBlocking

object SetupService {
    private val perguntasCollection = MongoDbManager.perguntasCollection

    // Lista de perguntas passadas na planilha excel (AJUSTAR DE ACORDO)
    private val initialQuestions = listOf(
        Pergunta(text = "Como você avalia a qualidade do seu sono hoje?", order = 1),
        Pergunta(text = "Qual o seu nível de energia ao acordar?", order = 2),
        Pergunta(text = "Como você se sentiu em relação às suas atividades diárias?", order = 3),
        Pergunta(text = "Qual foi o seu nível de interação social hoje?", order = 4)
    )

    fun inicializarPerguntas() = runBlocking {
        // Verifica se a collection já tem perguntas
        val perguntaCount = perguntasCollection.countDocuments()

        if (perguntaCount == 0L) {
            println("Nenhuma pergunta encontrada. Populando a collection 'questions'...")
            perguntasCollection.insertMany(initialQuestions)
            println("${initialQuestions.size} perguntas foram inseridas com sucesso.")
        } else {
            println("A collection 'questions' já está populada.")
        }
    }
}