package net.labymod.v1_16_5.mixins.client.resources.pack;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/resources/pack/MixinPack.class */
@Mixin({abu.class})
@Implements({@Interface(iface = ResourcePackDetails.class, prefix = "labyMod$", remap = Interface.Remap.NONE)})
public abstract class MixinPack implements ResourcePackDetails {

    @Unique
    private static final ComponentMapper COMPONENT_MAPPER = Laby.references().componentMapper();

    @Unique
    private Icon labymod4$icon = null;

    @Shadow
    @Final
    private nr e;

    @Shadow
    @Final
    private boolean i;

    @Shadow
    @Final
    private b h;

    @Shadow
    @Final
    private nr f;

    @Shadow
    @Final
    private abv g;

    @Shadow
    @Final
    private Supplier<abj> d;

    @Shadow
    @NotNull
    public abstract String shadow$e();

    @Shadow
    public abstract abj d();

    @Shadow
    public abstract boolean g();

    @Shadow
    public abstract abx i();

    @Intrinsic
    @NotNull
    public String labyMod$getId() {
        return shadow$e();
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
        return COMPONENT_MAPPER.fromMinecraftComponent(this.e);
    }

    @Intrinsic
    @NotNull
    public Component labyMod$getDescription() {
        return COMPONENT_MAPPER.fromMinecraftComponent(this.f);
    }

    @Intrinsic
    public boolean labyMod$isPositionFixed() {
        return g();
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

    /* JADX INFO: renamed from: net.labymod.v1_16_5.mixins.client.resources.pack.MixinPack$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/resources/pack/MixinPack$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility = new int[abv.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[abv.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[abv.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[abv.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Intrinsic
    @NotNull
    public ResourcePackDetails.Compatibility labyMod$getCompatibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$server$packs$repository$PackCompatibility[this.g.ordinal()]) {
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
        return i() == abx.a;
    }

    @Intrinsic
    @Nullable
    public File labyMod$getFile() {
        abg abgVar = (abj) this.d.get();
        try {
            if (abgVar instanceof abg) {
                abg r = abgVar;
                File file = r.a;
                if (abgVar != null) {
                    abgVar.close();
                }
                return file;
            }
            if (abgVar != null) {
                abgVar.close();
            }
            return null;
        } catch (Throwable th) {
            if (abgVar != null) {
                try {
                    abgVar.close();
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
                abj resources = d();
                try {
                    InputStream stream = resources.b("pack.png");
                    if (stream != null) {
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
                    if (stream != null) {
                        stream.close();
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
