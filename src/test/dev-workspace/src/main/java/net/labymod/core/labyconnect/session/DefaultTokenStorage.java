package net.labymod.core.labyconnect.session;

import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.models.Implements;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.IOUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/DefaultTokenStorage.class */
@Singleton
@Implements(TokenStorage.class)
public class DefaultTokenStorage implements TokenStorage {
    private Storage storage = new Storage();

    @Inject
    public DefaultTokenStorage() {
        try {
            load();
            this.storage.removeExpiredTokens();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.labyconnect.TokenStorage
    public TokenStorage.Token getToken(TokenStorage.Purpose purpose, UUID uniqueId) {
        return this.storage.getToken(purpose, uniqueId);
    }

    public void updateToken(TokenStorage.Purpose purpose, UUID uniqueId, TokenStorage.Token token) {
        this.storage.updateToken(purpose, uniqueId, token);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws IOException {
        if (IOUtil.exists(Constants.Files.TOKENS)) {
            BufferedReader reader = Files.newBufferedReader(Constants.Files.TOKENS);
            try {
                JsonReader jsonReader = new JsonReader(reader);
                try {
                    Storage storage = (Storage) GsonUtil.DEFAULT_GSON.fromJson(jsonReader, Storage.class);
                    if (storage != null) {
                        this.storage = storage;
                    }
                    jsonReader.close();
                    if (reader != null) {
                        reader.close();
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public void save() throws IOException {
        GsonUtil.writeJson(Constants.Files.TOKENS, this.storage);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/DefaultTokenStorage$Storage.class */
    public static class Storage {
        private Map<UUID, Map<TokenStorage.Purpose, TokenStorage.Token>> tokens = new HashMap();

        public TokenStorage.Token getToken(TokenStorage.Purpose purpose, UUID uniqueId) {
            Map<TokenStorage.Purpose, TokenStorage.Token> purposeTokenMap = this.tokens.get(uniqueId);
            if (purposeTokenMap != null) {
                return purposeTokenMap.get(purpose);
            }
            return null;
        }

        public void updateToken(TokenStorage.Purpose purpose, UUID uniqueId, TokenStorage.Token token) {
            Map<TokenStorage.Purpose, TokenStorage.Token> purposeTokenMap = this.tokens.computeIfAbsent(uniqueId, k -> {
                return new HashMap();
            });
            purposeTokenMap.put(purpose, token);
        }

        private void removeExpiredTokens() {
            this.tokens.forEach((uniqueId, purposeTokenMap) -> {
                purposeTokenMap.entrySet().removeIf(entry -> {
                    return ((TokenStorage.Token) entry.getValue()).isExpired();
                });
            });
            this.tokens.values().removeIf((v0) -> {
                return v0.isEmpty();
            });
        }

        public Map<UUID, Map<TokenStorage.Purpose, TokenStorage.Token>> getTokens() {
            return this.tokens;
        }
    }
}
