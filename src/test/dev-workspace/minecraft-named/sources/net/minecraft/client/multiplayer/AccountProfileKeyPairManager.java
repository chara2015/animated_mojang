package net.minecraft.client.multiplayer;

import com.google.common.base.Strings;
import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.minecraft.InsecurePublicKeyException;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.response.KeyPairResponse;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.PublicKey;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.SharedConstants;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.ProfileKeyPair;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/AccountProfileKeyPairManager.class */
public class AccountProfileKeyPairManager implements ProfileKeyPairManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Duration MINIMUM_PROFILE_KEY_REFRESH_INTERVAL = Duration.ofHours(1);
    private static final Path PROFILE_KEY_PAIR_DIR = Path.of("profilekeys", new String[0]);
    private final UserApiService userApiService;
    private final Path profileKeyPairPath;
    private CompletableFuture<Optional<ProfileKeyPair>> keyPair = CompletableFuture.completedFuture(Optional.empty());
    private Instant nextProfileKeyRefreshTime = Instant.EPOCH;

    public AccountProfileKeyPairManager(UserApiService $$0, UUID $$1, Path $$2) {
        this.userApiService = $$0;
        this.profileKeyPairPath = $$2.resolve(PROFILE_KEY_PAIR_DIR).resolve(String.valueOf($$1) + ".json");
    }

    @Override // net.minecraft.client.multiplayer.ProfileKeyPairManager
    public CompletableFuture<Optional<ProfileKeyPair>> prepareKeyPair() {
        this.nextProfileKeyRefreshTime = Instant.now().plus((TemporalAmount) MINIMUM_PROFILE_KEY_REFRESH_INTERVAL);
        this.keyPair = this.keyPair.thenCompose(this::readOrFetchProfileKeyPair);
        return this.keyPair;
    }

    @Override // net.minecraft.client.multiplayer.ProfileKeyPairManager
    public boolean shouldRefreshKeyPair() {
        if (this.keyPair.isDone() && Instant.now().isAfter(this.nextProfileKeyRefreshTime)) {
            return ((Boolean) this.keyPair.join().map((v0) -> {
                return v0.dueRefresh();
            }).orElse(true)).booleanValue();
        }
        return false;
    }

    private CompletableFuture<Optional<ProfileKeyPair>> readOrFetchProfileKeyPair(Optional<ProfileKeyPair> $$0) {
        return CompletableFuture.supplyAsync(() -> {
            if ($$0.isPresent() && !((ProfileKeyPair) $$0.get()).dueRefresh()) {
                if (!SharedConstants.IS_RUNNING_IN_IDE) {
                    writeProfileKeyPair(null);
                }
                return $$0;
            }
            try {
                ProfileKeyPair $$1 = fetchProfileKeyPair(this.userApiService);
                writeProfileKeyPair($$1);
                return Optional.ofNullable($$1);
            } catch (IOException | CryptException | MinecraftClientException $$2) {
                LOGGER.error("Failed to retrieve profile key pair", $$2);
                writeProfileKeyPair(null);
                return $$0;
            }
        }, Util.nonCriticalIoPool());
    }

    private Optional<ProfileKeyPair> readProfileKeyPair() {
        if (Files.notExists(this.profileKeyPairPath, new LinkOption[0])) {
            return Optional.empty();
        }
        try {
            BufferedReader $$0 = Files.newBufferedReader(this.profileKeyPairPath);
            try {
                Optional<ProfileKeyPair> optionalResult = ProfileKeyPair.CODEC.parse(JsonOps.INSTANCE, StrictJsonParser.parse($$0)).result();
                if ($$0 != null) {
                    $$0.close();
                }
                return optionalResult;
            } finally {
            }
        } catch (Exception $$1) {
            LOGGER.error("Failed to read profile key pair file {}", this.profileKeyPairPath, $$1);
            return Optional.empty();
        }
    }

    private void writeProfileKeyPair(ProfileKeyPair $$0) {
        try {
            Files.deleteIfExists(this.profileKeyPairPath);
        } catch (IOException $$1) {
            LOGGER.error("Failed to delete profile key pair file {}", this.profileKeyPairPath, $$1);
        }
        if ($$0 == null || !SharedConstants.IS_RUNNING_IN_IDE) {
            return;
        }
        ProfileKeyPair.CODEC.encodeStart(JsonOps.INSTANCE, $$0).ifSuccess($$02 -> {
            try {
                Files.createDirectories(this.profileKeyPairPath.getParent(), new FileAttribute[0]);
                Files.writeString(this.profileKeyPairPath, $$02.toString(), new OpenOption[0]);
            } catch (Exception $$12) {
                LOGGER.error("Failed to write profile key pair file {}", this.profileKeyPairPath, $$12);
            }
        });
    }

    private ProfileKeyPair fetchProfileKeyPair(UserApiService $$0) throws IOException, CryptException {
        KeyPairResponse $$1 = $$0.getKeyPair();
        if ($$1 != null) {
            ProfilePublicKey.Data $$2 = parsePublicKey($$1);
            return new ProfileKeyPair(Crypt.stringToPemRsaPrivateKey($$1.keyPair().privateKey()), new ProfilePublicKey($$2), Instant.parse($$1.refreshedAfter()));
        }
        return null;
    }

    private static ProfilePublicKey.Data parsePublicKey(KeyPairResponse $$0) throws CryptException {
        KeyPairResponse.KeyPair $$1 = $$0.keyPair();
        if ($$1 == null || Strings.isNullOrEmpty($$1.publicKey()) || $$0.publicKeySignature() == null || $$0.publicKeySignature().array().length == 0) {
            throw new CryptException(new InsecurePublicKeyException.MissingException("Missing public key"));
        }
        try {
            Instant $$2 = Instant.parse($$0.expiresAt());
            PublicKey $$3 = Crypt.stringToRsaPublicKey($$1.publicKey());
            ByteBuffer $$4 = $$0.publicKeySignature();
            return new ProfilePublicKey.Data($$2, $$3, $$4.array());
        } catch (IllegalArgumentException | DateTimeException $$5) {
            throw new CryptException($$5);
        }
    }
}
