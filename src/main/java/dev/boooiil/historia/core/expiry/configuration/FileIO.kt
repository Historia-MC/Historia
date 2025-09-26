package dev.boooiil.historia.core.expiry.configuration

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.expiry.HistoriaExpiry
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader

object FileIO {
    fun find(files: Array<File>, path: String?): Boolean = files.any{ it.getName() == path }

    fun get(path: String): YamlConfiguration {
        val config: YamlConfiguration
        val dataFolder: File = HistoriaCore.instance.dataFolder
        if (find(dataFolder.listFiles(), path)) {
            val var10000 = arrayOf("Obtained file from external directory: ", null)
            val var10003 = dataFolder.getPath()
            var10000[1] = var10003 + "\\" + path
            // CoreLogger.debugToConsole(*var10000)
            val file = File(dataFolder.getPath(), path)
            config = YamlConfiguration.loadConfiguration(file)
        } else {
            CoreLogger.debugToConsole("Obtained file from internal directory: $path")
            val stream = FileIO::class.java.getClassLoader().getResourceAsStream(path) ?: throw IOException("Could not find $path")
            val reader: Reader = InputStreamReader(stream)
            config = YamlConfiguration.loadConfiguration(reader)
        }

        return config
    }
}
