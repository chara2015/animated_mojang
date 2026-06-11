package net.labymod.api.client.resources.pack.rich;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/rich/PackSelectionModel.class */
public interface PackSelectionModel {
    List<ResourcePackDetails> getUnselected();

    List<ResourcePackDetails> getSelected();

    void findNewPacks();

    void updatePacks(@NotNull List<ResourcePackDetails> list, @NotNull List<ResourcePackDetails> list2);

    void selectPack(ResourcePackDetails resourcePackDetails);

    void deselectPack(ResourcePackDetails resourcePackDetails);

    void commitPacks();
}
