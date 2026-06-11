package net.labymod.v1_21_11.client.resources.pack;

import java.util.List;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.resources.pack.rich.PackSelectionModel;
import net.labymod.api.models.Implements;
import net.labymod.core.client.resources.pack.AbstractResourcePackRepository;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/resources/pack/VersionedResourcePackRepository.class */
@Singleton
@Implements(ResourcePackRepository.class)
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<bah> {
    @Inject
    public VersionedResourcePackRepository() {
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository, net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(ResourcePack pack) {
        super.registerSilentPack(pack);
        SilentReloadableResourceManager silentReloadableResourceManagerAh = gfj.V().ah();
        if (silentReloadableResourceManagerAh instanceof baw) {
            ((baw) silentReloadableResourceManagerAh).loadSilent(pack);
            loadSilentTranslation(pack);
        } else {
            thrownSilentError(pack);
        }
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository
    public void onRebuildSelected(List<bah> selected) {
        this.selectedPacks.clear();
        setHasServerPackSelected(false);
        for (bah pack : selected) {
            this.selectedPacks.add(pack.g());
            checkServerPack(pack);
        }
    }

    private void checkServerPack(bah pack) {
        azl resources = pack.f();
        try {
            azl unwrappedPackResources = PackUtil.unwrap(resources);
            if (((unwrappedPackResources instanceof azi) || (unwrappedPackResources instanceof aze)) && unwrappedPackResources.b().startsWith("server")) {
                setHasServerPackSelected(true);
            }
            if (resources != null) {
                resources.close();
            }
        } catch (Throwable th) {
            if (resources != null) {
                try {
                    resources.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    @NotNull
    public PackSelectionModel createSelectionModel(@NotNull Consumer<PackSelectionModel> onListChange) {
        PackSelectionModel model = new gwn(entry -> {
        }, pack -> {
            return null;
        }, gfj.V().ai(), packRepository -> {
            onListChange.accept((PackSelectionModel) packRepository);
        });
        model.findNewPacks();
        return model;
    }
}
