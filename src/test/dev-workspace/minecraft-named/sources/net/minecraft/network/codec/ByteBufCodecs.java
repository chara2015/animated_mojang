package net.minecraft.network.codec;

import com.google.common.collect.ImmutableMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IdMap;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.Utf8String;
import net.minecraft.network.VarInt;
import net.minecraft.network.VarLong;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.Mth;
import org.joml.Quaternionfc;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/ByteBufCodecs.class */
public interface ByteBufCodecs {
    public static final int MAX_INITIAL_COLLECTION_SIZE = 65536;
    public static final StreamCodec<ByteBuf, Boolean> BOOL = new StreamCodec<ByteBuf, Boolean>() { // from class: net.minecraft.network.codec.ByteBufCodecs.1
        @Override // net.minecraft.network.codec.StreamDecoder
        public Boolean decode(ByteBuf $$0) {
            return Boolean.valueOf($$0.readBoolean());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Boolean $$1) {
            $$0.writeBoolean($$1.booleanValue());
        }
    };
    public static final StreamCodec<ByteBuf, Byte> BYTE = new StreamCodec<ByteBuf, Byte>() { // from class: net.minecraft.network.codec.ByteBufCodecs.2
        @Override // net.minecraft.network.codec.StreamDecoder
        public Byte decode(ByteBuf $$0) {
            return Byte.valueOf($$0.readByte());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Byte $$1) {
            $$0.writeByte($$1.byteValue());
        }
    };
    public static final StreamCodec<ByteBuf, Float> ROTATION_BYTE = BYTE.map((v0) -> {
        return Mth.unpackDegrees(v0);
    }, (v0) -> {
        return Mth.packDegrees(v0);
    });
    public static final StreamCodec<ByteBuf, Short> SHORT = new StreamCodec<ByteBuf, Short>() { // from class: net.minecraft.network.codec.ByteBufCodecs.3
        @Override // net.minecraft.network.codec.StreamDecoder
        public Short decode(ByteBuf $$0) {
            return Short.valueOf($$0.readShort());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Short $$1) {
            $$0.writeShort($$1.shortValue());
        }
    };
    public static final StreamCodec<ByteBuf, Integer> UNSIGNED_SHORT = new StreamCodec<ByteBuf, Integer>() { // from class: net.minecraft.network.codec.ByteBufCodecs.4
        @Override // net.minecraft.network.codec.StreamDecoder
        public Integer decode(ByteBuf $$0) {
            return Integer.valueOf($$0.readUnsignedShort());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Integer $$1) {
            $$0.writeShort($$1.intValue());
        }
    };
    public static final StreamCodec<ByteBuf, Integer> INT = new StreamCodec<ByteBuf, Integer>() { // from class: net.minecraft.network.codec.ByteBufCodecs.5
        @Override // net.minecraft.network.codec.StreamDecoder
        public Integer decode(ByteBuf $$0) {
            return Integer.valueOf($$0.readInt());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Integer $$1) {
            $$0.writeInt($$1.intValue());
        }
    };
    public static final StreamCodec<ByteBuf, Integer> VAR_INT = new StreamCodec<ByteBuf, Integer>() { // from class: net.minecraft.network.codec.ByteBufCodecs.6
        @Override // net.minecraft.network.codec.StreamDecoder
        public Integer decode(ByteBuf $$0) {
            return Integer.valueOf(VarInt.read($$0));
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Integer $$1) {
            VarInt.write($$0, $$1.intValue());
        }
    };
    public static final StreamCodec<ByteBuf, OptionalInt> OPTIONAL_VAR_INT = VAR_INT.map($$0 -> {
        return $$0.intValue() == 0 ? OptionalInt.empty() : OptionalInt.of($$0.intValue() - 1);
    }, $$02 -> {
        return Integer.valueOf($$02.isPresent() ? $$02.getAsInt() + 1 : 0);
    });
    public static final StreamCodec<ByteBuf, Long> LONG = new StreamCodec<ByteBuf, Long>() { // from class: net.minecraft.network.codec.ByteBufCodecs.7
        @Override // net.minecraft.network.codec.StreamDecoder
        public Long decode(ByteBuf $$0) {
            return Long.valueOf($$0.readLong());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Long $$1) {
            $$0.writeLong($$1.longValue());
        }
    };
    public static final StreamCodec<ByteBuf, Long> VAR_LONG = new StreamCodec<ByteBuf, Long>() { // from class: net.minecraft.network.codec.ByteBufCodecs.8
        @Override // net.minecraft.network.codec.StreamDecoder
        public Long decode(ByteBuf $$0) {
            return Long.valueOf(VarLong.read($$0));
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Long $$1) {
            VarLong.write($$0, $$1.longValue());
        }
    };
    public static final StreamCodec<ByteBuf, Float> FLOAT = new StreamCodec<ByteBuf, Float>() { // from class: net.minecraft.network.codec.ByteBufCodecs.9
        @Override // net.minecraft.network.codec.StreamDecoder
        public Float decode(ByteBuf $$0) {
            return Float.valueOf($$0.readFloat());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Float $$1) {
            $$0.writeFloat($$1.floatValue());
        }
    };
    public static final StreamCodec<ByteBuf, Double> DOUBLE = new StreamCodec<ByteBuf, Double>() { // from class: net.minecraft.network.codec.ByteBufCodecs.10
        @Override // net.minecraft.network.codec.StreamDecoder
        public Double decode(ByteBuf $$0) {
            return Double.valueOf($$0.readDouble());
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Double $$1) {
            $$0.writeDouble($$1.doubleValue());
        }
    };
    public static final StreamCodec<ByteBuf, byte[]> BYTE_ARRAY = new StreamCodec<ByteBuf, byte[]>() { // from class: net.minecraft.network.codec.ByteBufCodecs.12
        @Override // net.minecraft.network.codec.StreamDecoder
        public byte[] decode(ByteBuf $$0) {
            return FriendlyByteBuf.readByteArray($$0);
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, byte[] $$1) {
            FriendlyByteBuf.writeByteArray($$0, $$1);
        }
    };
    public static final StreamCodec<ByteBuf, long[]> LONG_ARRAY = new StreamCodec<ByteBuf, long[]>() { // from class: net.minecraft.network.codec.ByteBufCodecs.13
        @Override // net.minecraft.network.codec.StreamDecoder
        public long[] decode(ByteBuf $$0) {
            return FriendlyByteBuf.readLongArray($$0);
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, long[] $$1) {
            FriendlyByteBuf.writeLongArray($$0, $$1);
        }
    };
    public static final StreamCodec<ByteBuf, String> STRING_UTF8 = stringUtf8(32767);
    public static final StreamCodec<ByteBuf, Tag> TAG = tagCodec(NbtAccounter::defaultQuota);
    public static final StreamCodec<ByteBuf, Tag> TRUSTED_TAG = tagCodec(NbtAccounter::unlimitedHeap);
    public static final StreamCodec<ByteBuf, CompoundTag> COMPOUND_TAG = compoundTagCodec(NbtAccounter::defaultQuota);
    public static final StreamCodec<ByteBuf, CompoundTag> TRUSTED_COMPOUND_TAG = compoundTagCodec(NbtAccounter::unlimitedHeap);
    public static final StreamCodec<ByteBuf, Optional<CompoundTag>> OPTIONAL_COMPOUND_TAG = new StreamCodec<ByteBuf, Optional<CompoundTag>>() { // from class: net.minecraft.network.codec.ByteBufCodecs.19
        @Override // net.minecraft.network.codec.StreamDecoder
        public Optional<CompoundTag> decode(ByteBuf $$0) {
            return Optional.ofNullable(FriendlyByteBuf.readNbt($$0));
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Optional<CompoundTag> $$1) throws EncoderException {
            FriendlyByteBuf.writeNbt($$0, $$1.orElse(null));
        }
    };
    public static final StreamCodec<ByteBuf, Vector3fc> VECTOR3F = new StreamCodec<ByteBuf, Vector3fc>() { // from class: net.minecraft.network.codec.ByteBufCodecs.20
        @Override // net.minecraft.network.codec.StreamDecoder
        public Vector3fc decode(ByteBuf $$0) {
            return FriendlyByteBuf.readVector3f($$0);
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Vector3fc $$1) {
            FriendlyByteBuf.writeVector3f($$0, $$1);
        }
    };
    public static final StreamCodec<ByteBuf, Quaternionfc> QUATERNIONF = new StreamCodec<ByteBuf, Quaternionfc>() { // from class: net.minecraft.network.codec.ByteBufCodecs.21
        @Override // net.minecraft.network.codec.StreamDecoder
        public Quaternionfc decode(ByteBuf $$0) {
            return FriendlyByteBuf.readQuaternion($$0);
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Quaternionfc $$1) {
            FriendlyByteBuf.writeQuaternion($$0, $$1);
        }
    };
    public static final StreamCodec<ByteBuf, Integer> CONTAINER_ID = new StreamCodec<ByteBuf, Integer>() { // from class: net.minecraft.network.codec.ByteBufCodecs.22
        @Override // net.minecraft.network.codec.StreamDecoder
        public Integer decode(ByteBuf $$0) {
            return Integer.valueOf(FriendlyByteBuf.readContainerId($$0));
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Integer $$1) {
            FriendlyByteBuf.writeContainerId($$0, $$1.intValue());
        }
    };
    public static final StreamCodec<ByteBuf, PropertyMap> GAME_PROFILE_PROPERTIES = new StreamCodec<ByteBuf, PropertyMap>() { // from class: net.minecraft.network.codec.ByteBufCodecs.32
        /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
        @Override // net.minecraft.network.codec.StreamDecoder
        public PropertyMap decode(ByteBuf $$0) throws DecoderException {
            int $$1 = ByteBufCodecs.readCount($$0, 16);
            ImmutableMultimap.Builder<String, Property> $$2 = ImmutableMultimap.builder();
            for (int $$3 = 0; $$3 < $$1; $$3++) {
                String $$4 = Utf8String.read($$0, 64);
                String $$5 = Utf8String.read($$0, 32767);
                String $$6 = (String) FriendlyByteBuf.readNullable($$0, $$02 -> {
                    return Utf8String.read($$02, 1024);
                });
                Property $$7 = new Property($$4, $$5, $$6);
                $$2.put($$7.name(), $$7);
            }
            return new PropertyMap($$2.build());
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, PropertyMap $$1) throws EncoderException {
            ByteBufCodecs.writeCount($$0, $$1.size(), 16);
            for (Property $$2 : $$1.values()) {
                Utf8String.write($$0, $$2.name(), 64);
                Utf8String.write($$0, $$2.value(), 32767);
                FriendlyByteBuf.writeNullable($$0, $$2.signature(), ($$02, $$12) -> {
                    Utf8String.write($$02, $$12, 1024);
                });
            }
        }
    };
    public static final StreamCodec<ByteBuf, String> PLAYER_NAME = stringUtf8(16);
    public static final StreamCodec<ByteBuf, GameProfile> GAME_PROFILE = StreamCodec.composite(UUIDUtil.STREAM_CODEC, (v0) -> {
        return v0.id();
    }, PLAYER_NAME, (v0) -> {
        return v0.name();
    }, GAME_PROFILE_PROPERTIES, (v0) -> {
        return v0.properties();
    }, GameProfile::new);
    public static final StreamCodec<ByteBuf, Integer> RGB_COLOR = new StreamCodec<ByteBuf, Integer>() { // from class: net.minecraft.network.codec.ByteBufCodecs.33
        @Override // net.minecraft.network.codec.StreamDecoder
        public Integer decode(ByteBuf $$0) {
            return Integer.valueOf(ARGB.color($$0.readByte() & 255, $$0.readByte() & 255, $$0.readByte() & 255));
        }

        @Override // net.minecraft.network.codec.StreamEncoder
        public void encode(ByteBuf $$0, Integer $$1) {
            $$0.writeByte(ARGB.red($$1.intValue()));
            $$0.writeByte(ARGB.green($$1.intValue()));
            $$0.writeByte(ARGB.blue($$1.intValue()));
        }
    };

