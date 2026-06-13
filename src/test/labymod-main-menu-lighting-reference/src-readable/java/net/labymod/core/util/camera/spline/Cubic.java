package net.labymod.core.util.camera.spline;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/camera/spline/Cubic.class */
public class Cubic {
    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public Cubic(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double eval(double u) {
        return (((((this.d * u) + this.c) * u) + this.b) * u) + this.a;
    }
}
