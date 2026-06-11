package net.labymod.v1_12_2.mixins.client.player;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.v1_12_2.client.player.PlayerSkinAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/player/MixinPlayerInfo.class */
@Mixin({bsc.class})
public abstract class MixinPlayerInfo implements PlayerSkin, PlayerSkinAccessor {

    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables = new HashMap();

    @Shadow
    Map<MinecraftProfileTexture.Type, nf> a;

    @Shadow
    private String f;

    @Shadow
    public abstract nf g();

    @Shadow
    public abstract nf h();

    @Shadow
    public abstract nf i();

    private CompletableResourceLocation getCompletableResourceLocation(MinecraftProfileTexture.Type type, Supplier<nf> locationSupplier) {
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        CompletableResourceLocation completable = new CompletableResourceLocation(locationSupplier.get(), this.a.containsKey(type));
        this.labymod$completables.put(type, completable);
        return completable;
    }

    @Unique
    private void updateCompletableResourceLocation(MinecraftProfileTexture.Type type, Supplier<nf> locationSupplier) {
        getCompletableResourceLocation(type, locationSupplier).updateCompletable(locationSupplier.get());
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setSkinTexture(@Nullable ResourceLocation skinTexture) {
        this.a.put(MinecraftProfileTexture.Type.SKIN, (nf) skinTexture);
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::g);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setCapeTexture(@Nullable ResourceLocation capeTexture) {
        this.a.put(MinecraftProfileTexture.Type.CAPE, (nf) capeTexture);
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::h);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setElytraTexture(@Nullable ResourceLocation elytraTexture) {
        this.a.put(MinecraftProfileTexture.Type.ELYTRA, (nf) elytraTexture);
        updateCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::i);
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
        return getCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::i);
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

    @Override // net.labymod.v1_12_2.client.player.PlayerSkinAccessor
    public Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod4$getPendingCompletables() {
        return this.labymod$completables;
    }
}
