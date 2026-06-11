package net.minecraft.world.waypoints;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.Waypoint;
import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint.class */
public abstract class TrackedWaypoint implements Waypoint {
    static final Logger LOGGER = LogUtils.getLogger();
    public static final StreamCodec<ByteBuf, TrackedWaypoint> STREAM_CODEC = StreamCodec.ofMember((v0, v1) -> {
        v0.write(v1);
    }, TrackedWaypoint::read);
    protected final Either<UUID, String> identifier;
    private final Waypoint.Icon icon;
    private final Type type;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$Camera.class */
    public interface Camera {
        float yaw();

        Vec3 position();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$PitchDirection.class */
    public enum PitchDirection {
        NONE,
        UP,
        DOWN
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$Projector.class */
    public interface Projector {
        Vec3 projectPointToScreen(Vec3 vec3);

        double projectHorizonToScreen();
    }

    public abstract void update(TrackedWaypoint trackedWaypoint);

    public abstract void writeContents(ByteBuf byteBuf);

    public abstract double yawAngleToCamera(Level level, Camera camera, PartialTickSupplier partialTickSupplier);

    public abstract PitchDirection pitchDirectionToCamera(Level level, Projector projector, PartialTickSupplier partialTickSupplier);

    public abstract double distanceSquared(Entity entity);

    TrackedWaypoint(Either<UUID, String> $$0, Waypoint.Icon $$1, Type $$2) {
        this.identifier = $$0;
        this.icon = $$1;
        this.type = $$2;
    }

    public Either<UUID, String> id() {
        return this.identifier;
    }

    public void write(ByteBuf $$0) {
        FriendlyByteBuf $$1 = new FriendlyByteBuf($$0);
        $$1.writeEither(this.identifier, UUIDUtil.STREAM_CODEC, (v0, v1) -> {
            v0.writeUtf(v1);
        });
        Waypoint.Icon.STREAM_CODEC.encode($$1, this.icon);
        $$1.writeEnum(this.type);
        writeContents($$0);
    }

    private static TrackedWaypoint read(ByteBuf $$0) {
        FriendlyByteBuf $$1 = new FriendlyByteBuf($$0);
        Either<UUID, String> $$2 = $$1.readEither(UUIDUtil.STREAM_CODEC, (v0) -> {
            return v0.readUtf();
        });
        Waypoint.Icon $$3 = Waypoint.Icon.STREAM_CODEC.decode($$1);
        Type $$4 = (Type) $$1.readEnum(Type.class);
        return (TrackedWaypoint) $$4.constructor.apply($$2, $$3, $$1);
    }

    public static TrackedWaypoint setPosition(UUID $$0, Waypoint.Icon $$1, Vec3i $$2) {
        return new Vec3iWaypoint($$0, $$1, $$2);
    }

    public static TrackedWaypoint setChunk(UUID $$0, Waypoint.Icon $$1, ChunkPos $$2) {
        return new ChunkWaypoint($$0, $$1, $$2);
    }

    public static TrackedWaypoint setAzimuth(UUID $$0, Waypoint.Icon $$1, float $$2) {
        return new AzimuthWaypoint($$0, $$1, $$2);
    }

    public static TrackedWaypoint empty(UUID $$0) {
        return new EmptyWaypoint($$0);
    }

