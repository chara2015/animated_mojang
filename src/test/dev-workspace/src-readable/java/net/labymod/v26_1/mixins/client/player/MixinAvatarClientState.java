package net.labymod.v26_1.mixins.client.player;

import net.labymod.v26_1.client.player.ClientAvatarStateAccessor;
import net.minecraft.client.entity.ClientAvatarState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/player/MixinAvatarClientState.class */
@Mixin({ClientAvatarState.class})
public class MixinAvatarClientState implements ClientAvatarStateAccessor {

    @Shadow
    private double xCloak;

    @Shadow
    private double yCloak;

    @Shadow
    private double zCloak;

    @Shadow
    private double xCloakO;

    @Shadow
    private double yCloakO;

    @Shadow
    private double zCloakO;

    @Shadow
    private float bob;

    @Shadow
    private float bobO;

    @Shadow
    private float walkDist;

    @Shadow
    private float walkDistO;

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public double labyMod$getXCloak() {
        return this.xCloak;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setXCloak(double x) {
        this.xCloak = x;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public double labyMod$getYCloak() {
        return this.yCloak;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setYCloak(double y) {
        this.yCloak = y;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public double labyMod$getZCloak() {
        return this.zCloak;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setZCloak(double z) {
        this.zCloak = z;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public double labyMod$getXCloakO() {
        return this.xCloakO;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setXCloakO(double xo) {
        this.xCloakO = xo;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public double labyMod$getYCloakO() {
        return this.yCloakO;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setYCloakO(double yo) {
        this.yCloakO = yo;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public double labyMod$getZCloakO() {
        return this.zCloakO;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setZCloakO(double zo) {
        this.zCloakO = zo;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public float labyMod$getBob() {
        return this.bob;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public float labyMod$getBobO() {
        return this.bobO;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public float labyMod$getWalkDist() {
        return this.walkDist;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public void labyMod$setWalkDist(float walkDist) {
        this.walkDist = walkDist;
    }

    @Override // net.labymod.v26_1.client.player.ClientAvatarStateAccessor
    public float labyMod$getWalkDistO() {
        return this.walkDistO;
    }
}
