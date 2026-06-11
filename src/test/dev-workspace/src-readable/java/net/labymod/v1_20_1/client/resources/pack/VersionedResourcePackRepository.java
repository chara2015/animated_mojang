package net.labymod.v1_20_1.client.resources.pack;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/resources/pack/VersionedResourcePackRepository.class */
@Singleton
@Implements(ResourcePackRepository.class)
public class VersionedResourcePackRepository extends AbstractResourcePackRepository<akg> {
    @Inject
    public VersionedResourcePackRepository() {
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository, net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(ResourcePack pack) {
        super.registerSilentPack(pack);
        SilentReloadableResourceManager silentReloadableResourceManagerY = enn.N().Y();
        if (silentReloadableResourceManagerY instanceof aku) {
            ((aku) silentReloadableResourceManagerY).loadSilent(pack);
            loadSilentTranslation(pack);
        } else {
            thrownSilentError(pack);
        }
    }

    @Override // net.labymod.core.client.resources.pack.AbstractResourcePackRepository
    public void onRebuildSelected(List<akg> selected) {
        this.selectedPacks.clear();
        setHasServerPackSelected(false);
        for (akg pack : selected) {
            this.selectedPacks.add(pack.f());
            checkServerPack(pack);
        }
    }

    private void checkServerPack(akg pack) {
        ajl resources = pack.e();
        try {
            ajl unwrappedPackResources = PackUtil.unwrap(resources);
            if (unwrappedPackResources instanceof ajk) {
                if ("server".equals(unwrappedPackResources.a())) {
                    setHasServerPackSelected(true);
                }
                if (resources != null) {
                    resources.close();
                    return;
                }
                return;
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
        PackSelectionModel model = new exu(() -> {
        }, pack -> {
            return null;
        }, enn.N().Z(), packRepository -> {
            onListChange.accept((PackSelectionModel) packRepository);
        });
        model.findNewPacks();
        return model;
    }
}
