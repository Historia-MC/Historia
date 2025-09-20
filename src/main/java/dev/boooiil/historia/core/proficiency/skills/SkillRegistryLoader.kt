package dev.boooiil.historia.core.proficiency.skills

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.file.FileIO
import dev.boooiil.historia.core.file.FileKeys
import dev.boooiil.historia.core.proficiency.skills.passive.block.SkillBypassBlockRestriction
import dev.boooiil.historia.core.proficiency.skills.passive.block.SkillNoConsumeBlock
import dev.boooiil.historia.core.proficiency.skills.passive.entity.SkillAnimalBreed
import dev.boooiil.historia.core.proficiency.skills.passive.entity.SkillEntityDrop
import dev.boooiil.historia.core.proficiency.skills.passive.entity.SkillEntityDropRestriction
import dev.boooiil.historia.core.proficiency.skills.passive.item.SkillAttributeOnItem
import dev.boooiil.historia.core.proficiency.skills.passive.item.SkillAttributeWithItem
import dev.boooiil.historia.core.proficiency.skills.passive.item.SkillEnchantOnItem
import dev.boooiil.historia.core.proficiency.skills.passive.item.SkillUseNametag
import dev.boooiil.historia.core.util.CoreLogger
import org.bukkit.configuration.file.FileConfiguration

object SkillRegistryLoader {
    fun load() {
        val config: FileConfiguration = FileIO.get(FileKeys.SKILLS)

        config.getKeys(false)
            .filter { it != "version" }
            .forEach { key ->
                val section = config.getConfigurationSection(key) ?: run {
                    CoreLogger.errorToConsole("Configuration section is null for skill: $key")
                    return@forEach
                }

                val skillName = getNamespacedKey(key)
                val skillType = section.getString("type") ?: run {
                    CoreLogger.errorToConsole("Skill type is null for skill: $key")
                    return@forEach
                }

                when (skillType) {
                    "attribute_with_item" -> {
                        SkillAttributeWithItem(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "attribute_on_item" -> {
                        SkillAttributeOnItem(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "enchant_on_item" -> {
                        SkillEnchantOnItem(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "nametag" -> {
                        SkillUseNametag(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "entity_drop" -> {
                        SkillEntityDrop(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "entity_drop_restriction" -> {
                        SkillEntityDropRestriction(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "entity_breed_restriction" -> {
                        SkillAnimalBreed(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "entity_tame_restriction" -> {
                        SkillAnimalBreed(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "no_consume_block" -> {
                        SkillNoConsumeBlock(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    "bypass_block_restriction" -> {
                        SkillBypassBlockRestriction(section).also {
                            HistoriaCore.SKILL_REGISTRY.register(skillName, it)
                            it.register()
                        }
                    }

                    else -> CoreLogger.errorToConsole("Unknown skill type: $skillType for skill: $key")
                }
            }
    }
}