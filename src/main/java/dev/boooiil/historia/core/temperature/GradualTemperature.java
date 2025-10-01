package dev.boooiil.historia.core.temperature;

public class GradualTemperature {
    private int step;
    private double target;      // target temperature
    private double start;      // starting temperature
    private double current;  // current temperature
    private double heatingRate;   // rate for temperature increases
    private double coolingRate;   // rate for temperature decreases (slower)


    public GradualTemperature(double target, double start, double heatingRate, double coolingRate) {
        this.step = 0;
        this.target = target;
        this.start = start;
        this.current = start;
        this.heatingRate = heatingRate;
        this.coolingRate = coolingRate;

    }

    // Backward compatibility constructor (uses same rate for both)
    public GradualTemperature(double target, double start, double rate) {
        this(target, start, rate, rate * 0.6); // Cooling is 60% of heating rate (slower)
    }

    public void setTarget(double target) {
        this.target = target;
        this.step = 0;
    }

    public void setStart(double start) {
        this.start = start;
        // Don't reset step when just updating starting point during target changes
        // Step should only reset when starting a completely new temperature progression
    }

    public void setHeatingRate(double heatingRate) {
        this.heatingRate = heatingRate;
        this.step = 0;
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
        this.step = 0;
    }

    // Backward compatibility
    public void setRate(double rate) {
        this.heatingRate = rate;
        this.coolingRate = rate * 0.6; // Cooling is 60% of heating rate
        this.step = 0;
    }

    public int step() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public double start() {
        return start;
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

    // Backward compatibility
    public double rate() {
        return heatingRate;
    }

    public double getCurrentTemperature() {
        return current;
    }

    // Calculate current temperature without incrementing step
    public double doStep() {
        boolean isDecreasing = target < start;
        double currentRate = isDecreasing ? coolingRate : heatingRate;

        if (isDecreasing) {
            current = start - (start - target) * Math.exp(-currentRate * step);
        } else {
            current = start + (target - start) * (1 - Math.exp(-currentRate * step));
        }

        step += 1;
        return current;
    }
} 