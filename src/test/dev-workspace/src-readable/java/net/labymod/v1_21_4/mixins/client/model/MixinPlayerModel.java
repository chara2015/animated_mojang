package net.labymod.v1_21_4.mixins.client.model;

import bvi;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/model/MixinPlayerModel.class */
@Mixin({gdh.class})
public class MixinPlayerModel<T extends bvi> extends MixinHumanoidModel<T> implements PlayerModel {

    @Shadow
    @Final
    public gfe e;

    @Shadow
    @Final
    public gfe c;

    @Shadow
    @Final
    public gfe d;

    @Shadow
    @Final
    public gfe a;

    @Shadow
    @Final
    public gfe b;

    @Shadow
    @Final
    private boolean A;

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
