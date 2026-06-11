package net.labymod.v1_21_11.mixins.client.player;

import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.util.CastUtil;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/player/MixinPlayerSkin.class */
@Mixin({PlayerSkin.class})
public class MixinPlayerSkin implements net.labymod.api.client.network.PlayerSkin {

    @Unique
    private CompletableResourceLocation labymod4$skin;

    @Unique
    private CompletableResourceLocation labymod4$cape;

    @Unique
    private CompletableResourceLocation labymod4$elytra;

    @Unique
    private PlayerModelType labymod4$skinModel;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void init(ClientAsset.Texture $$0, ClientAsset.Texture $$1, ClientAsset.Texture $$2, PlayerModelType $$3, boolean $$4, CallbackInfo ci) {
        ResourceLocation resourceLocation;
        ResourceLocation resourceLocation2;
        this.labymod4$skin = new CompletableResourceLocation((ResourceLocation) CastUtil.cast($$0.texturePath()), true);
        if ($$1 == null) {
            resourceLocation = null;
        } else {
            resourceLocation = (ResourceLocation) CastUtil.cast($$1.texturePath());
        }
        this.labymod4$cape = new CompletableResourceLocation(resourceLocation, true);
        if ($$2 == null) {
            resourceLocation2 = null;
        } else {
            resourceLocation2 = (ResourceLocation) CastUtil.cast($$2.texturePath());
        }
        this.labymod4$elytra = new CompletableResourceLocation(resourceLocation2, true);
        this.labymod4$skinModel = $$3;
    }

    public void setSkinTexture(@Nullable ResourceLocation skinTexture) {
        ResourceLocation resourceLocation;
        CompletableResourceLocation completableResourceLocation = this.labymod4$skin;
        if (skinTexture == null) {
            resourceLocation = (ResourceLocation) DefaultPlayerSkin.getDefaultTexture();
        } else {
            resourceLocation = skinTexture;
        }
        completableResourceLocation.updateCompletable(resourceLocation);
    }

    public void setCapeTexture(@Nullable ResourceLocation capeTexture) {
        this.labymod4$cape.updateCompletable(capeTexture);
    }

    public void setElytraTexture(@Nullable ResourceLocation elytraTexture) {
        this.labymod4$elytra.updateCompletable(elytraTexture);
    }

    public void setSkinVariant(@NotNull MinecraftServices.SkinVariant variant) {
        PlayerModelType playerModelType;
        boolean isSlim = variant == MinecraftServices.SkinVariant.SLIM;
        if (isSlim) {
            playerModelType = PlayerModelType.SLIM;
        } else {
            playerModelType = PlayerModelType.WIDE;
        }
        this.labymod4$skinModel = playerModelType;
    }

    @NotNull
    public CompletableResourceLocation getCompletableSkinTexture() {
        return this.labymod4$skin;
    }

    @NotNull
    public CompletableResourceLocation getCompletableCapeTexture() {
        return this.labymod4$cape;
    }

    @NotNull
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.labymod4$elytra;
    }

    @NotNull
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.labymod4$skinModel == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$PlayerModelType[this.labymod4$skinModel.ordinal()]) {
            case 1:
                break;
        }
        return MinecraftServices.SkinVariant.CLASSIC;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.player.MixinPlayerSkin$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/player/MixinPlayerSkin$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$PlayerModelType = new int[PlayerModelType.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$world$entity$player$PlayerModelType[PlayerModelType.SLIM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    @Overwrite
    @NotNull
    public ClientAsset.Texture a() {
        Identifier completed = (Identifier) CastUtil.cast(this.labymod4$skin.getCompleted());
        return new ClientAsset.ResourceTexture(completed, completed);
    }

    @Overwrite
    @Nullable
    public ClientAsset.Texture b() {
        ResourceLocation location = (ResourceLocation) this.labymod4$cape.getCompleted();
        if (location == null) {
            return null;
        }
        Identifier completed = (Identifier) CastUtil.cast(location);
        return new ClientAsset.ResourceTexture(completed, completed);
    }

    @Overwrite
    @Nullable
    public ClientAsset.Texture c() {
        ResourceLocation location = (ResourceLocation) this.labymod4$elytra.getCompleted();
        if (location == null) {
            return null;
        }
        Identifier completed = (Identifier) CastUtil.cast(location);
        return new ClientAsset.ResourceTexture(completed, completed);
    }

    @Overwrite
    @NotNull
    public PlayerModelType d() {
        return this.labymod4$skinModel;
    }
}
