package dev.boooiil.historia.core.expiry

import dev.boooiil.historia.core.BaseTest
import dev.boooiil.historia.core.expiry.block.FluidContent
import dev.boooiil.historia.core.expiry.block.HCauldron
import dev.boooiil.historia.core.expiry.block.HCauldrons
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockbukkit.mockbukkit.block.BlockMock
import org.mockbukkit.mockbukkit.world.ChunkMock
import org.mockbukkit.mockbukkit.world.Coordinate
import org.mockbukkit.mockbukkit.world.WorldMock

class CauldronTest : BaseTest() {
    private lateinit var chunk: ChunkMock
    private lateinit var block: BlockMock
    private lateinit var world: WorldMock

    @BeforeEach
    fun setUp() {

        println("Setting up world...")

        world = server.addSimpleWorld("world")

        this.chunk = world.getChunkAt(0, 0)

        this.block = world.createBlock(Coordinate(0, 0, 0))
        this.block.type = Material.WATER_CAULDRON

        println("Finished setup.")
    }

    @AfterEach
    fun tearDown() {
        server.removeWorld(world)
    }

    @Test
    fun testSetContent() {
        val cauldron: HCauldron = checkNotNull(HCauldrons.get(block))
        cauldron.content = FluidContent.SALT_WATER
        assert(HCauldrons.get(block)!!.content == FluidContent.SALT_WATER)
    }

    @Test
    fun testCauldronPersistence() {
        val savedCauldron = checkNotNull(HCauldrons.get(block))
        savedCauldron.content = FluidContent.SALT_WATER

        assert(chunk.isLoaded)

        // Unload chunk
        chunk.unload()
        println("Chunk loaded: " + chunk.isLoaded)
        val unloadEvent = ChunkUnloadEvent(chunk, true)
        Bukkit.getPluginManager().callEvent(unloadEvent)
        println("Chunk loaded: " + chunk.isLoaded)
        chunk.unload()

        assert(!chunk.isLoaded)

        // Load chunk
        this.chunk.load()
        val loadEvent = ChunkLoadEvent(chunk, false)
        Bukkit.getPluginManager().callEvent(loadEvent)

        assert(this.chunk.isLoaded)

        val loadedCauldron = checkNotNull(HCauldrons.get(block))
        // Loaded cauldron should be a new instance, but equivalent to saved cauldron
        assert(loadedCauldron !== savedCauldron)
        assert(loadedCauldron == savedCauldron)
    }

    @Test
    fun testChunkLoading() {
        this.chunk.load()
        assert(this.chunk.isLoaded)
    }

    @Test
    fun testChunkUnloading() {
        this.chunk.unload()
        assert(!this.chunk.isLoaded)
    }
}