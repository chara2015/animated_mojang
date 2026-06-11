package net.labymod.core.localization;

import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.PackTypes;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/InternationalizationReloader.class */
public final class InternationalizationReloader {
    private static final Logging LOGGER = Logging.getLogger();

    public static void reload() {
        ReferenceStorage references = Laby.references();
        DefaultInternationalization internationalization = (DefaultInternationalization) references.internationalization();
        internationalization.onResourceReload();
        ResourcePackRepository repository = references.resourcePackRepository();
        for (ResourcePack registeredPack : repository.getRegisteredPacks()) {
            for (String clientNamespace : registeredPack.getNamespaces(PackTypes.CLIENT_RESOURCES)) {
                try {
                    internationalization.loadTranslations(clientNamespace);
                } catch (IOException exception) {
                    LOGGER.error("Failed to load translations (Namespace: {})", clientNamespace, exception);
                }
            }
        }
    }
}
