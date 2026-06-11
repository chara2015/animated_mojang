package net.labymod.v1_21_11.mixins.client.player;

import net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor;
import net.minecraft.client.entity.ClientAvatarState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/player/MixinAvatarClientState.class */
@Mixin({ClientAvatarState.class})
public class MixinAvatarClientState implements ClientAvatarStateAccessor {

    @Shadow
    private double d;

    @Shadow
    private double e;

    @Shadow
    private double f;

    @Shadow
    private double g;

    @Shadow
    private double h;

    @Shadow
    private double i;

    @Shadow
    private float j;

    @Shadow
    private float k;

    @Shadow
    private float b;

    @Shadow
    private float c;

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public double labyMod$getXCloak() {
        return this.d;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setXCloak(double x) {
        this.d = x;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public double labyMod$getYCloak() {
        return this.e;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setYCloak(double y) {
        this.e = y;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public double labyMod$getZCloak() {
        return this.f;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setZCloak(double z) {
        this.f = z;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public double labyMod$getXCloakO() {
        return this.g;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setXCloakO(double xo) {
        this.g = xo;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public double labyMod$getYCloakO() {
        return this.h;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setYCloakO(double yo) {
        this.h = yo;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public double labyMod$getZCloakO() {
        return this.i;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setZCloakO(double zo) {
        this.i = zo;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public float labyMod$getBob() {
        return this.j;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public float labyMod$getBobO() {
        return this.k;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public float labyMod$getWalkDist() {
        return this.b;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public void labyMod$setWalkDist(float walkDist) {
        this.b = walkDist;
    }

    @Override // net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor
    public float labyMod$getWalkDistO() {
        return this.c;
    }
}
