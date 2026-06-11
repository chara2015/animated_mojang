package net.labymod.v1_17_1.mixins.client.model;

import atu;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/model/MixinPlayerModel.class */
@Mixin({ehc.class})
public class MixinPlayerModel<T extends atu> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public eir w;

    @Shadow
    @Final
    private eir D;

    @Shadow
    @Final
    public eir u;

    @Shadow
    @Final
    public eir v;

    @Shadow
    @Final
    public eir b;

    @Shadow
    @Final
    public eir t;

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.w;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return this.D;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftPants() {
        return this.u;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightPants() {
        return this.v;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftSleeve() {
        return this.b;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightSleeve() {
        return this.t;
    }
}
