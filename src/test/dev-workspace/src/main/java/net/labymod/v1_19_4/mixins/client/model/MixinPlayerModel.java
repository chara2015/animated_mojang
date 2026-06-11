package net.labymod.v1_19_4.mixins.client.model;

import bfx;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/model/MixinPlayerModel.class */
@Mixin({fax.class})
public class MixinPlayerModel<T extends bfx> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public fcr z;

    @Shadow
    @Final
    private fcr G;

    @Shadow
    @Final
    public fcr x;

    @Shadow
    @Final
    public fcr y;

    @Shadow
    @Final
    public fcr b;

    @Shadow
    @Final
    public fcr w;

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
