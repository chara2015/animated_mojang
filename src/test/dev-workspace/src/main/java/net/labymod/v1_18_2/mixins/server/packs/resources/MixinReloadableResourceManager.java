package net.labymod.v1_18_2.mixins.server.packs.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.v1_18_2.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/server/packs/resources/MixinReloadableResourceManager.class */
@Mixin({afy.class})
public class MixinReloadableResourceManager implements SilentReloadableResourceManager {

    @Shadow
    private afs b;

    @Shadow
    @Final
    private List<afv> c;
    private boolean labyMod$isFirstReload = true;

    @Inject(method = {"createReload"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.BEFORE)})
    private void labyMod$registerModifiedResourcePacks(Executor $$0, Executor $$1, CompletableFuture<aka> $$2, List<afa> $$3, CallbackInfoReturnable<afx> cir) {
        ResourceReloadEvent.Type type;
        if (this.labyMod$isFirstReload) {
            type = ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS;
        } else {
            type = ResourceReloadEvent.Type.RELOAD;
        }
        ResourceReloadEvent.Type type2 = type;
        Laby.fireEvent(new ResourceReloadEvent(type2, Phase.PRE));
        this.c.add((preparationBarrier, resourceManager, profilerFiller1, profilerFiller2, ex1, ex2) -> {
            return preparationBarrier.a(aka.a).thenRunAsync(() -> {
                Laby.fireEvent(new ResourceReloadEvent(type2, Phase.POST));
            }, ex2);
        });
        this.labyMod$isFirstReload = false;
    }

    @Override // net.labymod.v1_18_2.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack pack) {
        SilentReloadableResourceManager silentReloadableResourceManager = this.b;
        if (!(silentReloadableResourceManager instanceof afu)) {
            throw new IllegalStateException("Resource pack could not be loaded silently (" + pack.getName() + ") because it was not loaded by MultiPackResourceManager");
        }
        ((afu) silentReloadableResourceManager).loadSilent(pack);
    }
}
