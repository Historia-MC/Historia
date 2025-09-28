package dev.boooiil.historia.core.temperature;

public class GradualTemperature {
    private int step;
    private double max;      // target temperature
    private double min;      // starting temperature
    private double heatingRate;   // rate for temperature increases
    private double coolingRate;   // rate for temperature decreases (slower)


    public GradualTemperature(double max, double min, double heatingRate, double coolingRate) {
        this.step = 0;
        this.max = max;
        this.min = min;
        this.heatingRate = heatingRate;
        this.coolingRate = coolingRate;

    }

    // Backward compatibility constructor (uses same rate for both)
    public GradualTemperature(double max, double min, double rate) {
        this(max, min, rate, rate * 0.6); // Cooling is 60% of heating rate (slower)
    }

    public void setMax(double max) {
        this.max = max;
        this.step = 0;
    }

    public void setMin(double min) {
        this.min = min;
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

    public double min() {
        return min;
    }

    public double max() {
        return max;
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
        // Calculate current temperature without incrementing step
        boolean isDecreasing = max < min;
        double currentRate = isDecreasing ? coolingRate : heatingRate;

        if (isDecreasing) {
            return min - (min - max) * Math.exp(-currentRate * step);
        } else {
            return min + (max - min) * (1 - Math.exp(-currentRate * step));
        }
    }

    public double doStep() {
        double change = getCurrentTemperature();

        step += 1;
        return change;
    }
} 