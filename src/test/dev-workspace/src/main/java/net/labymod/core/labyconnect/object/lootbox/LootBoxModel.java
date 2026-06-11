package net.labymod.core.labyconnect.object.lootbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/LootBoxModel.class */
public final class LootBoxModel {
    private final Model model;
    private final List<GeometryEffect> effects;
    private ResourceLocation textureLocation;

    public LootBoxModel(Model model, List<GeometryEffect> effects) {
        this.model = model;
        this.effects = effects;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public void setTextureLocation(ResourceLocation textureLocation) {
        setTextureLocation(this.model, textureLocation);
    }

    private void setTextureLocation(Model model, ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    public Model model() {
        return this.model;
    }

    public ResourceLocation textureLocation() {
        return this.textureLocation;
    }

    public List<GeometryEffect> effects() {
        return this.effects;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        LootBoxModel that = (LootBoxModel) obj;
        return Objects.equals(this.model, that.model) && Objects.equals(this.textureLocation, that.textureLocation) && Objects.equals(this.effects, that.effects);
    }

    public int hashCode() {
        return Objects.hash(this.model, this.textureLocation, this.effects);
    }

    public String toString() {
        return "LootBoxModel[model=" + String.valueOf(this.model) + ", textureLocation=" + String.valueOf(this.textureLocation) + ", effects=" + String.valueOf(this.effects) + "]";
    }

    public LootBoxModel copy() {
        LootBoxModel lootBoxModel = new LootBoxModel(this.model.copy(), new ArrayList(this.effects));
        lootBoxModel.setTextureLocation(this.textureLocation);
        return lootBoxModel;
    }
}
