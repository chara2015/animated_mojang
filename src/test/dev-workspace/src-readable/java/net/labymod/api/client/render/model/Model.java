package net.labymod.api.client.render.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.metadata.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/Model.class */
public interface Model extends ModelPartHolder {
    public static final int MAX_BONES = 240;

    void addPart(@NotNull String str, @NotNull ModelPart modelPart);

    boolean isInvalidPart(@NotNull String str);

    @Nullable
    ModelPart getPart(@NotNull String str);

    @NotNull
    Map<String, ModelPart> getParts();

    @NotNull
    Metadata metadata();

    @NotNull
    default Optional<ModelPart> findPart(@NotNull String name) {
        return Optional.ofNullable(getPart(name));
    }

    @NotNull
    default List<BoneGroup> getBoneGroups() {
        throw new IllegalStateException("Bone groups are not supported");
    }

    default void setBoneGroups(@NotNull List<BoneGroup> groups) {
        throw new IllegalStateException("Bone groups are not supported");
    }

    default void printModelTree() {
    }

    @NotNull
    default Model copy() {
        throw new UnsupportedOperationException("Copy is not supported");
    }

    default void reset() {
        forChildren((v0) -> {
            v0.reset();
        });
    }

    default float getWidth() {
        float minX = Float.MAX_VALUE;
        float maxX = -3.4028235E38f;
        for (ModelPart modelPart : getChildren().values()) {
            minX = Math.min(minX, modelPart.getMinX());
            maxX = Math.max(maxX, modelPart.getMaxX());
        }
        return Math.abs(maxX - minX);
    }

    default float getHeight() {
        float minY = Float.MAX_VALUE;
        float maxY = -3.4028235E38f;
        for (ModelPart modelPart : getChildren().values()) {
            minY = Math.min(minY, modelPart.getMinY());
            maxY = Math.max(maxY, modelPart.getMaxY());
        }
        return Math.abs(maxY - minY);
    }

    default float getDepth() {
        float minZ = Float.MAX_VALUE;
        float maxZ = -3.4028235E38f;
        for (ModelPart modelPart : getChildren().values()) {
            minZ = Math.min(minZ, modelPart.getMinZ());
            maxZ = Math.max(maxZ, modelPart.getMaxZ());
        }
        return Math.abs(maxZ - minZ);
    }

    default void forChildren(@NotNull Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (ModelPart child : getChildren().values()) {
            consumer.accept(child);
        }
    }

    @NotNull
    default Map<String, ModelPart> getPartsStartingWith(@NotNull String prefix) {
        Map<String, ModelPart> result = new HashMap<>();
        for (Map.Entry<String, ModelPart> entry : getParts().entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    @Nullable
    default Matrix4f getPartWorldTransform(@NotNull String name) {
        ModelPart part = getPart(name);
        if (part == null) {
            return null;
        }
        return computePartWorldTransform(part);
    }

    @NotNull
    default Matrix4f computePartWorldTransform(@NotNull ModelPart part) {
        List<ModelPart> path = new ArrayList<>();
        ModelPart parent = part;
        while (true) {
            ModelPart current = parent;
            if (current == null) {
                break;
            }
            path.addFirst(current);
            parent = current.getParent();
        }
        Matrix4f result = new Matrix4f();
        for (ModelPart pathPart : path) {
            Matrix4f partTransform = pathPart.getModelPartTransform().toMatrix();
            Matrix4f animTransform = pathPart.getAnimationTransformation().toMatrix();
            result = result.mul(partTransform).mul(animTransform);
        }
        return result;
    }

    @NotNull
    default Map<String, Matrix4f> getPartWorldTransforms(@NotNull String prefix) {
        Map<String, Matrix4f> result = new HashMap<>();
        for (Map.Entry<String, ModelPart> entry : getParts().entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                result.put(entry.getKey(), computePartWorldTransform(entry.getValue()));
            }
        }
        return result;
    }
}
