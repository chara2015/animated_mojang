package net.labymod.api.client.render.model.geometry.shapes;

import net.labymod.api.Laby;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeType;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/CylinderShape.class */
public interface CylinderShape extends Shape {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/CylinderShape$Builder.class */
    @Referenceable
    public interface Builder {
        @NotNull
        Builder origin(float f, float f2, float f3);

        @NotNull
        Builder radius(float f);

        @NotNull
        Builder height(float f);

        @NotNull
        Builder segments(int i);

        @NotNull
        Builder caps(boolean z, boolean z2);

        @NotNull
        Builder textureOffset(int i, int i2);

        @NotNull
        Builder textureSize(int i, int i2);

        @NotNull
        CylinderShape build();
    }

    float getRadius();

    float getHeight();

    int getSegments();

    boolean hasTopCap();

    boolean hasBottomCap();

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    default ShapeType getType() {
        return ShapeType.CYLINDER;
    }

    @NotNull
    static Builder builder() {
        return Laby.references().cylinderShapeBuilder();
    }
}
