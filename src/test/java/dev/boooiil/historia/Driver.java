package dev.boooiil.historia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import dev.boooiil.historia.classes.historia.user.HistoriaPlayer;
import dev.boooiil.historia.util.Logging;
import dev.boooiil.historia.util.PlayerStorage;

public class Driver {
    
    private ServerMock server;
    private HistoriaPlugin plugin;
    private PluginDescriptionFile descriptionFile;
    private HistoriaPlayer historiaPlayer;
 

    @Before
    public void setUp() {

        Logging.infoToConsole("SETTING UP TESTS");
        
        descriptionFile = new PluginDescriptionFile("Historia", "6.0", "dev.boooiil.historia.HistoriaPlugin"); 

        Logging.infoToConsole("DESC: " + descriptionFile);

        server = MockBukkit.mock();
        plugin = MockBukkit.loadWith(dev.boooiil.historia.HistoriaPlugin.class, descriptionFile);

        Player player = server.addPlayer();
        historiaPlayer = new HistoriaPlayer(player.getUniqueId(), server);

        PlayerStorage.addPlayer(player.getUniqueId(), historiaPlayer);

    }

    @Test
    public void assertion_PlayerStorage() {

        assertEquals(1, PlayerStorage.players.size());

    }

    @After
    public void tearDown() {

        MockBukkit.unmock();

    }
    

}
