package net.labymod.v1_12_2.mixins.client.world;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.client.world.DefaultClientWorld;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/MixinWorld.class */
@Mixin({amu.class})
public class MixinWorld {

    @Shadow
    @Final
    public boolean G;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void labyMod$init(bfe p_i528_1_, bfd p_i528_2_, aym p_i528_3_, rl p_i528_4_, boolean p_i528_5_, CallbackInfo ci) {
        ((DefaultClientWorld) getClientWorld()).resetEntityList();
    }

    @Inject(method = {"spawnEntity"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/world/World;loadedEntityList:Ljava/util/List;", shift = At.Shift.AFTER)})
    private void startEntityTracking(vg entity, CallbackInfoReturnable<Boolean> cir) {
        ((DefaultClientWorld) getClientWorld()).addEntity((Entity) entity);
    }

    @Inject(method = {"removeEntity"}, at = {@At("HEAD")})
    private void labyMod$stopEntityTracking$removeEntity(vg entity, CallbackInfo ci) {
        ((DefaultClientWorld) getClientWorld()).removeEntity((Entity) entity);
    }

    private ClientWorld getClientWorld() {
        return LabyMod.getInstance().minecraft().clientWorld();
    }
}
