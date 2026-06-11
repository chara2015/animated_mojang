package net.labymod.v1_20_1.mixins.client.player;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/player/MixinPlayerInfo.class */
@Mixin({ffb.class})
public abstract class MixinPlayerInfo implements PlayerSkin {

    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables = new HashMap();

    @Shadow
    @Final
    private Map<MinecraftProfileTexture.Type, acq> b;

    @Shadow
    private String f;

    @Shadow
    @NotNull
    public abstract acq shadow$j();

    @Shadow
    @Nullable
    public abstract acq shadow$k();

    @Shadow
    @Nullable
    public abstract acq shadow$l();

    @Inject(method = {"lambda$registerTextures$0"}, at = {@At("RETURN")})
    private void onRegisterTexturesReturn(MinecraftProfileTexture.Type type, acq resourceLocation, MinecraftProfileTexture minecraftProfileTexture, CallbackInfo ci) {
        CompletableResourceLocation completable = this.labymod$completables.get(type);
        if (completable != null) {
            completable.executeCompletableListeners((ResourceLocation) resourceLocation);
        }
    }

    @Unique
    private CompletableResourceLocation getCompletableResourceLocation(MinecraftProfileTexture.Type type, Supplier<acq> locationSupplier) {
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        CompletableResourceLocation completable = new CompletableResourceLocation(locationSupplier.get(), this.b.containsKey(type));
        this.labymod$completables.put(type, completable);
        return completable;
    }

    @Unique
    private void updateCompletableResourceLocation(MinecraftProfileTexture.Type type, Supplier<acq> locationSupplier) {
        getCompletableResourceLocation(type, locationSupplier).updateCompletable(locationSupplier.get());
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setSkinTexture(@Nullable ResourceLocation skinTexture) {
        this.b.put(MinecraftProfileTexture.Type.SKIN, (acq) skinTexture);
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::shadow$j);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setCapeTexture(@Nullable ResourceLocation capeTexture) {
        this.b.put(MinecraftProfileTexture.Type.CAPE, (acq) capeTexture);
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::shadow$k);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setElytraTexture(@Nullable ResourceLocation elytraTexture) {
        this.b.put(MinecraftProfileTexture.Type.ELYTRA, (acq) elytraTexture);
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::shadow$l);
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
        this.f = str;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableSkinTexture() {
        return getCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::shadow$j);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableCapeTexture() {
        return getCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::shadow$k);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableElytraTexture() {
        return getCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::shadow$l);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.f == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        switch (this.f) {
            case "slim":
                break;
        }
        return MinecraftServices.SkinVariant.CLASSIC;
    }
}
