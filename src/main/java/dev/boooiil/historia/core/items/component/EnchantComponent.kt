package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.EnchantData
import dev.boooiil.historia.core.util.CoreLogger
import dev.boooiil.historia.core.util.JSONUtils
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.enchantments.Enchantment

class EnchantComponent(
    val enchantments: HashMap<Enchantment, Int>
) : ItemComponent {

    override val key = "enchant"

    override fun data(qualityModifier: Double?): EnchantData {
        return EnchantData(this.enchantments)
    }

    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("EnchantComponent")
        sb.append("{")
        sb.append("\"enchantments\":")
        sb.append("{")

        for (enchants in enchantments.entries) {
            sb.append(JSONUtils.fromValue(enchants.key.key.key, enchants.value))
            sb.append(", ")
        }
        sb.setLength(sb.length - 2)
        sb.append("}")
        sb.append("}")

        return sb.toString()
    }

    override fun toJSON(): String {
        val sb = StringBuilder()

        sb.append("{")
        sb.append("\"enchantments\":")
        sb.append("{")

        for (enchants in enchantments.entries) {
            sb.append(JSONUtils.fromValue(enchants.key.key.key, enchants.value))
            sb.append(", ")
        }
        sb.setLength(sb.length - 2)
        sb.append("}")
        sb.append("}")

        return sb.toString()
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): EnchantComponent {
            val map = HashMap<Enchantment, Int>()

            for (enchant in section.getKeys(false)) {
                val enchantment = Enchantment.getByName(enchant)

                if (enchantment == null) {
                    CoreLogger.errorToConsole(
                        "Tried to get enchantment",
                        enchant, "from enchantment component but it does not exist."
                    )
                    continue
                }

                map[enchantment] = section.getInt(enchant)
            }

            return EnchantComponent(map)
        }
    }
}
