package dev.boooiil.historia.core.handlers.block.blockPlace;

import dev.boooiil.historia.core.dependents.Permissions;
import dev.boooiil.historia.core.handlers.block.BaseBlockHandler;
import dev.boooiil.historia.core.proficiency.skills.SkillType;
import dev.boooiil.historia.core.util.Logging;
import dev.boooiil.historia.core.util.NumberUtils;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceHandler extends BaseBlockHandler {

    public BlockPlaceHandler(BlockPlaceEvent event) {
        super(event);
        this.event = event;

    }

    public void doDetermineBlockType() {

        switch (this.getBlock().getType()) {

            case LADDER:
                doLadderBypass();
                break;

            default:
                doConsumptionBypass();
                break;
        }
    }

    private void doLadderBypass() {

        // guard against players who can already place the block
        if (Permissions.canPlaceBlock(this.getPlayer(), this.getBlock()))
            return;

        // guard against players who do not have the skill
        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.LADDER_BYPASS))
            return;

        // TODO: test this!!

        Logging.debugToConsole(
                "[BPH#ladderBypass] Player: " + this.getPlayer().getName() + " placed a ladder!");

        Logging.debugToConsole("[BPH#ladderBypass] Block placed location: " + this.getPlacedBlock().getLocation());
        Logging.debugToConsole("[BPH#ladderBypass] Block location: " + this.getBlock().getLocation());
        Logging.debugToConsole("[BPH#ladderBypass] Player location: " + this.getPlayer().getLocation());

        ItemStack heldItem = this.getPlayer().getInventory().getItemInMainHand();
        ItemStack offhandItem = this.getPlayer().getInventory().getItemInOffHand();

        if (offhandItem.getType() == Material.LADDER) {
            if (offhandItem.getAmount() > 1)
                offhandItem.setAmount(offhandItem.getAmount() - 1);
            else
                this.getPlayer().getInventory().setItemInOffHand(null);
        } else if (heldItem.getType() == Material.LADDER) {
            if (offhandItem.getAmount() > 1)
                offhandItem.setAmount(offhandItem.getAmount() - 1);
            else
                this.getPlayer().getInventory().setItemInOffHand(null);
        }

    }

    private void doConsumptionBypass() {

        // guard against players who cannot place the block
        if (!Permissions.canPlaceBlock(this.getPlayer(), this.getBlock()))
            return;

        // guard against players that do not have this skill
        if (!historiaPlayer.getProficiency().getSkills().hasSkill(SkillType.CHANCE_NO_CONSUME_BLOCK))
            return;

        switch (this.getBlock().getType()) {

            case IRON_BLOCK:
            case DIAMOND_BLOCK:
            case EMERALD_BLOCK:
            case GOLD_BLOCK:
                return;

            default:
                break;
        }

        int chance = 1;
        int random = NumberUtils.randomInt(1, 100);

        if (random > chance)
            return;

        ItemStack item = new ItemStack(this.getBlock().getType(), 1);

        this.getPlayer().getInventory().addItem(item);

        Logging.infoToPlayer("Your skills allowed you to not consume a block!", historiaPlayer.getUUID());
    }

}
