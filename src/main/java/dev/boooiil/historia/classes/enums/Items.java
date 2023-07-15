package dev.boooiil.historia.classes.enums;

import java.util.List;

import org.bukkit.Material;

public class Items {

    private static final List<Material> crops = List.of(
        Material.WHEAT,
        Material.CARROTS,
        Material.POTATOES,
        Material.BEETROOTS,
        Material.NETHER_WART,
        Material.COCOA_BEANS,
        Material.SWEET_BERRIES,
        Material.MELON,
        Material.PUMPKIN,
        Material.CHORUS_FLOWER,
        Material.BROWN_MUSHROOM,
        Material.RED_MUSHROOM,
        Material.SUGAR_CANE,
        Material.BAMBOO_SAPLING,
        Material.BAMBOO,
        Material.CACTUS,
        Material.KELP
    );

    private static final List<Material> tallCrops = List.of(
        Material.SUGAR_CANE,
        Material.BAMBOO,
        Material.CACTUS,
        Material.KELP
    );


    public static List<Material> getCrops() {

        return crops;

    }

    public static List<Material> getTallCrops() {

        return tallCrops;

    }

    public static boolean isCrop(Material check) {

        return crops.contains(check);

    }

}
