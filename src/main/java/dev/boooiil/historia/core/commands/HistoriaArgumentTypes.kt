package dev.boooiil.historia.core.commands

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.proficiency.skills.ISkill

object HistoriaArgumentTypes {
    fun proficiency(): RegistryArgument<Proficiency> = RegistryArgument(HistoriaCore.PROFICIENCY_REGISTRY)
    fun skill(): RegistryArgument<ISkill> = RegistryArgument(HistoriaCore.SKILL_REGISTRY)
}