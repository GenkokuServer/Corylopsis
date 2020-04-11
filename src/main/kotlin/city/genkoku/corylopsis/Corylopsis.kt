package city.genkoku.corylopsis

import city.genkoku.corylopsis.dataclass.Translation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

object Corylopsis {

    /**
     *  翻訳ファイルをパースします。
     *  @param path 対象ファイルへのパス もしくは、対象ファイルが含まれたディレクトリ。
     */
    fun parseTranslation(path: Path) {
        if (Files.isDirectory(path)) {
            Files.walk(path, 2).asSequence()
        } else {
            sequenceOf(path)
        }.forEach {
            if (it.toString().endsWith(".json")) {
                val json = Files.newBufferedReader(it).use { reader ->
                    reader.lines().asSequence().joinToString(separator = "")
                }
                val parsedTranslation = Json(JsonConfiguration.Stable).parse(Translation.serializer(), json)
                if (I18n.translations.containsKey(parsedTranslation.language)) {
                    val translation = I18n.translations[parsedTranslation.language]
                    translation?.chats?.putAll(parsedTranslation.chats)
                    translation?.hoverTexts?.putAll(parsedTranslation.hoverTexts)
                } else {
                    I18n.translations[parsedTranslation.language] = parsedTranslation
                }
            }
        }
    }

}