    public Waypoint.Icon icon() {
        return this.icon;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$Type.class */
    enum Type {
        EMPTY(EmptyWaypoint::new),
        VEC3I(Vec3iWaypoint::new),
        CHUNK(ChunkWaypoint::new),
        AZIMUTH(AzimuthWaypoint::new);

        final TriFunction<Either<UUID, String>, Waypoint.Icon, FriendlyByteBuf, TrackedWaypoint> constructor;

        Type(TriFunction triFunction) {
            this.constructor = triFunction;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$EmptyWaypoint.class */
    static class EmptyWaypoint extends TrackedWaypoint {
        private EmptyWaypoint(Either<UUID, String> $$0, Waypoint.Icon $$1, FriendlyByteBuf $$2) {
            super($$0, $$1, Type.EMPTY);
        }

        EmptyWaypoint(UUID $$0) {
            super(Either.left($$0), Waypoint.Icon.NULL, Type.EMPTY);
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void update(TrackedWaypoint $$0) {
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void writeContents(ByteBuf $$0) {
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double yawAngleToCamera(Level $$0, Camera $$1, PartialTickSupplier $$2) {
            return Double.NaN;
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public PitchDirection pitchDirectionToCamera(Level $$0, Projector $$1, PartialTickSupplier $$2) {
            return PitchDirection.NONE;
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double distanceSquared(Entity $$0) {
            return Double.POSITIVE_INFINITY;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$Vec3iWaypoint.class */
    static class Vec3iWaypoint extends TrackedWaypoint {
        private Vec3i vector;

        public Vec3iWaypoint(UUID $$0, Waypoint.Icon $$1, Vec3i $$2) {
            super(Either.left($$0), $$1, Type.VEC3I);
            this.vector = $$2;
        }

        public Vec3iWaypoint(Either<UUID, String> $$0, Waypoint.Icon $$1, FriendlyByteBuf $$2) {
            super($$0, $$1, Type.VEC3I);
            this.vector = new Vec3i($$2.readVarInt(), $$2.readVarInt(), $$2.readVarInt());
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void update(TrackedWaypoint $$0) {
            if ($$0 instanceof Vec3iWaypoint) {
                Vec3iWaypoint $$1 = (Vec3iWaypoint) $$0;
                this.vector = $$1.vector;
            } else {
                TrackedWaypoint.LOGGER.warn("Unsupported Waypoint update operation: {}", $$0.getClass());
            }
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void writeContents(ByteBuf $$0) {
            VarInt.write($$0, this.vector.getX());
            VarInt.write($$0, this.vector.getY());
            VarInt.write($$0, this.vector.getZ());
        }

        private Vec3 position(Level $$0, PartialTickSupplier $$1) {
            Optional optionalLeft = this.identifier.left();
            Objects.requireNonNull($$0);
            return (Vec3) optionalLeft.map($$0::getEntity).map($$12 -> {
                if ($$12.blockPosition().distManhattan(this.vector) > 3) {
                    return null;
                }
                return $$12.getEyePosition($$1.apply($$12));
            }).orElseGet(() -> {
                return Vec3.atCenterOf(this.vector);
            });
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double yawAngleToCamera(Level $$0, Camera $$1, PartialTickSupplier $$2) {
            Vec3 $$3 = $$1.position().subtract(position($$0, $$2)).rotateClockwise90();
            float $$4 = ((float) Mth.atan2($$3.z(), $$3.x())) * 57.295776f;
            return Mth.degreesDifference($$1.yaw(), $$4);
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public PitchDirection pitchDirectionToCamera(Level $$0, Projector $$1, PartialTickSupplier $$2) {
            Vec3 $$3 = $$1.projectPointToScreen(position($$0, $$2));
            boolean $$4 = $$3.z > 1.0d;
            double $$5 = $$4 ? -$$3.y : $$3.y;
            if ($$5 < -1.0d) {
                return PitchDirection.DOWN;
            }
            if ($$5 > 1.0d) {
                return PitchDirection.UP;
            }
            if ($$4) {
                if ($$3.y > Density.SURFACE) {
                    return PitchDirection.UP;
                }
                if ($$3.y < Density.SURFACE) {
                    return PitchDirection.DOWN;
                }
            }
            return PitchDirection.NONE;
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double distanceSquared(Entity $$0) {
            return $$0.distanceToSqr(Vec3.atCenterOf(this.vector));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$ChunkWaypoint.class */
    static class ChunkWaypoint extends TrackedWaypoint {
        private ChunkPos chunkPos;

        public ChunkWaypoint(UUID $$0, Waypoint.Icon $$1, ChunkPos $$2) {
            super(Either.left($$0), $$1, Type.CHUNK);
            this.chunkPos = $$2;
        }

        public ChunkWaypoint(Either<UUID, String> $$0, Waypoint.Icon $$1, FriendlyByteBuf $$2) {
            super($$0, $$1, Type.CHUNK);
            this.chunkPos = new ChunkPos($$2.readVarInt(), $$2.readVarInt());
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void update(TrackedWaypoint $$0) {
            if ($$0 instanceof ChunkWaypoint) {
                ChunkWaypoint $$1 = (ChunkWaypoint) $$0;
                this.chunkPos = $$1.chunkPos;
            } else {
                TrackedWaypoint.LOGGER.warn("Unsupported Waypoint update operation: {}", $$0.getClass());
            }
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void writeContents(ByteBuf $$0) {
            VarInt.write($$0, this.chunkPos.x);
            VarInt.write($$0, this.chunkPos.z);
        }

        private Vec3 position(double $$0) {
            return Vec3.atCenterOf(this.chunkPos.getMiddleBlockPosition((int) $$0));
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double yawAngleToCamera(Level $$0, Camera $$1, PartialTickSupplier $$2) {
            Vec3 $$3 = $$1.position();
            Vec3 $$4 = $$3.subtract(position($$3.y())).rotateClockwise90();
            float $$5 = ((float) Mth.atan2($$4.z(), $$4.x())) * 57.295776f;
            return Mth.degreesDifference($$1.yaw(), $$5);
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public PitchDirection pitchDirectionToCamera(Level $$0, Projector $$1, PartialTickSupplier $$2) {
            double $$3 = $$1.projectHorizonToScreen();
            if ($$3 < -1.0d) {
                return PitchDirection.DOWN;
            }
            if ($$3 > 1.0d) {
                return PitchDirection.UP;
            }
            return PitchDirection.NONE;
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double distanceSquared(Entity $$0) {
            return $$0.distanceToSqr(Vec3.atCenterOf(this.chunkPos.getMiddleBlockPosition($$0.getBlockY())));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/waypoints/TrackedWaypoint$AzimuthWaypoint.class */
    static class AzimuthWaypoint extends TrackedWaypoint {
        private float angle;

        public AzimuthWaypoint(UUID $$0, Waypoint.Icon $$1, float $$2) {
            super(Either.left($$0), $$1, Type.AZIMUTH);
            this.angle = $$2;
        }

        public AzimuthWaypoint(Either<UUID, String> $$0, Waypoint.Icon $$1, FriendlyByteBuf $$2) {
            super($$0, $$1, Type.AZIMUTH);
            this.angle = $$2.readFloat();
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void update(TrackedWaypoint $$0) {
            if ($$0 instanceof AzimuthWaypoint) {
                AzimuthWaypoint $$1 = (AzimuthWaypoint) $$0;
                this.angle = $$1.angle;
            } else {
                TrackedWaypoint.LOGGER.warn("Unsupported Waypoint update operation: {}", $$0.getClass());
            }
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public void writeContents(ByteBuf $$0) {
            $$0.writeFloat(this.angle);
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double yawAngleToCamera(Level $$0, Camera $$1, PartialTickSupplier $$2) {
            return Mth.degreesDifference($$1.yaw(), this.angle * 57.295776f);
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public PitchDirection pitchDirectionToCamera(Level $$0, Projector $$1, PartialTickSupplier $$2) {
            double $$3 = $$1.projectHorizonToScreen();
            if ($$3 < -1.0d) {
                return PitchDirection.DOWN;
            }
            if ($$3 > 1.0d) {
                return PitchDirection.UP;
            }
            return PitchDirection.NONE;
        }

        @Override // net.minecraft.world.waypoints.TrackedWaypoint
        public double distanceSquared(Entity $$0) {
            return Double.POSITIVE_INFINITY;
        }
    }
}
