package net.labymod.v1_21_3.client.resources.pack;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements avi {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<avd> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            avd pack = avd.a(new auf(name, xv.b(name), avh.c, Optional.empty()), new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), aui.a, new auh(true, b.a, true));
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
