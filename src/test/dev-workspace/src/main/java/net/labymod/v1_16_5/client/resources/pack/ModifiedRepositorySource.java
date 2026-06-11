package net.labymod.v1_16_5.client.resources.pack;

import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements aby {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void a(Consumer<abu> packConsumer, a packConstructor) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            abu pack = abu.a(registeredPack.getName(), true, () -> {
                return new ModifiedPackResources(registeredPack);
            }, packConstructor, b.a, abx.b);
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
