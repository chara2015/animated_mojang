package net.minecraft.world.item.component;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.players.ProfileResolver;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/ResolvableProfile.class */
public abstract class ResolvableProfile implements TooltipProvider {
    private static final Codec<ResolvableProfile> FULL_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.mapEither(ExtraCodecs.STORED_GAME_PROFILE, Partial.MAP_CODEC).forGetter((v0) -> {
            return v0.unpack();
        }), PlayerSkin.Patch.MAP_CODEC.forGetter((v0) -> {
            return v0.skinPatch();
        })).apply($$0, ResolvableProfile::create);
    });
    public static final Codec<ResolvableProfile> CODEC = Codec.withAlternative(FULL_CODEC, ExtraCodecs.PLAYER_NAME, ResolvableProfile::createUnresolved);
    public static final StreamCodec<ByteBuf, ResolvableProfile> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.either(ByteBufCodecs.GAME_PROFILE, Partial.STREAM_CODEC), (v0) -> {
        return v0.unpack();
    }, PlayerSkin.Patch.STREAM_CODEC, (v0) -> {
        return v0.skinPatch();
    }, ResolvableProfile::create);
    protected final GameProfile partialProfile;
    protected final PlayerSkin.Patch skinPatch;

    protected abstract Either<GameProfile, Partial> unpack();

    public abstract CompletableFuture<GameProfile> resolveProfile(ProfileResolver profileResolver);

    public abstract Optional<String> name();

    private static ResolvableProfile create(Either<GameProfile, Partial> $$0, PlayerSkin.Patch $$1) {
        return (ResolvableProfile) $$0.map($$12 -> {
            return new Static(Either.left($$12), $$1);
        }, $$13 -> {
            if (!$$13.properties.isEmpty() || $$13.id.isPresent() == $$13.name.isPresent()) {
                return new Static(Either.right($$13), $$1);
            }
            return (ResolvableProfile) $$13.name.map($$13 -> {
                return new Dynamic(Either.left($$13), $$1);
            }).orElseGet(() -> {
                return new Dynamic(Either.right($$13.id.get()), $$1);
            });
        });
    }

    public static ResolvableProfile createResolved(GameProfile $$0) {
        return new Static(Either.left($$0), PlayerSkin.Patch.EMPTY);
    }

    public static ResolvableProfile createUnresolved(String $$0) {
        return new Dynamic(Either.left($$0), PlayerSkin.Patch.EMPTY);
    }

    public static ResolvableProfile createUnresolved(UUID $$0) {
        return new Dynamic(Either.right($$0), PlayerSkin.Patch.EMPTY);
    }

    protected ResolvableProfile(GameProfile $$0, PlayerSkin.Patch $$1) {
        this.partialProfile = $$0;
        this.skinPatch = $$1;
    }

    public GameProfile partialProfile() {
        return this.partialProfile;
    }

    public PlayerSkin.Patch skinPatch() {
        return this.skinPatch;
    }

    static GameProfile createPartialProfile(Optional<String> $$0, Optional<UUID> $$1, PropertyMap $$2) {
        String $$3 = $$0.orElse("");
        UUID $$4 = $$1.orElseGet(() -> {
            return (UUID) $$0.map(UUIDUtil::createOfflinePlayerUUID).orElse(Util.NIL_UUID);
        });
        return new GameProfile($$4, $$3, $$2);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/ResolvableProfile$Partial.class */
    protected static final class Partial extends Record {
        private final Optional<String> name;
        private final Optional<UUID> id;
        private final PropertyMap properties;
        public static final Partial EMPTY = new Partial(Optional.empty(), Optional.empty(), PropertyMap.EMPTY);
        static final MapCodec<Partial> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.PLAYER_NAME.optionalFieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
                return v0.name();
            }), UUIDUtil.CODEC.optionalFieldOf(Entity.TAG_ID).forGetter((v0) -> {
                return v0.id();
            }), ExtraCodecs.PROPERTY_MAP.optionalFieldOf("properties", PropertyMap.EMPTY).forGetter((v0) -> {
                return v0.properties();
            })).apply($$0, Partial::new);
        });
        public static final StreamCodec<ByteBuf, Partial> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.PLAYER_NAME.apply(ByteBufCodecs::optional), (v0) -> {
            return v0.name();
        }, UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs::optional), (v0) -> {
            return v0.id();
        }, ByteBufCodecs.GAME_PROFILE_PROPERTIES, (v0) -> {
            return v0.properties();
        }, Partial::new);

        protected Partial(Optional<String> $$0, Optional<UUID> $$1, PropertyMap $$2) {
            this.name = $$0;
            this.id = $$1;
            this.properties = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Partial.class), Partial.class, "name;id;properties", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->name:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->id:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->properties:Lcom/mojang/authlib/properties/PropertyMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Partial.class), Partial.class, "name;id;properties", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->name:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->id:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->properties:Lcom/mojang/authlib/properties/PropertyMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Partial.class, Object.class), Partial.class, "name;id;properties", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->name:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->id:Ljava/util/Optional;", "FIELD:Lnet/minecraft/world/item/component/ResolvableProfile$Partial;->properties:Lcom/mojang/authlib/properties/PropertyMap;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Optional<String> name() {
            return this.name;
        }

        public Optional<UUID> id() {
            return this.id;
        }

        public PropertyMap properties() {
            return this.properties;
        }

        private GameProfile createProfile() {
            return ResolvableProfile.createPartialProfile(this.name, this.id, this.properties);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/ResolvableProfile$Static.class */
    public static final class Static extends ResolvableProfile {
        public static final Static EMPTY = new Static(Either.right(Partial.EMPTY), PlayerSkin.Patch.EMPTY);
        private final Either<GameProfile, Partial> contents;

        Static(Either<GameProfile, Partial> $$0, PlayerSkin.Patch $$1) {
            super((GameProfile) $$0.map($$02 -> {
                return $$02;
            }, (v0) -> {
                return v0.createProfile();
            }), $$1);
            this.contents = $$0;
        }

        @Override // net.minecraft.world.item.component.ResolvableProfile
        public CompletableFuture<GameProfile> resolveProfile(ProfileResolver $$0) {
            return CompletableFuture.completedFuture(this.partialProfile);
        }

        @Override // net.minecraft.world.item.component.ResolvableProfile
        protected Either<GameProfile, Partial> unpack() {
            return this.contents;
        }

        @Override // net.minecraft.world.item.component.ResolvableProfile
        public Optional<String> name() {
            return (Optional) this.contents.map($$0 -> {
                return Optional.of($$0.name());
            }, $$02 -> {
                return $$02.name;
            });
        }

        public boolean equals(Object $$0) {
            if (this != $$0) {
                if ($$0 instanceof Static) {
                    Static $$1 = (Static) $$0;
                    if (!this.contents.equals($$1.contents) || !this.skinPatch.equals($$1.skinPatch)) {
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            int $$0 = 31 + this.contents.hashCode();
            return (31 * $$0) + this.skinPatch.hashCode();
        }

        @Override // net.minecraft.world.item.component.TooltipProvider
        public void addToTooltip(Item.TooltipContext $$0, Consumer<Component> $$1, TooltipFlag $$2, DataComponentGetter $$3) {
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/ResolvableProfile$Dynamic.class */
    public static final class Dynamic extends ResolvableProfile {
        private static final Component DYNAMIC_TOOLTIP = Component.translatable("component.profile.dynamic").withStyle(ChatFormatting.GRAY);
        private final Either<String, UUID> nameOrId;

        Dynamic(Either<String, UUID> $$0, PlayerSkin.Patch $$1) {
            super(ResolvableProfile.createPartialProfile($$0.left(), $$0.right(), PropertyMap.EMPTY), $$1);
            this.nameOrId = $$0;
        }

        @Override // net.minecraft.world.item.component.ResolvableProfile
        public Optional<String> name() {
            return this.nameOrId.left();
        }

        public boolean equals(Object $$0) {
            if (this != $$0) {
                if ($$0 instanceof Dynamic) {
                    Dynamic $$1 = (Dynamic) $$0;
                    if (!this.nameOrId.equals($$1.nameOrId) || !this.skinPatch.equals($$1.skinPatch)) {
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            int $$0 = 31 + this.nameOrId.hashCode();
            return (31 * $$0) + this.skinPatch.hashCode();
        }

        @Override // net.minecraft.world.item.component.ResolvableProfile
        protected Either<GameProfile, Partial> unpack() {
            return Either.right(new Partial(this.nameOrId.left(), this.nameOrId.right(), PropertyMap.EMPTY));
        }

        @Override // net.minecraft.world.item.component.ResolvableProfile
        public CompletableFuture<GameProfile> resolveProfile(ProfileResolver $$0) {
            return CompletableFuture.supplyAsync(() -> {
                return $$0.fetchByNameOrId(this.nameOrId).orElse(this.partialProfile);
            }, Util.nonCriticalIoPool());
        }

        @Override // net.minecraft.world.item.component.TooltipProvider
        public void addToTooltip(Item.TooltipContext $$0, Consumer<Component> $$1, TooltipFlag $$2, DataComponentGetter $$3) {
            $$1.accept(DYNAMIC_TOOLTIP);
        }
    }
}
