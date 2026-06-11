package net.minecraft.server.players;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import net.minecraft.util.StringUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/CachedUserNameToIdResolver.class */
public class CachedUserNameToIdResolver implements UserNameToIdResolver {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int GAMEPROFILES_MRU_LIMIT = 1000;
    private static final int GAMEPROFILES_EXPIRATION_MONTHS = 1;
    private final GameProfileRepository profileRepository;
    private final File file;
    private boolean resolveOfflineUsers = true;
    private final Map<String, GameProfileInfo> profilesByName = new ConcurrentHashMap();
    private final Map<UUID, GameProfileInfo> profilesByUUID = new ConcurrentHashMap();
    private final Gson gson = new GsonBuilder().create();
    private final AtomicLong operationCount = new AtomicLong();

    public CachedUserNameToIdResolver(GameProfileRepository $$0, File $$1) {
        this.profileRepository = $$0;
        this.file = $$1;
        Lists.reverse(load()).forEach(this::safeAdd);
    }

    private void safeAdd(GameProfileInfo $$0) {
        NameAndId $$1 = $$0.nameAndId();
        $$0.setLastAccess(getNextOperation());
        this.profilesByName.put($$1.name().toLowerCase(Locale.ROOT), $$0);
        this.profilesByUUID.put($$1.id(), $$0);
    }

    private Optional<NameAndId> lookupGameProfile(GameProfileRepository $$0, String $$1) {
        if (!StringUtil.isValidPlayerName($$1)) {
            return createUnknownProfile($$1);
        }
        Optional<NameAndId> $$2 = $$0.findProfileByName($$1).map(NameAndId::new);
        if ($$2.isEmpty()) {
            return createUnknownProfile($$1);
        }
        return $$2;
    }

    private Optional<NameAndId> createUnknownProfile(String $$0) {
        if (this.resolveOfflineUsers) {
            return Optional.of(NameAndId.createOffline($$0));
        }
        return Optional.empty();
    }

    @Override // net.minecraft.server.players.UserNameToIdResolver
    public void resolveOfflineUsers(boolean $$0) {
        this.resolveOfflineUsers = $$0;
    }

    @Override // net.minecraft.server.players.UserNameToIdResolver
    public void add(NameAndId $$0) {
        addInternal($$0);
    }

    private GameProfileInfo addInternal(NameAndId $$0) {
        Calendar $$1 = Calendar.getInstance(TimeZone.getDefault(), Locale.ROOT);
        $$1.setTime(new Date());
        $$1.add(2, 1);
        Date $$2 = $$1.getTime();
        GameProfileInfo $$3 = new GameProfileInfo($$0, $$2);
        safeAdd($$3);
        save();
        return $$3;
    }

    private long getNextOperation() {
        return this.operationCount.incrementAndGet();
    }

    @Override // net.minecraft.server.players.UserNameToIdResolver
    public Optional<NameAndId> get(String $$0) {
        Optional<NameAndId> $$7;
        String $$1 = $$0.toLowerCase(Locale.ROOT);
        GameProfileInfo $$2 = this.profilesByName.get($$1);
        boolean $$3 = false;
        if ($$2 != null && new Date().getTime() >= $$2.expirationDate.getTime()) {
            this.profilesByUUID.remove($$2.nameAndId().id());
            this.profilesByName.remove($$2.nameAndId().name().toLowerCase(Locale.ROOT));
            $$3 = true;
            $$2 = null;
        }
        if ($$2 != null) {
            $$2.setLastAccess(getNextOperation());
            $$7 = Optional.of($$2.nameAndId());
        } else {
            Optional<NameAndId> $$5 = lookupGameProfile(this.profileRepository, $$1);
            if ($$5.isPresent()) {
                $$7 = Optional.of(addInternal($$5.get()).nameAndId());
                $$3 = false;
            } else {
                $$7 = Optional.empty();
            }
        }
        if ($$3) {
            save();
        }
        return $$7;
    }

