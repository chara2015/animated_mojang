package net.labymod.v1_21_4.mixins.client;

import com.mojang.authlib.minecraft.MinecraftSessionService;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinMinecraftProfileRefresh.class */
@Mixin({flk.class})
public class MixinMinecraftProfileRefresh implements SessionRefresher {

    @Shadow
    @Final
    private static Logger G;

    @Unique
    private CompletableFuture<ProfileResult> labymod$profileFuture;

    @Shadow
    @Final
    public File q;

    @Mutable
    @Shadow
    @Final
    private UserApiService aA;

    @Mutable
    @Shadow
    @Final
    private ggn aP;

    @Shadow
    @Final
    private YggdrasilAuthenticationService ay;

    @Shadow
    @Final
    private flw Z;

    @Mutable
    @Shadow
    @Final
    private fzc aM;

    @Shadow
    private ghj bv;

    @Mutable
    @Shadow
    @Final
    private hka aO;

    @Shadow
    @Final
    private MinecraftSessionService az;

    @Override // net.labymod.api.client.session.SessionRefresher
    public void refresh() {
        this.aA = labyMod$createUserApiService();
        this.aP = ggn.a(this.aA, this.Z, this.q.toPath());
        this.aM = new fzc((flk) this, this.aA);
        this.aO = new hka((flk) this, this.aA, this.Z);
        this.bv = ghj.a(ghg.a(), this.aA);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> {
            return this.az.fetchProfile(this.Z.b(), true);
        }, af.j());
    }

    @Redirect(method = {"getGameProfile"}, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(CompletableFuture<ProfileResult> profileFuture) {
        ProfileResult result = profileFuture.join();
        UUID profileId = result == null ? null : result.profile().getId();
        UUID currentId = this.Z.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }

    private UserApiService labyMod$createUserApiService() {
        return this.ay.createUserApiService(this.Z.d());
    }
}
