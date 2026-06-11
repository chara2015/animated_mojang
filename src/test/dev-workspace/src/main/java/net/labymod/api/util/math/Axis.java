package net.labymod.api.util.math;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/Axis.class */
public enum Axis {
    X,
    Y,
    Z;

    private static final Axis[] VALUES = values();

    public static Axis[] getValues() {
        return VALUES;
    }

    public static Axis get(String name) {
        for (Axis value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + Axis.class.getCanonicalName() + "." + name);
    }

    public static Axis getOrDefault(String name, Axis defaultValue) {
        for (Axis value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
}
