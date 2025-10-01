package dev.boooiil.historia.core.temperature;

public class GradualTemperature {
    private double target;      // target temperature
    private double current;  // current temperature
    private double heatingRate;   // rate for temperature increases
    private double coolingRate;   // rate for temperature decreases (slower)

    public GradualTemperature(double start, double target, double heatingRate, double coolingRate) {
        this.current = start;
        this.target = target;
        this.heatingRate = heatingRate;
        this.coolingRate = coolingRate;

    }

    // Backward compatibility constructor (uses same rate for both)
    public GradualTemperature(double start, double target, double rate) {
        this(start, target, rate, rate * 0.6); // Cooling is 60% of heating rate (slower)
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setStart(double start) {
        this.current = start;
        // Don't reset step when just updating starting point during target changes
        // Step should only reset when starting a completely new temperature progression
    }

    public void setHeatingRate(double heatingRate) {
        this.heatingRate = heatingRate;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    // Backward compatibility
    public void setRate(double rate) {
        this.heatingRate = rate;
        this.coolingRate = rate * 0.6; // Cooling is 60% of heating rate
    }

    public double target() {
        return target;
    }

    public double heatingRate() {
        return heatingRate;
    }

    public double coolingRate() {
        return coolingRate;
    }

    public double current() {
        return current;
    }

    // Calculate current temperature without incrementing step
    public double doStep() {
        double delta = target - current;
        double rate = delta > 0 ? heatingRate : coolingRate;

        // move fraction of the remaining difference per step
        current += delta * rate;
        return current;
    }
} 