package net.labymod.v1_8_9.client.resources.pack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.resources.pack.rich.PackSelectionModel;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;
import net.labymod.core.client.resources.pack.AbstractResourcePackRepository;
import net.labymod.v1_8_9.client.resources.pack.rich.VersionedPackSelectionModel;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/resources/pack/VersionedResourcePackRepository.class */
@Singleton
@Implements(ResourcePackRepository.class)
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<bnk> {
    private final List<ResourcePack> registeredPacks = new ArrayList();

    @Inject
    public VersionedResourcePackRepository() {
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository, net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerPack(ResourcePack pack) {
        this.registeredPacks.add(pack);
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository, net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(ResourcePack pack) {
        super.registerSilentPack(pack);
        SilentReloadableResourceManager silentReloadableResourceManagerQ = ave.A().Q();
        if (silentReloadableResourceManagerQ instanceof bnn) {
            ((bnn) silentReloadableResourceManagerQ).loadSilent(new ModifiedPackResources(pack));
            loadSilentTranslation(pack);
        } else {
            thrownSilentError(pack);
        }
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository
    public void onRebuildSelected(List<bnk> selectedPacks) {
        this.selectedPacks.clear();
        setHasServerPackSelected(false);
        for (bnk selectedResourcePack : selectedPacks) {
            this.selectedPacks.add(selectedResourcePack.b());
            checkServerPack(selectedResourcePack);
        }
        this.selectedPacks.add(VanillaTheme.ID);
    }

    private void checkServerPack(bnk selectedResourcePack) {
        if (!(selectedResourcePack instanceof bnc)) {
            return;
        }
        bnc fileResourcePack = (bnc) selectedResourcePack;
        if ("server".equals(fileResourcePack.b())) {
            setHasServerPackSelected(true);
        }
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository, net.labymod.api.client.resources.pack.ResourcePackRepository
    public List<ResourcePack> getRegisteredPacks() {
        return this.registeredPacks;
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    @NotNull
    public PackSelectionModel createSelectionModel(@NotNull Consumer<PackSelectionModel> onListChange) {
        return new VersionedPackSelectionModel(ave.A().R());
    }
}
