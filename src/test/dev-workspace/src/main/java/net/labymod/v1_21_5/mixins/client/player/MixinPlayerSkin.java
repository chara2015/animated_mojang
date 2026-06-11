package net.labymod.v1_21_5.mixins.client.player;

import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/player/MixinPlayerSkin.class */
@Mixin({hls.class})
public class MixinPlayerSkin implements PlayerSkin {

    @Unique
    private CompletableResourceLocation labymod4$skin;

    @Unique
    private CompletableResourceLocation labymod4$cape;

    @Unique
    private CompletableResourceLocation labymod4$elytra;

    @Unique
    private a labymod4$skinModel;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void init(alr skinTexture, String skinTextureUrl, alr capeTexture, alr elytraTexture, a skinModel, boolean secure, CallbackInfo callback) {
        this.labymod4$skin = new CompletableResourceLocation((ResourceLocation) skinTexture, true);
        this.labymod4$cape = new CompletableResourceLocation((ResourceLocation) capeTexture, true);
        this.labymod4$elytra = new CompletableResourceLocation((ResourceLocation) elytraTexture, true);
        this.labymod4$skinModel = skinModel;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setSkinTexture(@Nullable ResourceLocation skinTexture) {
        ResourceLocation resourceLocation;
        CompletableResourceLocation completableResourceLocation = this.labymod4$skin;
        if (skinTexture == null) {
            resourceLocation = (ResourceLocation) hli.a();
        } else {
            resourceLocation = skinTexture;
        }
        completableResourceLocation.updateCompletable(resourceLocation);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setCapeTexture(@Nullable ResourceLocation capeTexture) {
        this.labymod4$cape.updateCompletable(capeTexture);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setElytraTexture(@Nullable ResourceLocation elytraTexture) {
        this.labymod4$elytra.updateCompletable(elytraTexture);
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    public void setSkinVariant(@NotNull MinecraftServices.SkinVariant variant) {
        a aVar;
        boolean isSlim = variant == MinecraftServices.SkinVariant.SLIM;
        if (isSlim) {
            aVar = a.a;
        } else {
            aVar = a.b;
        }
        this.labymod4$skinModel = aVar;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableSkinTexture() {
        return this.labymod4$skin;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableCapeTexture() {
        return this.labymod4$cape;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.labymod4$elytra;
    }

    @Override // net.labymod.api.client.network.PlayerSkin
    @NotNull
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.labymod4$skinModel == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$resources$PlayerSkin$Model[this.labymod4$skinModel.ordinal()]) {
            case 1:
                break;
        }
        return MinecraftServices.SkinVariant.CLASSIC;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_5.mixins.client.player.MixinPlayerSkin$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/player/MixinPlayerSkin$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$resources$PlayerSkin$Model = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$resources$PlayerSkin$Model[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    @Overwrite
    @NotNull
    public alr a() {
        return this.labymod4$skin.getCompleted();
    }

    @Overwrite
    @Nullable
    public alr c() {
        return this.labymod4$cape.getCompleted();
    }

    @Overwrite
    @Nullable
    public alr d() {
        return this.labymod4$elytra.getCompleted();
    }

    @Overwrite
    @NotNull
    public a e() {
        return this.labymod4$skinModel;
    }
}
