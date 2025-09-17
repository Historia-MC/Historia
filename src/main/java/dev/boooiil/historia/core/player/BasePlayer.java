package dev.boooiil.historia.core.player;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import dev.boooiil.historia.core.HistoriaCore;
import dev.boooiil.historia.core.dependents.towny.TownyHandler;
import dev.boooiil.historia.core.util.CoreLogger;
import dev.boooiil.historia.core.util.JSONSerializable;
import dev.boooiil.historia.core.util.JSONUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Base class for players in the HistoriaCore system.
 * This class provides common functionality and properties that can be extended
 * by specific player implementations.
 */
@NullMarked
abstract class BasePlayer implements JSONSerializable {

    /**
     * The unique identifier for the player.
     */
    private @Nullable UUID uuid;

    /**
     * The username of the player, if available.
     */
    protected @Nullable String username;

    /**
     * Indicates whether the player is currently online.
     */
    protected boolean isOnline;

    /**
     * The resident associated with the player, if applicable.
     */
    private @Nullable Resident resident;

    /**
     * The town associated with the player, if applicable.
     */
    private @Nullable Town town;

    /**
     * The nation associated with the player, if applicable.
     */
    private @Nullable Nation nation;

    /**
     * Constructs a new BasePlayer object with the given UUID.
     *
     * @param uuid the UUID of the player
     */
    public BasePlayer(UUID uuid) {

        if (uuid == null) {
            CoreLogger.debugToConsole("Constructing new BasePlayer object with null UUID.");
            return;
        }

        CoreLogger.debugToConsole("Constructing new BasePlayer object with UUID " + uuid + ".");

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {

            this.uuid = uuid;
            this.username = player.getName();
            this.isOnline = true;

        } else if (offlinePlayer.hasPlayedBefore()) {

            this.uuid = uuid;
            this.username = offlinePlayer.getName();
            this.isOnline = false;

        } else {

            this.uuid = uuid;
            this.username = null;
            this.isOnline = false;

        }

        if (this.uuid != null && !HistoriaCore.isTesting && Bukkit.getPluginManager().isPluginEnabled("Towny")) {

            this.resident = TownyHandler.getResident(uuid);
            this.town = TownyHandler.getTown(uuid);
            this.nation = TownyHandler.getNation(uuid);

        }

    }

    /**
     * Returns the UUID of the player.
     *
     * @return the UUID of the player
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Returns the username of the player.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the player.
     *
     * @param username the new username of the player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns whether the player is online.
     *
     * @return true if the player is online, false otherwise
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Sets whether the player is online.
     *
     * @param isOnline true if the player is online, false otherwise
     */
    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * Returns the resident object associated with the player.
     *
     * @return the resident object associated with the player
     */
    public Resident getResident() {
        return resident;
    }

    /**
     * Get the town ranks assocuated with this user.
     *
     * @return A list of town ranks.
     */
    public List<String> getTownRanks() {

        if (town == null)
            return List.of("None");

        else
            return resident.getTownRanks();

    }

    /**
     * Get the nation ranks assocuated with this user.
     *
     * @return A list of nation ranks.
     */
    public List<String> getNationRanks() {

        if (nation == null)
            return List.of("None");

        else
            return resident.getNationRanks();

    }

    /**
     * Get the town name that the current user is in.
     *
     * @return The name of the town, or "Wilderness" if not in a town.
     */
    public String getTownName() {

        return town != null ? town.getName() : "Wilderness";

    }

    /**
     * Sets the resident object associated with the player.
     *
     * @param resident the new resident object associated with the player
     */
    public void setResident(Resident resident) {
        this.resident = resident;
    }

    /**
     * Returns the town object associated with the player.
     *
     * @return the town object associated with the player
     */
    public Town getTown() {
        return town;
    }

    /**
     * Sets the town object associated with the player.
     *
     * @param town the new town object associated with the player
     */
    public void setTown(Town town) {
        this.town = town;
    }

    /**
     * Returns the nation object associated with the player.
     *
     * @return the nation object associated with the player
     */
    public Nation getNation() {
        return nation;
    }

    /**
     * Sets the nation object associated with the player.
     *
     * @param nation the new nation object associated with the player
     */
    public void setNation(Nation nation) {
        this.nation = nation;
    }

    @Override
    public String toString() {

        CoreLogger.debugToConsole("BP TS");

        StringBuilder sb = new StringBuilder();

        sb.append("BasePlayer");

        sb.append("{");
        sb.append(JSONUtils.fromValue("uuid", uuid == null ? "" : uuid.toString()) + ", ");
        sb.append(JSONUtils.fromValue("username", username) + ", ");
        sb.append(JSONUtils.fromValue("isOnline", isOnline) + ", ");

        if (resident != null) {
            sb.append(JSONUtils.fromValue("resident", resident.getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("resident", "None") + ", ");
        }

        if (town != null) {
            sb.append(JSONUtils.fromValue("town", town.getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("town", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("townRanks", getTownRanks()) + ", ");

        if (nation != null) {
            sb.append(JSONUtils.fromValue("nation", nation.getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("nation", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("nationRanks", getNationRanks()));

        sb.append("}");

        return sb.toString();

    }

    @Override
    public String toJSON() {

        CoreLogger.debugToConsole("BP TJ");
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONUtils.fromValue("uuid", uuid == null ? "" : uuid.toString()) + ", ");
        sb.append(JSONUtils.fromValue("username", username) + ", ");
        sb.append(JSONUtils.fromValue("isOnline", isOnline) + ", ");

        if (resident != null) {
            sb.append(JSONUtils.fromValue("resident", resident.getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("resident", "None") + ", ");
        }

        if (town != null) {
            sb.append(JSONUtils.fromValue("town", town.getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("town", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("townRanks", getTownRanks()) + ", ");

        if (nation != null) {
            sb.append(JSONUtils.fromValue("nation", nation.getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("nation", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("nationRanks", getNationRanks()));

        sb.append("}");

        return sb.toString();

    }

}