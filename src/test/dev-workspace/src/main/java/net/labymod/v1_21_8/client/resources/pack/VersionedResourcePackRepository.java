package net.labymod.v1_21_8.client.resources.pack;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/resources/pack/VersionedResourcePackRepository.class */
@Singleton
@Implements(ResourcePackRepository.class)
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<aww> {
    @Inject
    public VersionedResourcePackRepository() {
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository, net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(ResourcePack pack) {
        super.registerSilentPack(pack);
        SilentReloadableResourceManager silentReloadableResourceManagerAd = fue.R().ad();
        if (silentReloadableResourceManagerAd instanceof axl) {
            ((axl) silentReloadableResourceManagerAd).loadSilent(pack);
            loadSilentTranslation(pack);
        } else {
            thrownSilentError(pack);
        }
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository
    public void onRebuildSelected(List<aww> selected) {
        this.selectedPacks.clear();
        setHasServerPackSelected(false);
        for (aww pack : selected) {
            this.selectedPacks.add(pack.g());
            checkServerPack(pack);
        }
    }

    private void checkServerPack(aww pack) {
        awb resources = pack.f();
        try {
            awb unwrappedPackResources = PackUtil.unwrap(resources);
            if (((unwrappedPackResources instanceof avy) || (unwrappedPackResources instanceof avu)) && unwrappedPackResources.b().startsWith("server")) {
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
        PackSelectionModel model = new gjb(() -> {
        }, pack -> {
            return null;
        }, fue.R().ae(), packRepository -> {
            onListChange.accept((PackSelectionModel) packRepository);
        });
        model.findNewPacks();
        return model;
    }
}
