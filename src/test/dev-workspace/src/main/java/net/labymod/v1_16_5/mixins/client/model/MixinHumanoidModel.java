package net.labymod.v1_16_5.mixins.client.model;

import aqm;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.metadata.Metadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/model/MixinHumanoidModel.class */
@Mixin({dum.class})
public class MixinHumanoidModel<T extends aqm> implements HumanoidModel {

    @Shadow
    public dwn f;

    @Shadow
    public dwn g;

    @Shadow
    public dwn h;

    @Shadow
    public dwn l;

    @Shadow
    public dwn k;

    @Shadow
    public dwn j;

    @Shadow
    public dwn i;
    protected Map<String, ModelPart> labyMod$children;
    protected Map<String, ModelPart> labyMod$parts;
    private Metadata labyMod$metadata;

    @Inject(method = {"<init>(Ljava/util/function/Function;FFII)V"}, at = {@At("RETURN")})
    public void labyMod$addParts(Function function, float lvt_2_1_, float lvt_3_1_, int lvt_4_1_, int lvt_5_1_, CallbackInfo ci) {
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
        throw new UnsupportedOperationException("ADding part not supported");
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
        return this.f;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getHat() {
        return this.g;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getBody() {
        return this.h;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftLeg() {
        return this.l;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightLeg() {
        return this.k;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftArm() {
        return this.j;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightArm() {
        return this.i;
    }
}
