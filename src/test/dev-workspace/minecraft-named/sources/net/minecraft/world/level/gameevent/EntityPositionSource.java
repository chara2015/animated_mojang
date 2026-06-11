package net.minecraft.world.level.gameevent;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/EntityPositionSource.class */
public class EntityPositionSource implements PositionSource {
    public static final MapCodec<EntityPositionSource> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(UUIDUtil.CODEC.fieldOf("source_entity").forGetter((v0) -> {
            return v0.getUuid();
        }), Codec.FLOAT.fieldOf("y_offset").orElse(Float.valueOf(0.0f)).forGetter($$0 -> {
            return Float.valueOf($$0.yOffset);
        })).apply($$0, ($$02, $$1) -> {
            return new EntityPositionSource((Either<Entity, Either<UUID, Integer>>) Either.right(Either.left($$02)), $$1.floatValue());
        });
    });
    public static final StreamCodec<ByteBuf, EntityPositionSource> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, (v0) -> {
        return v0.getId();
    }, ByteBufCodecs.FLOAT, $$0 -> {
        return Float.valueOf($$0.yOffset);
    }, ($$02, $$1) -> {
        return new EntityPositionSource((Either<Entity, Either<UUID, Integer>>) Either.right(Either.right($$02)), $$1.floatValue());
    });
    private Either<Entity, Either<UUID, Integer>> entityOrUuidOrId;
    private final float yOffset;

    public EntityPositionSource(Entity $$0, float $$1) {
        this((Either<Entity, Either<UUID, Integer>>) Either.left($$0), $$1);
    }

    private EntityPositionSource(Either<Entity, Either<UUID, Integer>> $$0, float $$1) {
        this.entityOrUuidOrId = $$0;
        this.yOffset = $$1;
    }

    @Override // net.minecraft.world.level.gameevent.PositionSource
    public Optional<Vec3> getPosition(Level $$0) {
        if (this.entityOrUuidOrId.left().isEmpty()) {
            resolveEntity($$0);
        }
        return this.entityOrUuidOrId.left().map($$02 -> {
            return $$02.position().add(Density.SURFACE, this.yOffset, Density.SURFACE);
        });
    }

    private void resolveEntity(Level $$0) {
        ((Optional) this.entityOrUuidOrId.map((v0) -> {
            return Optional.of(v0);
        }, $$1 -> {
            Function function = $$1 -> {
                if (!($$0 instanceof ServerLevel)) {
                    return null;
                }
                ServerLevel $$2 = (ServerLevel) $$0;
                return $$2.getEntity($$1);
            };
            Objects.requireNonNull($$0);
            return Optional.ofNullable((Entity) $$1.map(function, (v1) -> {
                return r2.getEntity(v1);
            }));
        })).ifPresent($$02 -> {
            this.entityOrUuidOrId = Either.left($$02);
        });
    }

    public UUID getUuid() {
        return (UUID) this.entityOrUuidOrId.map((v0) -> {
            return v0.getUUID();
        }, $$0 -> {
            return (UUID) $$0.map(Function.identity(), $$0 -> {
                throw new RuntimeException("Unable to get entityId from uuid");
            });
        });
    }

    private int getId() {
        return ((Integer) this.entityOrUuidOrId.map((v0) -> {
            return v0.getId();
        }, $$0 -> {
            return (Integer) $$0.map($$0 -> {
                throw new IllegalStateException("Unable to get entityId from uuid");
            }, Function.identity());
        })).intValue();
    }

    @Override // net.minecraft.world.level.gameevent.PositionSource
    public PositionSourceType<EntityPositionSource> getType() {
        return PositionSourceType.ENTITY;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/EntityPositionSource$Type.class */
    public static class Type implements PositionSourceType<EntityPositionSource> {
        @Override // net.minecraft.world.level.gameevent.PositionSourceType
        public MapCodec<EntityPositionSource> codec() {
            return EntityPositionSource.CODEC;
        }

        @Override // net.minecraft.world.level.gameevent.PositionSourceType
        public StreamCodec<ByteBuf, EntityPositionSource> streamCodec() {
            return EntityPositionSource.STREAM_CODEC;
        }
    }
}
