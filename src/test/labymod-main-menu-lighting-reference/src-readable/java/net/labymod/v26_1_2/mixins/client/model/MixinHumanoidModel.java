package net.labymod.v26_1_2.mixins.client.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.labymod.api.metadata.Metadata;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/model/MixinHumanoidModel.class */
@Mixin({HumanoidModel.class})
public class MixinHumanoidModel<T extends LivingEntity> implements net.labymod.api.client.render.model.entity.HumanoidModel {

    @Shadow
    @Final
    public ModelPart head;

    @Shadow
    @Final
    public ModelPart hat;

    @Shadow
    @Final
    public ModelPart body;

    @Shadow
    @Final
    public ModelPart leftLeg;

    @Shadow
    @Final
    public ModelPart rightLeg;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Shadow
    @Final
    public ModelPart rightArm;
    protected Map<String, net.labymod.api.client.render.model.ModelPart> labyMod$children;
    protected Map<String, net.labymod.api.client.render.model.ModelPart> labyMod$parts;
    private Metadata labyMod$metadata;

    @Inject(method = {"<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V"}, at = {@At("RETURN")})
    public void labyMod$addParts(ModelPart modelPart, Function<Identifier, RenderType> function, CallbackInfo callbackInfo) {
        Map<String, net.labymod.api.client.render.model.ModelPart> children = ((net.labymod.api.client.render.model.ModelPart) modelPart).getChildren();
        this.labyMod$children = new HashMap(children);
        this.labyMod$parts = new HashMap(children);
    }

    @Override // net.labymod.api.client.render.model.Model
    public void addPart(String name, net.labymod.api.client.render.model.ModelPart part) {
        throw new UnsupportedOperationException("ADding part not supported");
    }

    @Override // net.labymod.api.client.render.model.Model
    public boolean isInvalidPart(String name) {
        return false;
    }

    @Override // net.labymod.api.client.render.model.Model
    public net.labymod.api.client.render.model.ModelPart getPart(String name) {
        return this.labyMod$parts.get(name);
    }

    @Override // net.labymod.api.client.render.model.Model
    public Map<String, net.labymod.api.client.render.model.ModelPart> getParts() {
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
    public void addChild(String name, net.labymod.api.client.render.model.ModelPart child) {
        throw new UnsupportedOperationException("Adding child not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public net.labymod.api.client.render.model.ModelPart getChild(String name) {
        return this.labyMod$children.get(name);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, net.labymod.api.client.render.model.ModelPart> getChildren() {
        return this.labyMod$children;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getHead() {
        return this.head;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getHat() {
        return this.hat;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getBody() {
        return this.body;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getLeftLeg() {
        return this.leftLeg;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getRightLeg() {
        return this.rightLeg;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getLeftArm() {
        return this.leftArm;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public net.labymod.api.client.render.model.ModelPart getRightArm() {
        return this.rightArm;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public void copyToSecondLayer() {
    }
}
