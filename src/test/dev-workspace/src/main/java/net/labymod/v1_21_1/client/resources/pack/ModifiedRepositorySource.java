package net.labymod.v1_21_1.client.resources.pack;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements atr {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<atm> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            atm pack = atm.a(new asp(name, wz.b(name), atq.c, Optional.empty()), new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), ass.a, new asr(true, b.a, true));
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
