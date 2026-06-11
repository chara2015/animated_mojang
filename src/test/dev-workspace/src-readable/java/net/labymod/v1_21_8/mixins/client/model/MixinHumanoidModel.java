package net.labymod.v1_21_8.mixins.client.model;

import cam;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.metadata.Metadata;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/model/MixinHumanoidModel.class */
@Mixin({gnx.class})
public class MixinHumanoidModel<T extends cam> implements HumanoidModel {

    @Shadow
    @Final
    public gqn o;

    @Shadow
    @Final
    public gqn p;

    @Shadow
    @Final
    public gqn q;

    @Shadow
    @Final
    public gqn u;

    @Shadow
    @Final
    public gqn t;

    @Shadow
    @Final
    public gqn s;

    @Shadow
    @Final
    public gqn r;
    protected Map<String, ModelPart> labyMod$children;
    protected Map<String, ModelPart> labyMod$parts;
    private Metadata labyMod$metadata;

    @Inject(method = {"<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V"}, at = {@At("RETURN")})
    public void labyMod$addParts(gqn modelPart, Function<ame, gxz> function, CallbackInfo callbackInfo) {
        Map<String, ModelPart> children = ((ModelPart) modelPart).getChildren();
        this.labyMod$children = new HashMap(children);
        this.labyMod$parts = new HashMap(children);
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
        return this.o;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getHat() {
        return this.p;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getBody() {
        return this.q;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftLeg() {
        return this.u;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightLeg() {
        return this.t;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getLeftArm() {
        return this.s;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public ModelPart getRightArm() {
        return this.r;
    }

    @Override // net.labymod.api.client.render.model.entity.HumanoidModel
    public void copyToSecondLayer() {
    }
}
