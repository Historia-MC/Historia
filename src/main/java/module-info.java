module Historia.Core {
    requires Historia.Spawnkill;
    requires towny;
    requires org.bukkit;
    requires bungeecord.chat;
    requires java.logging;
    requires worldedit.core;
    requires worldguard.core;
    requires worldguard.bukkit;
    requires java.sql;
    requires mysql.connector.java;

    exports dev.boooiil.historia.core.classes.user;
    exports dev.boooiil.historia.core.database.internal;
}