package net.labymod.core.client.resources.pack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.resources.pack.PackTypes;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.localization.DefaultInternationalization;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/pack/AbstractResourcePackRepository.class */
public abstract class AbstractResourcePackRepository<T> implements ResourcePackRepository {
    protected static final String SERVER_RESOURCES_PACK_IDENTIFIER = "server";
    protected static final Logging LOGGER = Logging.getLogger();
    private boolean hasServerPackSelected;
    protected final List<String> selectedPacks = new ArrayList();
    private final ResourcePack.Factory resourcePackFactory = Laby.references().resourcePackFactory();
    private final List<ResourcePack> registeredResourcePacks = new ArrayList();

    public abstract void onRebuildSelected(List<T> list);

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerPack(String name) {
        registerPack(this.resourcePackFactory.classpath(name));
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerPack(LoadedAddon addon) {
        registerPack(this.resourcePackFactory.classpath(addon.info().getNamespace(), addon));
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(String name) {
        registerSilentPack(this.resourcePackFactory.classpath(name));
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(LoadedAddon addon) {
        registerSilentPack(this.resourcePackFactory.classpath(addon.info().getNamespace(), addon));
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerFolderPack(String namespace, Path directory) {
        registerPack(this.resourcePackFactory.folder(namespace, directory));
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentFolderPack(String namespace, Path directory) {
        registerSilentPack(this.resourcePackFactory.folder(namespace, directory));
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerPack(ResourcePack pack) {
        this.registeredResourcePacks.add(pack);
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public void registerSilentPack(ResourcePack pack) {
        registerPack(pack);
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public List<ResourcePack> getRegisteredPacks() {
        return this.registeredResourcePacks;
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public Collection<String> getSelectedPacks() {
        return this.selectedPacks;
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePackRepository
    public boolean hasServerPackSelected() {
        return this.hasServerPackSelected;
    }

    public void setHasServerPackSelected(boolean hasServerPackSelected) {
        this.hasServerPackSelected = hasServerPackSelected;
    }

    public final void onReload(List<T> selected) {
        onRebuildSelected(selected);
    }

    protected void thrownSilentError(ResourcePack pack) {
        throw new IllegalStateException("Resource pack could not be loaded silently (" + pack.getName() + ")");
    }

    protected void loadSilentTranslation(ResourcePack pack) {
        DefaultInternationalization localeManager = (DefaultInternationalization) Laby.labyAPI().internationalization();
        for (String clientNamespace : pack.getNamespaces(PackTypes.CLIENT_RESOURCES)) {
            try {
                localeManager.loadTranslations(clientNamespace);
            } catch (IOException exception) {
                LOGGER.error("Translations could not be loaded silently (" + exception.getMessage() + ")", new Object[0]);
            }
        }
    }
}
