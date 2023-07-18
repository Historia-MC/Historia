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

        ATTACK(1l),
        DEFEND(2l),
        EVADE(2l),
        KILL(3l),
        DEATH(-2l),
        CROP_PLACE(1l),
        WEAPON_CRAFT(1l),
        TOOL_CRAFT(1l),
        ARMOR_CRAFT(1l),
        OTHER_CRAFT(1l),
        CROP_BREAK(1l),
        ORE_BREAK(1l),
        BLOCK_PLACE(1l),
        BLOCK_BREAK(1l),
        BLOCK_INTERACT(1l),
        BREED_ANIMAL(1l),
        TAME_ANIMAL(1l),
        FISH(1l),
        FISH_TRASH(-1l),
        FISH_TREASURE(3l),
        RANGED_HIT(1l),
        RANGED_KILL(2l);

        private long key;

        private AllSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }

    }

    public enum CombatSources {

        ATTACK(AllSources.valueOf("ATTACK")),
        DEFEND(AllSources.valueOf("DEFEND")),
        EVADE(AllSources.valueOf("EVADE")),
        KILL(AllSources.valueOf("KILL")),
        DEATH(AllSources.valueOf("DEATH")),
        RANGED_HIT(AllSources.valueOf("RANGED_HIT")),
        RANGED_KILL(AllSources.valueOf("RANGED_KILL"));

        private AllSources key;

        private CombatSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }

    public enum BlockSources {

        CROP_PLACE(AllSources.valueOf("CROP_PLACE")),
        CROP_BREAK(AllSources.valueOf("CROP_BREAK")),
        ORE_BREAK(AllSources.valueOf("ORE_BREAK")),
        BLOCK_PLACE(AllSources.valueOf("BLOCK_PLACE")),
        BLOCK_BREAK(AllSources.valueOf("BLOCK_BREAK")),
        BLOCK_INTERACT(AllSources.valueOf("BLOCK_INTERACT"));

        private AllSources key;

        private BlockSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }
 
    public enum FishingSources {

        FISH(AllSources.valueOf("FISH")),
        FISH_TRASH(AllSources.valueOf("FISH_TRASH")),
        FISH_TREASURE(AllSources.valueOf("FISH_TREASURE"));

        private AllSources key;

        private FishingSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }

    public enum AnimalSources {

        BREED_ANIMAL(AllSources.valueOf("BREED_ANIMAL")),
        TAME_ANIMAL(AllSources.valueOf("TAME_ANIMAL"));

        private AllSources key;

        private AnimalSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }

    public enum FarmingSources {

        CROP_PLACE(AllSources.valueOf("CROP_PLACE")),
        CROP_BREAK(AllSources.valueOf("CROP_BREAK"));

        private AllSources key;

        private FarmingSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }


    }

    public enum CraftingSources {

        WEAPON_CRAFT(AllSources.valueOf("WEAPON_CRAFT")),
        TOOL_CRAFT(AllSources.valueOf("TOOL_CRAFT")),
        ARMOR_CRAFT(AllSources.valueOf("ARMOR_CRAFT")),
        OTHER_CRAFT(AllSources.valueOf("OTHER_CRAFT"));

        private AllSources key;

        private CraftingSources(AllSources key) {
            this.key = key;
        }

        public AllSources getKey() {
            return key;
        }

    }
}
