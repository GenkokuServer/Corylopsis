package city.genkoku.corylopsis

import city.genkoku.corylopsis.dataclass.Translation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.asSequence

object Corylopsis {

    fun parseTranslation(directory: Path) {
        Files.list(directory).forEach {
            if (it.toString().endsWith(".json")) {
                val json = Files.newBufferedReader(it).use { reader ->
                    reader.lines().asSequence().joinToString(separator = "")
                }
                val translation = Json(JsonConfiguration.Stable).parse(Translation.serializer(), json)
                I18n.translations[it.fileName.toString().replace(".json", "")] = translation
            }
        }
    }

}
