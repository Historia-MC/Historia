package dev.boooiil.historia.core.items

import dev.boooiil.historia.core.util.JSONSerializable
import net.kyori.adventure.text.Component

interface ItemComponent : JSONSerializable {
    val key: String

    fun data(): ItemData
    fun data(qualityModifier: Double): ItemData = data()

    fun previewLore(qualityModifier: Double): List<Component> = emptyList()
}