package net.labymod.v1_21_11.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({gfj.class})
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
    private hir aI;

    @Shadow
    @Final
    private gfx W;

    @Mutable
    @Shadow
    @Final
    private gxo aF;

    @Shadow
    private hjn bq;

    @Mutable
    @Shadow
    @Final
    private iqw aH;

    @Shadow
    @Final
    private ano aL;
    private YggdrasilAuthenticationService labyMod$authenticationService;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.av = labyMod$createUserApiService();
        this.aI = hir.a(this.av, this.W, this.p.toPath());
        this.aF = new gxo((gfj) this, this.av);
        this.aH = new iqw((gfj) this, this.av, this.W);
        this.bq = hjn.a(hjk.a(), this.av);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.aL.c().fetchProfile(this.W.b(), true);
        }, bhs.j());
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
    private UserApiService labyMod$createUserApiService(gfj instance, YggdrasilAuthenticationService yggdrasilAuthenticationService, gzf gameConfig, Operation<UserApiService> original) {
        this.labyMod$authenticationService = yggdrasilAuthenticationService;
        return (UserApiService) original.call(new Object[]{instance, yggdrasilAuthenticationService, gameConfig});
    }

    private UserApiService labyMod$createUserApiService() {
        return this.labyMod$authenticationService.createUserApiService(this.W.d());
    }
}
