package net.minecraft.client.multiplayer;

import com.mojang.authlib.minecraft.UserApiService;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.User;
import net.minecraft.world.entity.player.ProfileKeyPair;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/ProfileKeyPairManager.class */
public interface ProfileKeyPairManager {
    public static final ProfileKeyPairManager EMPTY_KEY_MANAGER = new ProfileKeyPairManager() { // from class: net.minecraft.client.multiplayer.ProfileKeyPairManager.1
        @Override // net.minecraft.client.multiplayer.ProfileKeyPairManager
        public CompletableFuture<Optional<ProfileKeyPair>> prepareKeyPair() {
            return CompletableFuture.completedFuture(Optional.empty());
        }

        @Override // net.minecraft.client.multiplayer.ProfileKeyPairManager
        public boolean shouldRefreshKeyPair() {
            return false;
        }
    };

    CompletableFuture<Optional<ProfileKeyPair>> prepareKeyPair();

    boolean shouldRefreshKeyPair();

    static ProfileKeyPairManager create(UserApiService $$0, User $$1, Path $$2) {
        return new AccountProfileKeyPairManager($$0, $$1.getProfileId(), $$2);
    }
}
