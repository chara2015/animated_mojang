package net.labymod.v1_18_2.mixins.server.packs.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.v1_18_2.client.resources.pack.ModifiedPackResources;
import net.labymod.v1_18_2.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/server/packs/resources/MixinMultiPackResourceManager.class */
@Mixin({afu.class})
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private Map<String, aft> a;

    @Shadow
    @Mutable
    @Final
    private List<afa> b;

    @Override // net.labymod.v1_18_2.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack resourcePack) {
        ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        this.b = new ArrayList(this.b);
        this.b.add(pack);
        afb packType = afb.a;
        for (String namespace : pack.a(packType)) {
            this.a.computeIfAbsent(namespace, s -> {
                return new aft(packType, s);
            }).a(pack);
        }
    }
}
