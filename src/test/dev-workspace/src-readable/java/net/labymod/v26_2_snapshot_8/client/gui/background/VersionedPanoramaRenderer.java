package net.labymod.v26_2_snapshot_8.client.gui.background;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.background.panorama.AbstractPanoramaRenderer;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gui/background/VersionedPanoramaRenderer.class */
@Singleton
@Implements(PanoramaRenderer.class)
public class VersionedPanoramaRenderer extends AbstractPanoramaRenderer {
    @Inject
    public VersionedPanoramaRenderer(LabyAPI labyAPI) {
        super(labyAPI);
    }
}
