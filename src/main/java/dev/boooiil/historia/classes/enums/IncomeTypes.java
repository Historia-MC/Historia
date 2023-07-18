package dev.boooiil.historia.classes.enums;

public class IncomeTypes {

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

        ATTACK(AllSources.valueOf("ATTACK").getKey()),
        DEFEND(AllSources.valueOf("DEFEND").getKey()),
        EVADE(AllSources.valueOf("EVADE").getKey()),
        KILL(AllSources.valueOf("KILL").getKey()),
        DEATH(AllSources.valueOf("DEATH").getKey()),
        RANGED_HIT(AllSources.valueOf("RANGED_HIT").getKey()),
        RANGED_KILL(AllSources.valueOf("RANGED_KILL").getKey());

        private long key;

        private CombatSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }

    }

    public enum BlockSources {

        CROP_PLACE(AllSources.valueOf("CROP_PLACE").getKey()),
        CROP_BREAK(AllSources.valueOf("CROP_BREAK").getKey()),
        ORE_BREAK(AllSources.valueOf("ORE_BREAK").getKey()),
        BLOCK_PLACE(AllSources.valueOf("BLOCK_PLACE").getKey()),
        BLOCK_BREAK(AllSources.valueOf("BLOCK_BREAK").getKey()),
        BLOCK_INTERACT(AllSources.valueOf("BLOCK_INTERACT").getKey());

        private long key;

        private BlockSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }

    }
 
    public enum FishingSources {

        FISH(AllSources.valueOf("FISH").getKey()),
        FISH_TRASH(AllSources.valueOf("FISH_TRASH").getKey()),
        FISH_TREASURE(AllSources.valueOf("FISH_TREASURE").getKey());

        private long key;

        private FishingSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }

    }

    public enum AnimalSources {

        BREED_ANIMAL(AllSources.valueOf("BREED_ANIMAL").getKey()),
        TAME_ANIMAL(AllSources.valueOf("TAME_ANIMAL").getKey());

        private long key;

        private AnimalSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }

    }

    public enum FarmingSources {

        CROP_PLACE(AllSources.valueOf("CROP_PLACE").getKey()),
        CROP_BREAK(AllSources.valueOf("CROP_BREAK").getKey());

        private long key;

        private FarmingSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }


    }

    public enum CraftingSources {

        WEAPON_CRAFT(AllSources.valueOf("WEAPON_CRAFT").getKey()),
        TOOL_CRAFT(AllSources.valueOf("TOOL_CRAFT").getKey()),
        ARMOR_CRAFT(AllSources.valueOf("ARMOR_CRAFT").getKey());

        private long key;

        private CraftingSources(long key) {
            this.key = key;
        }

        public long getKey() {
            return key;
        }

    }
}
