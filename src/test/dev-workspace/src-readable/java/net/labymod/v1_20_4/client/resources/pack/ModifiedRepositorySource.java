package net.labymod.v1_20_4.client.resources.pack;

import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements apv {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<apq> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            apq pack = apq.a(name, vf.b(name), true, new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), new a(vf.i(), apr.c, chs.a(), List.of()), b.a, true, apu.c);
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
