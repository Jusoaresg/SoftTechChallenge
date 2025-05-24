import kotlinx.serialization.Serializable

@Serializable
data class WorldTimeResponse(
    val datetime: String
)