package dev.boooiil.historia.configuration;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.util.FileGetter;

public class ClassConfig {

    private static FileConfiguration configuration = FileGetter.get("classes.yml");

    //These are here for testing purposes.
    //Static reference will use less memory than creating a new instance in each HistoriaPlayer

    /**
     * Default player config.
     */
    public static ClassConfig noneConfig = new ClassConfig("None");

    /**
     * Warrior config.
     */
    public static ClassConfig warriorConfig = new ClassConfig("Warrior");
    
    /**
     * Archer config.
     */
    public static ClassConfig archerConfig = new ClassConfig("Archer");
    
    /**
     * Fisherman config.
     */
    public static ClassConfig fishermanConfig = new ClassConfig("Fisherman");
    
    /**
     * Miner config.
     */
    public static ClassConfig minerConfig = new ClassConfig("Miner");
    
    /**
     * Blacksmith config.
     */
    public static ClassConfig blacksmithConfig = new ClassConfig("Blacksmith");
    
    /**
     * Huntsman config.
     */
    public static ClassConfig huntsmanConfig = new ClassConfig("Huntsman");
    
    /**
     * Apothecary config.
     */
    public static ClassConfig apothecaryConfig = new ClassConfig("Apothecary");
    
    /**
     * Architect config.
     */
    public static ClassConfig architectConfig = new ClassConfig("Architect");
    
    /**
     * Lumberjack config.
     */
    public static ClassConfig lumberjackConfig = new ClassConfig("Lumberjack");
    
    /**
     * Farmer config.
     */
    public static ClassConfig farmerConfig = new ClassConfig("Farmer");

    /**
     * Return a preloaded configuration.
     * @param className Provided class name.
     * @return ClassConfig associated with the class.
     */
    public static ClassConfig getConfig(String className) {

        if (className.equals("Warrior")) return warriorConfig;
        if (className.equals("Archer")) return archerConfig;
        if (className.equals("Fisherman")) return fishermanConfig;
        if (className.equals("Miner")) return minerConfig;
        if (className.equals("Blacksmith")) return warriorConfig;
        if (className.equals("Huntsman")) return blacksmithConfig;
        if (className.equals("Apothecary")) return apothecaryConfig;
        if (className.equals("Architect")) return architectConfig;
        if (className.equals("Lumberjack")) return lumberjackConfig;
        if (className.equals("Farmer")) return farmerConfig;
        else return noneConfig;

    }
    /**
     * Reload the configurations.
     */
    public static void reloadConfig() {

        noneConfig = new ClassConfig("None");
        warriorConfig = new ClassConfig("Warrior");
        archerConfig = new ClassConfig("Archer");
        fishermanConfig = new ClassConfig("Fisherman");
        minerConfig = new ClassConfig("Miner");
        blacksmithConfig = new ClassConfig("Blacksmith");
        huntsmanConfig = new ClassConfig("Huntsman");
        apothecaryConfig = new ClassConfig("Apothecary");
        architectConfig = new ClassConfig("Architect");
        lumberjackConfig = new ClassConfig("Lumberjack");
        farmerConfig = new ClassConfig("Farmer");

    }
    private int baseHealth;

    private int maxHealth;
    private int baseFood;
    private float baseSpeed;
    private float baseEvasion;
    private float baseSwordProficiency;
    private float baseBowProficiency;

    private float baseCrossbowProficiency;
    private float baseExperienceGain;
    // ** SKILL MODIFIERS ** //
    private float harvestChance;
    private float doubleHarvestChance;

    private float instantGrowthChance;
    private float beheadChance;
    private boolean hasNameTag;

    private boolean hasFeatherFall;
    private boolean hasQuickCharge;

    private boolean hasEfficiencyPickaxe;
    private boolean hasEfficiencyAxe;
    private boolean hasChanceExtraOre;
    private boolean hasChanceExtraWood;
    private boolean hasChanceExtraFeathers;

    private boolean hasChanceNoAnvilDamage;
    private boolean hasChanceNoConsumeBlock;
    private boolean canIgniteOil;
    private boolean canExtractOil;
    private boolean canBreakGlass;
    private boolean canCraftSaddle;
    private boolean canTameAnimals;
    private boolean canSweepingEdge;
    private boolean canExtractBones;
    private boolean canBreakBeehive;

