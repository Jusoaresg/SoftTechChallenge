package br.com.fiap.softtech.database

import com.mongodb.MongoClientSettings
import br.com.fiap.softtech.data.Pergunta
import br.com.fiap.softtech.data.RespostaDiaria
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

object MongoDbManager {
    private const val CONNECTION_STRING = "mongodb://localhost:27017"
    private const val DATABASE_NAME = "saude_mental_db"

    // Configuração para permitir que o driver MongoDB use nossas Data Classes
    private val pojoCodecRegistry = CodecRegistries.fromRegistries(
        MongoClientSettings.getDefaultCodecRegistry(),
        CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true).build()
        )
    )

    // Cria o cliente de conexão
    private val client = MongoClient.create(CONNECTION_STRING)

    // Acessa o banco de dados
    private val database = client.getDatabase(DATABASE_NAME)
        .withCodecRegistry(pojoCodecRegistry)

    // Acessa as collections
    val perguntasCollection = database.getCollection<Pergunta>("perguntas")
    val respostasCollection = database.getCollection<RespostaDiaria>("respostas_diarias")

    fun close() {
        client.close()
    }
}