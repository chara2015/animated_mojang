package net.labymod.core.client.render.model.builder;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.builder.ModelPartBuilder;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.shapes.CubeShape;
import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.core.client.render.model.DefaultEnhancedModelPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/builder/DefaultModelPartBuilder.class */
public class DefaultModelPartBuilder implements ModelPartBuilder {
    private final String name;
    private final DefaultModelPartBuilder parent;
    private final DefaultModelBuilder modelBuilder;
    private int textureOffsetU;
    private int textureOffsetV;
    private int textureWidth;
    private int textureHeight;
    private final Map<String, DefaultModelPartBuilder> children = new HashMap();
    private final DefaultEnhancedModelPart part = new DefaultEnhancedModelPart();

    public DefaultModelPartBuilder(@NotNull String name, @Nullable DefaultModelPartBuilder parent, @Nullable DefaultModelBuilder modelBuilder) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.name = name;
        this.parent = parent;
        this.modelBuilder = modelBuilder;
        this.part.setDebugName(name);
        if (modelBuilder != null) {
            this.textureWidth = modelBuilder.getTextureWidth();
            this.textureHeight = modelBuilder.getTextureHeight();
        }
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder textureOffset(int u, int v) {
        this.textureOffsetU = u;
        this.textureOffsetV = v;
        this.part.setTextureOffset(u, v);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder textureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        this.part.setTextureSize(width, height);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder addCube(float x, float y, float z, float width, float height, float depth) {
        return addCube(x, y, z, width, height, depth, 0.0f, false);
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder addCube(float x, float y, float z, float width, float height, float depth, float grow) {
        return addCube(x, y, z, width, height, depth, grow, false);
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder addCube(float x, float y, float z, float width, float height, float depth, float grow, boolean mirror) {
        CubeShape cube = CubeShape.builder().origin(x, y, z).size(width, height, depth).grow(grow).textureOffset(this.textureOffsetU, this.textureOffsetV).textureSize(this.textureWidth, this.textureHeight).mirror(mirror).build();
        return addShape(cube);
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder addShape(@NotNull Shape shape) {
        this.part.addShape(shape);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder material(@NotNull Material material) {
        this.part.setMaterial(material);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder renderGroup(@NotNull RenderGroup renderGroup) {
        this.part.setRenderGroup(renderGroup);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder pivot(float x, float y, float z) {
        this.part.getModelPartTransform().setTranslation(x, y, z);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder rotation(float x, float y, float z) {
        this.part.getModelPartTransform().setRotation(x, y, z);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder visible(boolean visible) {
        this.part.setVisible(visible);
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder addChild(@NotNull String name) {
        DefaultModelPartBuilder childBuilder = new DefaultModelPartBuilder(name, this, this.modelBuilder);
        childBuilder.textureSize(this.textureWidth, this.textureHeight);
        return childBuilder;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public ModelPartBuilder end() {
        if (this.parent != null) {
            this.parent.part.addChild(this.name, this.part);
            this.parent.children.put(this.name, this);
            return this.parent;
        }
        return this;
    }

    @Override // net.labymod.api.client.render.model.builder.ModelPartBuilder
    @NotNull
    public EnhancedModelPart build() {
        return this.part;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @Nullable
    DefaultModelPartBuilder getChildBuilder(@NotNull String name) {
        return this.children.get(name);
    }

    @NotNull
    DefaultEnhancedModelPart getPart() {
        return this.part;
    }
}
