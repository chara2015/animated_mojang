package net.labymod.v1_21_11.mixins.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/model/MixinPlayerModel.class */
@Mixin({PlayerModel.class})
public class MixinPlayerModel<T extends LivingEntity> extends MixinHumanoidModel<T> implements net.labymod.api.client.render.model.entity.player.PlayerModel {

    @Shadow
    @Final
    public ModelPart jacket;

    @Shadow
    @Final
    public ModelPart leftPants;

    @Shadow
    @Final
    public ModelPart rightPants;

    @Shadow
    @Final
    public ModelPart leftSleeve;

    @Shadow
    @Final
    public ModelPart rightSleeve;

    @Shadow
    @Final
    private boolean slim;

    public net.labymod.api.client.render.model.ModelPart getJacket() {
        return this.jacket;
    }

    public net.labymod.api.client.render.model.ModelPart getCloak() {
        return null;
    }

    public net.labymod.api.client.render.model.ModelPart getLeftPants() {
        return this.leftPants;
    }

    public net.labymod.api.client.render.model.ModelPart getRightPants() {
        return this.rightPants;
    }

    public net.labymod.api.client.render.model.ModelPart getLeftSleeve() {
        return this.leftSleeve;
    }

    public net.labymod.api.client.render.model.ModelPart getRightSleeve() {
        return this.rightSleeve;
    }
}

