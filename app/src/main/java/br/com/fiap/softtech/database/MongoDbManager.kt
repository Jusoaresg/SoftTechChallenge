package br.com.fiap.softtech.database

import br.com.fiap.softtech.data.HumorDiario
import com.mongodb.MongoClientSettings
import br.com.fiap.softtech.data.Pergunta
import br.com.fiap.softtech.data.RespostaMensal
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider

object MongoDbManager {
    // O endereço 10.0.2.2 é um alias especial que, de dentro do emulador Android,
    // aponta para o localhost (127.0.0.1) do computador host.
    // Isso garante que o app no emulador consiga se conectar ao container Docker.
    //CASO NAO FUNCIONE, BASTA INSERIR O SEU IP NO LUGAR DE 10.0.2.2, FICANDO POR EXMEPLO 192.168.00.00:27017
    private const val CONNECTION_STRING = "mongodb://10.0.2.2:27017"
    // ----------------------------------

    private const val DATABASE_NAME = "saude_mental_db"

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
    val respostasCollection = database.getCollection<RespostaMensal>("respostas_mensais")
    val humorDiarioCollection = database.getCollection<HumorDiario>("humor_diario")

    fun close() {
        client.close()
    }
}