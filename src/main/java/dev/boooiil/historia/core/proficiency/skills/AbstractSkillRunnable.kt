package dev.boooiil.historia.core.proficiency.skills

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.database.internal.PlayerStorage
import dev.boooiil.historia.core.player.HistoriaPlayer
import dev.boooiil.historia.core.proficiency.Proficiency
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

abstract class AbstractSkillRunnable : BukkitRunnable(), ISkillRunnable {

    override val type: SkillType
        get() = throw UnsupportedOperationException()

    override val name: NamespacedKey
        get() = throw UnsupportedOperationException()

    override fun execute(vararg skillSuppliers: SkillSupplier<*>) {
        throw UnsupportedOperationException()
    }

    override fun register() {
        throw UnsupportedOperationException()
    }

    override fun deregister() {
        throw UnsupportedOperationException()
    }

    override fun run() {
        throw UnsupportedOperationException()
    }
    
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
        return HistoriaCore.PROFICIENCY_REGISTRY.get(historiaPlayer.proficiency.name)
    }
}
