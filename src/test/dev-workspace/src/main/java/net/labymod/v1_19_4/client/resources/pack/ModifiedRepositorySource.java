package net.labymod.v1_19_4.client.resources.pack;

import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements aku {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void a(Consumer<akq> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            akq pack = akq.a(name, tj.b(name), true, f -> {
                return new ModifiedPackResources(registeredPack);
            }, new a(tj.h(), 11, cau.a()), ajw.a, b.a, true, akt.c);
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
