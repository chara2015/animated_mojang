package net.minecraft.client.multiplayer;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/KnownPacksManager.class */
public class KnownPacksManager {
    private final PackRepository repository = ServerPacksSource.createVanillaTrustedRepository();
    private final Map<KnownPack, String> knownPackToId;

    public KnownPacksManager() {
        this.repository.reload();
        ImmutableMap.Builder<KnownPack, String> $$0 = ImmutableMap.builder();
        this.repository.getAvailablePacks().forEach($$1 -> {
            PackLocationInfo $$2 = $$1.location();
            $$2.knownPackInfo().ifPresent($$22 -> {
                $$0.put($$22, $$2.id());
            });
        });
        this.knownPackToId = $$0.build();
    }

    public List<KnownPack> trySelectingPacks(List<KnownPack> $$0) {
        List<KnownPack> $$1 = new ArrayList<>($$0.size());
        List<String> $$2 = new ArrayList<>($$0.size());
        for (KnownPack $$3 : $$0) {
            String $$4 = this.knownPackToId.get($$3);
            if ($$4 != null) {
                $$2.add($$4);
                $$1.add($$3);
            }
        }
        this.repository.setSelected($$2);
        return $$1;
    }

    public CloseableResourceManager createResourceManager() {
        List<PackResources> $$0 = this.repository.openAllSelected();
        return new MultiPackResourceManager(PackType.SERVER_DATA, $$0);
    }
}