    private boolean canCraftGunpowder;
    private boolean canApplyUnbreaking;

    private boolean canMakeHighTierArmor;

    private boolean canMakeKnowledgeBook;
    /**
     * Don't know what this is for yet.
     */
    public boolean specialItems;
    // ** END MODIFIERS ** //

    // Creating a list of strings called weaponProficiency.
    public List<String> weaponProficiency;

    // Creating a list of strings called armorProficiency.
    public List<String> armorProficiency;

    /**
     * Private constructor.
     * @param className Provided class name.
     */
    private ClassConfig(String className) {

        String statsRoot = className + ".stats";
        String skillRoot = className + ".skills";

        this.baseHealth = configuration.getInt(statsRoot + ".baseHealth");
        this.maxHealth = configuration.getInt(statsRoot + ".maxHealth");
        this.baseFood = configuration.getInt(statsRoot + ".baseFood");

        this.baseSpeed = configuration.getLong(statsRoot + ".baseSpeed");
        this.baseEvasion = configuration.getLong(statsRoot + ".baseEvasion");
        this.baseSwordProficiency = configuration.getLong(statsRoot + ".baseSwordProficiency");
        this.baseBowProficiency = configuration.getLong(statsRoot + ".baseBowProficiency");
        this.baseCrossbowProficiency = configuration.getLong(statsRoot + ".baseCrossbowProficiency");
        this.baseExperienceGain = configuration.getLong(statsRoot + ".baseExperienceGain");

        // If they have the base class they should not be having skills.
        if (!className.equals("None")) {

            this.harvestChance = configuration.getLong(skillRoot + ".harvestChance");
            this.beheadChance = configuration.getLong(skillRoot + ".beheadChance");

            this.hasNameTag = configuration.getBoolean(skillRoot + ".nametag");
            this.canSweepingEdge = configuration.getBoolean(skillRoot + ".sweepingEdge");
            this.hasQuickCharge = configuration.getBoolean(skillRoot + ".quickCharge");
            this.canExtractOil = configuration.getBoolean(skillRoot + ".extractOil");
            this.canIgniteOil = configuration.getBoolean(skillRoot + ".igniteOil");

        }
 
        this.weaponProficiency = configuration.getStringList(statsRoot + ".weaponProficiency");
        this.armorProficiency = configuration.getStringList(statsRoot + ".armorProficiency");

    }

    /**
     * This function returns the base health of the player
     * 
     * @return The baseHealth variable is being returned.
     */
    public int getBaseHealth() {
        return baseHealth;
    }

    /**
     * This function sets the base health of the player
     * 
     * @param baseHealth The base health of the player.
     */
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    /**
     * This function returns the maxHealth variable
     * 
     * @return The maxHealth variable is being returned.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * This function sets the maxHealth variable to the value of the maxHealth parameter
     * 
     * @param maxHealth The maximum health of the player.
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * This function returns the baseFood variable
     * 
     * @return The baseFood variable is being returned.
     */
    public int getBaseFood() {
        return baseFood;
    }

    /**
     * This function sets the base food of the player
     * 
     * @param baseFood The amount of food the player starts with.
     */
    public void setBaseFood(int baseFood) {
        this.baseFood = baseFood;
    }

    /**
     * This function returns the base speed of the player
     * 
     * @return The baseSpeed variable is being returned.
     */
    public float getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * This function sets the base speed of the player
     * 
     * @param baseSpeed The speed of the enemy.
     */
    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    /**
     * This function returns the baseEvasion variable
     * 
     * @return The baseEvasion variable is being returned.
     */
    public float getBaseEvasion() {
        return baseEvasion;
    }

    /**
     * This function sets the baseEvasion variable to the value of the parameter baseEvasion
     * 
     * @param baseEvasion The base evasion of the character.
     */
    public void setBaseEvasion(float baseEvasion) {
        this.baseEvasion = baseEvasion;
    }

    /**
     * This function returns the baseSwordProficiency variable
     * 
     * @return The baseSwordProficiency variable is being returned.
     */
    public float getBaseSwordProficiency() {
        return baseSwordProficiency;
    }

    /**
     * This function sets the baseSwordProficiency variable to the value of the parameter passed in
     * 
     * @param baseSwordProficiency The base proficiency of the sword.
     */
    public void setBaseSwordProficiency(float baseSwordProficiency) {
        this.baseSwordProficiency = baseSwordProficiency;
    }

