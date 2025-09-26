package dev.boooiil.historia.core.expiry.util

import dev.boooiil.historia.core.expiry.configuration.FileMap.ResourceKeys
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

object ConfigUtil {
    private val configFileNames: MutableList<String> = mutableListOf()

    init {
        for (resourceKey in ResourceKeys.entries) {
            configFileNames.add(resourceKey.key)
        }
    }

    fun checkFiles() {
        Logging.Companion.infoToConsole("Checking existance and version of config files.")

        for (fileName in configFileNames) {
            val diskFile = File(HistoriaExpiry.plugin.dataFolder, fileName)

            if (!diskFile.exists()) {
                Logging.Companion.infoToConsole("Missing config file: $fileName has been saved to disk from resources.")
                Logging.Companion.infoToConsole("Location: " + diskFile.absolutePath)
                HistoriaExpiry.plugin.saveResource(fileName, false)
                continue
            }

            val diskConfig = yamlFromSource(diskFile)
            val resource = HistoriaExpiry.plugin.getResource(fileName) ?: continue
            val jarConfig: YamlConfiguration = yamlFromSource(resource)

            val diskVersion = diskConfig.getInt("version")
            val jarVersion = jarConfig.getInt("version")

            if (diskVersion < jarVersion) {
                Logging.Companion.infoToConsole("Outdated config file ($diskVersion): $fileName has been replaced on disk by the newer version $jarVersion.")
                HistoriaExpiry.plugin.saveResource(fileName, true)
                continue
            }
        }

        Logging.Companion.infoToConsole("Completed checks of existance and version of config files.")
    }

    fun yamlFromSource(stream: InputStream): YamlConfiguration {
        val reader: Reader = InputStreamReader(stream, Charset.defaultCharset())
        return YamlConfiguration.loadConfiguration(reader)
    }

    fun yamlFromSource(file: File): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(file)
    }
}
