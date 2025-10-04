package br.com.fiap.softtech.database

import br.com.fiap.softtech.data.Pergunta
import br.com.fiap.softtech.data.TipoQuestionario
import com.mongodb.client.model.Filters
import kotlinx.coroutines.runBlocking

object SetupService {
    private val perguntasCollection = MongoDbManager.perguntasCollection

    private val initialQuestions = listOf(
        Pergunta(text = "Como você avalia a sua carga de trabalho?", order = 1, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Sua carga de trabalho afeta a sua qualidade de vida?", order = 2, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Você trabalha além do seu horário regular?", order = 3, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Você tem apresentado sintomas como insônia, irritabilidade e cansaço extremo?", order = 4, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Você sente que sua saúde mental prejudica sua função no trabalho?", order = 5, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Como está o seu relacionamento com seu chefe numa escala de 1 a 5 (sendo 1 ruim e 5 ótimo)?", order = 6, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Como está o relacionamento com seus colegas de trabalho numa escala de 1 a 5? (sendo 1 ruim e 5 ótimo)", order = 7, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Sinto que sou tratado com respeito pelos meus colegas de trabalho (sendo 1 Discordo Completamente e 5 Concordo Plenamente):" , order = 8, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Consigo me relacionar de forma saudável e colaborativa com minha equipe (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 9, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Tenho liberdade de expressar minhas opiniões sem medo de retaliações (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 10, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Me sinto acolhido e parte do time onde trabalho (sendo 1 Discordo Completamente e 5 Concordo Plenamente):", order = 11, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Sinto que existe espírito de cooperação entre os colaboradores (sendo 1 Discordo Completamente e 5 Concordo Plenamente):", order = 12, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Recebo Orientações Claras e Objetivas sobre minhas atividades e responsabilidades (sendo 1 Discordo Completamente e 5 Concordo Plenamente):", order = 13, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Sinto que posso me comunicar abertamente com a minha liderança (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 14, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "As informações importantes circulam de forma eficiente dentro das empresas (sendo 1 Discordo Completamente e 5 Concordo Plenamente):", order = 15, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Tenho calreza sobre as metas e os resultados esperados de mim (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 16, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Minha liderança demonstra interesse pelo meu bem-estar e trabalho (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 17, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Minha liderança está disponível para me ouvir quando necessário (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 18, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Me sinto confortável para reportar problemas ou dificuldades ao meu líder (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 19, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Minha liderança reconhece minhas entregas e esforço (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 20, tipo = TipoQuestionario.MENSAL),
        Pergunta(text = "Existe confiança e transparência na relação com a minha liderança (sendo 1 Discordo Completamente e 5 Concordo Plenamente): ", order = 21, tipo = TipoQuestionario.MENSAL),

        Pergunta(text = "Como está o seu humor hoje?", order = 22, tipo = TipoQuestionario.DIARIO),
        Pergunta(text = "Como está se sentindo em relação ao trabalho hoje?", order = 23, tipo = TipoQuestionario.DIARIO)
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