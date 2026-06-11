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
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ProfileKeyPairManager;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.client.telemetry.ClientTelemetryManager;
import net.minecraft.server.Services;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({Minecraft.class})
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
    private ProfileKeyPairManager aI;

    @Shadow
    @Final
    private User W;

    @Mutable
    @Shadow
    @Final
    private PlayerSocialManager aF;

    @Shadow
    private ReportingContext bq;

    @Mutable
    @Shadow
    @Final
    private ClientTelemetryManager aH;

    @Shadow
    @Final
    private Services aL;
    private YggdrasilAuthenticationService labyMod$authenticationService;

    public void refresh() {
        this.av = labyMod$createUserApiService();
        this.aI = ProfileKeyPairManager.create(this.av, this.W, this.p.toPath());
        this.aF = new PlayerSocialManager((Minecraft) this, this.av);
        this.aH = new ClientTelemetryManager((Minecraft) this, this.av, this.W);
        this.bq = ReportingContext.create(ReportEnvironment.local(), this.av);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.aL.sessionService().fetchProfile(this.W.getProfileId(), true);
        }, Util.nonCriticalIoPool());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().id();
        UUID currentId = this.W.getProfileId();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    @WrapOperation(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;createUserApiService(Lcom/mojang/authlib/yggdrasil/YggdrasilAuthenticationService;Lnet/minecraft/client/main/GameConfig;)Lcom/mojang/authlib/minecraft/UserApiService;")})
    private UserApiService labyMod$createUserApiService(Minecraft instance, YggdrasilAuthenticationService yggdrasilAuthenticationService, GameConfig gameConfig, Operation<UserApiService> original) {
        this.labyMod$authenticationService = yggdrasilAuthenticationService;
        return (UserApiService) original.call(new Object[]{instance, yggdrasilAuthenticationService, gameConfig});
    }

    private UserApiService labyMod$createUserApiService() {
        return this.labyMod$authenticationService.createUserApiService(this.W.getAccessToken());
    }
}
