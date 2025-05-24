import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

object TimeApiService {
    suspend fun fetchTime(): WorldTimeResponse? = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://worldtimeapi.org/api/timezone/America/Sao_Paulo")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            val data = connection.inputStream.bufferedReader().use { it.readText() }

            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString(WorldTimeResponse.serializer(), data)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}