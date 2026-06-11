package net.labymod.v1_8_9.client.resources.pack.rich;

import java.io.File;
import java.io.IOException;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.rich.ResourcePackDetails;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.io.LabyExecutors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/resources/pack/rich/VersionedPackDetails.class */
public class VersionedPackDetails implements ResourcePackDetails {
    private final a entry;
    private Icon icon = null;

    public VersionedPackDetails(@NotNull a entry) {
        this.entry = entry;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public String getId() {
        return this.entry.d();
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public Icon getIcon() {
        if (this.icon == null) {
            loadIcon();
        }
        return this.icon;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public Component getTitle() {
        return Component.text(this.entry.d());
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public Component getDescription() {
        return Component.text(this.entry.e());
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    public boolean isPositionFixed() {
        return isDefaultPack();
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    public boolean isRequired() {
        return isDefaultPack();
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public ResourcePackDetails.DefaultPosition getDefaultPosition() {
        return isDefaultPack() ? ResourcePackDetails.DefaultPosition.BOTTOM : ResourcePackDetails.DefaultPosition.TOP;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public ResourcePackDetails.Compatibility getCompatibility() {
        int result = this.entry.f();
        if (result == 1) {
            return ResourcePackDetails.Compatibility.COMPATIBLE;
        }
        if (result < 1) {
            return ResourcePackDetails.Compatibility.TOO_OLD;
        }
        return ResourcePackDetails.Compatibility.TOO_NEW;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @Nullable
    public File getFile() {
        bmx bmxVarC = this.entry.c();
        if (bmxVarC instanceof bmx) {
            bmx ar = bmxVarC;
            return ar.a;
        }
        return null;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    public boolean isUserPack() {
        return !isDefaultPack();
    }

    public a getEntry() {
        return this.entry;
    }

    private boolean isDefaultPack() {
        return this.entry.c() instanceof bna;
    }

    private void loadIcon() {
        CompletableResourceLocation completable = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                ResourceLocation location = ResourceLocation.create("labymod", "textures/resourcepacks/icons/" + this.entry.d() + ".png");
                GameImage image = GameImage.IMAGE_PROVIDER.getImage(this.entry.c().a());
                image.uploadTextureAt(location);
                completable.executeCompletableListeners(location);
            } catch (IOException exception) {
                LOGGER.error("Could not load pack icon.", exception);
            }
        });
        this.icon = Icon.completable(completable);
    }
}
