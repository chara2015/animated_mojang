package net.labymod.v1_12_2.mixins.client.model;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/model/MixinModelPlayer.class */
@Mixin({bqj.class})
public abstract class MixinModelPlayer extends MixinModelBiped implements PlayerModel {

    @Shadow
    public brs u;

    @Shadow
    @Final
    private brs v;

    @Shadow
    public brs c;

    @Shadow
    public brs d;

    @Shadow
    public brs a;

    @Shadow
    public brs b;

    @Shadow
    public abstract void a(float f, float f2, float f3, float f4, float f5, float f6, vg vgVar);

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    public void labyMod$addParts(float value, boolean slim, CallbackInfo callbackInfo) {
        labyMod$addInternalChild(HumanoidModel.LEFT_LEG_NAME, getLeftLeg());
        labyMod$addInternalChild(HumanoidModel.RIGHT_LEG_NAME, getRightLeg());
        labyMod$addInternalChild(HumanoidModel.LEFT_ARM_NAME, getLeftArm());
        labyMod$addInternalChild(HumanoidModel.RIGHT_ARM_NAME, getRightArm());
        labyMod$addInternalChild(PlayerModel.JACKET_NAME, getJacket());
        labyMod$addInternalChild(PlayerModel.CLOAK_NAME, getCloak());
        labyMod$addInternalChild(PlayerModel.LEFT_PANTS_NAME, getLeftPants());
        labyMod$addInternalChild(PlayerModel.RIGHT_PANTS_NAME, getRightPants());
        labyMod$addInternalChild(PlayerModel.LEFT_SLEEVE_NAME, getLeftSleeve());
        labyMod$addInternalChild(PlayerModel.RIGHT_SLEEVE_NAME, getRightSleeve());
    }

    @Insert(method = {"render(Lnet/minecraft/entity/Entity;FFFFFF)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$preRender(vg entity, float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, InsertInfo callbackInfo) {
        if (labyMod$firePlayerModelRenderEvent(entity, Phase.PRE)) {
            a(limbSwing, prevLimbSwing, renderTick, yawHead, pitch, scale, entity);
            callbackInfo.cancel();
        }
    }

    @Insert(method = {"render(Lnet/minecraft/entity/Entity;FFFFFF)V"}, at = @At("TAIL"))
    public void labyMod$postRender(vg entity, float limbSwing, float prevLimbSwing, float renderTick, float yawHead, float pitch, float scale, InsertInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(entity, Phase.POST);
    }

    private boolean labyMod$firePlayerModelRenderEvent(vg entity, Phase phase) {
        if (!(entity instanceof bua)) {
            return false;
        }
        int packedLight = MinecraftUtil.getPackedLight(entity);
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        return ((PlayerModelRenderEvent) Laby.fireEvent(new PlayerModelRenderEvent((Player) entity, this, VersionedStackProvider.DEFAULT_STACK, phase, packedLight))).isCancelled();
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.u;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return this.v;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftPants() {
        return this.c;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightPants() {
        return this.d;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftSleeve() {
        return this.a;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightSleeve() {
        return this.b;
    }
}
