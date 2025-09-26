package dev.boooiil.historia.core.expiry.configuration

class FileMap {
    /**
     * This enum represents the keys for the various resource files used in the
     * plugin.
     */
    enum class ResourceKeys(val key: String) {
        /** config.yml  */
        CONFIG("config.yml"),
        ITEMS("items.yml");
    }
}
