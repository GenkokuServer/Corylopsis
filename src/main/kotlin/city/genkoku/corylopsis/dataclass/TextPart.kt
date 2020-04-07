package city.genkoku.corylopsis.dataclass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.md_5.bungee.api.ChatColor

@Serializable
data class TextPart(
    val text: String,
    val color: ChatColor = ChatColor.WHITE,
    @SerialName("hovertext") val hoverText: String? = null
)
