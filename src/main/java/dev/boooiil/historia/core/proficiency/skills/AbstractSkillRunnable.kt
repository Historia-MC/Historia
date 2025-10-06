package dev.boooiil.historia.core.proficiency.skills

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.scheduler.BukkitRunnable

abstract class AbstractSkillRunnable(section: ConfigurationSection) : AbstractSkill(section), ISkillRunnable {

    abstract override fun run()

    override fun runnable(): BukkitRunnable {
        return object : BukkitRunnable() {
            override fun run() {
                this@AbstractSkillRunnable.run()
            }
        }
    }
}
