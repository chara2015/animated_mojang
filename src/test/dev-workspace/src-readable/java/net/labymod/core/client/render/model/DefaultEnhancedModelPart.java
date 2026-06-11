package net.labymod.core.client.render.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.laby3d.pipeline.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/DefaultEnhancedModelPart.class */
public class DefaultEnhancedModelPart extends DefaultModelPart implements EnhancedModelPart {
    private final List<Shape> shapes = new ArrayList();
    private final List<Shape> shapesView = Collections.unmodifiableList(this.shapes);
    private Material material;
    private RenderGroup renderGroup;
    private boolean hasShapes;
    private boolean inverted;

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    @NotNull
    public List<Shape> getShapes() {
        return this.shapesView;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public void addShape(@NotNull Shape shape) {
        this.shapes.add(shape);
        this.hasShapes = true;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public boolean removeShape(@NotNull Shape shape) {
        boolean removed = this.shapes.remove(shape);
        this.hasShapes = !this.shapes.isEmpty();
        return removed;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public void clearShapes() {
        this.shapes.clear();
        this.hasShapes = false;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    @Nullable
    public Material getMaterial() {
        return this.material;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public void setMaterial(@Nullable Material material) {
        this.material = material;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    @Nullable
    public RenderGroup getRenderGroup() {
        return this.renderGroup;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public void setRenderGroup(@Nullable RenderGroup renderGroup) {
        this.renderGroup = renderGroup;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public boolean isInverted() {
        return this.inverted;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    @Override // net.labymod.api.client.render.model.EnhancedModelPart
    public boolean hasGeometry() {
        return this.hasShapes || !getBoxes().isEmpty();
    }

    @Override // net.labymod.core.client.render.model.DefaultModelPart, net.labymod.api.client.render.model.ModelPart
    public boolean isInvisibleOrEmpty() {
        return isInvisible() || (!this.hasShapes && getBoxes().isEmpty() && getChildren().isEmpty());
    }

    @Override // net.labymod.core.client.render.model.DefaultModelPart, net.labymod.api.client.render.model.ModelPart
    @NotNull
    public EnhancedModelPart copy() {
        DefaultEnhancedModelPart newPart = new DefaultEnhancedModelPart();
        newPart.setDebugName(getDebugName());
        newPart.setId(getId());
        newPart.setParent(getParent());
        newPart.getModelPartTransform().set(getModelPartTransform());
        newPart.getAnimationTransformation().set(getAnimationTransformation());
        newPart.setVisible(isVisible());
        newPart.setTextureSize(getTextureWidth(), getTextureHeight());
        newPart.setTextureOffset(getTextureOffsetX(), getTextureOffsetY());
        for (Shape shape : this.shapes) {
            newPart.addShape(shape);
        }
        newPart.setMaterial(this.material);
        newPart.setRenderGroup(this.renderGroup);
        newPart.setInverted(this.inverted);
        for (ModelPart child : getChildren().values()) {
            newPart.addChild(child.getDebugName(), child.copy());
        }
        for (ModelBox box : getBoxes()) {
            newPart.addBox(box);
        }
        return newPart;
    }
}
