package dev.boooiil.historia.core.database.internal;

import java.util.HashMap;

import org.bukkit.Location;

public class BlockStorage {

    private static final HashMap<Location, Long> blockStorage = new HashMap<Location, Long>();
    private static final long blockStorageTime = 1200000;

    /**
     * Adds a block to the block storage
     * 
     * @param location the location of the block
     */
    public static void addBlock(Location location) {
        blockStorage.put(location, System.currentTimeMillis());
    }

    /**
     * Removes a block from the block storage
     * 
     * @param location the location of the block
     */
    public static void removeBlock(Location location) {
        blockStorage.remove(location);
    }

    /**
     * Gets the time a block was added to the block storage
     * 
     * @param location the location of the block
     * @return the time the block was added to the block storage
     */
    public static double getBlockTime(Location location) {
        return blockStorage.get(location);
    }

    /**
     * Checks if a block is in the block storage
     * 
     * @param location the location of the block
     * @return true if the block is in the block storage, false if not
     */
    public static boolean containsBlock(Location location) {
        return blockStorage.containsKey(location);
    }

    /**
     * Checks if a block is expired and remove it from the storage if it is.
     * 
     * @param location the location of the block
     * @return true if the block is expired, false if not
     */
    public static boolean isBlockExpired(Location location) {

        if (blockStorage.get(location) == null)
            return true;

        boolean expired = (System.currentTimeMillis() - blockStorage.get(location)) > blockStorageTime;

        if (expired)
            removeBlock(location);

        return expired;
    }

    /**
     * Clears the block storage
     */
    public static void clear() {
        blockStorage.clear();
    }

}
