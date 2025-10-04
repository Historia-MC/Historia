package dev.boooiil.historia.core

import dev.boooiil.historia.core.configuration.ConfigurationLoader
import dev.boooiil.historia.core.configuration.ItemRegistryLoader
import dev.boooiil.historia.core.configuration.specific.ExpiryConfig
import dev.boooiil.historia.core.configuration.specific.LoreConfiguration
import dev.boooiil.historia.core.database.sql.DataSourceProvider
import dev.boooiil.historia.core.database.sql.DatabaseExecutor
import dev.boooiil.historia.core.database.sql.tables.HistoriaTable
import dev.boooiil.historia.core.events.block.BlockBreakListener
import dev.boooiil.historia.core.events.block.BlockFromToListener
import dev.boooiil.historia.core.events.block.BlockPlaceListener
import dev.boooiil.historia.core.events.entity.EntityBreedListener
import dev.boooiil.historia.core.events.entity.EntityTameListener
import dev.boooiil.historia.core.events.inventory.InventoryClickListener
import dev.boooiil.historia.core.events.player.*
import dev.boooiil.historia.core.expiry.listeners.block.CauldronBlockListener
import dev.boooiil.historia.core.expiry.listeners.inventory.ExpiryInventoryOpenListener
import dev.boooiil.historia.core.expiry.listeners.player.PlayerBucketFillListener
import dev.boooiil.historia.core.expiry.listeners.player.PlayerCauldronInteractListener
import dev.boooiil.historia.core.expiry.listeners.player.PlayerConsumableConsumeListener
import dev.boooiil.historia.core.expiry.listeners.world.ChunkLoadListener
import dev.boooiil.historia.core.expiry.runnable.ConsumableUpdater
import dev.boooiil.historia.core.file.FileIO
import dev.boooiil.historia.core.file.FileKeys
import dev.boooiil.historia.core.items.ItemComponentType
import dev.boooiil.historia.core.items.events.entity.*
import dev.boooiil.historia.core.items.events.inventory.InventoryCloseListener
import dev.boooiil.historia.core.items.events.inventory.InventoryOpenListener
import dev.boooiil.historia.core.items.events.player.PlayerItemConsumeListener
import dev.boooiil.historia.core.items.events.player.PlayerSwapHandItemsListener
import dev.boooiil.historia.core.items.events.player.PlayerToggleSneakListener
import dev.boooiil.historia.core.items.events.player.PlayerToggleSprintListener
import dev.boooiil.historia.core.proficiency.ProficiencyRegistryLoader
import dev.boooiil.historia.core.proficiency.skills.ISkill
import dev.boooiil.historia.core.proficiency.skills.SkillRegistryLoader
import dev.boooiil.historia.core.runnable.SavePlayerRunnable
import dev.boooiil.historia.core.runnable.SyncDaylightCycleRunnable
import dev.boooiil.historia.core.runnable.TemperaturePollRunnable
import dev.boooiil.historia.core.runnable.UpdateScoreboardRunnable
import dev.boooiil.historia.core.util.CoreLogger
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.NamespacedKey
import org.bukkit.Server
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

/**
 * Historia-Core Main class
 */
open class HistoriaCore : JavaPlugin() {
    /**
     * Runs on plugin load.
     */
    override fun onLoad() {
        instance = this

        CoreLogger.infoToConsole("Plugin has loaded.")

        for (key: FileKeys in FileKeys.entries) {
            FileIO.checkAndSaveResources(key.key)
        }

        CoreLogger.infoToConsole("RUNNING VERSION: " + Bukkit.getVersion())

        if (Bukkit.getVersion().contains("MockBukkit")) {
            println("RUNNING IN TEST MODE")
        } else if (!Bukkit.getVersion().contains("Paper")) {
            CoreLogger.errorToConsole("PAPER SPIGOT WAS NOT DETECTED")
            CoreLogger.errorToConsole("DISABLING PLUGIN")
            disable()
        } else {
            isTesting = false
        }
    }

