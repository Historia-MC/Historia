package dev.boooiil.historia.core.expiry.util

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.expiry.configuration.FileMap.ResourceKeys
import dev.boooiil.historia.core.util.CoreLogger
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader

/**
 * It checks if a file exists in the plugin's data folder, and if it does, it
 * loads it from there. If
 * it doesn't, it loads it from the jar.
 */
object FileGetter {
    /**
     * It takes an array of files and a string, and returns true if the string is
     * the name of one of
     * the files in the array
     *
     * @param files The array of files to check
     * @param check The name of the file you want to check for.
     * @return A boolean value.
     */
    fun find(files: Array<File>, check: ResourceKeys): Boolean {
        for (file in files) {
            if (file.getName() == check.key) return true
        }

        return false
    }

    /**
     * If the file exists in the external directory, load it from there. If it
     * doesn't, load it from
     * the internal directory
     *
     * @param check The file name to check for.
     * @return A YamlConfiguration object.
     */
    @JvmStatic
    fun get(check: ResourceKeys): YamlConfiguration {
        val config: YamlConfiguration

        if (find(HistoriaCore.instance.dataFolder.listFiles(), check)) {
            CoreLogger.debugToConsole(
                "Obtained file from external directory: ",
                HistoriaCore.instance.dataFolder.path + "\\" + check.key
            )

            val file: File = File(HistoriaCore.instance.dataFolder.path, check.key)

            config = YamlConfiguration.loadConfiguration(file)
        } else {
            CoreLogger.debugToConsole("Obtained file from internal directory: " + check.key)

            val stream = FileGetter::class.java.getClassLoader().getResourceAsStream(check.key)
                ?: throw IOException("Could not find $check.key")
            val reader: Reader = InputStreamReader(stream)

            config = YamlConfiguration.loadConfiguration(reader)
        }

        return config
    }
}
