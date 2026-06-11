package net.labymod.core.client.gui.background;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.client.gui.background.bootlogo.LegacyMojangBootLogoRenderer;
import net.labymod.core.client.gui.background.bootlogo.MojangStudiosBootLogoRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/background/BootLogoController.class */
@Singleton
@Referenceable
public class BootLogoController {
    private final AbstractBootLogoRenderer renderer;

    @Inject
    public BootLogoController() {
        if (MinecraftVersions.V1_16.orOlder()) {
            this.renderer = new LegacyMojangBootLogoRenderer();
        } else {
            this.renderer = new MojangStudiosBootLogoRenderer();
        }
    }

    public AbstractBootLogoRenderer renderer() {
        return this.renderer;
    }
}
