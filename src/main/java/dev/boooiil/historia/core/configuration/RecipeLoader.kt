package dev.boooiil.historia.core.configuration;

public final class RecipeLoader {
//
//    @Generated(value = "Static Utility")
//    private RecipeLoader() {
//    }
//
//    public static void load() {
//        // EXAMPLE
//        final ShapedRecipe shaped = new ShapedRecipe(HistoriaCore.getNamespacedKey("example"),
//                new ItemStack(Material.PUMPKIN));
//        shaped.shape(" # ", "#$#", " # ");
//
//        shaped.setIngredient('#', customTypeChoice(HistoriaCore.getNamespacedKey("Common_Light_Bronze_Ingot")));
//        shaped.setIngredient('$', Material.STICK);
//
//        // Bukkit.addRecipe(shaped);
//
//    }
//
//    static RecipeChoice.PredicateChoice customTypeChoice(NamespacedKey id) {
//        NamespacedKey idKey = HistoriaCore.getNamespacedKey("item-id");
//
//        Predicate<ItemStack> predicate = stack -> {
//            boolean isCustom = stack.hasItemMeta()
//                    && stack.getItemMeta().getPersistentDataContainer().has(idKey, PersistentDataType.STRING);
//            if (isCustom) {
//                String stackId = stack.getItemMeta().getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
//                return stackId.equals(id.getKey());
//            }
//            return stack.getType().equals(Material.getMaterial(id.getKey()));
//        };
//
//        ItemStack stack;
//        if (HistoriaCore.Companion.getITEM_REGISTRY().contains(id)) {
//            stack = HistoriaCore.Companion.getITEM_REGISTRY().get(id).createItemStack();
//        } else {
//            stack = ItemStack.of(Material.getMaterial(id.getKey()));
//        }
//
//        return RecipeChoice.predicateChoice(predicate, stack);
//    }
}
