package net.labymod.v1_16_5.mixins.client.model;

import aqm;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/model/MixinPlayerModel.class */
@Mixin({dvd.class})
public class MixinPlayerModel<T extends aqm> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public dwn x;

    @Shadow
    @Final
    private dwn b;

    @Shadow
    @Final
    public dwn v;

    @Shadow
    @Final
    public dwn w;

    @Shadow
    @Final
    public dwn t;

    @Shadow
    @Final
    public dwn u;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$addParts(float lvt_1_1_, boolean lvt_2_1_, CallbackInfo ci) {
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

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.x;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return this.b;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftPants() {
        return this.v;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightPants() {
        return this.w;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftSleeve() {
        return this.t;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightSleeve() {
        return this.u;
    }
}
