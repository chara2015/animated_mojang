package net.labymod.core.client.render.model.builder;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.builder.ModelBuilder;
import net.labymod.api.client.render.model.builder.ModelPartBuilder;
import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.models.Implements;
import net.labymod.core.client.render.model.DefaultModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/builder/DefaultModelBuilder.class */
public class DefaultModelBuilder implements ModelBuilder {
    private Material defaultMaterial;
    private RenderGroup defaultRenderGroup;
    private int textureWidth = 64;
    private int textureHeight = 32;
    private final Map<String, DefaultModelPartBuilder> rootParts = new HashMap();

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @NotNull
    public ModelBuilder copyFrom(@NotNull Model model) {
        for (Map.Entry<String, ModelPart> entry : model.getParts().entrySet()) {
            DefaultModelPartBuilder builder = new DefaultModelPartBuilder(entry.getKey(), null, this);
            ModelPart part = entry.getValue();
            builder.textureSize(part.getTextureWidth(), part.getTextureHeight());
            builder.textureOffset(part.getTextureOffsetX(), part.getTextureOffsetY());
            builder.pivot(part.getModelPartTransform().getTranslation().getX(), part.getModelPartTransform().getTranslation().getY(), part.getModelPartTransform().getTranslation().getZ());
            builder.visible(part.isVisible());
            this.rootParts.put(entry.getKey(), builder);
        }
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @NotNull
    public ModelBuilder textureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @NotNull
    public ModelBuilder defaultMaterial(@NotNull Material material) {
        this.defaultMaterial = material;
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @NotNull
    public ModelBuilder defaultRenderGroup(@NotNull RenderGroup renderGroup) {
        this.defaultRenderGroup = renderGroup;
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @NotNull
    public ModelPartBuilder addPart(@NotNull String name) {
        DefaultModelPartBuilder builder = new DefaultModelPartBuilder(name, null, this);
        builder.textureSize(this.textureWidth, this.textureHeight);
        this.rootParts.put(name, builder);
        return builder;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @Nullable
    public ModelPartBuilder getPart(@NotNull String path) {
        String[] parts = path.split("\\.");
        if (parts.length == 0) {
            return null;
        }
        DefaultModelPartBuilder current = this.rootParts.get(parts[0]);
        if (current == null) {
            return null;
        }
        for (int i = 1; i < parts.length; i++) {
            current = current.getChildBuilder(parts[i]);
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelBuilder
    @NotNull
    public Model build() {
        DefaultModel model = new DefaultModel();
        for (Map.Entry<String, DefaultModelPartBuilder> entry : this.rootParts.entrySet()) {
            String name = entry.getKey();
            EnhancedModelPart modelPart = entry.getValue().build();
            model.addPart(name, modelPart);
            model.addChild(name, modelPart);
        }
        return model;
    }

    int getTextureWidth() {
        return this.textureWidth;
    }

    int getTextureHeight() {
        return this.textureHeight;
    }

    @Nullable
    Material getDefaultMaterial() {
        return this.defaultMaterial;
    }

    @Nullable
    RenderGroup getDefaultRenderGroup() {
        return this.defaultRenderGroup;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/builder/DefaultModelBuilder$Factory.class */
    @Implements(ModelBuilder.Factory.class)
    public static class Factory implements ModelBuilder.Factory {
        @Override // net.labymod.api.client.render.model.builder.ModelBuilder.Factory
        @NotNull
        public ModelBuilder create() {
            return new DefaultModelBuilder();
        }
    }
}
