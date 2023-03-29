package dev.boooiil.historia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import dev.boooiil.historia.classes.HistoriaPlayer;
import dev.boooiil.historia.util.PlayerStorage;

public class Driver {
    
    private ServerMock server;
    private HistoriaPlugin plugin;

    private HistoriaPlayer historiaPlayer;
 

    @Before
    public void setUp() {

        System.out.println("SETTING UP TESTS");

        server = MockBukkit.mock();
        plugin = MockBukkit.load(HistoriaPlugin.class);
        
        Player player = server.addPlayer();
        historiaPlayer = new HistoriaPlayer(player.getUniqueId(), server);

        PlayerStorage.addPlayer(
            player.getUniqueId(),
            historiaPlayer
        );

        PlayerStorage.getPlayer(player.getUniqueId(), false).saveCharacter();
        server.addPlayer();

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
