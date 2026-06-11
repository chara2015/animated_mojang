package net.labymod.core.client.render.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.math.Transformation;
import net.labymod.core.client.render.model.box.DefaultModelBox;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/DefaultModelPart.class */
public class DefaultModelPart implements ModelPart {
    private final Transformation animationTransformation;
    private String debugName;
    private boolean hasGeometry;
    private boolean hasChildren;
    private int textureOffsetX;
    private int textureOffsetY;

    @Nullable
    private ModelPart parent;
    private ModelPart[] bakedChildren = new ModelPart[0];
    private int textureWidth = 64;
    private int textureHeight = 32;
    private int id = -1;
    private final List<ModelBox> boxes = new ArrayList();
    private final Map<String, ModelPart> children = new HashMap();
    private final List<ModelPart> modelParts = new ArrayList();
    public boolean visible = true;
    private final Transformation transformation = new Transformation();

    public DefaultModelPart() {
        this.transformation.setScale(1.0f);
        this.animationTransformation = new Transformation();
        this.animationTransformation.set(this.transformation);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public void addChild(String name, ModelPart child) {
        child.setParent(this);
        this.children.put(name, child);
        this.bakedChildren = (ModelPart[]) this.children.values().toArray(ModelPart.EMPTY_CHILDREN);
        this.modelParts.add(child);
        this.hasChildren = true;
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public ModelPart getChild(String name) {
        return this.children.get(name);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, ModelPart> getChildren() {
        return this.children;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public ModelPart[] childArray() {
        return this.bakedChildren;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setId(int id) {
        this.id = id;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getId() {
        if (this.id != -1) {
            return this.id;
        }
        if (this.parent == null) {
            return 0;
        }
        return this.parent.getId();
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirror) {
        addBox(new DefaultModelBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirror, this.textureWidth, this.textureHeight));
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public ModelBox createAndAddBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirror) {
        ModelBox newBox = new DefaultModelBox(this.textureOffsetX, this.textureOffsetY, x, y, z, width, height, depth, delta, delta, delta, mirror, this.textureWidth, this.textureHeight);
        addBox(newBox);
        return newBox;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void addBox(ModelBox box) {
        this.boxes.add(box);
        this.hasGeometry = true;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public List<ModelBox> getBoxes() {
        return this.boxes;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public boolean isVisible() {
        return this.visible;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public Transformation getModelPartTransform() {
        return this.transformation;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public Transformation getAnimationTransformation() {
        return this.animationTransformation;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void copyFrom(ModelPart modelPart) {
        this.transformation.set(modelPart.getModelPartTransform());
        this.animationTransformation.set(modelPart.getAnimationTransformation());
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureWidth() {
        return this.textureWidth;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureHeight() {
        return this.textureHeight;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setTextureSize(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureOffsetX() {
        return this.textureOffsetX;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureOffsetY() {
        return this.textureOffsetY;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setTextureOffset(int textureOffsetX, int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    @Nullable
    public ModelPart getParent() {
        return this.parent;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setParent(@Nullable ModelPart parent) {
        this.parent = parent;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public ModelPart copy() {
        DefaultModelPart newModelPart = new DefaultModelPart();
        newModelPart.setDebugName(getDebugName());
        newModelPart.setId(getId());
        newModelPart.setParent(getParent());
        newModelPart.getModelPartTransform().set(getModelPartTransform());
        newModelPart.getAnimationTransformation().set(getAnimationTransformation());
        newModelPart.setVisible(isVisible());
        newModelPart.setTextureSize(getTextureWidth(), getTextureHeight());
        newModelPart.setTextureOffset(getTextureOffsetX(), getTextureOffsetY());
        for (ModelPart bakedChild : this.bakedChildren) {
            newModelPart.addChild(bakedChild.getDebugName(), bakedChild.copy());
        }
        for (ModelBox box : this.boxes) {
            newModelPart.addBox(box);
        }
        return newModelPart;
    }

    @Override // net.labymod.api.util.debug.DebugNameable
    public void setDebugName(String name) {
        this.debugName = name;
        if (this.animationTransformation != null) {
            this.animationTransformation.setDebugName(name);
        }
        if (this.transformation != null) {
            this.transformation.setDebugName(name);
        }
    }

    @Override // net.labymod.api.util.debug.DebugNameable
    public String getDebugName() {
        return this.debugName;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void forChildren(Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (ModelPart bakedChild : this.bakedChildren) {
            consumer.accept(bakedChild);
        }
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public boolean isInvisibleOrEmpty() {
        return isInvisible() || !(this.hasGeometry || this.hasChildren);
    }
}