    @Override // net.minecraft.server.players.UserNameToIdResolver
    public Optional<NameAndId> get(UUID $$0) {
        GameProfileInfo $$1 = this.profilesByUUID.get($$0);
        if ($$1 == null) {
            return Optional.empty();
        }
        $$1.setLastAccess(getNextOperation());
        return Optional.of($$1.nameAndId());
    }

    private static DateFormat createDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
    }

    private List<GameProfileInfo> load() {
        Reader $$1;
        JsonArray $$2;
        List<GameProfileInfo> $$0 = Lists.newArrayList();
        try {
            try {
                $$1 = Files.newReader(this.file, StandardCharsets.UTF_8);
                try {
                    $$2 = (JsonArray) this.gson.fromJson($$1, JsonArray.class);
                } catch (Throwable th) {
                    if ($$1 != null) {
                        try {
                            $$1.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e) {
            }
        } catch (IOException | JsonParseException $$4) {
            LOGGER.warn("Failed to load profile cache {}", this.file, $$4);
        }
        if ($$2 != null) {
            DateFormat $$3 = createDateFormat();
            $$2.forEach($$22 -> {
                Optional<GameProfileInfo> gameProfile = readGameProfile($$22, $$3);
                Objects.requireNonNull($$0);
                gameProfile.ifPresent((v1) -> {
                    r1.add(v1);
                });
            });
            if ($$1 != null) {
                $$1.close();
            }
            return $$0;
        }
        if ($$1 != null) {
            $$1.close();
        }
        return $$0;
    }

    @Override // net.minecraft.server.players.UserNameToIdResolver
    public void save() {
        JsonArray $$0 = new JsonArray();
        DateFormat $$1 = createDateFormat();
        getTopMRUProfiles(1000).forEach($$2 -> {
            $$0.add(writeGameProfile($$2, $$1));
        });
        String $$22 = this.gson.toJson($$0);
        try {
            Writer $$3 = Files.newWriter(this.file, StandardCharsets.UTF_8);
            try {
                $$3.write($$22);
                if ($$3 != null) {
                    $$3.close();
                }
            } finally {
            }
        } catch (IOException e) {
        }
    }

    private Stream<GameProfileInfo> getTopMRUProfiles(int $$0) {
        return ImmutableList.copyOf(this.profilesByUUID.values()).stream().sorted(Comparator.comparing((v0) -> {
            return v0.lastAccess();
        }).reversed()).limit($$0);
    }

    private static JsonElement writeGameProfile(GameProfileInfo $$0, DateFormat $$1) {
        JsonObject $$2 = new JsonObject();
        $$0.nameAndId().appendTo($$2);
        $$2.addProperty("expiresOn", $$1.format($$0.expirationDate()));
        return $$2;
    }

    private static Optional<GameProfileInfo> readGameProfile(JsonElement $$0, DateFormat $$1) {
        JsonObject $$2;
        NameAndId $$3;
        JsonElement $$4;
        if ($$0.isJsonObject() && ($$3 = NameAndId.fromJson(($$2 = $$0.getAsJsonObject()))) != null && ($$4 = $$2.get("expiresOn")) != null) {
            String $$5 = $$4.getAsString();
            try {
                Date $$6 = $$1.parse($$5);
                return Optional.of(new GameProfileInfo($$3, $$6));
            } catch (ParseException $$7) {
                LOGGER.warn("Failed to parse date {}", $$5, $$7);
            }
        }
        return Optional.empty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/CachedUserNameToIdResolver$GameProfileInfo.class */
    static class GameProfileInfo {
        private final NameAndId nameAndId;
        final Date expirationDate;
        private volatile long lastAccess;

        GameProfileInfo(NameAndId $$0, Date $$1) {
            this.nameAndId = $$0;
            this.expirationDate = $$1;
        }

        public NameAndId nameAndId() {
            return this.nameAndId;
        }

        public Date expirationDate() {
            return this.expirationDate;
        }

        public void setLastAccess(long $$0) {
            this.lastAccess = $$0;
        }

        public long lastAccess() {
            return this.lastAccess;
        }
    }
}
