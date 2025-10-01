package br.com.fiap.softtech.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class HumorDiario(
    @BsonId val id: ObjectId = ObjectId(),
    val date: String, // Formato "AAAA-MM-DD" para o controle diário
    val respostaHumor: String,
    val respostaTrabalho: String
)