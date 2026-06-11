package net.labymod.api.client.render.model.builder;

import net.labymod.api.Laby;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/builder/ModelBuilder.class */
public interface ModelBuilder {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/builder/ModelBuilder$Factory.class */
    @Referenceable
    public interface Factory {
        @NotNull
        ModelBuilder create();
    }

    @NotNull
    ModelBuilder copyFrom(@NotNull Model model);

    @NotNull
    ModelBuilder textureSize(int i, int i2);

    @NotNull
    ModelBuilder defaultMaterial(@NotNull Material material);

    @NotNull
    ModelBuilder defaultRenderGroup(@NotNull RenderGroup renderGroup);

    @NotNull
    ModelPartBuilder addPart(@NotNull String str);

    @Nullable
    ModelPartBuilder getPart(@NotNull String str);

    @NotNull
    Model build();

    @NotNull
    static ModelBuilder create() {
        return Laby.references().modelBuilderFactory().create();
    }

    @NotNull
    static ModelBuilder from(@NotNull Model model) {
        return Laby.references().modelBuilderFactory().create().copyFrom(model);
    }
}