    static StreamCodec<ByteBuf, byte[]> byteArray(final int $$0) {
        return new StreamCodec<ByteBuf, byte[]>() { // from class: net.minecraft.network.codec.ByteBufCodecs.11
            @Override // net.minecraft.network.codec.StreamDecoder
            public byte[] decode(ByteBuf $$02) {
                return FriendlyByteBuf.readByteArray($$02, $$0);
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf $$02, byte[] $$1) throws EncoderException {
                if ($$1.length > $$0) {
                    throw new EncoderException("ByteArray with size " + $$1.length + " is bigger than allowed " + $$0);
                }
                FriendlyByteBuf.writeByteArray($$02, $$1);
            }
        };
    }

    static StreamCodec<ByteBuf, String> stringUtf8(final int $$0) {
        return new StreamCodec<ByteBuf, String>() { // from class: net.minecraft.network.codec.ByteBufCodecs.14
            @Override // net.minecraft.network.codec.StreamDecoder
            public String decode(ByteBuf $$02) {
                return Utf8String.read($$02, $$0);
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf $$02, String $$1) throws EncoderException {
                Utf8String.write($$02, $$1, $$0);
            }
        };
    }

    static StreamCodec<ByteBuf, Optional<Tag>> optionalTagCodec(final Supplier<NbtAccounter> $$0) {
        return new StreamCodec<ByteBuf, Optional<Tag>>() { // from class: net.minecraft.network.codec.ByteBufCodecs.15
            @Override // net.minecraft.network.codec.StreamDecoder
            public Optional<Tag> decode(ByteBuf $$02) {
                return Optional.ofNullable(FriendlyByteBuf.readNbt($$02, (NbtAccounter) $$0.get()));
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf $$02, Optional<Tag> $$1) throws EncoderException {
                FriendlyByteBuf.writeNbt($$02, $$1.orElse(null));
            }
        };
    }

