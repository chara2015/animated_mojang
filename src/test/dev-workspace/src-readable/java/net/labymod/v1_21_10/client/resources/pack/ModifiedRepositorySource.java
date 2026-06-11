package net.labymod.v1_21_10.client.resources.pack;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements bac {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<azx> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            azx pack = azx.a(new aza(name, xx.b(name), bab.c, Optional.empty()), new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), azd.a, new azc(true, b.a, true));
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
