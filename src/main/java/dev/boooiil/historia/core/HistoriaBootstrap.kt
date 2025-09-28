package dev.boooiil.historia.core

import dev.boooiil.historia.core.commands.Commands
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

class HistoriaBootstrap : PluginBootstrap {

    override fun bootstrap(context: BootstrapContext) {
        context.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands -> Commands.register(commands) }
    }
}