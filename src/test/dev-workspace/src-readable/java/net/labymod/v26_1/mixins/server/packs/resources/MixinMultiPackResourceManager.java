package net.labymod.v26_1.mixins.server.packs.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.v26_1.client.resources.pack.ModifiedPackResources;
import net.labymod.v26_1.client.resources.pack.SilentReloadableResourceManager;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.FallbackResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/server/packs/resources/MixinMultiPackResourceManager.class */
@Mixin({MultiPackResourceManager.class})
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private Map<String, FallbackResourceManager> namespacedManagers;

    @Shadow
    @Mutable
    @Final
    private List<PackResources> packs;

    @Override // net.labymod.v26_1.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack resourcePack) {
        ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        this.packs = new ArrayList(this.packs);
        this.packs.add(pack);
        PackType packType = PackType.CLIENT_RESOURCES;
        for (String namespace : pack.getNamespaces(packType)) {
            this.namespacedManagers.computeIfAbsent(namespace, s -> {
                return new FallbackResourceManager(packType, s);
            }).push(pack);
        }
    }
}
