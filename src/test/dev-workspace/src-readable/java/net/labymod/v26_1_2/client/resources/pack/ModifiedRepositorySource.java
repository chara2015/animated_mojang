package net.labymod.v26_1_2.client.resources.pack;

import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/resources/pack/ModifiedRepositorySource.class */
public class ModifiedRepositorySource implements RepositorySource {
    private final LabyAPI labyAPI;

    public ModifiedRepositorySource(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    public void loadPacks(Consumer<Pack> packConsumer) {
        for (ResourcePack registeredPack : this.labyAPI.renderPipeline().resourcePackRepository().getRegisteredPacks()) {
            String name = registeredPack.getName();
            Pack pack = Pack.readMetaAndCreate(new PackLocationInfo(name, Component.literal(name), PackSource.BUILT_IN, Optional.empty()), new FixedResourcesSupplier(new ModifiedPackResources(registeredPack)), PackType.CLIENT_RESOURCES, new PackSelectionConfig(true, Pack.Position.TOP, true));
            if (pack != null) {
                packConsumer.accept(pack);
            }
        }
    }
}
