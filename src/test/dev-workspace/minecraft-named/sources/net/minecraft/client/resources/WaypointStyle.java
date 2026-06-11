package net.minecraft.client.resources;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.waypoints.Waypoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/WaypointStyle.class */
public final class WaypointStyle extends Record {
    private final int nearDistance;
    private final int farDistance;
    private final List<Identifier> sprites;
    private final List<Identifier> spriteLocations;

    @VisibleForTesting
    public static final String ICON_LOCATION_PREFIX = "hud/locator_bar_dot/";
    public static final int DEFAULT_NEAR_DISTANCE = 128;
    public static final int DEFAULT_FAR_DISTANCE = 332;
    private static final Codec<Integer> DISTANCE_CODEC = Codec.intRange(0, Waypoint.MAX_RANGE);
    public static final Codec<WaypointStyle> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(DISTANCE_CODEC.optionalFieldOf("near_distance", 128).forGetter((v0) -> {
            return v0.nearDistance();
        }), DISTANCE_CODEC.optionalFieldOf("far_distance", 332).forGetter((v0) -> {
            return v0.farDistance();
        }), ExtraCodecs.nonEmptyList(Identifier.CODEC.listOf()).fieldOf("sprites").forGetter((v0) -> {
            return v0.sprites();
        })).apply($$0, (v1, v2, v3) -> {
            return new WaypointStyle(v1, v2, v3);
        });
    }).validate((v0) -> {
        return v0.validate();
    });

    public WaypointStyle(int $$0, int $$1, List<Identifier> $$2, List<Identifier> $$3) {
        this.nearDistance = $$0;
        this.farDistance = $$1;
        this.sprites = $$2;
        this.spriteLocations = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WaypointStyle.class), WaypointStyle.class, "nearDistance;farDistance;sprites;spriteLocations", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->nearDistance:I", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->farDistance:I", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->sprites:Ljava/util/List;", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->spriteLocations:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WaypointStyle.class), WaypointStyle.class, "nearDistance;farDistance;sprites;spriteLocations", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->nearDistance:I", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->farDistance:I", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->sprites:Ljava/util/List;", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->spriteLocations:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WaypointStyle.class, Object.class), WaypointStyle.class, "nearDistance;farDistance;sprites;spriteLocations", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->nearDistance:I", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->farDistance:I", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->sprites:Ljava/util/List;", "FIELD:Lnet/minecraft/client/resources/WaypointStyle;->spriteLocations:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int nearDistance() {
        return this.nearDistance;
    }

    public int farDistance() {
        return this.farDistance;
    }

    public List<Identifier> sprites() {
        return this.sprites;
    }

    public List<Identifier> spriteLocations() {
        return this.spriteLocations;
    }

    @VisibleForTesting
    public DataResult<WaypointStyle> validate() {
        if (this.sprites.isEmpty()) {
            return DataResult.error(() -> {
                return "Must have at least one sprite icon";
            });
        }
        if (this.nearDistance <= 0) {
            return DataResult.error(() -> {
                return "Near distance (" + this.nearDistance + ") must be greater than zero";
            });
        }
        if (this.nearDistance >= this.farDistance) {
            return DataResult.error(() -> {
                return "Far distance (" + this.farDistance + ") cannot be closer or equal to near distance (" + this.nearDistance + ")";
            });
        }
        return DataResult.success(this);
    }

    public WaypointStyle(int $$0, int $$1, List<Identifier> $$2) {
        this($$0, $$1, $$2, $$2.stream().map($$02 -> {
            return $$02.withPrefix(ICON_LOCATION_PREFIX);
        }).toList());
    }

    public Identifier sprite(float $$0) {
        if ($$0 < this.nearDistance) {
            return (Identifier) this.spriteLocations.getFirst();
        }
        if ($$0 >= this.farDistance) {
            return (Identifier) this.spriteLocations.getLast();
        }
        if (this.spriteLocations.size() == 1) {
            return (Identifier) this.spriteLocations.getFirst();
        }
        if (this.spriteLocations.size() == 3) {
            return this.spriteLocations.get(1);
        }
        int $$1 = Mth.lerpInt(($$0 - this.nearDistance) / (this.farDistance - this.nearDistance), 1, this.spriteLocations.size() - 1);
        return this.spriteLocations.get($$1);
    }
}
