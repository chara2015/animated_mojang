package net.labymod.api.client.render.model.builder;

import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.laby3d.pipeline.material.Material;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/builder/ModelPartBuilder.class */
public interface ModelPartBuilder {
    @NotNull
    ModelPartBuilder textureOffset(int i, int i2);

    @NotNull
    ModelPartBuilder textureSize(int i, int i2);

    @NotNull
    ModelPartBuilder addCube(float f, float f2, float f3, float f4, float f5, float f6);

    @NotNull
    ModelPartBuilder addCube(float f, float f2, float f3, float f4, float f5, float f6, float f7);

    @NotNull
    ModelPartBuilder addCube(float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z);

    @NotNull
    ModelPartBuilder addShape(@NotNull Shape shape);

    @NotNull
    ModelPartBuilder material(@NotNull Material material);

    @NotNull
    ModelPartBuilder renderGroup(@NotNull RenderGroup renderGroup);

    @NotNull
    ModelPartBuilder pivot(float f, float f2, float f3);

    @NotNull
    ModelPartBuilder rotation(float f, float f2, float f3);

    @NotNull
    ModelPartBuilder visible(boolean z);

    @NotNull
    ModelPartBuilder addChild(@NotNull String str);

    @NotNull
    ModelPartBuilder end();

    @NotNull
    EnhancedModelPart build();
}