    static StreamCodec<ByteBuf, Tag> tagCodec(final Supplier<NbtAccounter> $$0) {
        return new StreamCodec<ByteBuf, Tag>() { // from class: net.minecraft.network.codec.ByteBufCodecs.16
            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            @Override // net.minecraft.network.codec.StreamDecoder
            public Tag decode(ByteBuf $$02) throws EncoderException, DecoderException {
                Tag $$1 = FriendlyByteBuf.readNbt($$02, (NbtAccounter) $$0.get());
                if ($$1 == null) {
                    throw new DecoderException("Expected non-null compound tag");
                }
                return $$1;
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf $$02, Tag $$1) throws EncoderException {
                if ($$1 == EndTag.INSTANCE) {
                    throw new EncoderException("Expected non-null compound tag");
                }
                FriendlyByteBuf.writeNbt($$02, $$1);
            }
        };
    }

    static StreamCodec<ByteBuf, CompoundTag> compoundTagCodec(Supplier<NbtAccounter> $$0) {
        return tagCodec($$0).map($$02 -> {
            if ($$02 instanceof CompoundTag) {
                CompoundTag $$1 = (CompoundTag) $$02;
                return $$1;
            }
            throw new DecoderException("Not a compound tag: " + String.valueOf($$02));
        }, $$03 -> {
            return $$03;
        });
    }

