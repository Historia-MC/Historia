package dev.boooiil.historia.core.util

import kotlin.math.roundToInt

/** Linearly interpolates within this range at position t (0.0 to 1.0). */
fun ClosedRange<Double>.lerp(t: Double): Double {
    val clamped  = t.coerceIn(0.0, 1.0)
    return start + (endInclusive - start) * clamped
}

/** Linearly interpolates within this range at position t (0.0 to 1.0), rounded to nearest int. */
fun IntRange.lerp(t: Double): Int {
    val clamped  = t.coerceIn(0.0, 1.0)
    return (first + (last - first) * clamped).roundToInt()
}