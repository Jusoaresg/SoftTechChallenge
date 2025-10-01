package br.com.fiap.softtech.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Resposta(
    val questionId: String,
    val responseValue: Int
)
data class RespostaMensal(
    @BsonId val id: ObjectId = ObjectId(),
    val date: String, // Este campo armazena "AAAA-MM"
    val answers: List<Resposta>
)