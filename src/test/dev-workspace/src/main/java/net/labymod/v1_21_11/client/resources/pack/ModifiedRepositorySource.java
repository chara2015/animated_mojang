package net.labymod.v1_21_11.client.resources.pack;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements bam {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<bah> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            bah pack = bah.a(new azk(name, yh.b(name), bal.c, Optional.empty()), new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), azn.a, new azm(true, b.a, true));
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