    static <T> StreamCodec<ByteBuf, T> fromCodecTrusted(Codec<T> $$0) {
        return fromCodec($$0, (Supplier<NbtAccounter>) NbtAccounter::unlimitedHeap);
    }

    static <T> StreamCodec<ByteBuf, T> fromCodec(Codec<T> $$0) {
        return fromCodec($$0, (Supplier<NbtAccounter>) NbtAccounter::defaultQuota);
    }

    static <T, B extends ByteBuf, V> StreamCodec.CodecOperation<B, T, V> fromCodec(DynamicOps<T> $$0, Codec<V> $$1) {
        return $$2 -> {
            return new StreamCodec<B, V>() { // from class: net.minecraft.network.codec.ByteBufCodecs.17
                /* JADX WARN: Incorrect types in method signature: (TB;)TV; */
                @Override // net.minecraft.network.codec.StreamDecoder
                public Object decode(ByteBuf byteBuf) {
                    V vDecode = $$2.decode(byteBuf);
                    return $$1.parse($$0, vDecode).getOrThrow($$12 -> {
                        return new DecoderException("Failed to decode: " + $$12 + " " + String.valueOf(vDecode));
                    });
                }

                /* JADX WARN: Incorrect types in method signature: (TB;TV;)V */
                @Override // net.minecraft.network.codec.StreamEncoder
                public void encode(ByteBuf byteBuf, Object obj) {
                    $$2.encode(byteBuf, $$1.encodeStart($$0, obj).getOrThrow($$12 -> {
                        return new EncoderException("Failed to encode: " + $$12 + " " + String.valueOf(obj));
                    }));
                }
            };
        };
    }

