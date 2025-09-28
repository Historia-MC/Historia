package dev.boooiil.historia.core.proficiency.skills

import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.CoreLogger
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

abstract class AbstractSkill : ISkill {

    override val type: SkillType
        get() = throw UnsupportedOperationException()

    override val name: NamespacedKey
        get() = throw UnsupportedOperationException()

    abstract override fun execute(vararg skillSuppliers: SkillSupplier<*>)

    abstract override fun register()

    abstract override fun deregister()

    override fun hasSkill(historiaPlayer: HistoriaPlayer): Boolean {
        return getProficiency(historiaPlayer)?.hasSkill(this) ?: false
    }

    override fun hasLevelRequirement(historiaPlayer: HistoriaPlayer): Boolean {
        return (getProficiency(historiaPlayer)?.skills?.get(this) ?: 0) > historiaPlayer.level

    }

    override fun getHistoriaPlayer(player: Player): HistoriaPlayer {
        return PlayerStorage.getPlayer(player.uniqueId)
    }

    override fun getProficiency(historiaPlayer: HistoriaPlayer): Proficiency? {
        return RegistryHolder.PROFICIENCY_REGISTRY.get(historiaPlayer.proficiency.key)
    }

    override fun <T> getOrThrow(skillSuppliers: Array<out SkillSupplier<*>>, index: Int): T {
        @Suppress("UNCHECKED_CAST")
        return skillSuppliers[index].get() as? T
            ?: error("Tried to get value of type T at position $index in $name but was actually ${skillSuppliers[index]::class}.")
    }

    override fun <T> getOrDefault(skillSuppliers: Array<out SkillSupplier<*>>, index: Int, default: T): T {
        @Suppress("UNCHECKED_CAST")
        return (skillSuppliers[index].get() as? T)
            ?: run {
                CoreLogger.warnToConsole(
                    "Tried to get value of type T at position $index in $name but was actually ${skillSuppliers[index]::class}."
                )
                default
            }
    }
}