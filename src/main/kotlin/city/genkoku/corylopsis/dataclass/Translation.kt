package city.genkoku.corylopsis.dataclass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Translation(
    val chats: Map<String, List<TextPart>>,
    @SerialName("hovertexts") val hoverTexts: Map<String, List<List<TextPart>>>
)
