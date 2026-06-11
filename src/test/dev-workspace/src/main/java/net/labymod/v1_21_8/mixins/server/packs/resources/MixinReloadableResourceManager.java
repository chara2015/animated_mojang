package net.labymod.v1_21_8.mixins.server.packs.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.v1_21_8.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/server/packs/resources/MixinReloadableResourceManager.class */
@Mixin({axl.class})
public class MixinReloadableResourceManager implements SilentReloadableResourceManager {

    @Shadow
    private axe c;

    @Shadow
    @Final
    private List<axi> d;
    private boolean labyMod$firstReload;

    @Inject(method = {"createReload"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.BEFORE)})
    private void registerModifiedResourcePacks(Executor executor1, Executor executor2, CompletableFuture<bdk> future, List<awb> list, CallbackInfoReturnable<axk> cir) {
        ResourceReloadEvent.Type type;
        if (this.labyMod$firstReload) {
            type = ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS;
        } else {
            type = ResourceReloadEvent.Type.RELOAD;
        }
        ResourceReloadEvent.Type type2 = type;
        Laby.fireEvent(new ResourceReloadEvent(type2, Phase.PRE));
        this.d.add((preparationBarrier, resourceManager, ex1, ex2) -> {
            return preparationBarrier.wait(bdk.a).thenRunAsync(() -> {
                Laby.fireEvent(new ResourceReloadEvent(type2, Phase.POST));
            }, ex2);
        });
        this.labyMod$firstReload = false;
    }

    @Override // net.labymod.v1_21_8.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack pack) {
        SilentReloadableResourceManager silentReloadableResourceManager = this.c;
        if (!(silentReloadableResourceManager instanceof axh)) {
            throw new IllegalStateException("Resource pack could not be loaded silently (" + pack.getName() + ") because it was not loaded by MultiPackResourceManager");
        }
        ((axh) silentReloadableResourceManager).loadSilent(pack);
    }
}
