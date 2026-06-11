package net.labymod.api.util.bounds.area;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/area/RectangleAreaPosition.class */
public enum RectangleAreaPosition {
    TOP_LEFT(0, 0, PositionUnit.TOP, PositionUnit.LEFT),
    TOP_CENTER(1, 0, PositionUnit.TOP, PositionUnit.CENTER),
    TOP_RIGHT(2, 0, PositionUnit.TOP, PositionUnit.RIGHT),
    MIDDLE_LEFT(0, 1, PositionUnit.MIDDLE, PositionUnit.LEFT),
    MIDDLE_CENTER(1, 1, PositionUnit.MIDDLE, PositionUnit.CENTER),
    MIDDLE_RIGHT(2, 1, PositionUnit.MIDDLE, PositionUnit.RIGHT),
    BOTTOM_LEFT(0, 2, PositionUnit.BOTTOM, PositionUnit.LEFT),
    BOTTOM_CENTER(1, 2, PositionUnit.BOTTOM, PositionUnit.CENTER),
    BOTTOM_RIGHT(2, 2, PositionUnit.BOTTOM, PositionUnit.RIGHT);

    private final Collection<PositionUnit> positionUnits;
    private final int xIndex;
    private final int yIndex;

    RectangleAreaPosition(int xIndex, int yIndex, PositionUnit... units) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.positionUnits = Arrays.asList(units);
    }

    @Contract(pure = true)
    @Nullable
    public static RectangleAreaPosition getPosition(int xIndex, int yIndex) {
        return (RectangleAreaPosition) Arrays.stream(values()).filter(value -> {
            return value.xIndex == xIndex && value.yIndex == yIndex;
        }).findFirst().orElse(null);
    }

    @NotNull
    public FloatVector2 anchorPoint(@NotNull Rectangle rectangle) {
        float x = rectangle.getX();
        float y = rectangle.getY();
        for (PositionUnit positionUnit : this.positionUnits) {
            x += positionUnit.getXModifier().apply(Float.valueOf(rectangle.getWidth())).floatValue();
            y += positionUnit.getYModifier().apply(Float.valueOf(rectangle.getHeight())).floatValue();
        }
        return new FloatVector2(x, y);
    }

    public Collection<PositionUnit> getPositionUnits() {
        return this.positionUnits;
    }

    public RectangleAreaPosition findBestPosition(PositionUnit unit) {
        Collection<PositionUnit> units = getPositionUnits();
        if (units.contains(unit)) {
            return this;
        }
        for (RectangleAreaPosition value : values()) {
            Collection<PositionUnit> valueUnits = value.getPositionUnits();
            if (valueUnits.contains(unit)) {
                for (PositionUnit positionUnit : units) {
                    if (valueUnits.contains(positionUnit)) {
                        return value;
                    }
                }
            }
        }
        return this;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/area/RectangleAreaPosition$PositionUnit.class */
    public enum PositionUnit {
        TOP((v0) -> {
            return zero(v0);
        }, (v0) -> {
            return zero(v0);
        }),
        MIDDLE((v0) -> {
            return zero(v0);
        }, (v0) -> {
            return half(v0);
        }),
        BOTTOM((v0) -> {
            return zero(v0);
        }, (v0) -> {
            return full(v0);
        }),
        LEFT((v0) -> {
            return zero(v0);
        }, (v0) -> {
            return zero(v0);
        }),
        CENTER((v0) -> {
            return half(v0);
        }, (v0) -> {
            return zero(v0);
        }),
        RIGHT((v0) -> {
            return full(v0);
        }, (v0) -> {
            return zero(v0);
        });

        private final Function<Float, Float> xModifier;
        private final Function<Float, Float> yModifier;

        PositionUnit(Function function, Function function2) {
            this.xModifier = function;
            this.yModifier = function2;
        }

        private static float zero(float value) {
            return 0.0f;
        }

        private static float half(float value) {
            return value / 2.0f;
        }

        private static float full(float value) {
            return value;
        }

        public Function<Float, Float> getXModifier() {
            return this.xModifier;
        }

        public Function<Float, Float> getYModifier() {
            return this.yModifier;
        }
    }
}
