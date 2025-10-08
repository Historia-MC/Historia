package dev.boooiil.historia.core.items

import dev.boooiil.historia.core.util.JSONSerializable

interface ItemComponent : JSONSerializable {
    val key: String
    fun data(qualityModifier: Double? = null): ItemData
}