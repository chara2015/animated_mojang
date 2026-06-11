package net.labymod.v1_20_4.mixins.server.packs.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.v1_20_4.client.resources.pack.ModifiedPackResources;
import net.labymod.v1_20_4.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/server/packs/resources/MixinMultiPackResourceManager.class */
@Mixin({aqb.class})
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private Map<String, apz> b;

    @Shadow
    @Mutable
    @Final
    private List<aow> c;

    @Override // net.labymod.v1_20_4.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack resourcePack) {
        ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        this.c = new ArrayList(this.c);
        this.c.add(pack);
        aox packType = aox.a;
        for (String namespace : pack.a(packType)) {
            this.b.computeIfAbsent(namespace, s -> {
                return new apz(packType, s);
            }).a(pack);
        }
    }
}
