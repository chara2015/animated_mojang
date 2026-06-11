package net.labymod.v1_17_1.mixins.server.packs.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.v1_17_1.client.resources.pack.ModifiedPackResources;
import net.labymod.v1_17_1.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/server/packs/resources/MixinSimpleReloadableResourceManager.class */
@Mixin({adz.class})
public abstract class MixinSimpleReloadableResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private List<ado> c;
    private boolean labyMod$isFirstReload = true;

    @Shadow
    public abstract void a(acv acvVar);

    @Inject(method = {"createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;"}, at = {@At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;isDebugEnabled()Z", shift = At.Shift.BEFORE)})
    private void labyMod$fireResourceReloadEvent(Executor foregroundExecutor, Executor backgroundExecutor, CompletableFuture<ahn> futureUnit, List<acv> packs, CallbackInfoReturnable<adq> cir) {
        ResourceReloadEvent.Type type;
        if (this.labyMod$isFirstReload) {
            type = ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS;
        } else {
            type = ResourceReloadEvent.Type.RELOAD;
        }
        ResourceReloadEvent.Type type2 = type;
        Laby.fireEvent(new ResourceReloadEvent(type2, Phase.PRE));
        this.c.add((preparationBarrier, resourceManager, profilerFiller1, profilerFiller2, ex1, ex2) -> {
            return preparationBarrier.a(ahn.a).thenRunAsync(() -> {
                Laby.fireEvent(new ResourceReloadEvent(type2, Phase.POST));
            }, ex2);
        });
        this.labyMod$isFirstReload = false;
    }

    @Override // net.labymod.v1_17_1.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack pack) {
        a(new ModifiedPackResources(pack));
    }
}
