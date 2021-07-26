import java.io.FileInputStream
import java.util.*

object Project {

    object Config {
        const val ANDROID_COMPILE = 30
        const val ANDROID_TARGET = 30
        const val ANDROID_MIN = 21
        const val BUILD_TOOL = "30.0.3"
    }

    object Version {
        private const val VERSION_FILE_NAME = "version.properties"

        data class VersionProperty(
            val code: Int,
            val name: String,
        )

        private fun getVersionProperty(): VersionProperty {
            val prop = loadVersionVersionPropertyFile()
            return VersionProperty(
                prop.getProperty("version.code", "1").toInt(),
                prop.getProperty("version.name", "1")
            )
        }

        private fun loadVersionVersionPropertyFile(): Properties {
            FileInputStream(VERSION_FILE_NAME).use { return Properties().apply { load(it) } }
        }

        val value = getVersionProperty()
    }
}
