package dev.boooiil.historia.core.items.types

import java.util.*

enum class Qualities(val displayName: String) {
    POOR("Poor"),
    COMMON("Common"),
    PERFECT("Perfect");

    fun lowercase(): String {
        return displayName.lowercase(Locale.getDefault())
    }

    companion object {
        fun fromString(str: String?): Qualities? {
            return entries.find { it.name.lowercase() == str }
        }
    }
}
