package br.com.fiap.softtech.data

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Pergunta(
    @BsonId val id: ObjectId = ObjectId(), // Gera um ID único automaticamente
    val text: String,
    val order: Int,
    val active: Boolean = true
)