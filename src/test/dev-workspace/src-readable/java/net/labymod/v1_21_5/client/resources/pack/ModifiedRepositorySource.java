package net.labymod.v1_21_5.client.resources.pack;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements avb {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<auv> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            auv pack = auv.a(new atz(name, xg.b(name), ava.c, Optional.empty()), new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), auc.a, new aub(true, b.a, true));
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
