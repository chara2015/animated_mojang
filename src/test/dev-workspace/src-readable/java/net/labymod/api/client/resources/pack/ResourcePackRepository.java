package net.labymod.api.client.resources.pack;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.resources.pack.rich.PackSelectionModel;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourcePackRepository.class */
@Referenceable
public interface ResourcePackRepository {
    void registerPack(String str);

    void registerPack(LoadedAddon loadedAddon);

    void registerSilentPack(String str);

    void registerSilentPack(LoadedAddon loadedAddon);

    void registerFolderPack(String str, Path path);

    void registerSilentFolderPack(String str, Path path);

    void registerPack(ResourcePack resourcePack);

    void registerSilentPack(ResourcePack resourcePack);

    boolean hasServerPackSelected();

    Collection<String> getSelectedPacks();

    List<ResourcePack> getRegisteredPacks();

    @NotNull
    PackSelectionModel createSelectionModel(@NotNull Consumer<PackSelectionModel> consumer);

    @NotNull
    default PackSelectionModel createSelectionModel() {
        return createSelectionModel(model -> {
        });
    }
}
