package net.minecraft.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.util.UndashedUuid;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/UUIDUtil.class */
public final class UUIDUtil {
    public static final Codec<UUID> CODEC = Codec.INT_STREAM.comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 4).map(UUIDUtil::uuidFromIntArray);
    }, $$02 -> {
        return Arrays.stream(uuidToIntArray($$02));
    });
    public static final Codec<Set<UUID>> CODEC_SET = Codec.list(CODEC).xmap((v0) -> {
        return Sets.newHashSet(v0);
    }, (v0) -> {
        return Lists.newArrayList(v0);
    });
    public static final Codec<Set<UUID>> CODEC_LINKED_SET = Codec.list(CODEC).xmap((v0) -> {
        return Sets.newLinkedHashSet(v0);
    }, (v0) -> {
        return Lists.newArrayList(v0);
    });
    public static final Codec<UUID> STRING_CODEC = Codec.STRING.comapFlatMap($$0 -> {
        try {
            return DataResult.success(UUID.fromString($$0), Lifecycle.stable());
        } catch (IllegalArgumentException $$1) {
            return DataResult.error(() -> {
                return "Invalid UUID " + $$0 + ": " + $$1.getMessage();
            });
        }
    }, (v0) -> {
        return v0.toString();
    });
    public static final Codec<UUID> AUTHLIB_CODEC = Codec.withAlternative(Codec.STRING.comapFlatMap($$0 -> {
        try {
            return DataResult.success(UndashedUuid.fromStringLenient($$0), Lifecycle.stable());
        } catch (IllegalArgumentException $$1) {
            return DataResult.error(() -> {
                return "Invalid UUID " + $$0 + ": " + $$1.getMessage();
            });
        }
    }, UndashedUuid::toString), CODEC);
    public static final Codec<UUID> LENIENT_CODEC = Codec.withAlternative(CODEC, STRING_CODEC);
    public static final StreamCodec<ByteBuf, UUID> STREAM_CODEC = new StreamCodec<ByteBuf, UUID>() { // from class: net.minecraft.core.UUIDUtil.1
        @Override // net.minecraft.network.codec.StreamDecoder
        public UUID decode(ByteBuf $$0) {
            return FriendlyByteBuf.readUUID($$0);
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, UUID $$1) {
            FriendlyByteBuf.writeUUID($$0, $$1);
        }
    };
    public static final int UUID_BYTES = 16;
    private static final String UUID_PREFIX_OFFLINE_PLAYER = "OfflinePlayer:";

    private UUIDUtil() {
    }

    public static UUID uuidFromIntArray(int[] $$0) {
        return new UUID((((long) $$0[0]) << 32) | (((long) $$0[1]) & 4294967295L), (((long) $$0[2]) << 32) | (((long) $$0[3]) & 4294967295L));
    }

    public static int[] uuidToIntArray(UUID $$0) {
        long $$1 = $$0.getMostSignificantBits();
        long $$2 = $$0.getLeastSignificantBits();
        return leastMostToIntArray($$1, $$2);
    }

    private static int[] leastMostToIntArray(long $$0, long $$1) {
        return new int[]{(int) ($$0 >> 32), (int) $$0, (int) ($$1 >> 32), (int) $$1};
    }

    public static byte[] uuidToByteArray(UUID $$0) {
        byte[] $$1 = new byte[16];
        ByteBuffer.wrap($$1).order(ByteOrder.BIG_ENDIAN).putLong($$0.getMostSignificantBits()).putLong($$0.getLeastSignificantBits());
        return $$1;
    }

    public static UUID readUUID(Dynamic<?> $$0) {
        int[] $$1 = $$0.asIntStream().toArray();
        if ($$1.length != 4) {
            throw new IllegalArgumentException("Could not read UUID. Expected int-array of length 4, got " + $$1.length + ".");
        }
        return uuidFromIntArray($$1);
    }

    public static UUID createOfflinePlayerUUID(String $$0) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + $$0).getBytes(StandardCharsets.UTF_8));
    }

    public static GameProfile createOfflineProfile(String $$0) {
        UUID $$1 = createOfflinePlayerUUID($$0);
        return new GameProfile($$1, $$0);
    }
}
