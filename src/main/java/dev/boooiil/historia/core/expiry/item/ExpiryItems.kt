package dev.boooiil.historia.core.expiry.item

import dev.boooiil.historia.core.HistoriaCore
import dev.boooiil.historia.core.items.HistoriaItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionType

object ExpiryItems {
    @JvmStatic
    fun getSalt(amount: Int): ItemStack {
        val salt = HistoriaItem(
            HistoriaCore.getNamespacedKey("salt"),
            "Salt",
            Material.SUGAR,
            mutableListOf(),
            0.0,
            mutableMapOf(),
        ).createItemStack()
        salt.amount = amount
        return salt
    }

    @JvmStatic
    val seawaterBucket: ItemStack
        get() {
            val custom = HistoriaItem(
                HistoriaCore.getNamespacedKey("seawater_bucket"),
                "Seawater Bucket",
                Material.WATER_BUCKET,
                mutableListOf(),
                0.0,
                mutableMapOf(),
            )
            return custom.createItemStack()
        }

    @JvmStatic
    val seawaterBottle: ItemStack
        get() = ItemStack.of(Material.POTION)
    //        List<String> lore = new ArrayList<>();
//        lore.add("You probably shouldn't drink this");
//        Custom custom = CraftedItemFactory.createCustom(Material.POTION, 1, "seawater_bottle", "Seawater Bottle", lore);
//        ItemStack stack = custom.getItemStack();
//        PotionMeta potionMeta = (PotionMeta) stack.getItemMeta();
//
//        potionMeta.setBasePotionData(new PotionData(PotionType.WATER));
//        stack.setItemMeta(potionMeta);
//        return stack;

    @JvmStatic
    @get:Deprecated("")
    val waterBottle: ItemStack
        get() {
            val stack = ItemStack(Material.POTION)
            val potionMeta =
                stack.getItemMeta() as PotionMeta

            potionMeta.setBasePotionData(PotionData(PotionType.WATER))
            stack.setItemMeta(potionMeta)

            return stack
        }
}
