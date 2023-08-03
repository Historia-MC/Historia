package dev.boooiil.historia.classes.historia.user;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import dev.boooiil.historia.dependents.towny.TownyHandler;
import dev.boooiil.historia.util.Logging;

public abstract class BasePlayer {

    private UUID uuid;
    private String username;
    private boolean isOnline;

    private Resident resident;
    private Town town;
    private Nation nation;

    /**
     * Constructs a new BasePlayer object with the given UUID.
     * @param uuid the UUID of the player
     */
    public BasePlayer(UUID uuid) {

        Logging.debugToConsole("Constructing new BasePlayer object with UUID " + uuid.toString() + ".");

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        Player player = Bukkit.getPlayer(uuid);

        if (player != null) {

            this.uuid = uuid;
            this.username = player.getName();
            this.isOnline = true;

        }

        else if (offlinePlayer != null) {

            this.uuid = uuid;
            this.username = offlinePlayer.getName();
            this.isOnline = false;

        }

        else {

            this.uuid = uuid;
            this.username = null;
            this.isOnline = false;

        }

        if (this.username != null) {

            this.resident = TownyHandler.getResident(uuid);
            this.town = TownyHandler.getTown(uuid);
            this.nation = TownyHandler.getNation(uuid);

        }

    }

    /**
     * Returns the UUID of the player.
     * @return the UUID of the player
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Returns the username of the player.
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the player.
     * @param username the new username of the player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns whether the player is online.
     * @return true if the player is online, false otherwise
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Sets whether the player is online.
     * @param isOnline true if the player is online, false otherwise
     */
    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * Returns the resident object associated with the player.
     * @return the resident object associated with the player
     */
    public Resident getResident() {
        return resident;
    }

    public List<String> getTownRanks() {

        if (town == null) return List.of("None");

        else return getResident().getTownRanks();

    }

    public List<String> getNationRanks() {

        if (nation == null) return List.of("None");

        else return getResident().getNationRanks();

    }


    /**
     * Sets the resident object associated with the player.
     * @param resident the new resident object associated with the player
     */
    public void setResident(Resident resident) {
        this.resident = resident;
    }

    /**
     * Returns the town object associated with the player.
     * @return the town object associated with the player
     */
    public Town getTown() {
        return town;
    }

    /**
     * Sets the town object associated with the player.
     * @param town the new town object associated with the player
     */
    public void setTown(Town town) {
        this.town = town;
    }

    /**
     * Returns the nation object associated with the player.
     * @return the nation object associated with the player
     */
    public Nation getNation() {
        return nation;
    }

    /**
     * Sets the nation object associated with the player.
     * @param nation the new nation object associated with the player
     */
    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public String toString() {

        String output = "*** BASE PLAYER *** \n";
        output += "UUID: " + uuid.toString() + "\n";
        output += "Username: " + username + "\n";
        output += "Online: " + isOnline + "\n";
        output += "******************* \n";

        return output;

    }

}