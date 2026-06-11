package net.labymod.v1_21_10.mixins.client.model;

import cew;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/model/MixinPlayerModel.class */
@Mixin({gwq.class})
public class MixinPlayerModel<T extends cew> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public gyo y;

    @Shadow
    @Final
    public gyo g;

    @Shadow
    @Final
    public gyo x;

    @Shadow
    @Final
    public gyo e;

    @Shadow
    @Final
    public gyo f;

    @Shadow
    @Final
    private boolean A;

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getJacket() {
        return this.y;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getCloak() {
        return null;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftPants() {
        return this.g;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightPants() {
        return this.x;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getLeftSleeve() {
        return this.e;
    }

    @Override // net.labymod.api.client.render.model.entity.player.PlayerModel
    public ModelPart getRightSleeve() {
        return this.f;
    }
}