    /**
     * Runs on plugin enable.
     */
    override fun onEnable() {
        // Save / Load the config in the Historia plugins folder.

        this.saveDefaultConfig()

        ConfigurationLoader.init()

        val provider = DataSourceProvider()
        databaseExecutor = DatabaseExecutor(provider)
        HistoriaTable.TABLE.insert(null)

        registerEvent(EntityBreedListener())
        registerEvent(EntityTameListener())
        registerEvent(PlayerExpChangeListener())
        registerEvent(InventoryClickListener())
        registerEvent(BlockBreakListener())
        registerEvent(BlockPlaceListener())
        registerEvent(PlayerJoinListener())
        registerEvent(PlayeQuitListener())
        // registerEvent(new PlayerRightClickAir());
        registerEvent(PlayerInteractEntityListener())
        registerEvent(BlockFromToListener())
        registerEvent(PlayerInteractListener())

        // historia items event listeners
        registerEvent(EntityDamageByEntityListener())
        registerEvent(EntityDropItemListener())
        registerEvent(EntityInteractListener())
        registerEvent(EntityPickupItemListener())
        registerEvent(EntityToggleSwimListener())
        registerEvent(ProjectileLaunchListener())
        registerEvent(InventoryCloseListener())
        registerEvent(InventoryOpenListener())
        // registerEvent(new PlayerInteractListener());
        registerEvent(PlayerItemConsumeListener())
        registerEvent(PlayerSwapHandItemsListener())
        registerEvent(PlayerToggleSneakListener())
        registerEvent(PlayerToggleSprintListener())
        // end

        // historia expiry event listeners
        registerEvent(CauldronBlockListener())
        registerEvent(ExpiryInventoryOpenListener())
        registerEvent(PlayerBucketFillListener())
        registerEvent(PlayerCauldronInteractListener())
        registerEvent(PlayerConsumableConsumeListener())
        registerEvent(ChunkLoadListener())
        // end

        server.worlds[0].setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false) // set false in favour of custom daylight cycle
        registerRunnable(SyncDaylightCycleRunnable(), 0)
        // registerRunnable(new ClassEnchantsRunnable());
        registerRunnable(UpdateScoreboardRunnable())
        registerRunnable(SavePlayerRunnable(), 6000)
        registerAsyncRunnable(TemperaturePollRunnable())

        Bukkit.getAsyncScheduler().runAtFixedRate(
            this,  // plugin
            Consumer { scheduledTask: ScheduledTask? ->
                TemperaturePollRunnable().run() // call your runnable
            },
            0,  // initial delay
            1,  // repeat delay
            TimeUnit.SECONDS // unit (can be TICKS, SECONDS, etc.)
        )

//        Bukkit.getScheduler().runTaskAsynchronously(this, TemperaturePollRunnable())
//        registerRunnable(TemperaturePollRunnable(), 20L)

        CoreLogger.infoToConsole("Plugin Enabled.")

        // TODO: figure out what to do with all of these 'loaders'

        SkillRegistryLoader.load()
        ProficiencyRegistryLoader.load()

        // item related configurations merged from HistoriaItems
        ItemComponentType.registerComponents()
        LoreConfiguration.initLoreMap()
        ItemRegistryLoader.load()
        // RecipeLoader.load()
        // end

