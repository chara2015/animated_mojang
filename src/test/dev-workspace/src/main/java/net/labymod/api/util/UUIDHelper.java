package net.labymod.api.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/UUIDHelper.class */
public final class UUIDHelper {
    private UUIDHelper() {
    }

    @NotNull
    public static UUID createUniqueId(@NotNull String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8));
    }

    public static UUID uuidFromIntArray(int[] array) {
        return new UUID((((long) array[0]) << 32) | (((long) array[1]) & 4294967295L), (((long) array[2]) << 32) | (((long) array[3]) & 4294967295L));
    }

    public static int[] uuidToIntArray(@NotNull UUID uuid) {
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        return new int[]{(int) (most >> 32), (int) most, (int) (least >> 32), (int) least};
    }
}
