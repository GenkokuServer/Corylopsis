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
            if (it.endsWith(".json")) {
                val file = Files.createFile(it)
                val json = Files.newBufferedReader(file).use { reader ->
                    reader.lines().asSequence().joinToString()
                }
                val translation = Json(JsonConfiguration.Stable).parse(Translation.serializer(), json)
                I18n.translations.put(it.fileName.toString().replace(".json", ""), translation)
            }
        }
    }

}
