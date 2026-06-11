package net.labymod.v1_20_4.mixins.client.model;

import bml;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/model/MixinPlayerModel.class */
@Mixin({flc.class})
public class MixinPlayerModel<T extends bml> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public fmx z;

    @Shadow
    @Final
    private fmx G;

    @Shadow
    @Final
    public fmx x;

    @Shadow
    @Final
    public fmx y;

    @Shadow
    @Final
    public fmx b;

    @Shadow
    @Final
    public fmx w;

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
