package net.labymod.v1_21_11.mixins.client.model;

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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/model/MixinHumanoidModel.class */
@Mixin({HumanoidModel.class})
public class MixinHumanoidModel<T extends LivingEntity> implements net.labymod.api.client.render.model.entity.HumanoidModel {

    @Shadow
    @Final
    public ModelPart h;

    @Shadow
    @Final
    public ModelPart i;

    @Shadow
    @Final
    public ModelPart j;

    @Shadow
    @Final
    public ModelPart n;

    @Shadow
    @Final
    public ModelPart m;

    @Shadow
    @Final
    public ModelPart l;

    @Shadow
    @Final
    public ModelPart k;
    protected Map<String, net.labymod.api.client.render.model.ModelPart> labyMod$children;
    protected Map<String, net.labymod.api.client.render.model.ModelPart> labyMod$parts;
    private Metadata labyMod$metadata;

    @Inject(method = {"<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V"}, at = {@At("RETURN")})
    public void labyMod$addParts(ModelPart modelPart, Function<Identifier, RenderType> function, CallbackInfo callbackInfo) {
        Map<String, net.labymod.api.client.render.model.ModelPart> children = ((net.labymod.api.client.render.model.ModelPart) modelPart).getChildren();
        this.labyMod$children = new HashMap(children);
        this.labyMod$parts = new HashMap(children);
    }

    public void addPart(String name, net.labymod.api.client.render.model.ModelPart part) {
        throw new UnsupportedOperationException("ADding part not supported");
    }

    public boolean isInvalidPart(String name) {
        return false;
    }

    public net.labymod.api.client.render.model.ModelPart getPart(String name) {
        return this.labyMod$parts.get(name);
    }

    public Map<String, net.labymod.api.client.render.model.ModelPart> getParts() {
        return this.labyMod$parts;
    }

    public Metadata metadata() {
        if (this.labyMod$metadata == null) {
            this.labyMod$metadata = Metadata.create();
        }
        return this.labyMod$metadata;
    }

    public void addChild(String name, net.labymod.api.client.render.model.ModelPart child) {
        throw new UnsupportedOperationException("Adding child not supported");
    }

    public net.labymod.api.client.render.model.ModelPart getChild(String name) {
        return this.labyMod$children.get(name);
    }

    public Map<String, net.labymod.api.client.render.model.ModelPart> getChildren() {
        return this.labyMod$children;
    }

    public net.labymod.api.client.render.model.ModelPart getHead() {
        return this.h;
    }

    public net.labymod.api.client.render.model.ModelPart getHat() {
        return this.i;
    }

    public net.labymod.api.client.render.model.ModelPart getBody() {
        return this.j;
    }

    public net.labymod.api.client.render.model.ModelPart getLeftLeg() {
        return this.n;
    }

    public net.labymod.api.client.render.model.ModelPart getRightLeg() {
        return this.m;
    }

    public net.labymod.api.client.render.model.ModelPart getLeftArm() {
        return this.l;
    }

    public net.labymod.api.client.render.model.ModelPart getRightArm() {
        return this.k;
    }

    public void copyToSecondLayer() {
    }
}
