package dev.boooiil.historia.core.proficiency.skills

import org.bukkit.NamespacedKey
import org.bukkit.scheduler.BukkitRunnable

abstract class AbstractSkillRunnable : BukkitRunnable(), ISkillRunnable {

    override val type: SkillType
        get() = throw UnsupportedOperationException()

    override val name: NamespacedKey
        get() = throw UnsupportedOperationException()

    override fun execute(vararg skillSuppliers: SkillSupplier<*>?) {
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
}
