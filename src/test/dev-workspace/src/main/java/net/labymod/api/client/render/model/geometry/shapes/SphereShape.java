package net.labymod.api.client.render.model.geometry.shapes;

import net.labymod.api.Laby;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeType;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/SphereShape.class */
public interface SphereShape extends Shape {
    float getRadius();

    int getLongitudeSegments();

    int getLatitudeSegments();

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    default ShapeType getType() {
        return ShapeType.SPHERE;
    }

    @NotNull
    static Builder builder() {
        return Laby.references().sphereShapeBuilder();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/SphereShape$Builder.class */
    @Referenceable
    public interface Builder {
        @NotNull
        Builder origin(float f, float f2, float f3);

        @NotNull
        Builder radius(float f);

        @NotNull
        Builder segments(int i, int i2);

        @NotNull
        Builder textureOffset(int i, int i2);

        @NotNull
        Builder textureSize(int i, int i2);

        @NotNull
        SphereShape build();

        @NotNull
        default Builder segments(int segments) {
            return segments(segments, segments / 2);
        }
    }
}
