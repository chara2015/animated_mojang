package net.labymod.v1_21_5.mixins.client.model;

import byf;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/model/MixinPlayerModel.class */
@Mixin({git.class})
public class MixinPlayerModel<T extends byf> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public gkr e;

    @Shadow
    @Final
    public gkr c;

    @Shadow
    @Final
    public gkr d;

    @Shadow
    @Final
    public gkr a;

    @Shadow
    @Final
    public gkr b;

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.e;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return null;
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
