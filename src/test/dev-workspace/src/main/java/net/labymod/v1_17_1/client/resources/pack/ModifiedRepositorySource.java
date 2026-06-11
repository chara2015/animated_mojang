package net.labymod.v1_17_1.client.resources.pack;

import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements adk {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void a(Consumer<adg> packConsumer, a packConstructor) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            adg pack = adg.a(registeredPack.getName(), true, () -> {
                return new ModifiedPackResources(registeredPack);
            }, packConstructor, b.a, adj.b);
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
