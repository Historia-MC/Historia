package dev.boooiil.historia.core.proficiency.skills;

import org.bukkit.NamespacedKey;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractSkillRunnable extends BukkitRunnable implements ISkillRunnable {

    public Skills.SkillType getType() {
        return null;
    }

    public NamespacedKey getName() {
        return null;
    }

    public void execute(SkillSupplier<?>... skillSuppliers) {
        throw new UnsupportedOperationException();
    }

    public void register() {
        throw new UnsupportedOperationException();
    }

    public void deregister() {
        throw new UnsupportedOperationException();
    }

    public void run() {
        throw new UnsupportedOperationException();
    }
}
