package net.labymod.v1_20_6.mixins.client.model;

import btr;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/model/MixinPlayerModel.class */
@Mixin({fvh.class})
public class MixinPlayerModel<T extends btr> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public fxc z;

    @Shadow
    @Final
    private fxc G;

    @Shadow
    @Final
    public fxc x;

    @Shadow
    @Final
    public fxc y;

    @Shadow
    @Final
    public fxc b;

    @Shadow
    @Final
    public fxc w;

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.z;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return this.G;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftPants() {
        return this.x;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightPants() {
        return this.y;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftSleeve() {
        return this.b;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightSleeve() {
        return this.w;
    }
}
