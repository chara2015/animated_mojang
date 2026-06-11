package net.labymod.v1_21_10.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.session.SessionRefresher;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({fzz.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Shadow
    @Final
    private static Logger B;

    @Unique
    private CompletableFuture<ProfileResult> labymod$profileFuture;

    @Shadow
    @Final
    public File p;

    @Mutable
    @Shadow
    @Final
    private UserApiService av;

    @Mutable
    @Shadow
    @Final
    private gzz aI;

    @Shadow
    @Final
    private gal W;

    @Mutable
    @Shadow
    @Final
    private gru aF;

    @Shadow
    private hav bq;

    @Mutable
    @Shadow
    @Final
    private ihv aH;

    @Shadow
    @Final
    private ane aL;
    private YggdrasilAuthenticationService labyMod$authenticationService;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.av = labyMod$createUserApiService();
        this.aI = gzz.a(this.av, this.W, this.p.toPath());
        this.aF = new gru((fzz) this, this.av);
        this.aH = new ihv((fzz) this, this.av, this.W);
        this.bq = hav.a(has.a(), this.av);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.aL.c().fetchProfile(this.W.b(), true);
        }, ag.j());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().id();
        UUID currentId = this.W.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    @WrapOperation(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;createUserApiService(Lcom/mojang/authlib/yggdrasil/YggdrasilAuthenticationService;Lnet/minecraft/client/main/GameConfig;)Lcom/mojang/authlib/minecraft/UserApiService;")})
    private UserApiService labyMod$createUserApiService(fzz instance, YggdrasilAuthenticationService yggdrasilAuthenticationService, gtl gameConfig, Operation<UserApiService> original) {
        this.labyMod$authenticationService = yggdrasilAuthenticationService;
        return (UserApiService) original.call(new Object[]{instance, yggdrasilAuthenticationService, gameConfig});
    }

    private UserApiService labyMod$createUserApiService() {
        return this.labyMod$authenticationService.createUserApiService(this.W.d());
    }
}
