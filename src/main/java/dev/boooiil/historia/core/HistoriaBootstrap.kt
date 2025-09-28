package dev.boooiil.historia.core

import dev.boooiil.historia.core.commands.commandGive
import dev.boooiil.historia.core.commands.commandProficiency
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

class HistoriaBootstrap : PluginBootstrap {

    override fun bootstrap(context: BootstrapContext) {
        context.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            commands.registrar().register(commandProficiency)
            commands.registrar().register(commandGive)
        }
    }
}