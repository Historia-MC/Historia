package dev.boooiil.historia.core.proficiency.stats;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency.ProficiencyName;
import dev.boooiil.historia.core.proficiency.stats.Stats.StatsType;
import dev.boooiil.historia.core.proficiency.stats.Stats.WeaponStatsType;
import dev.boooiil.historia.core.util.JSONUtils;

public class WeaponStats implements StatsComponent {

    /** The stat modifiers associated with these weapon stats. */
    private final StatModifiers statModifiers;
    /** Sword proficiency of the player. */
    private int sword;
    /** Sword level experience of the player. */
    private double swordExperience;
    /** Bow proficiency of the player. */
    private int bow;
    /** Bow level experience of the player. */
    private double bowExperience;
    /** Crossbow proficiency of the player. */
    private int crossbow;
    /** Crossbow level experience of the player. */
    private double crossbowExperience;
    /** Trident proficiency of the player. */
    private int trident;
    /** Trident level experience of the player. */
    private double tridentExperience;
    /** Axe proficiency of the player. */
    private int axe;
    /** Axe level experience of the player. */
    private double axeExperience;

    /** The usable weapon weights this player can use. */
    private List<String> usableWeaponWeights;

    /**
     * 
     * @param section The `proficiency.armor` configuration section.
     */
    public WeaponStats(ConfigurationSection section, ProficiencyName proficiencyName) {
        this.statModifiers = HistoriaCore.Companion.getSTAT_MODIFIERS_REGISTRY()
                .get(proficiencyName.getKey());
        this.sword = section.getInt("sword");
        this.swordExperience = 0;
        this.bow = section.getInt("bow");
        this.bowExperience = 0;
        this.crossbow = section.getInt("crossbow");
        this.crossbowExperience = 0;
        this.trident = section.getInt("trident");
        this.tridentExperience = 0;
        this.axe = section.getInt("axe");
        this.axeExperience = 0;
        this.usableWeaponWeights = section.getStringList("usableWeaponTypes");
    }

    public WeaponStats(StatModifiers statModifiers,
            int swordLevel, double swordExperience,
            int bowLevel, double bowExperience,
            int crossbowLevel, double crossbowExperience,
            int tridentLevel, double tridentExperience,
            int axeLevel, double axeExperience,
            List<String> usableWeaponTypes) {
        this.statModifiers = statModifiers;
        this.sword = swordLevel;
        this.swordExperience = swordExperience;
        this.bow = bowLevel;
        this.bowExperience = bowExperience;
        this.crossbow = crossbowLevel;
        this.crossbowExperience = crossbowExperience;
        this.trident = tridentLevel;
        this.tridentExperience = tridentExperience;
        this.axe = axeLevel;
        this.axeExperience = axeExperience;
        this.usableWeaponWeights = usableWeaponTypes;
    }

    public void advance(StatsType type) {
        switch (type) {
            case SWORD:
                swordExperience++;
                if (swordExperience >= sword * 10) {
                    sword++;
                    swordExperience = 0;
                }
                break;
            case BOW:
                bowExperience++;
                if (bowExperience >= bow * 10) {
                    bow++;
                    bowExperience = 0;
                }
                break;
            case CROSSBOW:
                crossbowExperience++;
                if (crossbowExperience >= crossbow * 10) {
                    crossbow++;
                    crossbowExperience = 0;
                }
                break;
            case TRIDENT:
                tridentExperience++;
                if (tridentExperience >= trident * 10) {
                    trident++;
                    tridentExperience = 0;
                }
                break;
            case AXE:
                axeExperience++;
                if (axeExperience >= axe * 10) {
                    axe++;
                    axeExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void advance(WeaponStatsType type) {
        advance(type.getKey());
    }

    public void decrease(StatsType type) {
        switch (type) {
            case SWORD:
                swordExperience--;
                if (swordExperience <= 0) {
                    sword--;
                    swordExperience = 0;
                }
                break;
            case BOW:
                bowExperience--;
                if (bowExperience <= 0) {
                    bow--;
                    bowExperience = 0;
                }
                break;
            case CROSSBOW:
                crossbowExperience--;
                if (crossbowExperience <= 0) {
                    crossbow--;
                    crossbowExperience = 0;
                }
                break;
            case TRIDENT:
                tridentExperience--;
                if (tridentExperience <= 0) {
                    trident--;
                    tridentExperience = 0;
                }
                break;
            case AXE:
                axeExperience--;
                if (axeExperience <= 0) {
                    axe--;
                    axeExperience = 0;
                }
                break;
            default:
                break;
        }
    }

    public void decrease(WeaponStatsType type) {
        decrease(type.getKey());
    }

    public int getLevel(StatsType type) {
        switch (type) {
            case SWORD:
                return sword;
            case BOW:
                return bow;
            case CROSSBOW:
                return crossbow;
            case TRIDENT:
                return trident;
            case AXE:
                return axe;
            default:
                return 0;
        }
    }

    public int getLevel(WeaponStatsType type) {
        return getLevel(type.getKey());
    }

    public double getExperience(StatsType type) {
        switch (type) {
            case SWORD:
                return swordExperience;
            case BOW:
                return bowExperience;
            case CROSSBOW:
                return crossbowExperience;
            case TRIDENT:
                return tridentExperience;
            case AXE:
                return axeExperience;
            default:
                return 0;
        }
    }

    public double getExperience(WeaponStatsType type) {
        return getExperience(type.getKey());
    }

    public void setLevel(StatsType type, int level) {
        switch (type) {
            case SWORD:
                sword = level;
                break;
            case BOW:
                bow = level;
                break;
            case CROSSBOW:
                crossbow = level;
                break;
            case TRIDENT:
                trident = level;
                break;
            case AXE:
                axe = level;
                break;
            default:
                break;
        }
    }

    public void setLevel(WeaponStatsType type, int level) {
        setLevel(type.getKey(), level);
    }

    public void setExperience(StatsType type, double experience) {
        switch (type) {
            case SWORD:
                swordExperience = experience;
                break;
            case BOW:
                bowExperience = experience;
                break;
            case CROSSBOW:
                crossbowExperience = experience;
                break;
            case TRIDENT:
                tridentExperience = experience;
                break;
            case AXE:
                axeExperience = experience;
                break;
            default:
                break;
        }
    }

    public void setExperience(WeaponStatsType type, double experience) {
        setExperience(type.getKey(), experience);
    }

    public boolean isUsableWeaponWeight(String weaponType) {
        return usableWeaponWeights.contains(weaponType.toLowerCase());
    }

    /**
     * returns a list of strings that represent the weapon proficiency
     * of the character
     * 
     * @return The weaponProficiency list.
     */
    public List<String> getUsableWeaponWeights() {
        return usableWeaponWeights;
    }

    /**
     * sets the weapon proficiency of the character
     * 
     * @param weaponProficiency List of Strings
     */
    public void setUsableWeaponWeights(List<String> weaponWeight) {
        this.usableWeaponWeights = weaponWeight;
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("sword", this.sword) + ", ");
        sb.append(JSONUtils.fromValue("bow", this.bow) + ", ");
        sb.append(JSONUtils.fromValue("crossbow", this.crossbow) + ", ");
        sb.append(JSONUtils.fromValue("trident", this.trident) + ", ");
        sb.append(JSONUtils.fromValue("axe", this.axe) + ", ");
        sb.append("}");

        return sb.toString();
    }

}
