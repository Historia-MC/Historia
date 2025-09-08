package dev.boooiil.historia.core.proficiency.skills;

import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.util.JSONSerializable;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;

public interface ISkill extends JSONSerializable {
    /**
     * Get the type of the skill.
     * 
     * @return Type of the skill.
     */
    SkillType getType();

    /**
     * Get the name of the skill.
     * 
     * @return Name of the skill.
     */
    NamespacedKey getName();

    /**
     * Execute the skill with a given set of supplied objects.
     * 
     * @param skillSuppliers - Objects to be provided for this skill.
     */
    void execute(SkillSupplier<?>... skillSuppliers);

    /**
     * Register to be used to handle when the skill executes.
     */
    void register();

    void deregister();

    ISkill create(ConfigurationSection section);
}
