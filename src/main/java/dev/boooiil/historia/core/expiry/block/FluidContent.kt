package dev.boooiil.historia.core.expiry.block

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.expiry.item.ExpiryItems.seawaterBottle
import dev.boooiil.historia.core.expiry.item.ExpiryItems.seawaterBucket
import dev.boooiil.historia.core.expiry.item.ExpiryItems.waterBottle
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionType

enum class FluidContent(
    val cauldron: Material,
    private val _bucket: () -> ItemStack = { ItemStack.empty() },
    private val _bottle: () -> ItemStack = { ItemStack.empty() }
) {
    EMPTY(
        Material.CAULDRON,
        { ItemStack(Material.BUCKET) },
        { ItemStack(Material.GLASS_BOTTLE) }
    ),
    WATER(
        Material.WATER_CAULDRON,
        { ItemStack(Material.WATER_BUCKET) },
        { waterBottle }
    ),
    SALT_WATER(
        Material.WATER_CAULDRON,
        { seawaterBucket },
        { seawaterBottle }
    ),
    LAVA(
        Material.LAVA_CAULDRON,
        { ItemStack(Material.LAVA_BUCKET) }
    ),
    POWDER_SNOW(
        Material.POWDER_SNOW_CAULDRON,
        { ItemStack(Material.POWDER_SNOW_BUCKET) }
    );

    val bucket: ItemStack get() = _bucket()
    val bottle: ItemStack get() = _bucket()

    companion object {
        fun fromItem(stack: ItemStack): FluidContent {
            val itemMeta = stack.itemMeta
            val dataContainer = itemMeta.persistentDataContainer
            if (dataContainer.has(HistoriaCore.getNamespacedKey("item-id"))) {
                val id = dataContainer.get<String, String>(HistoriaCore.getNamespacedKey("item-id"), PersistentDataType.STRING)
                if (id != null && (id == "seawater_bucket" || id == "seawater_bottle")) {
                    return SALT_WATER
                }
            }
            if (itemMeta is PotionMeta) {
                if (itemMeta.basePotionType == PotionType.WATER) {
                    return WATER
                }
            }
            return fluidContentOf(stack.type)
        }
    }
}

fun fluidContentOf(type: Material): FluidContent = when (type) {
    Material.WATER_CAULDRON, Material.WATER_BUCKET -> FluidContent.WATER
    Material.LAVA_CAULDRON, Material.LAVA_BUCKET -> FluidContent.LAVA
    Material.POWDER_SNOW_CAULDRON, Material.POWDER_SNOW_BUCKET -> FluidContent.POWDER_SNOW
    else -> FluidContent.EMPTY
}
