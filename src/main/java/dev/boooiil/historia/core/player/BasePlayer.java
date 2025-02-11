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

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

@NullMarked
abstract class BasePlayer implements JSONSerializable {

    private UUID uuid;
    @Nullable
    protected String username;
    protected boolean isOnline;

    @Nullable
    private Resident resident;
    @Nullable
    private Town town;
    @Nullable
    private Nation nation;

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

        CoreLogger.debugToConsole("Constructing new BasePlayer object with UUID " + uuid.toString() + ".");

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {

            this.uuid = uuid;
            this.username = player.getName();
            this.isOnline = true;

        }

        else if (offlinePlayer.hasPlayedBefore()) {

            this.uuid = uuid;
            this.username = offlinePlayer.getName();
            this.isOnline = false;

        }

        else {

            this.uuid = uuid;
            this.username = null;
            this.isOnline = false;

        }

        if (this.uuid != null && !HistoriaCore.isTesting) {

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

    public List<String> getTownRanks() {

        if (town == null)
            return List.of("None");

        else
            return getResident().getTownRanks();

    }

    public List<String> getNationRanks() {

        if (nation == null)
            return List.of("None");

        else
            return getResident().getNationRanks();

    }

    public String getTownName() {

        if (town == null)
            return "Wilderness";

        else
            return town.getName();

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
        sb.append(JSONUtils.fromValue("uuid", getUUID().toString()) + ", ");
        sb.append(JSONUtils.fromValue("username", username) + ", ");
        sb.append(JSONUtils.fromValue("isOnline", isOnline) + ", ");

        if (getResident() != null) {
            sb.append(JSONUtils.fromValue("resident", getResident().getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("resident", "None") + ", ");
        }

        if (getTown() != null) {
            sb.append(JSONUtils.fromValue("town", getTown().getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("town", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("townRanks", getTownRanks()) + ", ");

        if (getNation() != null) {
            sb.append(JSONUtils.fromValue("nation", getNation().getName()) + ", ");
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
        sb.append(JSONUtils.fromValue("uuid", getUUID().toString()) + ", ");
        sb.append(JSONUtils.fromValue("username", username) + ", ");
        sb.append(JSONUtils.fromValue("isOnline", isOnline) + ", ");

        if (getResident() != null) {
            sb.append(JSONUtils.fromValue("resident", getResident().getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("resident", "None") + ", ");
        }

        if (getTown() != null) {
            sb.append(JSONUtils.fromValue("town", getTown().getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("town", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("townRanks", getTownRanks()) + ", ");

        if (getNation() != null) {
            sb.append(JSONUtils.fromValue("nation", getNation().getName()) + ", ");
        } else {
            sb.append(JSONUtils.fromValue("nation", "None") + ", ");
        }

        sb.append(JSONUtils.fromStringList("nationRanks", getNationRanks()));

        sb.append("}");

        return sb.toString();

    }

}