    static <T> StreamCodec<ByteBuf, T> fromCodec(Codec<T> codec, Supplier<NbtAccounter> supplier) {
        return (StreamCodec<ByteBuf, T>) tagCodec(supplier).apply(fromCodec(NbtOps.INSTANCE, codec));
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, T> fromCodecWithRegistriesTrusted(Codec<T> $$0) {
        return fromCodecWithRegistries($$0, NbtAccounter::unlimitedHeap);
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, T> fromCodecWithRegistries(Codec<T> $$0) {
        return fromCodecWithRegistries($$0, NbtAccounter::defaultQuota);
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, T> fromCodecWithRegistries(final Codec<T> $$0, Supplier<NbtAccounter> $$1) {
        final StreamCodec<ByteBuf, Tag> $$2 = tagCodec($$1);
        return new StreamCodec<RegistryFriendlyByteBuf, T>() { // from class: net.minecraft.network.codec.ByteBufCodecs.18
            @Override // net.minecraft.network.codec.StreamDecoder
            public T decode(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
                Tag tag = (Tag) $$2.decode(registryFriendlyByteBuf);
                return (T) $$0.parse(registryFriendlyByteBuf.registryAccess().createSerializationContext(NbtOps.INSTANCE), tag).getOrThrow($$12 -> {
                    return new DecoderException("Failed to decode: " + $$12 + " " + String.valueOf(tag));
                });
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(RegistryFriendlyByteBuf $$02, T $$12) {
                RegistryOps<Tag> $$22 = $$02.registryAccess().createSerializationContext(NbtOps.INSTANCE);
                Tag $$3 = (Tag) $$0.encodeStart($$22, $$12).getOrThrow($$13 -> {
                    return new EncoderException("Failed to encode: " + $$13 + " " + String.valueOf($$12));
                });
                $$2.encode($$02, $$3);
            }
        };
    }

    static <B extends ByteBuf, V> StreamCodec<B, Optional<V>> optional(final StreamCodec<? super B, V> streamCodec) {
        return (StreamCodec<B, Optional<V>>) new StreamCodec<B, Optional<V>>() { // from class: net.minecraft.network.codec.ByteBufCodecs.23
            /* JADX WARN: Incorrect types in method signature: (TB;)Ljava/util/Optional<TV;>; */
            @Override // net.minecraft.network.codec.StreamDecoder
            public Optional decode(ByteBuf byteBuf) {
                if (byteBuf.readBoolean()) {
                    return Optional.of(streamCodec.decode(byteBuf));
                }
                return Optional.empty();
            }

            /* JADX WARN: Incorrect types in method signature: (TB;Ljava/util/Optional<TV;>;)V */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf byteBuf, Optional optional) {
                if (optional.isPresent()) {
                    byteBuf.writeBoolean(true);
                    streamCodec.encode(byteBuf, optional.get());
                } else {
                    byteBuf.writeBoolean(false);
                }
            }
        };
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
    static int readCount(ByteBuf $$0, int $$1) throws DecoderException {
        int $$2 = VarInt.read($$0);
        if ($$2 > $$1) {
            throw new DecoderException($$2 + " elements exceeded max size of: " + $$1);
        }
        return $$2;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
    static void writeCount(ByteBuf $$0, int $$1, int $$2) throws EncoderException {
        if ($$1 > $$2) {
            throw new EncoderException($$1 + " elements exceeded max size of: " + $$2);
        }
        VarInt.write($$0, $$1);
    }

    static <B extends ByteBuf, V, C extends Collection<V>> StreamCodec<B, C> collection(IntFunction<C> $$0, StreamCodec<? super B, V> $$1) {
        return collection($$0, $$1, Integer.MAX_VALUE);
    }

    static <B extends ByteBuf, V, C extends Collection<V>> StreamCodec<B, C> collection(final IntFunction<C> intFunction, final StreamCodec<? super B, V> streamCodec, final int i) {
        return (StreamCodec<B, C>) new StreamCodec<B, C>() { // from class: net.minecraft.network.codec.ByteBufCodecs.24
            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
            /* JADX WARN: Incorrect return type in method signature: (TB;)TC; */
            @Override // net.minecraft.network.codec.StreamDecoder
            public Collection decode(ByteBuf byteBuf) throws DecoderException {
                int $$1 = ByteBufCodecs.readCount(byteBuf, i);
                Collection collection = (Collection) intFunction.apply(Math.min($$1, ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE));
                for (int $$3 = 0; $$3 < $$1; $$3++) {
                    collection.add(streamCodec.decode(byteBuf));
                }
                return collection;
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            /* JADX WARN: Incorrect types in method signature: (TB;TC;)V */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf byteBuf, Collection collection) throws EncoderException {
                ByteBufCodecs.writeCount(byteBuf, collection.size(), i);
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    streamCodec.encode(byteBuf, it.next());
                }
            }
        };
    }

    static <B extends ByteBuf, V, C extends Collection<V>> StreamCodec.CodecOperation<B, V, C> collection(IntFunction<C> $$0) {
        return $$1 -> {
            return collection($$0, $$1);
        };
    }

    static <B extends ByteBuf, V> StreamCodec.CodecOperation<B, V, List<V>> list() {
        return $$0 -> {
            return collection(ArrayList::new, $$0);
        };
    }

    static <B extends ByteBuf, V> StreamCodec.CodecOperation<B, V, List<V>> list(int $$0) {
        return $$1 -> {
            return collection(ArrayList::new, $$1, $$0);
        };
    }

    static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> map(IntFunction<? extends M> $$0, StreamCodec<? super B, K> $$1, StreamCodec<? super B, V> $$2) {
        return map($$0, $$1, $$2, Integer.MAX_VALUE);
    }

    static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> map(final IntFunction<? extends M> intFunction, final StreamCodec<? super B, K> streamCodec, final StreamCodec<? super B, V> streamCodec2, final int i) {
        return (StreamCodec<B, M>) new StreamCodec<B, M>() { // from class: net.minecraft.network.codec.ByteBufCodecs.25
            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            /* JADX WARN: Incorrect types in method signature: (TB;TM;)V */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf byteBuf, Map map) throws EncoderException {
                ByteBufCodecs.writeCount(byteBuf, map.size(), i);
                StreamCodec streamCodec3 = streamCodec;
                StreamCodec streamCodec4 = streamCodec2;
                map.forEach(($$3, $$4) -> {
                    streamCodec3.encode(byteBuf, $$3);
                    streamCodec4.encode(byteBuf, $$4);
                });
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
            /* JADX WARN: Incorrect return type in method signature: (TB;)TM; */
            @Override // net.minecraft.network.codec.StreamDecoder
            public Map decode(ByteBuf byteBuf) throws DecoderException {
                int $$1 = ByteBufCodecs.readCount(byteBuf, i);
                Map map = (Map) intFunction.apply(Math.min($$1, ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE));
                for (int $$3 = 0; $$3 < $$1; $$3++) {
                    map.put(streamCodec.decode(byteBuf), streamCodec2.decode(byteBuf));
                }
                return map;
            }
        };
    }

    static <B extends ByteBuf, L, R> StreamCodec<B, Either<L, R>> either(final StreamCodec<? super B, L> streamCodec, final StreamCodec<? super B, R> streamCodec2) {
        return (StreamCodec<B, Either<L, R>>) new StreamCodec<B, Either<L, R>>() { // from class: net.minecraft.network.codec.ByteBufCodecs.26
            /* JADX WARN: Incorrect types in method signature: (TB;)Lcom/mojang/datafixers/util/Either<TL;TR;>; */
            @Override // net.minecraft.network.codec.StreamDecoder
            public Either decode(ByteBuf byteBuf) {
                if (byteBuf.readBoolean()) {
                    return Either.left(streamCodec.decode(byteBuf));
                }
                return Either.right(streamCodec2.decode(byteBuf));
            }

            /* JADX WARN: Incorrect types in method signature: (TB;Lcom/mojang/datafixers/util/Either<TL;TR;>;)V */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf byteBuf, Either either) {
                StreamCodec streamCodec3 = streamCodec;
                Either eitherIfLeft = either.ifLeft($$2 -> {
                    byteBuf.writeBoolean(true);
                    streamCodec3.encode(byteBuf, $$2);
                });
                StreamCodec streamCodec4 = streamCodec2;
                eitherIfLeft.ifRight($$22 -> {
                    byteBuf.writeBoolean(false);
                    streamCodec4.encode(byteBuf, $$22);
                });
            }
        };
    }

    static <B extends ByteBuf, V> StreamCodec.CodecOperation<B, V, V> lengthPrefixed(int $$0, BiFunction<B, ByteBuf, B> $$1) {
        return $$2 -> {
            return new StreamCodec<B, V>() { // from class: net.minecraft.network.codec.ByteBufCodecs.27
                /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
                /* JADX WARN: Incorrect types in method signature: (TB;)TV; */
                @Override // net.minecraft.network.codec.StreamDecoder
                public Object decode(ByteBuf byteBuf) throws DecoderException {
                    int $$12 = VarInt.read(byteBuf);
                    if ($$12 > $$0) {
                        throw new DecoderException("Buffer size " + $$12 + " is larger than allowed limit of " + $$0);
                    }
                    int $$2 = byteBuf.readerIndex();
                    ByteBuf byteBuf2 = (ByteBuf) $$1.apply(byteBuf, byteBuf.slice($$2, $$12));
                    byteBuf.readerIndex($$2 + $$12);
                    return $$2.decode(byteBuf2);
                }

                /* JADX WARN: Incorrect types in method signature: (TB;TV;)V */
                @Override // net.minecraft.network.codec.StreamEncoder
                public void encode(ByteBuf byteBuf, Object obj) {
                    ByteBuf byteBuf2 = (ByteBuf) $$1.apply(byteBuf, byteBuf.alloc().buffer());
                    try {
                        $$2.encode(byteBuf2, obj);
                        int $$3 = byteBuf2.readableBytes();
                        if ($$3 > $$0) {
                            throw new EncoderException("Buffer size " + $$3 + " is  larger than allowed limit of " + $$0);
                        }
                        VarInt.write(byteBuf, $$3);
                        byteBuf.writeBytes(byteBuf2);
                        byteBuf2.release();
                    } catch (Throwable th) {
                        byteBuf2.release();
                        throw th;
                    }
                }
            };
        };
    }

    static <V> StreamCodec.CodecOperation<ByteBuf, V, V> lengthPrefixed(int $$0) {
        return lengthPrefixed($$0, ($$02, $$1) -> {
            return $$1;
        });
    }

    static <V> StreamCodec.CodecOperation<RegistryFriendlyByteBuf, V, V> registryFriendlyLengthPrefixed(int $$0) {
        return lengthPrefixed($$0, ($$02, $$1) -> {
            return new RegistryFriendlyByteBuf($$1, $$02.registryAccess());
        });
    }

    static <T> StreamCodec<ByteBuf, T> idMapper(final IntFunction<T> $$0, final ToIntFunction<T> $$1) {
        return new StreamCodec<ByteBuf, T>() { // from class: net.minecraft.network.codec.ByteBufCodecs.28
            @Override // net.minecraft.network.codec.StreamDecoder
            public T decode(ByteBuf byteBuf) {
                return (T) $$0.apply(VarInt.read(byteBuf));
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf $$02, T $$12) {
                int $$2 = $$1.applyAsInt($$12);
                VarInt.write($$02, $$2);
            }
        };
    }

    static <T> StreamCodec<ByteBuf, T> idMapper(IdMap<T> $$0) {
        Objects.requireNonNull($$0);
        IntFunction intFunction = $$0::byIdOrThrow;
        Objects.requireNonNull($$0);
        return idMapper(intFunction, $$0::getIdOrThrow);
    }

    private static <T, R> StreamCodec<RegistryFriendlyByteBuf, R> registry(final ResourceKey<? extends Registry<T>> $$0, final Function<Registry<T>, IdMap<R>> $$1) {
        return new StreamCodec<RegistryFriendlyByteBuf, R>() { // from class: net.minecraft.network.codec.ByteBufCodecs.29
            private IdMap<R> getRegistryOrThrow(RegistryFriendlyByteBuf $$02) {
                return (IdMap) $$1.apply($$02.registryAccess().lookupOrThrow($$0));
            }

            @Override // net.minecraft.network.codec.StreamDecoder
            public R decode(RegistryFriendlyByteBuf $$02) {
                int $$12 = VarInt.read($$02);
                return getRegistryOrThrow($$02).byIdOrThrow($$12);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(RegistryFriendlyByteBuf $$02, R $$12) {
                int $$2 = getRegistryOrThrow($$02).getIdOrThrow($$12);
                VarInt.write($$02, $$2);
            }
        };
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, T> registry(ResourceKey<? extends Registry<T>> $$0) {
        return registry($$0, $$02 -> {
            return $$02;
        });
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, Holder<T>> holderRegistry(ResourceKey<? extends Registry<T>> $$0) {
        return registry($$0, (v0) -> {
            return v0.asHolderIdMap();
        });
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, Holder<T>> holder(final ResourceKey<? extends Registry<T>> $$0, final StreamCodec<? super RegistryFriendlyByteBuf, T> $$1) {
        return new StreamCodec<RegistryFriendlyByteBuf, Holder<T>>() { // from class: net.minecraft.network.codec.ByteBufCodecs.30
            private static final int DIRECT_HOLDER_ID = 0;

            private IdMap<Holder<T>> getRegistryOrThrow(RegistryFriendlyByteBuf $$02) {
                return $$02.registryAccess().lookupOrThrow($$0).asHolderIdMap();
            }

            @Override // net.minecraft.network.codec.StreamDecoder
            public Holder<T> decode(RegistryFriendlyByteBuf $$02) {
                int $$12 = VarInt.read($$02);
                if ($$12 == 0) {
                    return Holder.direct($$1.decode($$02));
                }
                return getRegistryOrThrow($$02).byIdOrThrow($$12 - 1);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(RegistryFriendlyByteBuf $$02, Holder<T> $$12) {
                switch (AnonymousClass35.$SwitchMap$net$minecraft$core$Holder$Kind[$$12.kind().ordinal()]) {
                    case 1:
                        int $$2 = getRegistryOrThrow($$02).getIdOrThrow($$12);
                        VarInt.write($$02, $$2 + 1);
                        break;
                    case 2:
                        VarInt.write($$02, 0);
                        $$1.encode($$02, $$12.value());
                        break;
                }
            }
        };
    }

    /* JADX INFO: renamed from: net.minecraft.network.codec.ByteBufCodecs$35, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/codec/ByteBufCodecs$35.class */
    static /* synthetic */ class AnonymousClass35 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Holder$Kind = new int[Holder.Kind.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$core$Holder$Kind[Holder.Kind.REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$core$Holder$Kind[Holder.Kind.DIRECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static <T> StreamCodec<RegistryFriendlyByteBuf, HolderSet<T>> holderSet(final ResourceKey<? extends Registry<T>> $$0) {
        return new StreamCodec<RegistryFriendlyByteBuf, HolderSet<T>>() { // from class: net.minecraft.network.codec.ByteBufCodecs.31
            private static final int NAMED_SET = -1;
            private final StreamCodec<RegistryFriendlyByteBuf, Holder<T>> holderCodec;

            {
                this.holderCodec = ByteBufCodecs.holderRegistry($$0);
            }

            @Override // net.minecraft.network.codec.StreamDecoder
            public HolderSet<T> decode(RegistryFriendlyByteBuf $$02) {
                int $$1 = VarInt.read($$02) - 1;
                if ($$1 == -1) {
                    Registry<T> $$2 = $$02.registryAccess().lookupOrThrow($$0);
                    return $$2.get(TagKey.create($$0, Identifier.STREAM_CODEC.decode($$02))).orElseThrow();
                }
                List<Holder<T>> $$3 = new ArrayList<>(Math.min($$1, ByteBufCodecs.MAX_INITIAL_COLLECTION_SIZE));
                for (int $$4 = 0; $$4 < $$1; $$4++) {
                    $$3.add(this.holderCodec.decode($$02));
                }
                return HolderSet.direct($$3);
            }

            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(RegistryFriendlyByteBuf $$02, HolderSet<T> $$1) {
                Optional<TagKey<T>> $$2 = $$1.unwrapKey();
                if ($$2.isPresent()) {
                    VarInt.write($$02, 0);
                    Identifier.STREAM_CODEC.encode($$02, $$2.get().location());
                    return;
                }
                VarInt.write($$02, $$1.size() + 1);
                for (T $$3 : $$1) {
                    this.holderCodec.encode($$02, $$3);
                }
            }
        };
    }

    static StreamCodec<ByteBuf, JsonElement> lenientJson(final int $$0) {
        return new StreamCodec<ByteBuf, JsonElement>() { // from class: net.minecraft.network.codec.ByteBufCodecs.34
            private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.DecoderException */
            @Override // net.minecraft.network.codec.StreamDecoder
            public JsonElement decode(ByteBuf $$02) throws DecoderException {
                String $$1 = Utf8String.read($$02, $$0);
                try {
                    return LenientJsonParser.parse($$1);
                } catch (JsonSyntaxException $$2) {
                    throw new DecoderException("Failed to parse JSON", $$2);
                }
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
            @Override // net.minecraft.network.codec.StreamEncoder
            public void encode(ByteBuf $$02, JsonElement $$1) throws EncoderException {
                String $$2 = GSON.toJson($$1);
                Utf8String.write($$02, $$2, $$0);
            }
        };
    }
}
