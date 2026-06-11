package net.labymod.v1_19_4.mixins.client.resources.pack;

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
import net.labymod.v1_19_4.client.resources.pack.PackUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/resources/pack/MixinPack.class */
@Mixin({akq.class})
@Implements({@Interface(iface = ResourcePackDetails.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPack implements ResourcePackDetails {

    @Unique
    private static final ComponentMapper COMPONENT_MAPPER = Laby.references().componentMapper();

    @Unique
    private Icon labymod4$icon = null;

    @Shadow
    @Final
    private tj d;

    @Shadow
    @Final
    private boolean i;

    @Shadow
    @Final
    private b h;

    @Shadow
    @Final
    private tj e;

    @Shadow
    @Final
    private akr f;

    @Shadow
    @NotNull
    public abstract String shadow$f();

    @Shadow
    public abstract ajv e();

    @Shadow
    public abstract boolean h();

    @Shadow
    public abstract akt j();

    @Intrinsic
    @NotNull
    public String labyMod$getId() {
        return shadow$f();
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
        return COMPONENT_MAPPER.fromMinecraftComponent(this.d);
    }

    @Intrinsic
    @NotNull
    public Component labyMod$getDescription() {
        return COMPONENT_MAPPER.fromMinecraftComponent(this.e);
    }

    @Intrinsic
    public boolean labyMod$isPositionFixed() {
        return h();
    }

    @Intrinsic
    public boolean labyMod$isRequired() {
        return this.i;
    }

    @Intrinsic
    @NotNull
    public ResourcePackDetails.DefaultPosition labyMod$getDefaultPosition() {
        return this.h == b.a ? ResourcePackDetails.DefaultPosition.TOP : ResourcePackDetails.DefaultPosition.BOTTOM;
    }

    /* JADX INFO: renamed from: net.labymod.v1_19_4.mixins.client.resources.pack.MixinPack$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/resources/pack/MixinPack$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility = new int[akr.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[akr.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[akr.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[akr.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Intrinsic
    @NotNull
    public ResourcePackDetails.Compatibility labyMod$getCompatibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[this.f.ordinal()]) {
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
        return j() == akt.b;
    }

    @Intrinsic
    @Nullable
    public File labyMod$getFile() {
        ajv resources = e();
        try {
            aju ajuVarUnwrap = PackUtil.unwrap(resources);
            if (ajuVarUnwrap instanceof aju) {
                aju filePackResources = ajuVarUnwrap;
                File file = filePackResources.e;
                if (resources != null) {
                    resources.close();
                }
                return file;
            }
            if (ajuVarUnwrap instanceof akp) {
                akp source = (akp) ajuVarUnwrap;
                File file2 = source.b.toFile();
                if (resources != null) {
                    resources.close();
                }
                return file2;
            }
            if (!(ajuVarUnwrap instanceof ajx)) {
                if (resources != null) {
                    resources.close();
                }
                return null;
            }
            ajx pathResources = (ajx) ajuVarUnwrap;
            File file3 = pathResources.e.toFile();
            if (resources != null) {
                resources.close();
            }
            return file3;
        } catch (Throwable th) {
            if (resources != null) {
                try {
                    resources.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Unique
    @NotNull
    private Icon labymod4$loadPackIcon() {
        CompletableResourceLocation completable = new CompletableResourceLocation(Textures.DEFAULT_SERVER_ICON);
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                ajv resources = e();
                try {
                    akz<InputStream> rootResource = resources.a(new String[]{"pack.png"});
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
