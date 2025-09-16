package dev.boooiil.historia.core.proficiency.skills

import dev.boooiil.historia.core.HistoriaCore
import org.bukkit.NamespacedKey
import java.util.*

enum class SkillName(val key: NamespacedKey) {
    ANIMAL_TAME(HistoriaCore.getNamespacedKey("animal_tame")),
    ANIMAL_BREED_ALL(HistoriaCore.getNamespacedKey("animal_breed_all")),
    ANIMAL_DROPS_BONES_ALL(HistoriaCore.getNamespacedKey("animal_drops_bones_all")),
    ANIMAL_DROPS_CHICKEN_SHEAR(HistoriaCore.getNamespacedKey("animal_drops_chicken_shear")),
    ANIMAL_DROPS_INCREASE_FEATHERS(HistoriaCore.getNamespacedKey("animal_drops_increase_feathers")),
    ANIMAL_DROPS_INCREASE_WOOL(HistoriaCore.getNamespacedKey("animal_drops_increase_wool")),
    ANIMAL_DROPS_LEATHER(HistoriaCore.getNamespacedKey("animal_drops_leather")),
    BREAK_BLOCK_BEEHIVE(HistoriaCore.getNamespacedKey("break_block_beehive")),
    BREAK_BLOCK_GRASS(HistoriaCore.getNamespacedKey("break_block_grass")),
    BLOCK_DROPS_INCREASE_ORES(HistoriaCore.getNamespacedKey("block_drops_increase_ores")),
    BYPASS_BLOCK_LADDER(HistoriaCore.getNamespacedKey("bypass_block_ladder")),
    CHANCE_NO_CONSUME_ITEM_BUILDER(HistoriaCore.getNamespacedKey("chance_no_consume_item_builder")),
    CHANCE_EXTRA_BLOCK_WOOD(HistoriaCore.getNamespacedKey("chance_extra_block_wood")),
    ENHANCED_AXE_SHARPNESS(HistoriaCore.getNamespacedKey("enhanced_axe_sharpness")),
    ENHANCED_BOOTS_BLAST_PROTECTION(HistoriaCore.getNamespacedKey("enhanced_boots_blast_protection")),
    ENHANCED_BOOTS_DEPTH_STRIDER(HistoriaCore.getNamespacedKey("enhanced_boots_depth_strider")),
    ENHANCED_BOOTS_FEATHER_FALL(HistoriaCore.getNamespacedKey("enhanced_boots_feather_fall")),
    ENHANCED_CHESTPLATE_BLAST_PROTECTION(HistoriaCore.getNamespacedKey("enhanced_chestplate_blast_protection")),
    ENHANCED_CROSSBOW_QUICK_CHARGE(HistoriaCore.getNamespacedKey("enhanced_crossbow_quick_charge")),
    ENHANCED_HELMET_AQUA_AFFINITY(HistoriaCore.getNamespacedKey("enhanced_helmet_aqua_affinity")),
    ENHANCED_HELMET_BLAST_PROTECTION(HistoriaCore.getNamespacedKey("enhanced_helmet_blast_protection")),
    ENHANCED_LEGGINGS_BLAST_PROTECTION(HistoriaCore.getNamespacedKey("enhanced_leggings_blast_protection")),
    ENHANCED_PICKAXE_EFFICIENCY(HistoriaCore.getNamespacedKey("enhanced_pickaxe_efficiency")),
    ENHANCED_SHOVEL_EFFICIENCY(HistoriaCore.getNamespacedKey("enhanced_shovel_efficiency")),
    ENHANCED_SWORD_SHARPNESS(HistoriaCore.getNamespacedKey("enhanced_sword_sharpness")),
    ENHANCED_SWORD_SWEEPING_EDGE(HistoriaCore.getNamespacedKey("enhanced_sword_sweeping_edge")),
    ENHANCE_ITEM_UNBREAKING(HistoriaCore.getNamespacedKey("enhance_item_unbreaking")),
    ENHANCE_ITEM_SHARPNESS(HistoriaCore.getNamespacedKey("enhance_item_sharpness")),
    LOOT_TABLE_ENHANCED_FISH_DROPS(HistoriaCore.getNamespacedKey("loot_table_enhanced_fish_drops")),
    OFFHAND_ITEM_IGNITE_OIL(HistoriaCore.getNamespacedKey("offhand_item_ignite_oil")),
    TRANSFER_EXPERIENCE_BOOK(HistoriaCore.getNamespacedKey("transfer_experience_book")),
    CAN_CLIMB_LOGS(HistoriaCore.getNamespacedKey("can_climb_logs"));

    val instance: ISkill? = HistoriaCore.SKILL_REGISTRY[key]

    val namespacedKey: String
        get() = this.key.value()

    fun matches(name: String): Boolean {
        return this.key.value() == name.lowercase(Locale.getDefault())
    }

    companion object {
        fun fromString(name: String): SkillName? {
            for (skillName in entries) {
                if (skillName.matches(name)) {
                    return skillName
                }
            }
            return null
        }
    }
}
