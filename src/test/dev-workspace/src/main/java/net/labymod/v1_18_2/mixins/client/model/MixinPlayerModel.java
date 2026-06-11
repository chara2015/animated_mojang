package net.labymod.v1_18_2.mixins.client.model;

import axy;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/model/MixinPlayerModel.class */
@Mixin({ekm.class})
public class MixinPlayerModel<T extends axy> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public emb w;

    @Shadow
    @Final
    private emb D;

    @Shadow
    @Final
    public emb u;

    @Shadow
    @Final
    public emb v;

    @Shadow
    @Final
    public emb b;

    @Shadow
    @Final
    public emb t;

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
