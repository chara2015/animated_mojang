package net.labymod.v1_12_2.mixins.client.resources;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.resources.pack.ResourceReloadEvent;
import net.labymod.core.client.resources.pack.util.EventResourcePackRepositoryCaller;
import net.labymod.v1_12_2.client.resources.pack.ModifiedPackResources;
import net.labymod.v1_12_2.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/resources/MixinSimpleReloadableResourceManager.class */
@Mixin({cev.class})
public abstract class MixinSimpleReloadableResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private Set<String> e;

    @Shadow
    @Final
    private Map<String, cei> c;

    @Shadow
    @Final
    private cfg f;
    private boolean labyMod$isFirstReload;

    @Inject(method = {"reloadResources"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;clearResources()V", shift = At.Shift.AFTER)})
    private void labyMod$registerModifiedResourcesPacks(List<cer> packs, CallbackInfo ci) {
        for (ResourcePack pack : Laby.labyAPI().renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            packs.add(new ModifiedPackResources(pack));
        }
    }

    @Inject(method = {"reloadResources"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;notifyReloadListeners()V", shift = At.Shift.BEFORE)})
    private void labyMod$rebuildSelected(List<cer> selected, CallbackInfo ci) {
        EventResourcePackRepositoryCaller.onRebuildSelected(selected);
    }

    @WrapOperation(method = {"reloadResources"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SimpleReloadableResourceManager;notifyReloadListeners()V")})
    private void labyMod$firePostResourceReloadEvent(cev instance, Operation<Void> original) {
        ResourceReloadEvent.Type type;
        if (this.labyMod$isFirstReload) {
            type = ResourceReloadEvent.Type.INITIALIZATION_RESOURCE_PACKS;
        } else {
            type = ResourceReloadEvent.Type.RELOAD;
        }
        ResourceReloadEvent.Type type2 = type;
        Laby.fireEvent(new ResourceReloadEvent(type2, Phase.PRE));
        original.call(new Object[]{instance});
        Laby.fireEvent(new ResourceReloadEvent(type2, Phase.POST));
        this.labyMod$isFirstReload = false;
    }

    @Override // net.labymod.v1_12_2.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(cer pack) {
        for (String resourceDomain : pack.c()) {
            this.e.add(resourceDomain);
            this.c.computeIfAbsent(resourceDomain, s -> {
                return new cei(this.f);
            }).a(pack);
        }
    }
}
