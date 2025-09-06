package dev.boooiil.historia.core.proficiency.skills.passive.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.proficiency.Proficiency;
import dev.boooiil.historia.core.proficiency.skills.AbstractSkillRunnable;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.proficiency.skills.ISkillRunnable;
import dev.boooiil.historia.core.proficiency.skills.SkillSupplier;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillName;
import dev.boooiil.historia.core.proficiency.skills.Skills.SkillType;
import dev.boooiil.historia.core.util.CoreLogger;

public class SkillEnchantItem extends AbstractSkillRunnable {

    // skill -> material -> enchant -> level

    public SkillEnchantItem(Material material, int level) {


    }

    @Override
    public SkillType getType() {
        return SkillType.RUNNER_PASSIVE;
    }

    @Override
    public NamespacedKey getName() {
        return SkillName.APPLY_UNBREAKING.getKey();
    }

    @Override
    public void execute(SkillSupplier<?>... skillSuppliers) {

        /**
         * check if the player is at the required level stage
         */

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            HistoriaPlayer histPlayer = PlayerStorage.getPlayer(player.getUniqueId());
            Proficiency proficiency = HistoriaCore.PROFICIENCY_REGISTRY.get(histPlayer.getProficiency().getName());

            if (proficiency == null) {}
        }
    }

    @Override
    public void register() {
        // Register event handlers if needed
        HistoriaCore.getInstance().registerRunnable(this);
    }

    @Override
    public void deregister() {
        // Unregister event handlers if needed
    }

    @Override
    public void run() {
        execute();
    }

    @Override
    public String toJSON() {
        StringBuilder sb = new StringBuilder();

        sb.append(JSONUtils.fromValue("name", getName()));
        return "";
    }
}
