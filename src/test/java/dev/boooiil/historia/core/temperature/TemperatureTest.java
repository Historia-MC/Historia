package dev.boooiil.historia.core.temperature;

import dev.boooiil.historia.core.BaseTest;
import dev.boooiil.historia.core.configuration.ConfigurationLoader;
import dev.boooiil.historia.core.database.internal.TemperatureStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureTest extends BaseTest {

    private static WorldMock world;
    private final double baseTemperature = 15.6;

    @BeforeAll
    public static void setupTest() {
        world = new WorldMock(Material.WATER, 20);
        server.addWorld(world);
    }

    @AfterAll
    public static void destroyTest() {
        server.removeWorld(world);
    }

    @Test
    public void testBaseTemperature() {
        PlayerMock player = server.addPlayer();

        Location loc = new Location(world, 0, 64, 0);
        world.setBlockData(loc, Material.STONE.createBlockData());
        player.teleport(loc);

        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double temp = manager.getTemperature(player);
        assertEquals(baseTemperature, temp, 0.01, "Base temperature should match expected value");
    }

    @Test
    public void testHeatSourceEffect() {
        PlayerMock player = server.addPlayer();

        Location playerLoc = new Location(world, 0, 64, 0);
        Location campfireLoc = playerLoc.clone().add(1, 0, 0);

        world.setBlockData(playerLoc, Material.AIR.createBlockData());
        world.setBlockData(campfireLoc, Material.LAVA.createBlockData());
        player.teleport(playerLoc);

        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double temp = manager.getTemperature(player);
        assertTrue(temp > baseTemperature, "Temperature should increase near heat source");
    }

    @Test
    public void testBiomeTemperature() {
        PlayerMock player = server.addPlayer();

        Location loc = new Location(world, 0, 64, 0);
        world.setBiome(loc, Biome.DESERT);
        player.teleport(loc);

        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double temp = manager.getTemperature(player);
        assertTrue(temp > baseTemperature, "Desert biome should be warmer than base temperature");
    }

    @Test
    public void testUndergroundTemperature() {
        PlayerMock player = server.addPlayer();

        Location loc = new Location(world, 0, 32, 0);
        // Create stone box around player location
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 2; y++) { // Extra height to cover player
                for (int z = -1; z <= 1; z++) {
                    // Skip the center block at player height to avoid suffocation
                    if (x == 0 && y == 0 && z == 0)
                        continue;
                    if (x == 0 && y == 1 && z == 0)
                        continue;
                    Location boxLoc = loc.clone().add(x, y, z);
                    world.setBlockData(boxLoc, Material.STONE.createBlockData());
                }
            }
        }
        player.teleport(loc);

        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double temp = manager.getTemperature(player);
        assertNotEquals(baseTemperature, temp, "Underground temperature should differ from surface");
    }

    @Test
    public void testConfigReload() {

        PlayerMock player = server.addPlayer();

        ConfigurationLoader.init();
        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double tempBefore = manager.getTemperature(player);

        ConfigurationLoader.reload();
        double tempAfter = manager.getTemperature(player);

        // ?? useless test, should change configuration somehow and then test if
        // configuration gets overwritten
        assertEquals(tempBefore, tempAfter, "Temperature should remain consistent after config reload");
    }

    // Test for temperature in water.
    @Test
    public void testTemperatureInWater() {
        PlayerMock player = server.addPlayer();

        player.setInWater(true);
        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double temp = manager.getTemperature(player);
        assertTrue(temp < baseTemperature, "Temperature in water should be higher than base temperature");
    }

    @Test
    public void testTemperatureRunnable() {
        PlayerMock player = server.addPlayer();
        
        Location location = new Location(world, 10, 80, 20);
        player.teleport(location);
        world.setTime(3000);
        TemperatureManager manager = TemperatureStorage.getTemperatureManager(player.getUniqueId());
        double tempInitial = manager.getTemperature(player);
        world.setTime(13000);
        manager.updateTemperature();
        double tempFinal = manager.getTemperature(player);
        assertNotEquals(tempInitial, tempFinal, "Temperature should not be the same after time change");
    }
}