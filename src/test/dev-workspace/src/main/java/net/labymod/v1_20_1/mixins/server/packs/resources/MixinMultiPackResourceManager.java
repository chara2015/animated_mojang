package net.labymod.v1_20_1.mixins.server.packs.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.v1_20_1.client.resources.pack.ModifiedPackResources;
import net.labymod.v1_20_1.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/server/packs/resources/MixinMultiPackResourceManager.class */
@Mixin({akq.class})
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private Map<String, ako> b;

    @Shadow
    @Mutable
    @Final
    private List<ajl> c;

    @Override // net.labymod.v1_20_1.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack resourcePack) {
        ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        this.c = new ArrayList(this.c);
        this.c.add(pack);
        ajm packType = ajm.a;
        for (String namespace : pack.a(packType)) {
            this.b.computeIfAbsent(namespace, s -> {
                return new ako(packType, s);
            }).a(pack);
        }
    }
}
