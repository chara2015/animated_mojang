package net.labymod.v1_21_1.mixins.client.model;

import btn;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/model/MixinPlayerModel.class */
@Mixin({fwp.class})
public class MixinPlayerModel<T extends btn> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public fyk z;

    @Shadow
    @Final
    private fyk G;

    @Shadow
    @Final
    public fyk x;

    @Shadow
    @Final
    public fyk y;

    @Shadow
    @Final
    public fyk b;

    @Shadow
    @Final
    public fyk w;

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
