package net.labymod.v1_21_11.mixins.client.resources.pack;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.rich.ResourcePackDetails;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.HashUtil;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.io.LabyExecutors;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/resources/pack/MixinPack.class */
@Mixin({Pack.class})
@Implements({@Interface(iface = ResourcePackDetails.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPack implements ResourcePackDetails {

    @Unique
    private static final ComponentMapper COMPONENT_MAPPER = Laby.references().componentMapper();

    @Unique
    private Icon labymod4$icon = null;

    @Shadow
    @Final
    private PackLocationInfo location;

    @Shadow
    @Final
    private Pack.Metadata metadata;

    @Shadow
    @Final
    private PackSelectionConfig selectionConfig;

    @Shadow
    @Final
    private Pack.ResourcesSupplier resources;

    @Shadow
    @NotNull
    public abstract String shadow$getId();

    @Shadow
    public abstract PackResources open();

    @Shadow
    public abstract boolean isFixedPosition();

    @Shadow
    public abstract PackSource getPackSource();

    @Intrinsic
    @NotNull
    public String labyMod$getId() {
        return shadow$getId();
    }

    @Intrinsic
    @NotNull
    public Icon labyMod$getIcon() {
        if (this.labymod4$icon == null) {
            this.labymod4$icon = labymod4$loadPackIcon();
        }
        return this.labymod4$icon;
    }

    @Intrinsic
    @NotNull
    public Component labyMod$getTitle() {
        return COMPONENT_MAPPER.fromMinecraftComponent(this.location.title());
    }

    @Intrinsic
    @NotNull
    public Component labyMod$getDescription() {
        return COMPONENT_MAPPER.fromMinecraftComponent(this.metadata.description());
    }

    @Intrinsic
    public boolean labyMod$isPositionFixed() {
        return isFixedPosition();
    }

    @Intrinsic
    public boolean labyMod$isRequired() {
        return this.selectionConfig.required();
    }

    @Intrinsic
    @NotNull
    public ResourcePackDetails.DefaultPosition labyMod$getDefaultPosition() {
        return this.selectionConfig.defaultPosition() == Pack.Position.TOP ? ResourcePackDetails.DefaultPosition.TOP : ResourcePackDetails.DefaultPosition.BOTTOM;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.resources.pack.MixinPack$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/resources/pack/MixinPack$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility = new int[PackCompatibility.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[PackCompatibility.TOO_OLD.ordinal()] = 1;
            } catch (NoSuchFieldError selectionConfig) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[PackCompatibility.TOO_NEW.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[PackCompatibility.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[PackCompatibility.COMPATIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Intrinsic
    @NotNull
    public ResourcePackDetails.Compatibility labyMod$getCompatibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[this.metadata.compatibility().ordinal()]) {
            case 1:
                return ResourcePackDetails.Compatibility.TOO_OLD;
            case 2:
                return ResourcePackDetails.Compatibility.TOO_NEW;
            case 3:
                return ResourcePackDetails.Compatibility.UNKNOWN;
            case 4:
                return ResourcePackDetails.Compatibility.COMPATIBLE;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Intrinsic
    public boolean labyMod$isUserPack() {
        return getPackSource() == PackSource.DEFAULT;
    }

    @Intrinsic
    @Nullable
    public File labyMod$getFile() {
        FilePackResources filePackResources = this.resources;
        if (filePackResources instanceof FilePackResources) {
            FilePackResources packResources = filePackResources;
            FilePackResources.SharedZipFileAccess zipFileAccess = packResources.zipFileAccess;
            return zipFileAccess.file;
        }
        FilePackResources.FileResourcesSupplier fileResourcesSupplier = this.resources;
        if (fileResourcesSupplier instanceof FilePackResources.FileResourcesSupplier) {
            FilePackResources.FileResourcesSupplier supplier = fileResourcesSupplier;
            return supplier.content;
        }
        PathPackResources pathPackResources = this.resources;
        if (pathPackResources instanceof PathPackResources) {
            PathPackResources pathPack = pathPackResources;
            return pathPack.root.toFile();
        }
        PathPackResources.PathResourcesSupplier pathResourcesSupplier = this.resources;
        if (pathResourcesSupplier instanceof PathPackResources.PathResourcesSupplier) {
            PathPackResources.PathResourcesSupplier resourcesSupplier = pathResourcesSupplier;
            return resourcesSupplier.content.toFile();
        }
        return null;
    }

    @Unique
    @NotNull
    private Icon labymod4$loadPackIcon() {
        CompletableResourceLocation completable = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                PackResources resources = open();
                try {
                    IoSupplier<InputStream> rootResource = resources.getRootResource(new String[]{"pack.png"});
                    if (rootResource != null) {
                        InputStream stream = (InputStream) rootResource.get();
                        try {
                            ResourceLocation location = ResourceLocation.create("labymod", "icon/" + StringUtil.sanitizePath(getId()) + "/" + HashUtil.sha1Hex(getId().getBytes(StandardCharsets.UTF_8)) + ".png");
                            GameImage.IMAGE_PROVIDER.getImage(stream).uploadTextureAt(location);
                            completable.executeCompletableListeners(location);
                            if (stream != null) {
                                stream.close();
                            }
                            if (resources != null) {
                                resources.close();
                            }
                            return;
                        } catch (Throwable t$) {
                            if (stream != null) {
                                try {
                                    stream.close();
                                } catch (Throwable x2) {
                                    t$.addSuppressed(x2);
                                }
                            }
                            throw t$;
                        }
                    }
                    if (resources != null) {
                        resources.close();
                    }
                } finally {
                }
            } catch (Exception ex) {
                LOGGER.warn("Failed to load icon from pack " + getId(), ex);
            }
        });
        return Icon.completable(completable);
    }
}

