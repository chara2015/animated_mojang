package net.labymod.v1_21_4.mixins.client.resources.pack;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/resources/pack/MixinPack.class */
@Mixin({atx.class})
@Implements({@Interface(iface = ResourcePackDetails.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPack implements ResourcePackDetails {

    @Unique
    private static final ComponentMapper COMPONENT_MAPPER = Laby.references().componentMapper();

    @Unique
    private Icon labymod4$icon = null;

    @Shadow
    @Final
    private atb b;

    @Shadow
    @Final
    private a d;

    @Shadow
    @Final
    private atd e;

    @Shadow
    @Final
    private c c;

    @Shadow
    @NotNull
    public abstract String shadow$g();

    @Shadow
    public abstract atc f();

    @Shadow
    public abstract boolean j();

    @Shadow
    public abstract aub l();

    @Intrinsic
    @NotNull
    public String labyMod$getId() {
        return shadow$g();
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
        return COMPONENT_MAPPER.fromMinecraftComponent(this.b.b());
    }

    @Intrinsic
    @NotNull
    public Component labyMod$getDescription() {
        return COMPONENT_MAPPER.fromMinecraftComponent(this.d.a());
    }

    @Intrinsic
    public boolean labyMod$isPositionFixed() {
        return j();
    }

    @Intrinsic
    public boolean labyMod$isRequired() {
        return this.e.a();
    }

    @Intrinsic
    @NotNull
    public ResourcePackDetails.DefaultPosition labyMod$getDefaultPosition() {
        return this.e.b() == b.a ? ResourcePackDetails.DefaultPosition.TOP : ResourcePackDetails.DefaultPosition.BOTTOM;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_4.mixins.client.resources.pack.MixinPack$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/resources/pack/MixinPack$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility = new int[aty.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[aty.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[aty.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[aty.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Intrinsic
    @NotNull
    public ResourcePackDetails.Compatibility labyMod$getCompatibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[this.d.b().ordinal()]) {
            case 1:
                return ResourcePackDetails.Compatibility.TOO_OLD;
            case 2:
                return ResourcePackDetails.Compatibility.TOO_NEW;
            case 3:
                return ResourcePackDetails.Compatibility.COMPATIBLE;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Intrinsic
    public boolean labyMod$isUserPack() {
        return l() == aub.b;
    }

    @Intrinsic
    @Nullable
    public File labyMod$getFile() {
        asz aszVar = this.c;
        if (aszVar instanceof asz) {
            asz packResources = aszVar;
            b zipFileAccess = packResources.d;
            return zipFileAccess.a;
        }
        a aVar = this.c;
        if (aVar instanceof a) {
            a supplier = aVar;
            return supplier.a;
        }
        atf atfVar = this.c;
        if (atfVar instanceof atf) {
            atf pathPack = atfVar;
            return pathPack.e.toFile();
        }
        a aVar2 = this.c;
        if (aVar2 instanceof a) {
            a resourcesSupplier = aVar2;
            return resourcesSupplier.a.toFile();
        }
        return null;
    }

    @Unique
    @NotNull
    private Icon labymod4$loadPackIcon() {
        CompletableResourceLocation completable = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                atc resources = f();
                try {
                    auh<InputStream> rootResource = resources.a(new String[]{"pack.png"});
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
