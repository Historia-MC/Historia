package dev.boooiil.historia.classes.items.craftable;

import java.util.List;

import dev.boooiil.historia.classes.items.BaseItem;

public class CraftedItem extends BaseItem {
    
    protected List<String> recipeShape;
    protected List<String> recipeItems;
    protected List<String> proficiencies;
    protected boolean isShaped;

    /**
     * This function returns the list of items that are required to craft the item
     * 
     * @return The list of recipe items.
     */
    public List<String> getRecipeItems() {

        return this.recipeItems;

    }

    /**
     * It returns the recipe shape
     * 
     * @return The recipe shape.
     */
    public List<String> getRecipeShape() {

        return this.recipeShape;

    }

    /**
     * Validate recipe of items to see if it matches an armor.
     * @param inputItems List of recipe items.
     * @param inputShape Recipe shape.
     * 
     * @return If the recipe is valid.
     */
    public Boolean isValidRecipe(List<String> inputItems, List<String> inputShape) {

        boolean validItems = this.recipeItems.equals(inputItems);
        boolean validShape = this.recipeShape.equals(inputShape);

        return validItems && validShape;

    }

    /**
     * It returns if the item is shape dependent.
     * 
     * @return If the item is shape dependent.
     */
    public boolean isShapeDependent() {
        return isShaped;
    }

    /**
     * It returns whether the given proficiency can craft this item.
     * @param proficiency The proficiency to check.
     * @return If the proficiency can craft the item.
     */
    public boolean canCraft(String proficiency) {

        return this.proficiencies.contains(proficiency);

    }
}
