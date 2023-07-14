package dev.boooiil.historia.classes.historia.proficiency;

import org.bukkit.configuration.file.FileConfiguration;

import dev.boooiil.historia.classes.enums.FileMap.ResourceKeys;
import dev.boooiil.historia.util.FileGetter;

public class Proficiency {
    
    private String name;

    private Stats stats;
    private Skills skills;

    public Proficiency(String proficiencyName) {

        FileConfiguration config = FileGetter.get(ResourceKeys.PROFICIENCY);

        this.name = proficiencyName;
        this.stats = new Stats(config, proficiencyName + ".stats");
        this.skills = new Skills(config, proficiencyName + ".skills");
    }

    public String getName() {
        return name;
    }

    public void setName(String proficiencyName) {
        this.name = proficiencyName;
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

    public String toString() {

        String output = "*** PROFICIENCY ***\n";

        output += "Name: " + name + "\n";
        output += stats.toString();
        output += skills.toString();

        return output;

    }

}
