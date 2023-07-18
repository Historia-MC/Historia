package dev.boooiil.historia.classes.historia.proficiency;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * This class represents the skills of a player in the game. It contains boolean variables that represent
 * whether or not the player has certain skills or abilities.
 */
public class Skills {

    private boolean hasNameTag = false;

    private boolean hasFeatherFall = false;
    private boolean hasQuickCharge = false;

    private boolean hasEfficiencyPickaxe = false;
    private boolean hasEfficiencyAxe = false;
    private boolean hasChanceExtraOre = false;
    private boolean hasChanceExtraWood = false;
    private boolean hasChanceExtraFeathers = false;

    private boolean hasChanceNoAnvilDamage = false;
    private boolean hasChanceNoConsumeBlock = false;
    private boolean canIgniteOil = false;
    private boolean canExtractOil = false;
    private boolean canBreakGrass = false;
    private boolean canCraftSaddle = false;
    private boolean canTameAnimals = false;
    private boolean canSweepingEdge = false;
    private boolean canExtractBones = false;
    private boolean canBreakBeehive = false;

    private boolean canCraftGunpowder = false;
    private boolean canApplyUnbreaking = false;
    private boolean canApplySharpness = false;

    private boolean canCraftHighTier = false;

    private boolean canMakeKnowledgeBook = false;
    /**
     * Don't know what this is for yet.
     */
    private boolean specialItems = false;

    public Skills(FileConfiguration config, String root) {
        
        this.hasNameTag = config.getBoolean(root + ".nametag");
        this.hasFeatherFall = config.getBoolean(root + ".featherFall");
        this.hasQuickCharge = config.getBoolean(root + ".quickCharge");
        this.hasEfficiencyPickaxe = config.getBoolean(root + ".efficiencyPickaxe");
        this.hasEfficiencyAxe = config.getBoolean(root + ".efficiencyAxe");
        this.hasChanceExtraOre = config.getBoolean(root + ".chanceExtraOre");
        this.hasChanceExtraWood = config.getBoolean(root + ".chanceExtraWood");
        this.hasChanceExtraFeathers = config.getBoolean(root + ".chanceExtraFeathers");
        this.hasChanceNoAnvilDamage = config.getBoolean(root + ".chanceNoAnvilDamage");
        this.hasChanceNoConsumeBlock = config.getBoolean(root + ".chanceNoConsumeBlock");
        this.canIgniteOil = config.getBoolean(root + ".igniteOil");
        this.canExtractOil = config.getBoolean(root + ".extractOil");
        this.canBreakGrass = config.getBoolean(root + ".breakGrass");
        this.canCraftSaddle = config.getBoolean(root + ".craftSaddle");
        this.canTameAnimals = config.getBoolean(root + ".tameAnimals");
        this.canSweepingEdge = config.getBoolean(root + ".sweepingEdge");
        this.canExtractBones = config.getBoolean(root + ".extractBones");
        this.canBreakBeehive = config.getBoolean(root + ".breakBeehive");
        this.canCraftGunpowder = config.getBoolean(root + ".craftGunpowder");
        this.canApplyUnbreaking = config.getBoolean(root + ".applyUnbreaking");
        this.canApplySharpness = config.getBoolean(root + ".applySharpness");
        this.canCraftHighTier = config.getBoolean(root + ".makeHighTierArmor");
        this.canMakeKnowledgeBook = config.getBoolean(root + ".makeKnowledgeBook");
        this.specialItems = config.getBoolean(root + ".specialItems");
        
    }
    
    /**
     * This function returns a boolean value that represents whether or not the player has a name tag
     * 
     * @return The boolean value of hasNameTag.
     */
    public boolean hasNameTag() {
        return hasNameTag;
    }

