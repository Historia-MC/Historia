package dev.boooiil.historia.core.proficiency.skills

import org.bukkit.scheduler.BukkitRunnable

abstract class AbstractSkillRunnable : AbstractSkill(), ISkillRunnable {

    abstract override fun run()

    override fun runnable(): BukkitRunnable {
        return object : BukkitRunnable() {
            override fun run() {
                this@AbstractSkillRunnable.run()
            }
        }
    }
}
