package net.labymod.api.client.render.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.geometry.BoundingBox;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.util.debug.DebugNameable;
import net.labymod.api.util.math.Transformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/ModelPart.class */
public interface ModelPart extends ModelPartHolder, DebugNameable {
    public static final ModelPart[] EMPTY_CHILDREN = new ModelPart[0];

    int getId();

    void setId(int i);

    void addBox(float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z);

    void addBox(@NotNull ModelBox modelBox);

    @NotNull
    List<ModelBox> getBoxes();

    boolean isVisible();

    void setVisible(boolean z);

    @NotNull
    Transformation getModelPartTransform();

    @NotNull
    Transformation getAnimationTransformation();

    void copyFrom(@NotNull ModelPart modelPart);

    int getTextureWidth();

    int getTextureHeight();

    void setTextureSize(int i, int i2);

    int getTextureOffsetX();

    int getTextureOffsetY();

    void setTextureOffset(int i, int i2);

    @Nullable
    ModelPart getParent();

    void setParent(@Nullable ModelPart modelPart);

    default ModelPart[] childArray() {
        Map<String, ModelPart> children = getChildren();
        if (children.isEmpty()) {
            return EMPTY_CHILDREN;
        }
        return (ModelPart[]) children.values().toArray(new ModelPart[0]);
    }

    @NotNull
    default ModelBox createAndAddBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirror) {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    default float getMinX() {
        float minX = Float.MAX_VALUE;
        for (ModelBox box : getBoxes()) {
            minX = Math.min(minX, box.getMinX());
        }
        if (this instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            for (Shape shape : enhancedPart.getShapes()) {
                BoundingBox bounds = shape.getBounds();
                minX = Math.min(minX, bounds.getMinX());
            }
        }
        for (ModelPart child : childArray()) {
            minX = Math.min(minX, child.getMinX());
        }
        return (minX + getModelPartTransform().getTranslation().getX()) * ModelUtil.getScale(this, (v0) -> {
            return v0.getX();
        });
    }

    default float getMaxX() {
        float maxX = -3.4028235E38f;
        for (ModelBox box : getBoxes()) {
            maxX = Math.max(maxX, box.getMaxX());
        }
        if (this instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            for (Shape shape : enhancedPart.getShapes()) {
                BoundingBox bounds = shape.getBounds();
                maxX = Math.max(maxX, bounds.getMaxX());
            }
        }
        for (ModelPart child : childArray()) {
            maxX = Math.max(maxX, child.getMaxX());
        }
        return (maxX + getModelPartTransform().getTranslation().getX()) * ModelUtil.getScale(this, (v0) -> {
            return v0.getX();
        });
    }

    default float getMinY() {
        float minY = Float.MAX_VALUE;
        for (ModelBox box : getBoxes()) {
            minY = Math.min(minY, box.getMinY());
        }
        if (this instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            for (Shape shape : enhancedPart.getShapes()) {
                BoundingBox bounds = shape.getBounds();
                minY = Math.min(minY, bounds.getMinY());
            }
        }
        for (ModelPart child : childArray()) {
            minY = Math.min(minY, child.getMinY());
        }
        return (minY + getModelPartTransform().getTranslation().getY()) * ModelUtil.getScale(this, (v0) -> {
            return v0.getY();
        });
    }

    default float getMaxY() {
        float maxY = -3.4028235E38f;
        for (ModelBox box : getBoxes()) {
            maxY = Math.max(maxY, box.getMaxY());
        }
        if (this instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            for (Shape shape : enhancedPart.getShapes()) {
                BoundingBox bounds = shape.getBounds();
                maxY = Math.max(maxY, bounds.getMaxY());
            }
        }
        for (ModelPart child : childArray()) {
            maxY = Math.max(maxY, child.getMaxY());
        }
        return (maxY + getModelPartTransform().getTranslation().getY()) * ModelUtil.getScale(this, (v0) -> {
            return v0.getY();
        });
    }

    default float getMinZ() {
        float minZ = Float.MAX_VALUE;
        for (ModelBox box : getBoxes()) {
            minZ = Math.min(minZ, box.getMinZ());
        }
        if (this instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            for (Shape shape : enhancedPart.getShapes()) {
                BoundingBox bounds = shape.getBounds();
                minZ = Math.min(minZ, bounds.getMinZ());
            }
        }
        for (ModelPart child : childArray()) {
            minZ = Math.min(minZ, child.getMinZ());
        }
        return (minZ + getModelPartTransform().getTranslation().getZ()) * ModelUtil.getScale(this, (v0) -> {
            return v0.getZ();
        });
    }

    default float getMaxZ() {
        float maxZ = -3.4028235E38f;
        for (ModelBox box : getBoxes()) {
            maxZ = Math.max(maxZ, box.getMaxZ());
        }
        if (this instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            for (Shape shape : enhancedPart.getShapes()) {
                BoundingBox bounds = shape.getBounds();
                maxZ = Math.max(maxZ, bounds.getMaxZ());
            }
        }
        for (ModelPart child : childArray()) {
            maxZ = Math.max(maxZ, child.getMaxZ());
        }
        return (maxZ + getModelPartTransform().getTranslation().getZ()) * ModelUtil.getScale(this, (v0) -> {
            return v0.getZ();
        });
    }

    default float getWidth() {
        return Math.abs(getMaxX() - getMinX());
    }

    default float getHeight() {
        return Math.abs(getMaxY() - getMinY());
    }

    default float getDepth() {
        return Math.abs(getMaxZ() - getMinZ());
    }

    default boolean isInvisible() {
        return !isVisible();
    }

    default void reset() {
        Transformation animationTransformation = getAnimationTransformation();
        animationTransformation.reset();
        forChildren((v0) -> {
            v0.reset();
        });
    }

    default void forChildren(@NotNull Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (ModelPart child : childArray()) {
            consumer.accept(child);
        }
    }

    default boolean isInvisibleOrEmpty() {
        if (isInvisible()) {
            return true;
        }
        boolean hasGeometry = !getBoxes().isEmpty();
        if (!hasGeometry && (this instanceof EnhancedModelPart)) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) this;
            hasGeometry = !enhancedPart.getShapes().isEmpty();
        }
        return !hasGeometry && getChildren().isEmpty();
    }

    @NotNull
    default ModelPart copy() {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