    /**
     * This function returns the baseBowProficiency variable
     * 
     * @return The baseBowProficiency variable is being returned.
     */
    public float getBaseBowProficiency() {
        return baseBowProficiency;
    }

    /**
     * This function sets the baseBowProficiency variable to the value of the parameter passed in
     * 
     * @param baseBowProficiency The base proficiency of the bow.
     */
    public void setBaseBowProficiency(float baseBowProficiency) {
        this.baseBowProficiency = baseBowProficiency;
    }

    /**
     * This function returns the baseCrossbowProficiency variable
     * 
     * @return The baseCrossbowProficiency variable is being returned.
     */
    public float getBaseCrossbowProficiency() {
        return baseCrossbowProficiency;
    }

    /**
     * This function sets the baseCrossbowProficiency variable to the value of the parameter passed in
     * 
     * @param baseCrossbowProficiency The base proficiency of the crossbow.
     */
    public void setBaseCrossbowProficiency(float baseCrossbowProficiency) {
        this.baseCrossbowProficiency = baseCrossbowProficiency;
    }

    /**
     * This function returns the base experience gain of the Pokemon
     * 
     * @return The baseExperienceGain variable is being returned.
     */
    public float getBaseExperienceGain() {
        return baseExperienceGain;
    }

    /**
     * This function sets the base experience gain of the player
     * 
     * @param baseExperienceGain The base experience gain for the player.
     */
    public void setBaseExperienceGain(float baseExperienceGain) {
        this.baseExperienceGain = baseExperienceGain;
    }

    /**
     * This function returns the harvest chance of the crop
     * 
     * @return The harvestChance variable is being returned.
     */
    public float getHarvestChance() {
        return harvestChance;
    }

    /**
     * This function sets the harvest chance of the crop to the value of the parameter harvestChance
     * 
     * @param harvestChance The chance that the block will drop an item when broken.
     */
    public void setHarvestChance(float harvestChance) {
        this.harvestChance = harvestChance;
    }

    /**
     * It returns the doubleHarvestChance variable
     * 
     * @return The doubleHarvestChance variable is being returned.
     */
    public float getDoubleHarvestChance() {
        return doubleHarvestChance;
    }

    /**
     * This function sets the double harvest chance to the value of the parameter
     * 
     * @param doubleHarvestChance The chance that a crop will drop two items instead of one.
     */
    public void setDoubleHarvestChance(float doubleHarvestChance) {
        this.doubleHarvestChance = doubleHarvestChance;
    }

    /**
     * This function returns the instantGrowthChance variable
     * 
     * @return The instantGrowthChance variable is being returned.
     */
    public float getInstantGrowthChance() {
        return instantGrowthChance;
    }

    /**
     * This function sets the instantGrowthChance variable to the value of the instantGrowthChance
     * parameter
     * 
     * @param instantGrowthChance The chance that a plant will grow instantly.
     */
    public void setInstantGrowthChance(float instantGrowthChance) {
        this.instantGrowthChance = instantGrowthChance;
    }

    /**
     * This function returns the beheadChance variable
     * 
     * @return The beheadChance variable is being returned.
     */
    public float getBeheadChance() {
        return beheadChance;
    }

