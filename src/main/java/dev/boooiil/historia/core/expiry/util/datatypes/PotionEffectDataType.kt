package dev.boooiil.historia.core.expiry.util.datatypes

import dev.boooiil.historia.core.HistoriaCore
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PotionEffectDataType : PersistentDataType<PersistentDataContainer, PotionEffect> {

    override fun getPrimitiveType() = PersistentDataContainer::class.java
    override fun getComplexType() = PotionEffect::class.java

    override fun toPrimitive(
        effect: PotionEffect,
        adapterContext: PersistentDataAdapterContext
    ): PersistentDataContainer {
        val container = adapterContext.newPersistentDataContainer()

        container.set(TYPE_KEY, PersistentDataType.STRING, effect.type.name)
        container.set(DURATION_KEY, PersistentDataType.INTEGER, effect.duration)
        container.set(AMPLIFIER_KEY, PersistentDataType.INTEGER, effect.amplifier)

        return container
    }

    override fun fromPrimitive(
        container: PersistentDataContainer,
        adapterContext: PersistentDataAdapterContext
    ): PotionEffect {
        val typeName = container.get<String, String>(TYPE_KEY, PersistentDataType.STRING)
        val type = PotionEffectType.getByName(typeName!!)

        val duration: Int = container.get<Int, Int>(DURATION_KEY, PersistentDataType.INTEGER)!!
        val amplifier: Int = container.get<Int, Int>(AMPLIFIER_KEY, PersistentDataType.INTEGER)!!

        return PotionEffect(type!!, duration, amplifier)
    }
}

private val TYPE_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("type")
private val DURATION_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("duration")
private val AMPLIFIER_KEY: NamespacedKey = HistoriaCore.getNamespacedKey("amplifier")