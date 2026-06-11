package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.server.notifications.NotificationService;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Util;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/StoredUserList.class */
public abstract class StoredUserList<K, V extends StoredUserEntry<K>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final File file;
    private final Map<String, V> map = Maps.newHashMap();
    protected final NotificationService notificationService;

    protected abstract StoredUserEntry<K> createEntry(JsonObject jsonObject);

    public StoredUserList(File $$0, NotificationService $$1) {
        this.file = $$0;
        this.notificationService = $$1;
    }

    public File getFile() {
        return this.file;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean add(V $$0) {
        String $$1 = getKeyForUser($$0.getUser());
        V $$2 = this.map.get($$1);
        if ($$0.equals($$2)) {
            return false;
        }
        this.map.put($$1, $$0);
        try {
            save();
            return true;
        } catch (IOException $$3) {
            LOGGER.warn("Could not save the list after adding a user.", $$3);
            return true;
        }
    }

    public V get(K $$0) {
        removeExpired();
        return this.map.get(getKeyForUser($$0));
    }

    public boolean remove(K $$0) {
        V $$1 = this.map.remove(getKeyForUser($$0));
        if ($$1 == null) {
            return false;
        }
        try {
            save();
            return true;
        } catch (IOException $$2) {
            LOGGER.warn("Could not save the list after removing a user.", $$2);
            return true;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean remove(StoredUserEntry<K> $$0) {
        return remove(Objects.requireNonNull($$0.getUser()));
    }

    public void clear() {
        this.map.clear();
        try {
            save();
        } catch (IOException $$0) {
            LOGGER.warn("Could not save the list after removing a user.", $$0);
        }
    }

    public String[] getUserList() {
        return (String[]) this.map.keySet().toArray(new String[0]);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    protected String getKeyForUser(K $$0) {
        return $$0.toString();
    }

    protected boolean contains(K $$0) {
        return this.map.containsKey(getKeyForUser($$0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void removeExpired() {
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        for (V $$1 : this.map.values()) {
            if ($$1.hasExpired()) {
                arrayListNewArrayList.add($$1.getUser());
            }
        }
        Iterator it = arrayListNewArrayList.iterator();
        while (it.hasNext()) {
            this.map.remove(getKeyForUser(it.next()));
        }
    }

    public Collection<V> getEntries() {
        return this.map.values();
    }

    public void save() throws IOException {
        JsonArray $$0 = new JsonArray();
        Stream<R> map = this.map.values().stream().map($$02 -> {
            JsonObject jsonObject = new JsonObject();
            Objects.requireNonNull($$02);
            return (JsonObject) Util.make(jsonObject, $$02::serialize);
        });
        Objects.requireNonNull($$0);
        map.forEach((v1) -> {
            r1.add(v1);
        });
        BufferedWriter $$1 = Files.newWriter(this.file, StandardCharsets.UTF_8);
        try {
            GSON.toJson($$0, GSON.newJsonWriter($$1));
            if ($$1 != null) {
                $$1.close();
            }
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
    }

    public void load() throws IOException {
        if (!this.file.exists()) {
            return;
        }
        BufferedReader bufferedReaderNewReader = Files.newReader(this.file, StandardCharsets.UTF_8);
        try {
            this.map.clear();
            JsonArray jsonArray = (JsonArray) GSON.fromJson(bufferedReaderNewReader, JsonArray.class);
            if (jsonArray != null) {
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    StoredUserEntry<K> storedUserEntryCreateEntry = createEntry(GsonHelper.convertToJsonObject((JsonElement) it.next(), "entry"));
                    if (storedUserEntryCreateEntry.getUser() != null) {
                        this.map.put(getKeyForUser(storedUserEntryCreateEntry.getUser()), (V) storedUserEntryCreateEntry);
                    }
                }
                if (bufferedReaderNewReader != null) {
                    bufferedReaderNewReader.close();
                    return;
                }
                return;
            }
            if (bufferedReaderNewReader != null) {
                bufferedReaderNewReader.close();
            }
        } catch (Throwable th) {
            if (bufferedReaderNewReader != null) {
                try {
                    bufferedReaderNewReader.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