    /**
     * This function sets the beheadChance variable to the value of the beheadChance parameter
     * 
     * @param beheadChance The chance that the player will behead the player.
     */
    public void setBeheadChance(float beheadChance) {
        this.beheadChance = beheadChance;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has a name tag
     * 
     * @return The boolean value of hasNameTag.
     */
    public boolean isHasNameTag() {
        return hasNameTag;
    }

    /**
     * This function sets the value of the variable hasNameTag to the value of the parameter hasNameTag
     * 
     * @param hasNameTag Whether or not the player has a name tag.
     */
    public void setHasNameTag(boolean hasNameTag) {
        this.hasNameTag = hasNameTag;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has the feather
     * fall enchantment
     * 
     * @return The boolean value of hasFeatherFall.
     */
    public boolean isHasFeatherFall() {
        return hasFeatherFall;
    }

    /**
     * This function sets the boolean value of the variable hasFeatherFall to the boolean value of the
     * parameter hasFeatherFall
     * 
     * @param hasFeatherFall Whether or not the player has the feather fall effect.
     */
    public void setHasFeatherFall(boolean hasFeatherFall) {
        this.hasFeatherFall = hasFeatherFall;
    }

    /**
     * This function returns a boolean value that indicates whether the device has quick charge or not
     * 
     * @return The value of the hasQuickCharge variable.
     */
    public boolean isHasQuickCharge() {
        return hasQuickCharge;
    }

    /**
     * This function sets the value of the variable hasQuickCharge to the value of the parameter
     * hasQuickCharge
     * 
     * @param hasQuickCharge boolean
     */
    public void setHasQuickCharge(boolean hasQuickCharge) {
        this.hasQuickCharge = hasQuickCharge;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has an
     * Efficiency pickaxe.
     * 
     * @return The boolean value of hasEfficiencyPickaxe.
     */
    public boolean isHasEfficiencyPickaxe() {
        return hasEfficiencyPickaxe;
    }

    /**
     * It sets the value of the variable hasEfficiencyPickaxe to the value of the parameter
     * hasEfficiencyPickaxe
     * 
     * @param hasEfficiencyPickaxe Whether or not the player has an Efficiency pickaxe
     */
    public void setHasEfficiencyPickaxe(boolean hasEfficiencyPickaxe) {
        this.hasEfficiencyPickaxe = hasEfficiencyPickaxe;
    }

    /**
     * This function returns the value of the variable hasEfficiencyAxe.
     * 
     * @return The boolean value of hasEfficiencyAxe.
     */
    public boolean isHasEfficiencyAxe() {
        return hasEfficiencyAxe;
    }

    /**
     * This function sets the value of the variable hasEfficiencyAxe to the value of the parameter
     * hasEfficiencyAxe
     * 
     * @param hasEfficiencyAxe If the player has an efficiency axe
     */
    public void setHasEfficiencyAxe(boolean hasEfficiencyAxe) {
        this.hasEfficiencyAxe = hasEfficiencyAxe;
    }

    /**
     * This function returns a boolean value that represents whether or not the player has a chance to
     * get extra ore
     * 
     * @return The boolean value of hasChanceExtraOre.
     */
    public boolean isHasChanceExtraOre() {
        return hasChanceExtraOre;
    }

    /**
     * This function sets the value of the variable hasChanceExtraOre to the value of the parameter
     * hasChanceExtraOre
     * 
     * @param hasChanceExtraOre If the ore has a chance to drop an extra ore.
     */
    public void setHasChanceExtraOre(boolean hasChanceExtraOre) {
        this.hasChanceExtraOre = hasChanceExtraOre;
    }

    /**
     * This function returns a boolean value that indicates whether or not the player has a chance to
     * get extra wood
     * 
     * @return The boolean value of hasChanceExtraWood.
     */
    public boolean isHasChanceExtraWood() {
        return hasChanceExtraWood;
    }

    /**
     * This function sets the value of the variable hasChanceExtraWood to the value of the parameter
     * hasChanceExtraWood
     * 
     * @param hasChanceExtraWood If true, the player has a chance to get extra wood when chopping down
     * a tree.
     */
    public void setHasChanceExtraWood(boolean hasChanceExtraWood) {
        this.hasChanceExtraWood = hasChanceExtraWood;
    }

    /**
     * This function returns a boolean value that indicates whether or not the chicken has a chance to
     * drop extra feathers
     * 
     * @return The boolean value of hasChanceExtraFeathers.
     */
    public boolean isHasChanceExtraFeathers() {
        return hasChanceExtraFeathers;
    }

    /**
     * This function sets the boolean value of the variable hasChanceExtraFeathers to the boolean value
     * of the parameter hasChanceExtraFeathers
     * 
     * @param hasChanceExtraFeathers If true, the player has a chance to get extra feathers when
     * killing a chicken.
     */
    public void setHasChanceExtraFeathers(boolean hasChanceExtraFeathers) {
        this.hasChanceExtraFeathers = hasChanceExtraFeathers;
    }

    /**
     * This function returns a boolean value that is true if the item has a chance to not take damage
     * when used in an anvil
     * 
     * @return The boolean value of hasChanceNoAnvilDamage.
     */
    public boolean isHasChanceNoAnvilDamage() {
        return hasChanceNoAnvilDamage;
    }

    /**
     * This function sets the boolean value of the variable hasChanceNoAnvilDamage to the boolean value
     * of the variable hasChanceNoAnvilDamage
     * 
     * @param hasChanceNoAnvilDamage If true, the item will have a chance to not take damage when used
     * in an anvil.
     */
    public void setHasChanceNoAnvilDamage(boolean hasChanceNoAnvilDamage) {
        this.hasChanceNoAnvilDamage = hasChanceNoAnvilDamage;
    }

    /**
     * This function returns a boolean value that indicates whether the player has a chance to not
     * consume a block
     * 
     * @return The value of the variable hasChanceNoConsumeBlock.
     */
    public boolean isHasChanceNoConsumeBlock() {
        return hasChanceNoConsumeBlock;
    }

    /**
     * This function sets the value of the variable hasChanceNoConsumeBlock to the value of the
     * parameter hasChanceNoConsumeBlock
     * 
     * @param hasChanceNoConsumeBlock If true, the block will have a chance to not consume the item.
     */
    public void setHasChanceNoConsumeBlock(boolean hasChanceNoConsumeBlock) {
        this.hasChanceNoConsumeBlock = hasChanceNoConsumeBlock;
    }

    /**
     * This function returns a boolean value that indicates whether or not the player can ignite oil.
     * 
     * @return The boolean value of canIgniteOil.
     */
    public boolean isCanIgniteOil() {
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
    public boolean isCanExtractOil() {
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
    public boolean isCanBreakGlass() {
        return canBreakGlass;
    }

    /**
     * This function sets the value of the variable canBreakGlass to the value of the parameter
     * canBreakGlass
     * 
     * @param canBreakGlass Whether or not the player can break glass with this tool.
     */
    public void setCanBreakGlass(boolean canBreakGlass) {
        this.canBreakGlass = canBreakGlass;
    }

    /**
     * This function returns a boolean value that is true if the horse can craft a saddle, and false if
     * it cannot.
     * 
     * @return The boolean value of canCraftSaddle.
     */
    public boolean isCanCraftSaddle() {
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
    public boolean isCanTameAnimals() {
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
    public boolean isCanSweepingEdge() {
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
    public boolean isCanExtractBones() {
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
    public boolean isCanBreakBeehive() {
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
    public boolean isCanCraftGunpowder() {
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
    public boolean isCanApplyUnbreaking() {
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
     * This function returns a boolean value that is true if the player can make high tier armor, and
     * false if they can't.
     * 
     * @return The boolean value of canMakeHighTierArmor.
     */
    public boolean isCanMakeHighTierArmor() {
        return canMakeHighTierArmor;
    }

    /**
     * This function sets the value of the variable canMakeHighTierArmor to the value of the parameter
     * canMakeHighTierArmor
     * 
     * @param canMakeHighTierArmor Whether or not the player can make high tier armor.
     */
    public void setCanMakeHighTierArmor(boolean canMakeHighTierArmor) {
        this.canMakeHighTierArmor = canMakeHighTierArmor;
    }

    /**
     * This function returns a boolean value that determines whether or not the player can make a
     * knowledge book
     * 
     * @return The boolean value of canMakeKnowledgeBook.
     */
    public boolean isCanMakeKnowledgeBook() {
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
    public boolean isSpecialItems() {
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

    /**
     * This function returns a list of strings that represent the weapon proficiency of the character
     * 
     * @return The weaponProficiency list.
     */
    public List<String> getWeaponProficiency() {
        return weaponProficiency;
    }

    /**
     * This function sets the weapon proficiency of the character
     * 
     * @param weaponProficiency List of Strings
     */
    public void setWeaponProficiency(List<String> weaponProficiency) {
        this.weaponProficiency = weaponProficiency;
    }

    /**
     * This function returns a list of strings that represent the armor proficiency of the class
     * 
     * @return A list of strings.
     */
    public List<String> getArmorProficiency() {
        return armorProficiency;
    }

    /**
     * This function sets the armorProficiency variable to the value of the parameter armorProficiency
     * 
     * @param armorProficiency List of armor types the character is proficient with.
     */
    public void setArmorProficiency(List<String> armorProficiency) {
        this.armorProficiency = armorProficiency;
    }

}
