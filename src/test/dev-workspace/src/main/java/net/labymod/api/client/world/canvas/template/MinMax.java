package net.labymod.api.client.world.canvas.template;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/canvas/template/MinMax.class */
public class MinMax {
    private static final MinMax UNRESTRICTED = new MinMax(null, null);
    private static final MinMax POSITIVE = new MinMax(Float.valueOf(0.0f), null);
    private final Float min;
    private final Float max;

    private MinMax(Float min, Float max) {
        this.min = min;
        this.max = max;
    }

    public static MinMax min(float min) {
        return new MinMax(Float.valueOf(min), null);
    }

    public static MinMax max(float max) {
        return new MinMax(null, Float.valueOf(max));
    }

    public static MinMax range(float min, float max) {
        return new MinMax(Float.valueOf(min), Float.valueOf(max));
    }

    public static MinMax unrestricted() {
        return UNRESTRICTED;
    }

    public static MinMax positive() {
        return POSITIVE;
    }

    public Float getMin() {
        return this.min;
    }

    public Float getMax() {
        return this.max;
    }

    public boolean isValid(float value) {
        if (this.min != null && value < this.min.floatValue()) {
            return false;
        }
        if (this.max != null && value > this.max.floatValue()) {
            return false;
        }
        return true;
    }
}
