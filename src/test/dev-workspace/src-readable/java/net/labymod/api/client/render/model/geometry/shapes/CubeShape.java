package net.labymod.api.client.render.model.geometry.shapes;

import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeType;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/CubeShape.class */
public interface CubeShape extends Shape {
    boolean isMirror();

    @NotNull
    Set<Direction> getVisibleFaces();

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    default ShapeType getType() {
        return ShapeType.CUBE;
    }

    @NotNull
    static Builder builder() {
        return Laby.references().cubeShapeBuilder();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/CubeShape$Builder.class */
    @Referenceable
    public interface Builder {
        @NotNull
        Builder origin(float f, float f2, float f3);

        @NotNull
        Builder size(float f, float f2, float f3);

        @NotNull
        Builder grow(float f, float f2, float f3);

        @NotNull
        Builder textureOffset(int i, int i2);

        @NotNull
        Builder textureSize(int i, int i2);

        @NotNull
        Builder mirror(boolean z);

        @NotNull
        Builder visibleFaces(@NotNull Set<Direction> set);

        @NotNull
        Builder addVisibleFace(@NotNull Direction direction);

        @NotNull
        Builder removeVisibleFace(@NotNull Direction direction);

        @NotNull
        CubeShape build();

        @NotNull
        default Builder grow(float grow) {
            return grow(grow, grow, grow);
        }
    }
}