    /**
     * This function sets the value of the variable hasNameTag to the value of the parameter hasNameTag
     * 
     * @param hasNameTag Whether or not the player has a name tag.
     */
    public void hasNameTag(boolean hasNameTag) {
        this.hasNameTag = hasNameTag;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has the feather
     * fall enchantment
     * 
     * @return The boolean value of hasFeatherFall.
     */
    public boolean hasFeatherFall() {
        return hasFeatherFall;
    }

    /**
     * This function sets the boolean value of the variable hasFeatherFall to the boolean value of the
     * parameter hasFeatherFall
     * 
     * @param hasFeatherFall Whether or not the player has the feather fall effect.
     */
    public void hasFeatherFall(boolean hasFeatherFall) {
        this.hasFeatherFall = hasFeatherFall;
    }

    /**
     * This function returns a boolean value that indicates whether the device has quick charge or not
     * 
     * @return The value of the hasQuickCharge variable.
     */
    public boolean hasQuickCharge() {
        return hasQuickCharge;
    }

    /**
     * This function sets the value of the variable hasQuickCharge to the value of the parameter
     * hasQuickCharge
     * 
     * @param hasQuickCharge boolean
     */
    public void hasQuickCharge(boolean hasQuickCharge) {
        this.hasQuickCharge = hasQuickCharge;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has an
     * Efficiency pickaxe.
     * 
     * @return The boolean value of hasEfficiencyPickaxe.
     */
    public boolean hasEfficiencyPickaxe() {
        return hasEfficiencyPickaxe;
    }

    /**
     * It sets the value of the variable hasEfficiencyPickaxe to the value of the parameter
     * hasEfficiencyPickaxe
     * 
     * @param hasEfficiencyPickaxe Whether or not the player has an Efficiency pickaxe
     */
    public void hasEfficiencyPickaxe(boolean hasEfficiencyPickaxe) {
        this.hasEfficiencyPickaxe = hasEfficiencyPickaxe;
    }

    /**
     * This function returns the value of the variable hasEfficiencyAxe.
     * 
     * @return The boolean value of hasEfficiencyAxe.
     */
    public boolean hasEfficiencyAxe() {
        return hasEfficiencyAxe;
    }

    /**
     * This function sets the value of the variable hasEfficiencyAxe to the value of the parameter
     * hasEfficiencyAxe
     * 
     * @param hasEfficiencyAxe If the player has an efficiency axe
     */
    public void hasEfficiencyAxe(boolean hasEfficiencyAxe) {
        this.hasEfficiencyAxe = hasEfficiencyAxe;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has a chance to
     * get extra ore
     * 
     * @return The boolean value of hasChanceExtraOre.
     */
    public boolean hasChanceExtraOre() {
        return hasChanceExtraOre;
    }

    /**
     * This function sets the value of the variable hasChanceExtraOre to the value of the parameter
     * hasChanceExtraOre
     * 
     * @param hasChanceExtraOre If the ore has a chance to drop an extra ore.
     */
    public void hasChanceExtraOre(boolean hasChanceExtraOre) {
        this.hasChanceExtraOre = hasChanceExtraOre;
    }

    /**
     * This function returns a boolean value that indicates whether or not the player has a chance to
     * get extra wood
     * 
     * @return The boolean value of hasChanceExtraWood.
     */
    public boolean hasChanceExtraWood() {
        return hasChanceExtraWood;
    }

    /**
     * This function sets the value of the variable hasChanceExtraWood to the value of the parameter
     * hasChanceExtraWood
     * 
     * @param hasChanceExtraWood If true, the player has a chance to get extra wood when chopping down
     * a tree.
     */
    public void hasChanceExtraWood(boolean hasChanceExtraWood) {
        this.hasChanceExtraWood = hasChanceExtraWood;
    }

    /**
     * This function returns a boolean value that indicates whether or not the chicken has a chance to
     * drop extra feathers
     * 
     * @return The boolean value of hasChanceExtraFeathers.
     */
    public boolean hasChanceExtraFeathers() {
        return hasChanceExtraFeathers;
    }

    /**
     * This function sets the boolean value of the variable hasChanceExtraFeathers to the boolean value
     * of the parameter hasChanceExtraFeathers
     * 
     * @param hasChanceExtraFeathers If true, the player has a chance to get extra feathers when
     * killing a chicken.
     */
    public void hasChanceExtraFeathers(boolean hasChanceExtraFeathers) {
        this.hasChanceExtraFeathers = hasChanceExtraFeathers;
    }

    /**
     * This function returns a boolean value that is true if the item has a chance to not take damage
     * when used in an anvil
     * 
     * @return The boolean value of hasChanceNoAnvilDamage.
     */
    public boolean hasChanceNoAnvilDamage() {
        return hasChanceNoAnvilDamage;
    }

    /**
     * This function sets the boolean value of the variable hasChanceNoAnvilDamage to the boolean value
     * of the variable hasChanceNoAnvilDamage
     * 
     * @param hasChanceNoAnvilDamage If true, the item will have a chance to not take damage when used
     * in an anvil.
     */
    public void hasChanceNoAnvilDamage(boolean hasChanceNoAnvilDamage) {
        this.hasChanceNoAnvilDamage = hasChanceNoAnvilDamage;
    }

    /**
     * This function returns a boolean value that indicates whether the player has a chance to not
     * consume a block
     * 
     * @return The value of the variable hasChanceNoConsumeBlock.
     */
    public boolean hasChanceNoConsumeBlock() {
        return hasChanceNoConsumeBlock;
    }

    /**
     * This function sets the value of the variable hasChanceNoConsumeBlock to the value of the
     * parameter hasChanceNoConsumeBlock
     * 
     * @param hasChanceNoConsumeBlock If true, the block will have a chance to not consume the item.
     */
    public void hasChanceNoConsumeBlock(boolean hasChanceNoConsumeBlock) {
        this.hasChanceNoConsumeBlock = hasChanceNoConsumeBlock;
    }

    /**
     * This function returns a boolean value that indicates whether or not the player can ignite oil.
     * 
     * @return The boolean value of canIgniteOil.
     */
    public boolean canIgniteOil() {
        return canIgniteOil;
    }

    /**
     * This function sets the boolean value of canIgniteOil to the boolean value of the parameter
     * canIgniteOil
     * 
     * @param canIgniteOil If true, the fire will ignite oil.
     */
    public void setCanIgniteOil(boolean canIgniteOil) {
        this.canIgniteOil = canIgniteOil;
    }

    /**
     * This function returns a boolean value that indicates whether or not the country can extract oil
     * 
     * @return The value of the variable canExtractOil.
     */
    public boolean canExtractOil() {
        return canExtractOil;
    }

    /**
     * This function sets the value of the boolean variable canExtractOil to the value of the boolean
     * parameter canExtractOil
     * 
     * @param canExtractOil If the block can extract oil from the ground.
     */
    public void setCanExtractOil(boolean canExtractOil) {
        this.canExtractOil = canExtractOil;
    }

    /**
     * This function returns a boolean value that determines whether or not the player can break glass
     * 
     * @return The boolean value of canBreakGlass.
     */
    public boolean canBreakGrass() {
        return canBreakGrass;
    }

    /**
     * This function sets the value of the variable canBreakGlass to the value of the parameter
     * canBreakGlass
     * 
     * @param canBreakGlass Whether or not the player can break glass with this tool.
     */
    public void setCanBreakGrass(boolean canBreakGlass) {
        this.canBreakGrass = canBreakGlass;
    }

    /**
     * This function returns a boolean value that is true if the horse can craft a saddle, and false if
     * it cannot.
     * 
     * @return The boolean value of canCraftSaddle.
     */
    public boolean canCraftSaddle() {
        return canCraftSaddle;
    }

    /**
     * This function sets the boolean value of canCraftSaddle to the boolean value of the parameter
     * canCraftSaddle
     * 
     * @param canCraftSaddle Whether or not the player can craft a saddle.
     */
    public void setCanCraftSaddle(boolean canCraftSaddle) {
        this.canCraftSaddle = canCraftSaddle;
    }

    /**
     * This function returns a boolean value that determines whether or not the player can tame animals
     * 
     * @return The boolean value of canTameAnimals.
     */
    public boolean canTameAnimals() {
        return canTameAnimals;
    }

    /**
     * This function sets the value of the variable canTameAnimals to the value of the parameter
     * canTameAnimals
     * 
     * @param canTameAnimals Whether or not the player can tame animals.
     */
    public void setCanTameAnimals(boolean canTameAnimals) {
        this.canTameAnimals = canTameAnimals;
    }

    /**
     * This function returns a boolean value that indicates whether the edge can be swept
     * 
     * @return The value of the variable canSweepingEdge.
     */
    public boolean canSweepingEdge() {
        return canSweepingEdge;
    }

    /**
     * This function sets the value of the boolean variable canSweepingEdge to the value of the
     * parameter canSweepingEdge
     * 
     * @param canSweepingEdge Whether the edge can be swept
     */
    public void setCanSweepingEdge(boolean canSweepingEdge) {
        this.canSweepingEdge = canSweepingEdge;
    }

    /**
     * This function returns a boolean value that determines whether or not the player can extract
     * bones from the bone pile
     * 
     * @return The method is returning a boolean value.
     */
    public boolean canExtractBones() {
        return canExtractBones;
    }

    /**
     * This function sets the value of the boolean variable canExtractBones to the value of the boolean
     * variable canExtractBones
     * 
     * @param canExtractBones If true, the mob can be extracted for bones.
     */
    public void setCanExtractBones(boolean canExtractBones) {
        this.canExtractBones = canExtractBones;
    }

    /**
     * This function returns a boolean value that determines whether or not the player can break
     * beehives
     * 
     * @return The boolean value of canBreakBeehive.
     */
    public boolean canBreakBeehive() {
        return canBreakBeehive;
    }

    /**
     * This function sets the value of the variable canBreakBeehive to the value of the parameter
     * canBreakBeehive
     * 
     * @param canBreakBeehive Whether or not the player can break beehives.
     */
    public void setCanBreakBeehive(boolean canBreakBeehive) {
        this.canBreakBeehive = canBreakBeehive;
    }

    /**
     * This function returns a boolean value that represents whether or not the player can craft
     * gunpowder.
     * 
     * @return The boolean value of canCraftGunpowder.
     */
    public boolean canCraftGunpowder() {
        return canCraftGunpowder;
    }

    /**
     * This function sets the value of the variable canCraftGunpowder to the value of the parameter
     * canCraftGunpowder
     * 
     * @param canCraftGunpowder Whether or not the player can craft gunpowder.
     */
    public void setCanCraftGunpowder(boolean canCraftGunpowder) {
        this.canCraftGunpowder = canCraftGunpowder;
    }

    /**
     * Returns whether or not the item can have the unbreaking enchantment applied to it.
     * 
     * @return The boolean value of canApplyUnbreaking.
     */
    public boolean canApplyUnbreaking() {
        return canApplyUnbreaking;
    }

    /**
     * This function sets the boolean value of canApplyUnbreaking to the boolean value of the parameter
     * canApplyUnbreaking.
     * 
     * @param canApplyUnbreaking Whether or not the enchantment can be applied to the item.
     */
    public void setCanApplyUnbreaking(boolean canApplyUnbreaking) {
        this.canApplyUnbreaking = canApplyUnbreaking;
    }

    /**
     * This function returns a boolean value that represents whether or not the player can apply the
     * sharpness enchantment to the item.
     * 
     * @return The boolean value of canApplySharpness.
     */
    public boolean canApplySharpness() {
        return canApplySharpness;
    }

    /**
     * This function returns a boolean value that is true if the player can make high tier armor, and
     * false if they can't.
     * 
     * @return The boolean value of canMakeHighTierArmor.
     */
    public boolean canCraftHighTier() {
        return canCraftHighTier;
    }

    /**
     * This function sets the value of the variable canMakeHighTierArmor to the value of the parameter
     * canMakeHighTierArmor
     * 
     * @param canMakeHighTierArmor Whether or not the player can make high tier armor.
     */
    public void setCanMakeHighTierArmor(boolean canMakeHighTierArmor) {
        this.canCraftHighTier = canMakeHighTierArmor;
    }

    /**
     * This function returns a boolean value that determines whether or not the player can make a
     * knowledge book
     * 
     * @return The boolean value of canMakeKnowledgeBook.
     */
    public boolean canMakeKnowledgeBook() {
        return canMakeKnowledgeBook;
    }

    /**
     * This function sets the value of the variable canMakeKnowledgeBook to the value of the parameter
     * canMakeKnowledgeBook
     * 
     * @param canMakeKnowledgeBook Whether or not the player can make a knowledge book.
     */
    public void setCanMakeKnowledgeBook(boolean canMakeKnowledgeBook) {
        this.canMakeKnowledgeBook = canMakeKnowledgeBook;
    }

    /**
     * This function returns a boolean value that indicates whether or not the item is special
     * 
     * @return The boolean value of specialItems.
     */
    public boolean specialItems() {
        return specialItems;
    }

    /**
     * This function sets the value of the specialItems variable to the value of the specialItems
     * parameter
     * 
     * @param specialItems This is a boolean value that determines whether or not the special items are
     * enabled.
     */
    public void setSpecialItems(boolean specialItems) {
        this.specialItems = specialItems;
    }

    public String toString() {

        String output = "*** SKILLS *** \n";

        output += "Has Nametag: " + hasNameTag + "\n";
        output += "Has Feather Falling: " + hasFeatherFall + "\n";
        output += "Has Quick Charge: " + hasQuickCharge + "\n";
        output += "Has Efficiency Pickaxe: " + hasEfficiencyPickaxe + "\n";
        output += "Has Efficiency Axe: " + hasEfficiencyAxe + "\n";
        output += "Has Chance Extra Ore: " + hasChanceExtraOre + "\n";
        output += "Has Chance Extra Wood: " + hasChanceExtraWood + "\n";
        output += "Has Chance Extra Feathers: " + hasChanceExtraFeathers + "\n";
        output += "Has Chance No Anvil Damage: " + hasChanceNoAnvilDamage + "\n";
        output += "Has Chance No Consume Block: " + hasChanceNoConsumeBlock + "\n";
        output += "Can Ignite Oil: " + canIgniteOil + "\n";
        output += "Can Extract Oil: " + canExtractOil + "\n";
        output += "Can Break Grass: " + canBreakGrass + "\n";
        output += "Can Craft Saddle: " + canCraftSaddle + "\n";
        output += "Can Tame Animals: " + canTameAnimals + "\n";
        output += "Can Sweeping Edge: " + canSweepingEdge + "\n";
        output += "Can Extract Bones: " + canExtractBones + "\n";
        output += "Can Break Beehive: " + canBreakBeehive + "\n";
        output += "Can Craft Gunpowder: " + canCraftGunpowder + "\n";
        output += "Can Apply Unbreaking: " + canApplyUnbreaking + "\n";
        output += "Can Make High Tier Armor: " + canCraftHighTier + "\n";
        output += "Can Make Knowledge Book: " + canMakeKnowledgeBook + "\n";
        output += "Special Items: " + specialItems + "\n";
        output += "************** \n";

        return output;

    }

}
