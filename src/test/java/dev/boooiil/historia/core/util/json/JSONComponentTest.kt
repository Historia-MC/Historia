package dev.boooiil.historia.core.util.json

import dev.boooiil.historia.core.BaseTest
import dev.boooiil.historia.core.registry.Registry
import org.bukkit.NamespacedKey
import org.junit.jupiter.api.Test

class JSONComponentTest : BaseTest() {
    @Test
    fun get() {
        val json = JSONComponent("something")

        json["some"]
        json["some"]["other"] = "aaaaa"
        json["some"]["thing"] = 0

        val some = Registry<String>(String::class.java)
        some[NamespacedKey.fromString("historia:str")!!] = "Some"

        json["abba"] = some

        println("output:" + json.toJSON())
    }

    @Test
    fun set() {
    }

    @Test
    fun toJSON() {
    }

    @Test
    fun getKey() {
    }

    @Test
    fun setKey() {
    }

    @Test
    fun getValue() {
    }

    @Test
    fun setValue() {
    }

}