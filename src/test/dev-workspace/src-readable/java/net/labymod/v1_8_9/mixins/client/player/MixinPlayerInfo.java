package net.labymod.v1_8_9.mixins.client.player;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.v1_8_9.client.player.PlayerSkinAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/player/MixinPlayerInfo.class */
@Mixin({bdc.class})
public abstract class MixinPlayerInfo implements PlayerSkin, PlayerSkinAccessor {

    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables = new HashMap();

    @Unique
    private final CompletableResourceLocation labymod4$Elytra = new CompletableResourceLocation(null, true);

    @Shadow
    private jy e;

    @Shadow
    private jy f;

    @Shadow
    private String g;

    @Shadow
    public abstract jy g();

    @Shadow
    public abstract jy h();

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private CompletableResourceLocation getCompletableResourceLocation(MinecraftProfileTexture.Type type, Supplier<jy> locationSupplier) throws MatchException {
        boolean z;
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        switch (AnonymousClass1.$SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[type.ordinal()]) {
            case 1:
                z = this.e != null;
                break;
            case 2:
                z = this.f != null;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        boolean isLoaded = z;
        CompletableResourceLocation completable = new CompletableResourceLocation(locationSupplier.get(), isLoaded);
        this.labymod$completables.put(type, completable);
        return completable;
    }

    @Unique
    private void updateCompletableResourceLocation(MinecraftProfileTexture.Type type, Supplier<jy> locationSupplier) {
        getCompletableResourceLocation(type, locationSupplier).updateCompletable(locationSupplier.get());
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setSkinTexture(@Nullable ResourceLocation skinTexture) {
        this.e = (jy) skinTexture;
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::g);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setCapeTexture(@Nullable ResourceLocation capeTexture) {
        this.f = (jy) capeTexture;
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::h);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setElytraTexture(@Nullable ResourceLocation elytraTexture) {
    }

    /* JADX INFO: renamed from: net.labymod.v1_8_9.mixins.client.player.MixinPlayerInfo$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/player/MixinPlayerInfo$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type;

        static {
            try {
                $SwitchMap$net$labymod$api$client$session$MinecraftServices$SkinVariant[MinecraftServices.SkinVariant.SLIM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$session$MinecraftServices$SkinVariant[MinecraftServices.SkinVariant.CLASSIC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type = new int[MinecraftProfileTexture.Type.values().length];
            try {
                $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[MinecraftProfileTexture.Type.SKIN.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[MinecraftProfileTexture.Type.CAPE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.network.PlayerSkin
    public void setSkinVariant(@NotNull MinecraftServices.SkinVariant variant) throws MatchException {
        String str;
        switch (variant) {
            case SLIM:
                str = "slim";
                break;
            case CLASSIC:
                str = "default";
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        this.g = str;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableSkinTexture() {
        return getCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::g);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableCapeTexture() {
        return getCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::h);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.labymod4$Elytra;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.g == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        switch (this.g) {
            case "slim":
                break;
        }
        return MinecraftServices.SkinVariant.CLASSIC;
    }

    @Override // net.labymod.v1_8_9.client.player.PlayerSkinAccessor
    public Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod4$getPendingCompletables() {
        return this.labymod$completables;
    }
}
