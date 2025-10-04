package dev.boooiil.historia.core.temperature

class GradualTemperature(

    /**
     * Current temperature value.
     *
     * This value is updated gradually towards the [target] temperature
     * based on the [heatingRate] and [coolingRate] in [progress].
     */
    var current: Double,

    /**
     * Target temperature value.
     */
    var target: Double,

    /**
     * Rate of change for heating.
     */
    var heatingRate: Double,

    /**
     * Rate of change for cooling.
     */
    var coolingRate: Double
) {

    /**
     * Set a new rate of change for both heating and cooling.
     * Cooling rate is automatically set to 60% of the heating rate.
     *
     * @param rate - The new rate to set for heating (and 60% for cooling).
     */
    fun setRate(rate: Double) {
        this.heatingRate = rate
        this.coolingRate = rate * 0.6 // Cooling is 60% of heating rate
    }

    /**
     * Perform a single step towards the target temperature.
     *
     * @return The updated current temperature after the step.
     */
    fun progress(): Double {
        val delta = target - current
        val rate = if (delta > 0) heatingRate else coolingRate

        current += delta * rate
        return current
    }
}