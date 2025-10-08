package dev.boooiil.historia.core.items.recipe.result

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.registry.RegistryHolder
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.inventory.ItemStack

interface Result {
    val isStatic: Boolean

    fun preview(inputs: List<ItemStack>): ItemStack
    fun get(inputs: List<ItemStack>): ItemStack

    companion object {
        fun fromConfig(section: ConfigurationSection): Result {

            val keyString = section.getString(ID_KEY)?.lowercase()
                ?: throw InvalidConfigurationException("Recipe result must have an item id")

            val resultId = if (keyString.contains(':')) {
                NamespacedKey.fromString(keyString) ?: throw InvalidConfigurationException("Invalid item id $keyString")
            } else if (RegistryHolder.ITEM_REGISTRY.keys.any { it.key == keyString }) {
                HistoriaCore.getNamespacedKey(keyString)
            } else {
                NamespacedKey.minecraft(keyString)
            }

            val amount = section.getInt(AMOUNT_KEY, 1)

            return when (resultId.namespace) {
                HistoriaCore.instance.namespace() -> return QualityResult(resultId, amount)
                else -> createSimpleResult(resultId, amount)
            }
        }

        private fun createSimpleResult(itemId: NamespacedKey, amount: Int): SimpleResult {
            val itemStack = RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.ITEM)
                .get(itemId)
                ?.createItemStack(amount)
                ?: throw InvalidConfigurationException("$itemId is not a valid item id")

            return SimpleResult(itemStack)
        }
        private const val ID_KEY = "id"
        private const val AMOUNT_KEY = "amount"
    }
}