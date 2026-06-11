package net.labymod.core.revision;

import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetScreenOverlay;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.singleplayer.SingleplayerOverlay;
import net.labymod.core.client.gui.screen.widget.widgets.RevisionPopupWidget;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/revision/PopupRevision.class */
public class PopupRevision extends SimpleRevision {
    private final String bannerPath;
    private boolean showPopUp;
    private boolean showToNewUsers;

    public PopupRevision(String namespace, SemanticVersion version, String releaseDate, String bannerPath, boolean showToNewUsers) {
        super(namespace, version, releaseDate);
        this.showPopUp = false;
        this.bannerPath = bannerPath;
        this.showToNewUsers = showToNewUsers;
        Laby.labyAPI().eventBus().registerListener(this);
    }

    @Subscribe
    public void onActivityOpen(ActivityOpenEvent event) {
        if (!this.showPopUp) {
            return;
        }
        Activity activity = event.activity();
        if (!(activity instanceof MultiplayerActivity) && !(activity instanceof SingleplayerOverlay)) {
            return;
        }
        WidgetScreenOverlay overlay = (WidgetScreenOverlay) Laby.labyAPI().screenOverlayHandler().screenOverlay(WidgetScreenOverlay.class);
        if (overlay == null) {
            return;
        }
        Laby.labyAPI().minecraft().executeNextTick(() -> {
            try {
                Icon icon = getIcon(this.bannerPath);
                RevisionPopupWidget popupWidget = new RevisionPopupWidget(icon);
                popupWidget.displayInOverlay();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            this.showPopUp = false;
        });
    }

    private Icon getIcon(String path) throws IOException {
        ResourceLocation resourceLocation = Laby.references().resourceLocationFactory().create(getNamespace(), path);
        Icon icon = Icon.texture(resourceLocation);
        GameImage image = Laby.references().gameImageProvider().getImage(resourceLocation);
        icon.resolution(image.getWidth(), image.getHeight());
        SimpleTexture simpleTexture = SimpleTexture.builder().setSamplerDescription(SamplerDescription.builder().setMinFilter(SamplerDescription.Filter.LINEAR).setMagFilter(SamplerDescription.Filter.LINEAR).build()).setDebugName(resourceLocation.toString()).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).build(resourceLocation, image);
        simpleTexture.upload();
        simpleTexture.bindTo(resourceLocation);
        image.close();
        return icon;
    }

    public void visit(boolean isNewUser) {
        if (isNewUser && !this.showToNewUsers) {
            return;
        }
        this.showPopUp = true;
    }
}