        val updatePeriod = ExpiryConfig.CONSUMABLE_UPDATE_TICKS
        val scheduler = this.server.scheduler
        scheduler.runTaskTimer(instance, ConsumableUpdater(), 0, updatePeriod)
        isLoaded = true
    }

    /**
     * Runs on plugin disable.
     */
    // It's a method that is called when the plugin is disabled.
    override fun onDisable() {

        databaseExecutor.close()
        CoreLogger.errorToConsole("The plugin has been disabled.")
        CoreLogger.errorToConsole("Stopping the server to prevent potential harm.")

        if (!isTesting) server.shutdown()
    }

    /**
     * It registers an event
     *
     * @param event The event you want to register.
     */
    fun registerEvent(event: Listener) {
        this.server.pluginManager.registerEvents(event, this)
    }

    /**
     * It registers a runnable
     *
     * @param runnable The runnable you want to register.
     */
    fun registerRunnable(runnable: BukkitRunnable) {
        runnable.runTaskTimer(this, 0, 20)
    }

    /**
     * Registers a BukkitRunnable to be executed on a timer.
     *
     * @param runnable The BukkitRunnable to be executed.
     * @param time     The time in ticks between each execution of the runnable.
     */
    fun registerRunnable(runnable: BukkitRunnable, period: Long) {
        runnable.runTaskTimer(this, 0, period)
    }

    /**
     * Registers a BukkitRunnable to be executed on a timer every second with no delay.
     *
     * @param runnable     The BukkitRunnable to be executed.
     */
    fun registerAsyncRunnable(runnable: BukkitRunnable) {
        registerAsyncRunnable(runnable, TimeUnit.SECONDS, 1, 0)
    }

    /**
     * Registers a BukkitRunnable to be executed on a timer with no delay.
     *
     * @param runnable     The BukkitRunnable to be executed.
     * @param period       The time in seconds between each execution of the runnable.
     */
    fun registerAsyncRunnable(runnable: BukkitRunnable, period: Long) {
        registerAsyncRunnable(runnable, TimeUnit.MILLISECONDS, period * 50, 0)
    }

    /**
     * Registers a BukkitRunnable to be executed on a timer.
     *
     * @param runnable     The BukkitRunnable to be executed.
     * @param period       The time in seconds between each execution of the runnable.
     * @param initialDelay The initial delay in seconds before the first execution of the runnable.
     */
    fun registerAsyncRunnable(runnable: BukkitRunnable, period: Long, initialDelay: Long) {
        registerAsyncRunnable(runnable, TimeUnit.MILLISECONDS, period * 50, initialDelay * 50L)
    }

    /**
     * Registers a BukkitRunnable to be executed on a timer with no delay.
     *
     * @param runnable     The BukkitRunnable to be executed.
     * @param unit         The time unit of the period and initial delay.
     * @param period       The time between each execution of the runnable.
     */
    fun registerAsyncRunnable(runnable: BukkitRunnable, unit: TimeUnit, period: Long) {
        registerAsyncRunnable(runnable, unit, period, 0)
    }

    fun registerAsyncRunnable(runnable: BukkitRunnable, unit: TimeUnit, period: Long, initialDelay: Long) {
        Bukkit.getAsyncScheduler().runAtFixedRate(
            this,
            { scheduledTask: ScheduledTask? ->
                runnable.run()
            },
            initialDelay,
            period,
            unit
        )
    }

    companion object {
        private const val PLUGIN_NAMESPACE = "historia"

        /** if the plugin is testing  */
        @JvmField
        var isTesting: Boolean = true

        @JvmField
        var isLoaded: Boolean = false

        /** this plugin instance  */
        lateinit var instance: HistoriaCore
            private set

        /** the database handler  */
        lateinit var databaseExecutor: DatabaseExecutor
            private set

        /**
         * It returns the server instance
         *
         * @return The server.
         */
        val server: Server get() = instance.server

        /**
         * It disables the plugin
         *
         *
         */
        fun disable() {
            server.pluginManager.disablePlugin(instance)
        }

        /**
         * Get a namespacedkey in the HistoriaCore namespace.
         *
         * @param key - key to set.
         * @return the namespaced key
         */
        @JvmStatic
        fun getNamespacedKey(key: String): NamespacedKey {
            return NamespacedKey(PLUGIN_NAMESPACE, key.lowercase())
        }

        fun registerSkill(skill: ISkill) {
            skill.register()
        }
    }
}
