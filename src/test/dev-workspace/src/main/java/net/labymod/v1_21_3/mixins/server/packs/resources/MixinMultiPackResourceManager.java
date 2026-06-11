package net.labymod.v1_21_3.mixins.server.packs.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.v1_21_3.client.resources.pack.ModifiedPackResources;
import net.labymod.v1_21_3.client.resources.pack.SilentReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/server/packs/resources/MixinMultiPackResourceManager.class */
@Mixin({avo.class})
public class MixinMultiPackResourceManager implements SilentReloadableResourceManager {

    @Shadow
    @Final
    private Map<String, avm> c;

    @Shadow
    @Mutable
    @Final
    private List<aug> d;

    @Override // net.labymod.v1_21_3.client.resources.pack.SilentReloadableResourceManager
    public void loadSilent(ResourcePack resourcePack) {
        ModifiedPackResources pack = new ModifiedPackResources(resourcePack);
        this.d = new ArrayList(this.d);
        this.d.add(pack);
        aui packType = aui.a;
        for (String namespace : pack.a(packType)) {
            this.c.computeIfAbsent(namespace, s -> {
                return new avm(packType, s);
            }).a(pack);
        }
    }
}
