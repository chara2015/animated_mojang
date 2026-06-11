package net.labymod.v1_12_2.mixins.client.model;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.metadata.Metadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/model/MixinModelBiped.class */
@Mixin({bpx.class})
public class MixinModelBiped implements HumanoidModel {

    @Shadow
    public brs e;

    @Shadow
    public brs f;

    @Shadow
    public brs g;

    @Shadow
    public brs k;

    @Shadow
    public brs j;

    @Shadow
    public brs i;

    @Shadow
    public brs h;
    protected Map<String, ModelPart> labyMod$children;
    protected Map<String, ModelPart> labyMod$parts;
    private Metadata labyMod$metadata;

    @Inject(method = {"<init>(FFII)V"}, at = {@At("RETURN")})
    public void labyMod$addParts(float p_i354_1_, float p_i354_2_, int textureWidth, int textureHeight, CallbackInfo callbackInfo) {
        this.labyMod$children = new HashMap();
        this.labyMod$parts = new HashMap();
        labyMod$addInternalChild(HumanoidModel.HEAD_NAME, getHead());
        labyMod$addInternalChild(HumanoidModel.HAT_NAME, getHat());
        labyMod$addInternalChild(HumanoidModel.BODY_NAME, getBody());
        labyMod$addInternalChild(HumanoidModel.LEFT_LEG_NAME, getLeftLeg());
        labyMod$addInternalChild(HumanoidModel.RIGHT_LEG_NAME, getRightLeg());
        labyMod$addInternalChild(HumanoidModel.LEFT_ARM_NAME, getLeftArm());
        labyMod$addInternalChild(HumanoidModel.RIGHT_ARM_NAME, getRightArm());
    }

    protected void labyMod$addInternalChild(String name, ModelPart child) {
        this.labyMod$children.put(name, child);
        this.labyMod$parts.put(name, child);
    }

    @Override // net.labymod.api.client.render.model.Model
    public void addPart(String name, ModelPart part) {
        this.labyMod$parts.put(name, part);
        part.setDebugName(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public boolean isInvalidPart(String name) {
        return false;
    }

    @Override // net.labymod.api.client.render.model.Model
    public ModelPart getPart(String name) {
        return this.labyMod$parts.get(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public Map<String, ModelPart> getParts() {
        return this.labyMod$parts;
    }

    @Override // net.labymod.api.client.render.model.Model
    public Metadata metadata() {
        if (this.labyMod$metadata == null) {
            this.labyMod$metadata = Metadata.create();
        }
        return this.labyMod$metadata;
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public void addChild(String name, ModelPart child) {
        throw new UnsupportedOperationException("Adding child not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public ModelPart getChild(String name) {
        return this.labyMod$children.get(name);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, ModelPart> getChildren() {
        return this.labyMod$children;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getHead() {
        return this.e;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getHat() {
        return this.f;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getBody() {
        return this.g;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftLeg() {
        return this.k;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightLeg() {
        return this.j;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftArm() {
        return this.i;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightArm() {
        return this.h;
    }

    @Override // net.labymod.api.client.render.model.Model
    public void reset() {
        for (ModelPart modelPart : this.labyMod$parts.values()) {
            modelPart.reset();
        }
    }
}
