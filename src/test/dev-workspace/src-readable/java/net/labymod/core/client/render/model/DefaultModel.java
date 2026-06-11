package net.labymod.core.client.render.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.labymod.api.client.render.model.BoneGroup;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.metadata.Metadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/DefaultModel.class */
public class DefaultModel implements Model {
    private final Map<String, ModelPart> children;
    private final Map<String, ModelPart> parts;
    private final Set<String> invalidParts;
    private ModelPart[] bakedChildren;
    private List<BoneGroup> boneGroups;
    private Metadata metadata;

    public DefaultModel() {
        this(new HashMap());
    }

    public DefaultModel(Map<String, ModelPart> children) {
        this.parts = new HashMap();
        this.invalidParts = new HashSet();
        this.boneGroups = List.of();
        this.children = children;
        this.bakedChildren = (ModelPart[]) children.values().toArray(ModelPart.EMPTY_CHILDREN);
    }

    @Override // net.labymod.api.client.render.model.Model
    public void addPart(String name, ModelPart part) {
        this.parts.put(name, part);
        part.setDebugName(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public boolean isInvalidPart(String name) {
        return this.invalidParts.contains(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public ModelPart getPart(String name) {
        ModelPart part = this.parts.get(name);
        if (part == null) {
            this.invalidParts.add(name);
        }
        return part;
    }

    @Override // net.labymod.api.client.render.model.Model
    public Map<String, ModelPart> getParts() {
        return this.parts;
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public void addChild(String name, ModelPart child) {
        addPart(name, child);
        this.children.put(name, child);
        this.bakedChildren = (ModelPart[]) this.children.values().toArray(ModelPart.EMPTY_CHILDREN);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public ModelPart getChild(String name) {
        return this.children.get(name);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, ModelPart> getChildren() {
        return this.children;
    }

    @Override // net.labymod.api.client.render.model.Model
    public List<BoneGroup> getBoneGroups() {
        if (this.boneGroups.isEmpty() && !this.children.isEmpty()) {
            this.boneGroups = List.of(new BoneGroup(new ArrayList(this.children.values())));
        }
        return this.boneGroups;
    }

    @Override // net.labymod.api.client.render.model.Model
    public void setBoneGroups(List<BoneGroup> groups) {
        this.boneGroups = groups;
    }

    @Override // net.labymod.api.client.render.model.Model
    public Metadata metadata() {
        if (this.metadata != null) {
            return this.metadata;
        }
        Metadata metadataCreate = Metadata.create();
        this.metadata = metadataCreate;
        return metadataCreate;
    }

    @Override // net.labymod.api.client.render.model.Model
    public void printModelTree() {
        for (Map.Entry<String, ModelPart> entry : this.children.entrySet()) {
            String name = entry.getKey();
            ModelPart modelPart = entry.getValue();
            printModelPart(name, modelPart, 0);
            System.out.println();
        }
    }

    @Override // net.labymod.api.client.render.model.Model
    public Model copy() {
        Model newModel = new DefaultModel();
        forChildren(child -> {
            ModelPart copiedChild = child.copy();
            addChild(newModel, copiedChild);
            newModel.addChild(child.getDebugName(), copiedChild);
        });
        return newModel;
    }

    @Override // net.labymod.api.client.render.model.Model
    public void forChildren(Consumer<ModelPart> consumer) {
        Objects.requireNonNull(consumer);
        for (ModelPart bakedChild : this.bakedChildren) {
            consumer.accept(bakedChild);
        }
    }

    private void addChild(Model model, ModelPart child) {
        model.addPart(child.getDebugName(), child);
        for (ModelPart innerChild : child.getChildren().values()) {
            addChild(model, innerChild);
        }
    }

    private void printModelPart(String name, ModelPart part, int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append(" ");
        }
        System.out.println(String.valueOf(builder) + "-" + name + "(" + part.getId() + ")");
        int depth2 = depth + 1;
        for (Map.Entry<String, ModelPart> entry : part.getChildren().entrySet()) {
            printModelPart(entry.getKey(), entry.getValue(), depth2);
        }
    }
}
