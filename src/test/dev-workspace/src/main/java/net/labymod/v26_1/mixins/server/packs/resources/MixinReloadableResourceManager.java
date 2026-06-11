package net.labymod.v26_1.mixins.server.packs.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.v26_1.client.resources.pack.SilentReloadableResourceManager;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/server/packs/resources/MixinReloadableResourceManager.class */
@Mixin({ReloadableResourceManager.class})
public class MixinReloadableResourceManager implements SilentReloadableResourceManager {

    @Shadow
    private CloseableResourceManager resources;

    @Shadow
    @Final
    private List<PreparableReloadListener> listeners;
    private boolean labyMod$firstReload;

    @Inject(method = {"createReload"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.BEFORE)})
    private void registerModifiedResourcePacks(Executor executor1, Executor executor2, CompletableFuture<Unit> future, List<PackResources> list, CallbackInfoReturnable<ReloadInstance> cir) {
        ResourceReloadEvent.Type type;
        if (this.labyMod$firstReload) {
            type = ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS;
        } else {
            type = ResourceReloadEvent.Type.RELOAD;
        }
        ResourceReloadEvent.Type type2 = type;
        Laby.fireEvent(new ResourceReloadEvent(type2, Phase.PRE));
        this.listeners.add((sharedState, resourceManager, ex1, ex2) -> {
            return ex1.wait(Unit.INSTANCE).thenRunAsync(() -> {
                Laby.fireEvent(new ResourceReloadEvent(type2, Phase.POST));
            }, ex2);
        });
        this.labyMod$firstReload = false;
    }

    @Override // net.labymod.v26_1.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack pack) {
        SilentReloadableResourceManager silentReloadableResourceManager = this.resources;
        if (!(silentReloadableResourceManager instanceof MultiPackResourceManager)) {
            throw new IllegalStateException("Resource pack could not be loaded silently (" + pack.getName() + ") because it was not loaded by MultiPackResourceManager");
        }
        ((MultiPackResourceManager) silentReloadableResourceManager).loadSilent(pack);
    }
}
