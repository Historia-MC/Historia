package dev.boooiil.historia.classes.enums;

public class ExperienceTypes {

    // MAKE THIS INTO ITS OWN YAML
    // COULD CHANGE THE PROFICIENCY.YML
    // CLASS:
    //     stats:
    //         ...
    //         income_source:
    //             BLOCK_BREAK: base xp amount
    //             ...

    public enum AllSources {

        ATTACK(1d),
        DEFEND(2d),
        EVADE(2d),
        KILL(3d),
        DEATH(-2d),
        CROP_PLACE(1d),
        WEAPON_CRAFT(1d),
        TOOL_CRAFT(1d),
        ARMOR_CRAFT(1d),
        OTHER_CRAFT(1d),
        CROP_BREAK(1d),
        ORE_BREAK(1d),
        BLOCK_PLACE(1d),
        BLOCK_BREAK(1d),
        BLOCK_INTERACT(1d),
        BREED_ANIMAL(1d),
        TAME_ANIMAL(1d),
        FISH(1d),
        FISH_TRASH(-1d),
        FISH_TREASURE(3d),
        RANGED_HIT(1d),
        RANGED_KILL(2d),
        NONE(0d);

        private final double key;

        private AllSources(double key) {
            this.key = key;
        }

        public double getKey() {
            return key;
        }

    }

    public enum CombatSources {

        NONE(AllSources.valueOf("NONE")),
        ATTACK(AllSources.valueOf("ATTACK")),
        DEFEND(AllSources.valueOf("DEFEND")),
        EVADE(AllSources.valueOf("EVADE")),
        KILL(AllSources.valueOf("KILL")),
        DEATH(AllSources.valueOf("DEATH")),
        RANGED_HIT(AllSources.valueOf("RANGED_HIT")),
        RANGED_KILL(AllSources.valueOf("RANGED_KILL"));

        private final AllSources key;

        private CombatSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }

    public enum BlockSources {

        NONE(AllSources.valueOf("NONE")),
        CROP_PLACE(AllSources.valueOf("CROP_PLACE")),
        CROP_BREAK(AllSources.valueOf("CROP_BREAK")),
        ORE_BREAK(AllSources.valueOf("ORE_BREAK")),
        BLOCK_PLACE(AllSources.valueOf("BLOCK_PLACE")),
        BLOCK_BREAK(AllSources.valueOf("BLOCK_BREAK")),
        BLOCK_INTERACT(AllSources.valueOf("BLOCK_INTERACT"));

        private final AllSources key;

        private BlockSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }
 
    public enum FishingSources {

        NONE(AllSources.valueOf("NONE")),
        FISH(AllSources.valueOf("FISH")),
        FISH_TRASH(AllSources.valueOf("FISH_TRASH")),
        FISH_TREASURE(AllSources.valueOf("FISH_TREASURE"));

        private final AllSources key;

        private FishingSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }

    public enum AnimalSources {

        NONE(AllSources.valueOf("NONE")),
        BREED_ANIMAL(AllSources.valueOf("BREED_ANIMAL")),
        TAME_ANIMAL(AllSources.valueOf("TAME_ANIMAL"));

        private final AllSources key;

        private AnimalSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }

    public enum FarmingSources {

        NONE(AllSources.valueOf("NONE")),
        CROP_PLACE(AllSources.valueOf("CROP_PLACE")),
        CROP_BREAK(AllSources.valueOf("CROP_BREAK"));

        private final AllSources key;

        private FarmingSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }


    }

    public enum CraftingSources {

        NONE(AllSources.valueOf("NONE")),
        WEAPON_CRAFT(AllSources.valueOf("WEAPON_CRAFT")),
        TOOL_CRAFT(AllSources.valueOf("TOOL_CRAFT")),
        ARMOR_CRAFT(AllSources.valueOf("ARMOR_CRAFT")),
        OTHER_CRAFT(AllSources.valueOf("OTHER_CRAFT"));

        private final AllSources key;

        private CraftingSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }
}
