package dev.boooiil.historia.core.items.component

import dev.boooiil.historia.core.items.ItemComponent
import dev.boooiil.historia.core.items.data.WeaponData
import dev.boooiil.historia.core.util.JSONUtils
import dev.boooiil.historia.core.util.NumberUtils
import org.bukkit.configuration.ConfigurationSection

class WeaponComponent(
    val sweepingRange: MutableList<Float>
) : ItemComponent {

    override val key = "weapon"

    override fun data(): WeaponData {
        val sweeping = NumberUtils.roundFloat(NumberUtils.random(this.sweepingRange[0], this.sweepingRange[1]), 2)

        return WeaponData(sweeping)
    }

    override fun toString(): String {
        val sb = "WeaponComponent" +
                toJSON()

        return sb
    }

    override fun toJSON(): String {
        val sb = "{" +
                JSONUtils.fromList("sweepRange", sweepingRange) +
                "}"

        return sb
    }

    companion object {
        fun fromConfig(section: ConfigurationSection): WeaponComponent {
            return WeaponComponent(section.getFloatList("sweeping"))
        }
    }
}
