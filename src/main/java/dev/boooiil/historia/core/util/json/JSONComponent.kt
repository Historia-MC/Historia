package dev.boooiil.historia.core.util.json

import dev.boooiil.historia.core.util.JSONSerializable
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.NamespacedKey

class JSONComponent(
    var key: String,
    var value: Any? = null
) : JSONSerializable {

    private var members: MutableSet<JSONComponent>? = null

    // --- Bracket operator: get child node by key ---
    operator fun get(k: String): JSONComponent {
        // Ensure members exists
        if (members == null) members = mutableSetOf()

        // Try to find existing child
        val existing = members!!.find { it.key == k }
        if (existing != null) return existing

        // If not found, create a new child
        val child = JSONComponent(k)
        members!!.add(child)
        return child
    }

    // --- Bracket operator: assign value to key ---
    operator fun set(k: String, v: Any?) {
        // Ensure members exists
        if (members == null) members = mutableSetOf()

        // Check if key exists
        val existing = members!!.find { it.key == k }
        if (existing != null) {
            existing.value = v
        } else {
            members!!.add(JSONComponent(k, v))
        }
    }

    fun test() {
        val some = this["some"]
        some["thing"]

        this["some"] = "another"
        this["some"]["thing"] = 9
        this["some"]["thing"]
    }

    override fun toJSON(): String {
        // Leaf node
        if (members.isNullOrEmpty()) {
            println("Serializing $key")
            // should return something like
            // "key": "value"
            return serialize(value)
        }


        // Object node
        val inner = members!!.joinToString(",") { "{${it.toJSON()}}" }
        println("Starting with $key")
        return "{\"$key\": $inner}"
    }

    private fun serialize(v: Any?): String = when (v) {
        null -> "null"
        is String -> JSONUtils.fromValue(key, v)
        is Int -> JSONUtils.fromValue(key, v)
        is Float -> JSONUtils.fromValue(key, v)
        is Long -> JSONUtils.fromValue(key, v)
        is Double -> JSONUtils.fromValue(key, v)
        is Boolean -> JSONUtils.fromValue(key, v)
        is NamespacedKey -> JSONUtils.fromValue(key, v)
        is JSONSerializable -> JSONUtils.fromValue(key, v)
        else -> "\"$v\""
    }

}