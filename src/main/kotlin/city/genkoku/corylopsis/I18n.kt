package city.genkoku.corylopsis

import city.genkoku.corylopsis.dataclass.Translation
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent

object I18n {

    internal val translations = mutableMapOf<String, Translation>()

    fun getChatText(node: String, language: String = "ja_JP", replacements: Map<String, String> = mapOf()): BaseComponent {
        val translation = translations.takeIf { it.containsKey(language) }?.get(language)
            ?: translations["ja_JP"]
        translation ?: throw IllegalStateException("Could not load translations of the language \"$language\"")
        val chatText = translation.chats.takeUnless { it.isNullOrEmpty() }?.get(node)
            ?: throw IllegalStateException("The specified translation \"$node\" is not found")

        val message = TextComponent()

        chatText.map { part ->
            val text = part.text
            replacements.forEach { replacement ->
                text.replace(replacement.key, replacement.value, true)
            }
            TextComponent(text).also { component ->
                component.color = part.color
                if (!part.hoverText.isNullOrEmpty()) {
                    component.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, getHoverText(part.hoverText, language, replacements))
                }
            }
        }.forEach { mappedPart ->
            message.addExtra(mappedPart)
        }

        return message
    }

    fun getHoverText(node: String, language: String = "ja_JP", replacements: Map<String, String> = mapOf()): Array<BaseComponent> {
        val translation = translations.takeIf { it.containsKey(language) }?.get(language)
            ?: translations["ja_JP"]
        translation ?: throw IllegalStateException("Could not load translations of the language \"$language\"")
        val hoverTexts = translation.hoverTexts.takeUnless { it.isNullOrEmpty() }?.get(node)
            ?: throw IllegalStateException("The specified translation \"$node\" is not found")

        val lines = mutableListOf<BaseComponent>()

        hoverTexts.forEach { parts ->
            val message = TextComponent()
            parts.map { part ->
                val text = part.text
                replacements.forEach { replacement ->
                    text.replace(replacement.key, replacement.value, true)
                }
                TextComponent(text).also { component ->
                    component.color = part.color
                }
            }.forEach { mappedPart ->
                message.addExtra(mappedPart)
            }
            lines.add(message)
        }

        return lines.toTypedArray()
    }

}
