package dev.boooiil.historia.core.util

import org.bukkit.Location
import org.bukkit.Particle
import java.util.*

object ParticleUtil {
    fun spawnParticlesWithVelocity(
        particle: Particle, location: Location, count: Int,
        vx: Double, vy: Double, vz: Double,
        radiusX: Double, radiusY: Double, radiusZ: Double
    ) {
        for (i in 0..<count) {
            val rnd = Random()
            val x = location.x() + (2 * rnd.nextFloat() - 1f) * radiusX
            val y = location.y() + (2 * rnd.nextFloat() - 1f) * radiusY
            val z = location.z() + (2 * rnd.nextFloat() - 1f) * radiusZ

            location.getWorld().spawnParticle(particle, x, y, z, 0, vx, vy, vz)
        }
    }
}
