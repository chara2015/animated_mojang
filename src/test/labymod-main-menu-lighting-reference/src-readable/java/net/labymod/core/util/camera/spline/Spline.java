package net.labymod.core.util.camera.spline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.core.util.camera.spline.position.PositionValueAdapter;
import net.labymod.core.util.camera.spline.position.PositionValueType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/camera/spline/Spline.class */
public class Spline {
    private final Vector<Location> points = new Vector<>();
    private List<Vector<Cubic>> cubics = Collections.emptyList();
    private int cubicEntrySize;

    public boolean isValid() {
        return this.points.size() >= 2 && !this.cubics.isEmpty();
    }

    public void reset() {
        this.points.clear();
        this.cubics.clear();
        this.cubicEntrySize = 0;
    }

    public void add(Location point) {
        if (this.points.isEmpty()) {
            point.setYaw(((point.getYaw() + 180.0d) % 360.0d) - 180.0d);
            point.setRoll(point.getRoll() % 360.0d);
        } else {
            Location last = this.points.get(this.points.size() - 1);
            point.setYaw(fixEulerRotation(last.getYaw(), point.getYaw(), 180));
            point.setRoll(fixEulerRotation(last.getRoll(), point.getRoll(), 0));
        }
        this.points.add(point);
    }

    public Location get(float progress) {
        float progressAtCubics = progress * this.cubicEntrySize;
        int cubicIndex = (int) Math.min(this.cubicEntrySize - 1, progressAtCubics);
        float cubicProgress = progressAtCubics - cubicIndex;
        Location position = new Location();
        for (PositionValueType valueType : PositionValueType.values()) {
            double value = this.cubics.get(valueType.ordinal()).get(cubicIndex).eval(cubicProgress);
            valueType.getAdapter().set(position, value);
        }
        return position;
    }

    public void calculate() {
        this.cubics = new ArrayList(PositionValueType.values().length);
        for (PositionValueType type : PositionValueType.values()) {
            Vector<Cubic> vector = new Vector<>();
            try {
                calculateNaturalCubic(type, this.points, vector);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.cubicEntrySize = vector.size();
            this.cubics.add(vector);
        }
    }

    private void calculateNaturalCubic(PositionValueType type, Vector<Location> points, Vector<Cubic> cubics) {
        PositionValueAdapter adapter = type.getAdapter();
        int num = points.size() - 1;
        double[] gamma = new double[num + 1];
        double[] delta = new double[num + 1];
        double[] D = new double[num + 1];
        gamma[0] = 0.5d;
        for (int i = 1; i < num; i++) {
            gamma[i] = 1.0d / (4.0d - gamma[i - 1]);
        }
        gamma[num] = 1.0d / (2.0d - gamma[num - 1]);
        double p0 = adapter.get(points.get(0));
        double p1 = adapter.get(points.get(1));
        delta[0] = 3.0d * (p1 - p0) * gamma[0];
        for (int i2 = 1; i2 < num; i2++) {
            double p02 = adapter.get(points.get(i2 - 1));
            double p12 = adapter.get(points.get(i2 + 1));
            delta[i2] = ((3.0d * (p12 - p02)) - delta[i2 - 1]) * gamma[i2];
        }
        double p03 = adapter.get(points.get(num - 1));
        double p13 = adapter.get(points.get(num));
        delta[num] = ((3.0d * (p13 - p03)) - delta[num - 1]) * gamma[num];
        D[num] = delta[num];
        for (int i3 = num - 1; i3 >= 0; i3--) {
            D[i3] = delta[i3] - (gamma[i3] * D[i3 + 1]);
        }
        cubics.clear();
        for (int i4 = 0; i4 < num; i4++) {
            double p04 = adapter.get(points.get(i4));
            double p14 = adapter.get(points.get(i4 + 1));
            cubics.add(new Cubic(p04, D[i4], ((3.0d * (p14 - p04)) - (2.0d * D[i4])) - D[i4 + 1], (2.0d * (p04 - p14)) + D[i4] + D[i4 + 1]));
        }
    }

    private double fixEulerRotation(double first, double second, int eulerBreak) {
        if (first == second) {
            return first;
        }
        double normalizedFirst = (first + ((double) eulerBreak)) % 360.0d;
        double normalizedSecond = (second + ((double) eulerBreak)) % 360.0d;
        double pathDifference = Math.abs(normalizedSecond - normalizedFirst);
        int factor = normalizedSecond > normalizedFirst ? 1 : -1;
        if (pathDifference > 180.0d) {
            pathDifference = (-1.0d) * (360.0d - pathDifference);
        }
        return first + (((double) factor) * pathDifference);
    }
}
