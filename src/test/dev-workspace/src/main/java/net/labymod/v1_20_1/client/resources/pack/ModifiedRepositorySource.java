package net.labymod.v1_20_1.client.resources.pack;

import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements akk {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void a(Consumer<akg> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            akg pack = akg.a(name, sw.b(name), true, f -> {
                return new ModifiedPackResources(registeredPack);
            }, new a(sw.h(), 11, caw.a()), ajm.a, b.a, true, akj.c);
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
