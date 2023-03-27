package dev.boooiil.historia.classes;

import java.util.List;

public class CraftedItem extends Item {
    
    protected List<String> recipeShape;
    protected List<String> recipeItems;

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
}
