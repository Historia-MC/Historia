package dev.boooiil.historia.core;

import dev.boooiil.historia.core.database.internal.PlayerStorage;
import dev.boooiil.historia.core.player.HistoriaPlayer;
import dev.boooiil.historia.core.util.CoreLogger;
import org.junit.jupiter.api.Test;

public class MainTest extends BaseTest {

    @Test
    public void testNewPlayerJoinLeave() {
        server.addPlayer(player);
        player.disconnect();
    }

    @Test
    public void testWarriorProficiency() {

        System.out.println("*********************************");
        System.out.println("Testing Warrior Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        CoreLogger.debugToConsole(historiaPlayer.toString());
        CoreLogger.debugToConsole(historiaPlayer.toJSON());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("warrior"));

    }

    @Test
    public void testArcherProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Archer Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("archer"));
    }

    @Test
    public void testFishermanProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Fisherman Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Fisherman"));
    }

    @Test
    public void testMinerProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Miner Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Miner"));
    }

    @Test
    public void testBlacksmithProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Blacksmith Proficiency");
        System.out.println("*********************************");

        System.out.println(server.getOnlinePlayers().size());

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Blacksmith"));
    }

    @Test
    public void testHuntsmanProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Huntsman Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Huntsman"));
    }

    @Test
    public void testApothecaryProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Apothecary Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Apothecary"));
    }

    @Test
    public void testArchitectProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Architect Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Architect"));
    }

    @Test
    public void testLumberjackProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Lumberjack Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Lumberjack"));
    }

    @Test
    public void testFarmerProficiency() {
        System.out.println("*********************************");
        System.out.println("Testing Farmer Proficiency");
        System.out.println("*********************************");

        server.addPlayer(player);

        HistoriaPlayer historiaPlayer = PlayerStorage.getPlayer(player.getUniqueId());

        historiaPlayer.changeProficiency(HistoriaCore.getNamespacedKey("Farmer"));
    }
}
