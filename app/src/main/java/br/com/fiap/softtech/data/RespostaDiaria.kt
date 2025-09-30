package br.com.fiap.softtech.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Resposta(
    val questionId: String, // Armazenaremos o ID da pergunta como String
    val responseValue: Int
)

data class RespostaDiaria(
    @BsonId val id: ObjectId = ObjectId(),
    val date: String, // Formato "YYYY-MM-DD"
    val answers: List<Resposta>
)