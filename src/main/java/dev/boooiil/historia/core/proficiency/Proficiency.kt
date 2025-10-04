package dev.boooiil.historia.core.proficiency

import dev.boooiil.historia.core.HistoriaCore.Companion.getNamespacedKey
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.proficiency.stats.Stats
import dev.boooiil.historia.core.registry.RegistryHolder
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONSerializable
import dev.boooiil.historia.core.util.JSONUtils
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.jspecify.annotations.NullMarked
import java.util.*

/**
 * This class represents a proficiency that a character can have. It contains
 * information about the proficiency's name, stats, and skills.
 */
@NullMarked
class Proficiency : JSONSerializable {
    /*
         * This is now a configuration class.
         * HistoriaCore -> Init -> Registry<Proficiency> -> Proficiency.register()
         *
         *
         */
    /**
     * Enum of valid proficiencies.
     */
    enum class ProficiencyName(val key: NamespacedKey) {
        NONE(getNamespacedKey("none")),
        ARCHER(getNamespacedKey("archer")),
        WARRIOR(getNamespacedKey("warrior")),
        FISHERMAN(getNamespacedKey("fisherman")),
        MINER(getNamespacedKey("miner")),
        BLACKSMITH(getNamespacedKey("blacksmith")),
        HUNTSMAN(getNamespacedKey("huntsman")),
        APOTHECARY(getNamespacedKey("apothecary")),
        ARCHITECT(getNamespacedKey("architect")),
        LUMBERJACK(getNamespacedKey("lumberjack")),
        FARMER(getNamespacedKey("farmer"));

        val keyLowercase: String
            get() = this.key.key.lowercase(Locale.getDefault())

        companion object {
            @Deprecated("")
            fun fromString(key: String): ProficiencyName {
                var proficiency = NONE

                for (proficiencyName in entries) {
                    if (proficiencyName.key.key.equals(key, ignoreCase = true)) {
                        proficiency = proficiencyName
                    }
                }

                return proficiency
            }

            fun fromNSKey(key: NamespacedKey): ProficiencyName {
                var proficiency = NONE

                for (proficiencyName in entries) {
                    if (proficiencyName.key == key) {
                        proficiency = proficiencyName
                    }
                }

                return proficiency
            }
        }
    }

    /**
     * Returns the name of the proficiency.
     *
     * @return the name of the proficiency
     */
    /**
     * The name of the proficiency.
     */
    val key: NamespacedKey

    /**
     * The skills associated with the proficiency.
     */
    val skills = HashMap<ISkill, Int>()

    constructor(section: ConfigurationSection) {
        CoreLogger.traceToConsole("Loading proficiency from section: " + section.name)

        val sName = section.name
        this.key = getNamespacedKey(sName)

        require(section.contains("skills")) { "Key 'skills' must be specified for proficiency $sName." }

        // proficiency.skills
        val skillSection = section.getConfigurationSection("skills")

        for (skillKey in skillSection!!.getKeys(false)) {
            // proficiency.skills.SkillName

            val skillName = getNamespacedKey(skillKey)
            // proficiency.skills.SkillName.Integer
            val skillLevel = skillSection.getInt(skillKey, -1)

            if (!RegistryHolder.SKILL_REGISTRY.contains(skillName)) {
                CoreLogger.errorToConsole(
                    ("Skill '" + skillKey + "' not found in skill registry for proficiency "
                            + sName + ".")
                )
                continue
            }

            if (skillLevel < 0) {
                CoreLogger.warnToConsole(
                    ("Skill level for skill '" + skillKey + "' is not specified or invalid for "
                            + "proficiency " + sName + ".")
                )
            }

            val skill = RegistryHolder.SKILL_REGISTRY.get(skillName)

            this.skills[skill!!] = skillLevel
        }
    }

    fun registerSkills() {
        for (skill in skills.keys) {
            skill.register()
        }
    }

    fun deregisterSkills() {
        for (skill in skills.keys) {
            skill.deregister()
        }
    }

    fun hasSkill(skill: ISkill): Boolean {
        // console log the skill and the skills map
        return skills.containsKey(skill)
    }

    /**
     * Sets the skills associated with the proficiency.
     *
     * @param skills the new skills associated with the proficiency
     */
    fun setSkills(skills: HashMap<ISkill, Int>) {
        this.skills.clear()
        this.skills.putAll(skills)
    }

    val stats: Stats
        get() = Stats()

    // TODO read from config
    val displayName: Component
        get() {
            return Component.text(key.key.replaceFirstChar { it.uppercaseChar() })
        }

    /**
     * Returns a string representation of the Proficiency object.
     *
     * @return a string containing the name, stats, and skills of the Proficiency
     * object.
     */
    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("Proficiency")
        sb.append("{")
        sb.append(JSONUtils.fromValue("proficiencyName", key.key.lowercase(Locale.getDefault())) + ", ")
        sb.append("\"skills\": $skills")
        sb.append("}")

        return sb.toString()
    }

    override fun toJSON(): String {
        val sb = StringBuilder()

        sb.append("{")
        sb.append(JSONUtils.fromValue("proficiencyName", key.key.lowercase(Locale.getDefault())) + ", ")
        sb.append("\"skills\":[")

        for (entry in skills.entries) {
            sb.append("{")
            sb.append(JSONUtils.fromValue(entry.key.name.key, entry.key)).append(",")
            sb.append(JSONUtils.fromValue("level", entry.value))
            sb.append("}").append(",")
        }

        if (!skills.isEmpty()) {
            sb.deleteCharAt(sb.length - 1) // Remove trailing comma
        }

        sb.append("]")
        sb.append("}")

        return sb.toString()
    }
}
