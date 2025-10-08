package dev.boooiil.historia.core.items.types

import java.util.*

enum class Quality(val displayName: String) {
    POOR("Poor"),
    COMMON("Common"),
    PERFECT("Perfect");

    fun lowercase(): String {
        return displayName.lowercase(Locale.getDefault())
    }

    companion object {
        fun fromString(str: String?): Quality? {
            return entries.find { it.name.lowercase() == str }
        }
    }
}
