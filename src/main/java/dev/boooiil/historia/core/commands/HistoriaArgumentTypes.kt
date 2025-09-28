package dev.boooiil.historia.core.commands

import dev.boooiil.historia.core.items.HistoriaItem
import dev.boooiil.historia.core.proficiency.Proficiency
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.registry.RegistryHolder

object HistoriaArgumentTypes {
    fun proficiency(): RegistryArgument<Proficiency> = RegistryArgument(RegistryHolder.PROFICIENCY_REGISTRY)
    fun skill(): RegistryArgument<ISkill> = RegistryArgument(RegistryHolder.SKILL_REGISTRY)
    fun item(): RegistryArgument<HistoriaItem> = RegistryArgument(RegistryHolder.ITEM_REGISTRY)
}