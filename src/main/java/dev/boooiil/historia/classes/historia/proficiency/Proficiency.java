package dev.boooiil.historia.classes.historia.proficiency;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.classes.enums.FileMap.ResourceKeys;
import dev.boooiil.historia.util.FileGetter;

public class Proficiency {
    
    private String proficiencyName;

    private Stats stats;
    private Skills skills;

    public Proficiency(String proficiencyName) {

        FileConfiguration config = FileGetter.get(ResourceKeys.PROFICIENCY);

        this.proficiencyName = proficiencyName;
        this.stats = new Stats(config, proficiencyName + ".stats");
        this.skills = new Skills(config, proficiencyName + ".skills");
    }

    public String getProficiencyName() {
        return proficiencyName;
    }

    public void setProficiencyName(String proficiencyName) {
        this.proficiencyName = proficiencyName;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

}
