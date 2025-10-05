package dev.boooiil.historia.core.proficiency.skills

import org.bukkit.scheduler.BukkitRunnable

interface ISkillRunnable : ISkill, Runnable {
    override fun run()
    fun runnable(): BukkitRunnable
}
