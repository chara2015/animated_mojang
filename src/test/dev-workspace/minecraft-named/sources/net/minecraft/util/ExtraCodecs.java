package net.minecraft.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.primitives.UnsignedBytes;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JavaOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.BaseMapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Base64;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;
import net.minecraft.core.HolderSet;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.joml.Vector4f;
import org.joml.Vector4fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ExtraCodecs.class */
public class ExtraCodecs {
    private static final String HEX_COLOR_PREFIX = "#";
    public static final int MAX_PROPERTY_NAME_LENGTH = 64;
    public static final int MAX_PROPERTY_VALUE_LENGTH = 32767;
    public static final int MAX_PROPERTY_SIGNATURE_LENGTH = 1024;
    public static final int MAX_PROPERTIES = 16;
    public static final Codec<JsonElement> JSON = converter(JsonOps.INSTANCE);
    public static final Codec<Object> JAVA = converter(JavaOps.INSTANCE);
    public static final Codec<Tag> NBT = converter(NbtOps.INSTANCE);
    public static final Codec<Vector2fc> VECTOR2F = Codec.FLOAT.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 2).map($$0 -> {
            return new Vector2f(((Float) $$0.get(0)).floatValue(), ((Float) $$0.get(1)).floatValue());
        });
    }, $$02 -> {
        return List.of(Float.valueOf($$02.x()), Float.valueOf($$02.y()));
    });
    public static final Codec<Vector3fc> VECTOR3F = Codec.FLOAT.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 3).map($$0 -> {
            return new Vector3f(((Float) $$0.get(0)).floatValue(), ((Float) $$0.get(1)).floatValue(), ((Float) $$0.get(2)).floatValue());
        });
    }, $$02 -> {
        return List.of(Float.valueOf($$02.x()), Float.valueOf($$02.y()), Float.valueOf($$02.z()));
    });
    public static final Codec<Vector3ic> VECTOR3I = Codec.INT.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 3).map($$0 -> {
            return new Vector3i(((Integer) $$0.get(0)).intValue(), ((Integer) $$0.get(1)).intValue(), ((Integer) $$0.get(2)).intValue());
        });
    }, $$02 -> {
        return List.of(Integer.valueOf($$02.x()), Integer.valueOf($$02.y()), Integer.valueOf($$02.z()));
    });
    public static final Codec<Vector4fc> VECTOR4F = Codec.FLOAT.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 4).map($$0 -> {
            return new Vector4f(((Float) $$0.get(0)).floatValue(), ((Float) $$0.get(1)).floatValue(), ((Float) $$0.get(2)).floatValue(), ((Float) $$0.get(3)).floatValue());
        });
    }, $$02 -> {
        return List.of(Float.valueOf($$02.x()), Float.valueOf($$02.y()), Float.valueOf($$02.z()), Float.valueOf($$02.w()));
    });
    public static final Codec<Quaternionfc> QUATERNIONF_COMPONENTS = Codec.FLOAT.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 4).map($$0 -> {
            return new Quaternionf(((Float) $$0.get(0)).floatValue(), ((Float) $$0.get(1)).floatValue(), ((Float) $$0.get(2)).floatValue(), ((Float) $$0.get(3)).floatValue()).normalize();
        });
    }, $$02 -> {
        return List.of(Float.valueOf($$02.x()), Float.valueOf($$02.y()), Float.valueOf($$02.z()), Float.valueOf($$02.w()));
    });
    public static final Codec<AxisAngle4f> AXISANGLE4F = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.FLOAT.fieldOf("angle").forGetter($$0 -> {
            return Float.valueOf($$0.angle);
        }), VECTOR3F.fieldOf("axis").forGetter($$02 -> {
            return new Vector3f($$02.x, $$02.y, $$02.z);
        })).apply($$0, (v1, v2) -> {
            return new AxisAngle4f(v1, v2);
        });
    });
    public static final Codec<Quaternionfc> QUATERNIONF = Codec.withAlternative(QUATERNIONF_COMPONENTS, AXISANGLE4F.xmap(Quaternionf::new, (v1) -> {
        return new AxisAngle4f(v1);
    }));
    public static final Codec<Matrix4fc> MATRIX4F = Codec.FLOAT.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 16).map($$0 -> {
            Matrix4f $$1 = new Matrix4f();
            for (int $$2 = 0; $$2 < $$0.size(); $$2++) {
                $$1.setRowColumn($$2 >> 2, $$2 & 3, ((Float) $$0.get($$2)).floatValue());
            }
            return $$1.determineProperties();
        });
    }, $$02 -> {
        FloatArrayList floatArrayList = new FloatArrayList(16);
        for (int $$2 = 0; $$2 < 16; $$2++) {
            floatArrayList.add($$02.getRowColumn($$2 >> 2, $$2 & 3));
        }
        return floatArrayList;
    });
    public static final Codec<Integer> RGB_COLOR_CODEC = Codec.withAlternative(Codec.INT, VECTOR3F, $$0 -> {
        return Integer.valueOf(ARGB.colorFromFloat(1.0f, $$0.x(), $$0.y(), $$0.z()));
    });
    public static final Codec<Integer> ARGB_COLOR_CODEC = Codec.withAlternative(Codec.INT, VECTOR4F, $$0 -> {
        return Integer.valueOf(ARGB.colorFromFloat($$0.w(), $$0.x(), $$0.y(), $$0.z()));
    });
    public static final Codec<Integer> STRING_RGB_COLOR = Codec.withAlternative(hexColor(6).xmap((v0) -> {
        return ARGB.opaque(v0);
    }, (v0) -> {
        return ARGB.transparent(v0);
    }), RGB_COLOR_CODEC);
    public static final Codec<Integer> STRING_ARGB_COLOR = Codec.withAlternative(hexColor(8), ARGB_COLOR_CODEC);
    public static final Codec<Integer> UNSIGNED_BYTE = Codec.BYTE.flatComapMap((v0) -> {
        return UnsignedBytes.toInt(v0);
    }, $$0 -> {
        if ($$0.intValue() > 255) {
            return DataResult.error(() -> {
                return "Unsigned byte was too large: " + $$0 + " > 255";
            });
        }
        return DataResult.success(Byte.valueOf($$0.byteValue()));
    });
    public static final Codec<Integer> NON_NEGATIVE_INT = intRangeWithMessage(0, Integer.MAX_VALUE, $$0 -> {
        return "Value must be non-negative: " + $$0;
    });
    public static final Codec<Integer> POSITIVE_INT = intRangeWithMessage(1, Integer.MAX_VALUE, $$0 -> {
        return "Value must be positive: " + $$0;
    });
    public static final Codec<Long> NON_NEGATIVE_LONG = longRangeWithMessage(0, DynamicGraphMinFixedPoint.SOURCE, $$0 -> {
        return "Value must be non-negative: " + $$0;
    });
    public static final Codec<Long> POSITIVE_LONG = longRangeWithMessage(1, DynamicGraphMinFixedPoint.SOURCE, $$0 -> {
        return "Value must be positive: " + $$0;
    });
    public static final Codec<Float> NON_NEGATIVE_FLOAT = floatRangeMinInclusiveWithMessage(0.0f, Float.MAX_VALUE, $$0 -> {
        return "Value must be non-negative: " + $$0;
    });
    public static final Codec<Float> POSITIVE_FLOAT = floatRangeMinExclusiveWithMessage(0.0f, Float.MAX_VALUE, $$0 -> {
        return "Value must be positive: " + $$0;
    });
    public static final Codec<Pattern> PATTERN = Codec.STRING.comapFlatMap($$0 -> {
        try {
            return DataResult.success(Pattern.compile($$0));
        } catch (PatternSyntaxException $$1) {
            return DataResult.error(() -> {
                return "Invalid regex pattern '" + $$0 + "': " + $$1.getMessage();
            });
        }
    }, (v0) -> {
        return v0.pattern();
    });
    public static final Codec<Instant> INSTANT_ISO8601 = temporalCodec(DateTimeFormatter.ISO_INSTANT).xmap(Instant::from, Function.identity());
    public static final Codec<byte[]> BASE64_STRING = Codec.STRING.comapFlatMap($$0 -> {
        try {
            return DataResult.success(Base64.getDecoder().decode($$0));
        } catch (IllegalArgumentException e) {
            return DataResult.error(() -> {
                return "Malformed base64 string";
            });
        }
    }, $$02 -> {
        return Base64.getEncoder().encodeToString($$02);
    });
    public static final Codec<String> ESCAPED_STRING = Codec.STRING.comapFlatMap($$0 -> {
        return DataResult.success(StringEscapeUtils.unescapeJava($$0));
    }, StringEscapeUtils::escapeJava);
    public static final Codec<TagOrElementLocation> TAG_OR_ELEMENT_ID = Codec.STRING.comapFlatMap($$0 -> {
        if ($$0.startsWith("#")) {
            return Identifier.read($$0.substring(1)).map($$0 -> {
                return new TagOrElementLocation($$0, true);
            });
        }
        return Identifier.read($$0).map($$02 -> {
            return new TagOrElementLocation($$02, false);
        });
    }, (v0) -> {
        return v0.decoratedId();
    });
    public static final Function<Optional<Long>, OptionalLong> toOptionalLong = $$0 -> {
        return (OptionalLong) $$0.map((v0) -> {
            return OptionalLong.of(v0);
        }).orElseGet(OptionalLong::empty);
    };
    public static final Function<OptionalLong, Optional<Long>> fromOptionalLong = $$0 -> {
        return $$0.isPresent() ? Optional.of(Long.valueOf($$0.getAsLong())) : Optional.empty();
    };
    public static final Codec<BitSet> BIT_SET = Codec.LONG_STREAM.xmap($$0 -> {
        return BitSet.valueOf($$0.toArray());
    }, $$02 -> {
        return Arrays.stream($$02.toLongArray());
    });
    private static final Codec<Property> PROPERTY = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.sizeLimitedString(64).fieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
            return v0.name();
        }), Codec.sizeLimitedString(32767).fieldOf("value").forGetter((v0) -> {
            return v0.value();
        }), Codec.sizeLimitedString(1024).optionalFieldOf("signature").forGetter($$0 -> {
            return Optional.ofNullable($$0.signature());
        })).apply($$0, ($$02, $$1, $$2) -> {
            return new Property($$02, $$1, (String) $$2.orElse(null));
        });
    });
    public static final Codec<PropertyMap> PROPERTY_MAP = Codec.either(Codec.unboundedMap(Codec.STRING, Codec.STRING.listOf()).validate($$0 -> {
        if ($$0.size() > 16) {
            return DataResult.error(() -> {
                return "Cannot have more than 16 properties, but was " + $$0.size();
            });
        }
        return DataResult.success($$0);
    }), PROPERTY.sizeLimitedListOf(16)).xmap($$02 -> {
        ImmutableMultimap.Builder<String, Property> $$1 = ImmutableMultimap.builder();
        $$02.ifLeft($$12 -> {
            $$12.forEach(($$12, $$2) -> {
                Iterator it = $$2.iterator();
                while (it.hasNext()) {
                    String $$3 = (String) it.next();
                    $$1.put($$12, new Property($$12, $$3));
                }
            });
        }).ifRight($$13 -> {
            Iterator it = $$13.iterator();
            while (it.hasNext()) {
                Property $$2 = (Property) it.next();
                $$1.put($$2.name(), $$2);
            }
        });
        return new PropertyMap($$1.build());
    }, $$03 -> {
        return Either.right($$03.values().stream().toList());
    });
    public static final Codec<String> PLAYER_NAME = Codec.string(0, 16).validate($$0 -> {
        if (StringUtil.isValidPlayerName($$0)) {
            return DataResult.success($$0);
        }
        return DataResult.error(() -> {
            return "Player name contained disallowed characters: '" + $$0 + "'";
        });
    });
    public static final Codec<GameProfile> AUTHLIB_GAME_PROFILE = gameProfileCodec(UUIDUtil.AUTHLIB_CODEC).codec();
    public static final MapCodec<GameProfile> STORED_GAME_PROFILE = gameProfileCodec(UUIDUtil.CODEC);
    public static final Codec<String> NON_EMPTY_STRING = Codec.STRING.validate($$0 -> {
        return $$0.isEmpty() ? DataResult.error(() -> {
            return "Expected non-empty string";
        }) : DataResult.success($$0);
    });
    public static final Codec<Integer> CODEPOINT = Codec.STRING.comapFlatMap($$0 -> {
        int[] $$1 = $$0.codePoints().toArray();
        if ($$1.length != 1) {
            return DataResult.error(() -> {
                return "Expected one codepoint, got: " + $$0;
            });
        }
        return DataResult.success(Integer.valueOf($$1[0]));
    }, (v0) -> {
        return Character.toString(v0);
    });
    public static final Codec<String> RESOURCE_PATH_CODEC = Codec.STRING.validate($$0 -> {
        if (!Identifier.isValidPath($$0)) {
            return DataResult.error(() -> {
                return "Invalid string to use as a resource path element: " + $$0;
            });
        }
        return DataResult.success($$0);
    });
    public static final Codec<URI> UNTRUSTED_URI = Codec.STRING.comapFlatMap($$0 -> {
        try {
            return DataResult.success(Util.parseAndValidateUntrustedUri($$0));
        } catch (URISyntaxException $$1) {
            Objects.requireNonNull($$1);
            return DataResult.error($$1::getMessage);
        }
    }, (v0) -> {
        return v0.toString();
    });
    public static final Codec<String> CHAT_STRING = Codec.STRING.validate($$0 -> {
        for (int $$1 = 0; $$1 < $$0.length(); $$1++) {
            char $$2 = $$0.charAt($$1);
            if (!StringUtil.isAllowedChatCharacter($$2)) {
                return DataResult.error(() -> {
                    return "Disallowed chat character: '" + $$2 + "'";
                });
            }
        }
        return DataResult.success($$0);
    });

    public static <T> Codec<T> converter(DynamicOps<T> $$0) {
        return Codec.PASSTHROUGH.xmap($$1 -> {
            return $$1.convert($$0).getValue();
        }, $$12 -> {
            return new Dynamic($$0, $$12);
        });
    }

    private static Codec<Integer> hexColor(int $$0) {
        long $$1 = (1 << ($$0 * 4)) - 1;
        return Codec.STRING.comapFlatMap($$2 -> {
            if (!$$2.startsWith("#")) {
                return DataResult.error(() -> {
                    return "Hex color must begin with #";
                });
            }
            int $$3 = $$2.length() - "#".length();
            if ($$3 != $$0) {
                return DataResult.error(() -> {
                    return "Hex color is wrong size, expected " + $$0 + " digits but got " + $$3;
                });
            }
            try {
                long $$4 = HexFormat.fromHexDigitsToLong($$2, "#".length(), $$2.length());
                if ($$4 < 0 || $$4 > $$1) {
                    return DataResult.error(() -> {
                        return "Color value out of range: " + $$2;
                    });
                }
                return DataResult.success(Integer.valueOf((int) $$4));
            } catch (NumberFormatException e) {
                return DataResult.error(() -> {
                    return "Invalid color value: " + $$2;
                });
            }
        }, $$12 -> {
            return "#" + HexFormat.of().toHexDigits($$12.intValue(), $$0);
        });
    }

    public static <P, I> Codec<I> intervalCodec(Codec<P> $$0, String $$1, String $$2, BiFunction<P, P, DataResult<I>> $$3, Function<I, P> $$4, Function<I, P> $$5) {
        Codec<I> $$6 = Codec.list($$0).comapFlatMap($$12 -> {
            return Util.fixedSize($$12, 2).flatMap($$12 -> {
                return (DataResult) $$3.apply($$12.get(0), $$12.get(1));
            });
        }, $$22 -> {
            return ImmutableList.of($$4.apply($$22), $$5.apply($$22));
        });
        Codec<I> $$7 = RecordCodecBuilder.create($$32 -> {
            return $$32.group($$0.fieldOf($$1).forGetter((v0) -> {
                return v0.getFirst();
            }), $$0.fieldOf($$2).forGetter((v0) -> {
                return v0.getSecond();
            })).apply($$32, Pair::of);
        }).comapFlatMap($$13 -> {
            return (DataResult) $$3.apply($$13.getFirst(), $$13.getSecond());
        }, $$23 -> {
            return Pair.of($$4.apply($$23), $$5.apply($$23));
        });
        Codec<I> $$8 = Codec.withAlternative($$6, $$7);
        return Codec.either($$0, $$8).comapFlatMap($$14 -> {
            return (DataResult) $$14.map($$14 -> {
                return (DataResult) $$3.apply($$14, $$14);
            }, DataResult::success);
        }, $$24 -> {
            Object objApply = $$4.apply($$24);
            if (Objects.equals(objApply, $$5.apply($$24))) {
                return Either.left(objApply);
            }
            return Either.right($$24);
        });
    }

    public static <A> Codec.ResultFunction<A> orElsePartial(final A $$0) {
        return new Codec.ResultFunction<A>() { // from class: net.minecraft.util.ExtraCodecs.1
            public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> $$02, T $$1, DataResult<Pair<A, T>> $$2) {
                MutableObject<String> $$3 = new MutableObject<>();
                Objects.requireNonNull($$3);
                Optional<Pair<A, T>> $$4 = $$2.resultOrPartial((v1) -> {
                    r1.setValue(v1);
                });
                if ($$4.isPresent()) {
                    return $$2;
                }
                return DataResult.error(() -> {
                    return "(" + ((String) $$3.get()) + " -> using default)";
                }, Pair.of($$0, $$1));
            }

            public <T> DataResult<T> coApply(DynamicOps<T> $$02, A $$1, DataResult<T> $$2) {
                return $$2;
            }

            public String toString() {
                return "OrElsePartial[" + String.valueOf($$0) + "]";
            }
        };
    }

    public static <E> Codec<E> idResolverCodec(ToIntFunction<E> $$0, IntFunction<E> $$1, int $$2) {
        return Codec.INT.flatXmap($$12 -> {
            return (DataResult) Optional.ofNullable($$1.apply($$12.intValue())).map(DataResult::success).orElseGet(() -> {
                return DataResult.error(() -> {
                    return "Unknown element id: " + $$12;
                });
            });
        }, $$22 -> {
            int $$3 = $$0.applyAsInt($$22);
            return $$3 == $$2 ? DataResult.error(() -> {
                return "Element with unknown id: " + String.valueOf($$22);
            }) : DataResult.success(Integer.valueOf($$3));
        });
    }

    public static <I, E> Codec<E> idResolverCodec(Codec<I> $$0, Function<I, E> $$1, Function<E, I> $$2) {
        return $$0.flatXmap($$12 -> {
            Object objApply = $$1.apply($$12);
            return objApply == null ? DataResult.error(() -> {
                return "Unknown element id: " + String.valueOf($$12);
            }) : DataResult.success(objApply);
        }, $$13 -> {
            Object objApply = $$2.apply($$13);
            if (objApply == null) {
                return DataResult.error(() -> {
                    return "Element with unknown id: " + String.valueOf($$13);
                });
            }
            return DataResult.success(objApply);
        });
    }

    public static <E> Codec<E> orCompressed(final Codec<E> $$0, final Codec<E> $$1) {
        return new Codec<E>() { // from class: net.minecraft.util.ExtraCodecs.2
            public <T> DataResult<T> encode(E $$02, DynamicOps<T> $$12, T $$2) {
                if ($$12.compressMaps()) {
                    return $$1.encode($$02, $$12, $$2);
                }
                return $$0.encode($$02, $$12, $$2);
            }

            public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> $$02, T $$12) {
                if ($$02.compressMaps()) {
                    return $$1.decode($$02, $$12);
                }
                return $$0.decode($$02, $$12);
            }

            public String toString() {
                return String.valueOf($$0) + " orCompressed " + String.valueOf($$1);
            }
        };
    }

    public static <E> MapCodec<E> orCompressed(final MapCodec<E> $$0, final MapCodec<E> $$1) {
        return new MapCodec<E>() { // from class: net.minecraft.util.ExtraCodecs.3
            public <T> RecordBuilder<T> encode(E $$02, DynamicOps<T> $$12, RecordBuilder<T> $$2) {
                if ($$12.compressMaps()) {
                    return $$1.encode($$02, $$12, $$2);
                }
                return $$0.encode($$02, $$12, $$2);
            }

            public <T> DataResult<E> decode(DynamicOps<T> $$02, MapLike<T> $$12) {
                if ($$02.compressMaps()) {
                    return $$1.decode($$02, $$12);
                }
                return $$0.decode($$02, $$12);
            }

            public <T> Stream<T> keys(DynamicOps<T> $$02) {
                return $$1.keys($$02);
            }

            public String toString() {
                return String.valueOf($$0) + " orCompressed " + String.valueOf($$1);
            }
        };
    }

    public static <E> Codec<E> overrideLifecycle(Codec<E> $$0, final Function<E, Lifecycle> $$1, final Function<E, Lifecycle> $$2) {
        return $$0.mapResult(new Codec.ResultFunction<E>() { // from class: net.minecraft.util.ExtraCodecs.4
            public <T> DataResult<Pair<E, T>> apply(DynamicOps<T> $$02, T $$12, DataResult<Pair<E, T>> $$22) {
                Optional optionalResult = $$22.result();
                Function function = $$1;
                return (DataResult) optionalResult.map($$23 -> {
                    return $$22.setLifecycle((Lifecycle) function.apply($$23.getFirst()));
                }).orElse($$22);
            }

            public <T> DataResult<T> coApply(DynamicOps<T> $$02, E $$12, DataResult<T> $$22) {
                return $$22.setLifecycle((Lifecycle) $$2.apply($$12));
            }

            public String toString() {
                return "WithLifecycle[" + String.valueOf($$1) + " " + String.valueOf($$2) + "]";
            }
        });
    }

    public static <E> Codec<E> overrideLifecycle(Codec<E> $$0, Function<E, Lifecycle> $$1) {
        return overrideLifecycle($$0, $$1, $$1);
    }

    public static <K, V> StrictUnboundedMapCodec<K, V> strictUnboundedMap(Codec<K> $$0, Codec<V> $$1) {
        return new StrictUnboundedMapCodec<>($$0, $$1);
    }

    public static <E> Codec<List<E>> compactListCodec(Codec<E> $$0) {
        return compactListCodec($$0, $$0.listOf());
    }

    public static <E> Codec<List<E>> compactListCodec(Codec<E> $$0, Codec<List<E>> $$1) {
        return Codec.either($$1, $$0).xmap($$02 -> {
            return (List) $$02.map($$02 -> {
                return $$02;
            }, List::of);
        }, $$03 -> {
            return $$03.size() == 1 ? Either.right($$03.getFirst()) : Either.left($$03);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec.class */
    public static final class StrictUnboundedMapCodec<K, V> extends Record implements Codec<Map<K, V>>, BaseMapCodec<K, V> {
        private final Codec<K> keyCodec;
        private final Codec<V> elementCodec;

        public StrictUnboundedMapCodec(Codec<K> $$0, Codec<V> $$1) {
            this.keyCodec = $$0;
            this.elementCodec = $$1;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StrictUnboundedMapCodec.class), StrictUnboundedMapCodec.class, "keyCodec;elementCodec", "FIELD:Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;->keyCodec:Lcom/mojang/serialization/Codec;", "FIELD:Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;->elementCodec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StrictUnboundedMapCodec.class, Object.class), StrictUnboundedMapCodec.class, "keyCodec;elementCodec", "FIELD:Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;->keyCodec:Lcom/mojang/serialization/Codec;", "FIELD:Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;->elementCodec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Codec<K> keyCodec() {
            return this.keyCodec;
        }

        public Codec<V> elementCodec() {
            return this.elementCodec;
        }

        public /* synthetic */ DataResult encode(Object obj, DynamicOps dynamicOps, Object obj2) {
            return encode((Map) obj, (DynamicOps<Object>) dynamicOps, obj2);
        }

        public <T> DataResult<Map<K, V>> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
            ImmutableMap.Builder<K, V> $$2 = ImmutableMap.builder();
            for (T $$3 : $$1.entries().toList()) {
                DataResult<K> $$4 = keyCodec().parse($$0, $$3.getFirst());
                DataResult<V> $$5 = elementCodec().parse($$0, $$3.getSecond());
                DataResult<Pair<K, V>> $$6 = $$4.apply2stable(Pair::of, $$5);
                Optional<DataResult.Error<Pair<K, V>>> $$7 = $$6.error();
                if ($$7.isPresent()) {
                    String $$8 = $$7.get().message();
                    return DataResult.error(() -> {
                        if ($$4.result().isPresent()) {
                            return "Map entry '" + String.valueOf($$4.result().get()) + "' : " + $$8;
                        }
                        return $$8;
                    });
                }
                if ($$6.result().isPresent()) {
                    Pair<K, V> $$9 = (Pair) $$6.result().get();
                    $$2.put($$9.getFirst(), $$9.getSecond());
                } else {
                    return DataResult.error(() -> {
                        return "Empty or invalid map contents are not allowed";
                    });
                }
            }
            return DataResult.success($$2.build());
        }

        public <T> DataResult<Pair<Map<K, V>, T>> decode(DynamicOps<T> $$0, T $$1) {
            return $$0.getMap($$1).setLifecycle(Lifecycle.stable()).flatMap($$12 -> {
                return decode($$0, $$12);
            }).map($$13 -> {
                return Pair.of($$13, $$1);
            });
        }

        public <T> DataResult<T> encode(Map<K, V> $$0, DynamicOps<T> $$1, T $$2) {
            return encode((Map) $$0, (DynamicOps) $$1, $$1.mapBuilder()).build($$2);
        }

        @Override // java.lang.Record
        public String toString() {
            return "StrictUnboundedMapCodec[" + String.valueOf(this.keyCodec) + " -> " + String.valueOf(this.elementCodec) + "]";
        }
    }

    private static Codec<Integer> intRangeWithMessage(int $$0, int $$1, Function<Integer, String> $$2) {
        return Codec.INT.validate($$3 -> {
            if ($$3.compareTo(Integer.valueOf($$0)) >= 0 && $$3.compareTo(Integer.valueOf($$1)) <= 0) {
                return DataResult.success($$3);
            }
            return DataResult.error(() -> {
                return (String) $$2.apply($$3);
            });
        });
    }

    public static Codec<Integer> intRange(int $$0, int $$1) {
        return intRangeWithMessage($$0, $$1, $$2 -> {
            return "Value must be within range [" + $$0 + ";" + $$1 + "]: " + $$2;
        });
    }

    private static Codec<Long> longRangeWithMessage(long $$0, long $$1, Function<Long, String> $$2) {
        return Codec.LONG.validate($$3 -> {
            if ($$3.compareTo(Long.valueOf($$0)) >= 0 && $$3.compareTo(Long.valueOf($$1)) <= 0) {
                return DataResult.success($$3);
            }
            return DataResult.error(() -> {
                return (String) $$2.apply($$3);
            });
        });
    }

    public static Codec<Long> longRange(int $$0, int $$1) {
        return longRangeWithMessage($$0, $$1, $$2 -> {
            return "Value must be within range [" + $$0 + ";" + $$1 + "]: " + $$2;
        });
    }

    private static Codec<Float> floatRangeMinInclusiveWithMessage(float $$0, float $$1, Function<Float, String> $$2) {
        return Codec.FLOAT.validate($$3 -> {
            if ($$3.compareTo(Float.valueOf($$0)) >= 0 && $$3.compareTo(Float.valueOf($$1)) <= 0) {
                return DataResult.success($$3);
            }
            return DataResult.error(() -> {
                return (String) $$2.apply($$3);
            });
        });
    }

    private static Codec<Float> floatRangeMinExclusiveWithMessage(float $$0, float $$1, Function<Float, String> $$2) {
        return Codec.FLOAT.validate($$3 -> {
            if ($$3.compareTo(Float.valueOf($$0)) > 0 && $$3.compareTo(Float.valueOf($$1)) <= 0) {
                return DataResult.success($$3);
            }
            return DataResult.error(() -> {
                return (String) $$2.apply($$3);
            });
        });
    }

    public static Codec<Float> floatRange(float $$0, float $$1) {
        return floatRangeMinInclusiveWithMessage($$0, $$1, $$2 -> {
            return "Value must be within range [" + $$0 + ";" + $$1 + "]: " + $$2;
        });
    }

    public static <T> Codec<List<T>> nonEmptyList(Codec<List<T>> $$0) {
        return $$0.validate($$02 -> {
            return $$02.isEmpty() ? DataResult.error(() -> {
                return "List must have contents";
            }) : DataResult.success($$02);
        });
    }

    public static <T> Codec<HolderSet<T>> nonEmptyHolderSet(Codec<HolderSet<T>> $$0) {
        return $$0.validate($$02 -> {
            if ($$02.unwrap().right().filter((v0) -> {
                return v0.isEmpty();
            }).isPresent()) {
                return DataResult.error(() -> {
                    return "List must have contents";
                });
            }
            return DataResult.success($$02);
        });
    }

    public static <M extends Map<?, ?>> Codec<M> nonEmptyMap(Codec<M> $$0) {
        return $$0.validate($$02 -> {
            return $$02.isEmpty() ? DataResult.error(() -> {
                return "Map must have contents";
            }) : DataResult.success($$02);
        });
    }

    public static <E> MapCodec<E> retrieveContext(final Function<DynamicOps<?>, DataResult<E>> $$0) {
        return new MapCodec<E>() { // from class: net.minecraft.util.ExtraCodecs.1ContextRetrievalCodec
            public <T> RecordBuilder<T> encode(E $$02, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
                return $$2;
            }

            public <T> DataResult<E> decode(DynamicOps<T> $$02, MapLike<T> $$1) {
                return (DataResult) $$0.apply($$02);
            }

            public String toString() {
                return "ContextRetrievalCodec[" + String.valueOf($$0) + "]";
            }

            public <T> Stream<T> keys(DynamicOps<T> $$02) {
                return Stream.empty();
            }
        };
    }

    public static <E, L extends Collection<E>, T> Function<L, DataResult<L>> ensureHomogenous(Function<E, T> $$0) {
        return $$1 -> {
            Iterator it = $$1.iterator();
            if (it.hasNext()) {
                Object objApply = $$0.apply(it.next());
                while (it.hasNext()) {
                    Object next = it.next();
                    Object objApply2 = $$0.apply(next);
                    if (objApply2 != objApply) {
                        return DataResult.error(() -> {
                            return "Mixed type list: element " + String.valueOf(next) + " had type " + String.valueOf(objApply2) + ", but list is of type " + String.valueOf(objApply);
                        });
                    }
                }
            }
            return DataResult.success($$1, Lifecycle.stable());
        };
    }

    public static <A> Codec<A> catchDecoderException(final Codec<A> $$0) {
        return Codec.of($$0, new Decoder<A>() { // from class: net.minecraft.util.ExtraCodecs.5
            public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> $$02, T $$1) {
                try {
                    return $$0.decode($$02, $$1);
                } catch (Exception $$2) {
                    return DataResult.error(() -> {
                        return "Caught exception decoding " + String.valueOf($$1) + ": " + $$2.getMessage();
                    });
                }
            }
        });
    }

    public static Codec<TemporalAccessor> temporalCodec(DateTimeFormatter $$0) {
        PrimitiveCodec primitiveCodec = Codec.STRING;
        Function function = $$1 -> {
            try {
                return DataResult.success($$0.parse($$1));
            } catch (Exception $$2) {
                Objects.requireNonNull($$2);
                return DataResult.error($$2::getMessage);
            }
        };
        Objects.requireNonNull($$0);
        return primitiveCodec.comapFlatMap(function, $$0::format);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ExtraCodecs$TagOrElementLocation.class */
    public static final class TagOrElementLocation extends Record {
        private final Identifier id;
        private final boolean tag;

        public TagOrElementLocation(Identifier $$0, boolean $$1) {
            this.id = $$0;
            this.tag = $$1;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TagOrElementLocation.class), TagOrElementLocation.class, "id;tag", "FIELD:Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;->tag:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TagOrElementLocation.class, Object.class), TagOrElementLocation.class, "id;tag", "FIELD:Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;->tag:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }

        public boolean tag() {
            return this.tag;
        }

        @Override // java.lang.Record
        public String toString() {
            return decoratedId();
        }

        private String decoratedId() {
            return this.tag ? "#" + String.valueOf(this.id) : this.id.toString();
        }
    }

    public static MapCodec<OptionalLong> asOptionalLong(MapCodec<Optional<Long>> $$0) {
        return $$0.xmap(toOptionalLong, fromOptionalLong);
    }

    private static MapCodec<GameProfile> gameProfileCodec(Codec<UUID> $$0) {
        return RecordCodecBuilder.mapCodec($$1 -> {
            return $$1.group($$0.fieldOf(Entity.TAG_ID).forGetter((v0) -> {
                return v0.id();
            }), PLAYER_NAME.fieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
                return v0.name();
            }), PROPERTY_MAP.optionalFieldOf("properties", PropertyMap.EMPTY).forGetter((v0) -> {
                return v0.properties();
            })).apply($$1, GameProfile::new);
        });
    }

    public static <K, V> Codec<Map<K, V>> sizeLimitedMap(Codec<Map<K, V>> $$0, int $$1) {
        return $$0.validate($$12 -> {
            if ($$12.size() > $$1) {
                return DataResult.error(() -> {
                    return "Map is too long: " + $$12.size() + ", expected range [0-" + $$1 + "]";
                });
            }
            return DataResult.success($$12);
        });
    }

    public static <T> Codec<Object2BooleanMap<T>> object2BooleanMap(Codec<T> $$0) {
        return Codec.unboundedMap($$0, Codec.BOOL).xmap(Object2BooleanOpenHashMap::new, (v1) -> {
            return new Object2ObjectOpenHashMap(v1);
        });
    }

    @Deprecated
    public static <K, V> MapCodec<V> dispatchOptionalValue(final String $$0, final String $$1, final Codec<K> $$2, final Function<? super V, ? extends K> $$3, final Function<? super K, ? extends Codec<? extends V>> $$4) {
        return new MapCodec<V>() { // from class: net.minecraft.util.ExtraCodecs.6
            public <T> Stream<T> keys(DynamicOps<T> $$02) {
                return Stream.of($$02.createString($$0), $$02.createString($$1));
            }

            public <T> DataResult<V> decode(DynamicOps<T> $$02, MapLike<T> $$12) {
                Object obj = $$12.get($$0);
                if (obj == null) {
                    String str = $$0;
                    return DataResult.error(() -> {
                        return "Missing \"" + str + "\" in: " + String.valueOf($$12);
                    });
                }
                DataResult dataResultDecode = $$2.decode($$02, obj);
                String str2 = $$1;
                Function function = $$4;
                return dataResultDecode.flatMap($$42 -> {
                    Object obj2 = $$12.get(str2);
                    Objects.requireNonNull($$02);
                    return ((Codec) function.apply($$42.getFirst())).decode($$02, Objects.requireNonNullElseGet(obj2, $$02::emptyMap)).map((v0) -> {
                        return v0.getFirst();
                    });
                });
            }

            /* JADX WARN: Multi-variable type inference failed */
            public <T> RecordBuilder<T> encode(V $$02, DynamicOps<T> $$12, RecordBuilder<T> $$22) {
                Object objApply = $$3.apply($$02);
                $$22.add($$0, $$2.encodeStart($$12, objApply));
                DataResult<T> $$42 = encode((Codec) $$4.apply(objApply), $$02, $$12);
                if ($$42.result().isEmpty() || !Objects.equals($$42.result().get(), $$12.emptyMap())) {
                    $$22.add($$1, $$42);
                }
                return $$22;
            }

            private <T, V2 extends V> DataResult<T> encode(Codec<V2> $$02, V $$12, DynamicOps<T> $$22) {
                return $$02.encodeStart($$22, $$12);
            }
        };
    }

    public static <A> Codec<Optional<A>> optionalEmptyMap(final Codec<A> $$0) {
        return new Codec<Optional<A>>() { // from class: net.minecraft.util.ExtraCodecs.7
            public /* synthetic */ DataResult encode(Object obj, DynamicOps dynamicOps, Object obj2) {
                return encode((Optional) obj, (DynamicOps<Object>) dynamicOps, obj2);
            }

            public <T> DataResult<Pair<Optional<A>, T>> decode(DynamicOps<T> $$02, T $$1) {
                if (isEmptyMap($$02, $$1)) {
                    return DataResult.success(Pair.of(Optional.empty(), $$1));
                }
                return $$0.decode($$02, $$1).map($$03 -> {
                    return $$03.mapFirst(Optional::of);
                });
            }

            private static <T> boolean isEmptyMap(DynamicOps<T> $$02, T $$1) {
                Optional<MapLike<T>> $$2 = $$02.getMap($$1).result();
                return $$2.isPresent() && $$2.get().entries().findAny().isEmpty();
            }

            public <T> DataResult<T> encode(Optional<A> $$02, DynamicOps<T> $$1, T $$2) {
                if ($$02.isEmpty()) {
                    return DataResult.success($$1.emptyMap());
                }
                return $$0.encode($$02.get(), $$1, $$2);
            }
        };
    }

    @Deprecated
    public static <E extends Enum<E>> Codec<E> legacyEnum(Function<String, E> $$0) {
        return Codec.STRING.comapFlatMap($$1 -> {
            try {
                return DataResult.success((Enum) $$0.apply($$1));
            } catch (IllegalArgumentException e) {
                return DataResult.error(() -> {
                    return "No value with id: " + $$1;
                });
            }
        }, (v0) -> {
            return v0.toString();
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ExtraCodecs$LateBoundIdMapper.class */
    public static class LateBoundIdMapper<I, V> {
        private final BiMap<I, V> idToValue = HashBiMap.create();

        public Codec<V> codec(Codec<I> $$0) {
            BiMap<V, I> $$1 = this.idToValue.inverse();
            BiMap<I, V> biMap = this.idToValue;
            Objects.requireNonNull(biMap);
            Function function = biMap::get;
            Objects.requireNonNull($$1);
            return ExtraCodecs.idResolverCodec($$0, function, $$1::get);
        }

        public LateBoundIdMapper<I, V> put(I $$0, V $$1) {
            Objects.requireNonNull($$1, (Supplier<String>) () -> {
                return "Value for " + String.valueOf($$0) + " is null";
            });
            this.idToValue.put($$0, $$1);
            return this;
        }

        public Set<V> values() {
            return Collections.unmodifiableSet(this.idToValue.values());
        }
    }
}
