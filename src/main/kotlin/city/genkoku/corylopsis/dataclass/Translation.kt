package city.genkoku.corylopsis.dataclass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Translation(
    val language: String,
    val chats: MutableMap<String, List<TextPart>>,
    @SerialName("hovertexts") val hoverTexts: MutableMap<String, List<List<TextPart>>>
)
