package net.labymod.v1_12_2.client.resources.pack.rich;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/pack/rich/VanillaPackDetails.class */
public class VanillaPackDetails implements ResourcePackDetails {
    private final cer pack;
    private Icon icon = null;

    public VanillaPackDetails(cer pack) {
        this.pack = pack;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public String getId() {
        return this.pack.b();
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
        return Component.text(this.pack.b());
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public Component getDescription() {
        return Component.text("The default look of Minecraft");
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    public boolean isPositionFixed() {
        return true;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    public boolean isRequired() {
        return true;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public ResourcePackDetails.DefaultPosition getDefaultPosition() {
        return ResourcePackDetails.DefaultPosition.BOTTOM;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @NotNull
    public ResourcePackDetails.Compatibility getCompatibility() {
        return ResourcePackDetails.Compatibility.COMPATIBLE;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    @Nullable
    public File getFile() {
        return null;
    }

    @Override // net.labymod.api.client.resources.pack.rich.ResourcePackDetails
    public boolean isUserPack() {
        return false;
    }

    private void loadIcon() {
        CompletableResourceLocation completable = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                ResourceLocation location = ResourceLocation.create("labymod", "textures/resourcepacks/icons/vanilla-pack-default.png");
                GameImage image = GameImage.IMAGE_PROVIDER.getImage(this.pack.a());
                image.uploadTextureAt(location);
                completable.executeCompletableListeners(location);
            } catch (IOException exception) {
                LOGGER.error("Could not load pack icon.", exception);
            }
        });
        this.icon = Icon.completable(completable);
    }
}
