package dev.boooiil.historia.core.util.datatypes

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

class LocationDataType : PersistentDataType<IntArray, Location> {

    override fun getPrimitiveType() = IntArray::class.java
    override fun getComplexType() = Location::class.java

    override fun toPrimitive(location: Location, persistentDataAdapterContext: PersistentDataAdapterContext): IntArray {
        return intArrayOf(location.x().toInt(), location.y().toInt(), location.x().toInt())
    }

    override fun fromPrimitive(
        primitive: IntArray,
        persistentDataAdapterContext: PersistentDataAdapterContext
    ): Location {
        return Location(
            Bukkit.getWorld("world"),
            primitive[0].toDouble(),
            primitive[1].toDouble(),
            primitive[2].toDouble()
        )
    }
}
