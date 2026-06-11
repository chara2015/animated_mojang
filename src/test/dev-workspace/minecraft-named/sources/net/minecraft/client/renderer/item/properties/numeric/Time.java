package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.NeedleDirectionHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.MoonPhase;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/Time.class */
public class Time extends NeedleDirectionHelper implements RangeSelectItemModelProperty {
    public static final MapCodec<Time> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.BOOL.optionalFieldOf("wobble", true).forGetter((v0) -> {
            return v0.wobble();
        }), TimeSource.CODEC.fieldOf("source").forGetter($$0 -> {
            return $$0.source;
        })).apply($$0, (v1, v2) -> {
            return new Time(v1, v2);
        });
    });
    private final TimeSource source;
    private final RandomSource randomSource;
    private final NeedleDirectionHelper.Wobbler wobbler;

    public Time(boolean $$0, TimeSource $$1) {
        super($$0);
        this.randomSource = RandomSource.create();
        this.source = $$1;
        this.wobbler = newWobbler(0.9f);
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.NeedleDirectionHelper
    protected float calculate(ItemStack $$0, ClientLevel $$1, int $$2, ItemOwner $$3) {
        float $$4 = this.source.get($$1, $$0, $$3, this.randomSource);
        long $$5 = $$1.getGameTime();
        if (this.wobbler.shouldUpdate($$5)) {
            this.wobbler.update($$5, $$4);
        }
        return this.wobbler.rotation();
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<Time> type() {
        return MAP_CODEC;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/Time$TimeSource.class */
    public enum TimeSource implements StringRepresentable {
        RANDOM("random") { // from class: net.minecraft.client.renderer.item.properties.numeric.Time.TimeSource.1
            @Override // net.minecraft.client.renderer.item.properties.numeric.Time.TimeSource
            public float get(ClientLevel $$0, ItemStack $$1, ItemOwner $$2, RandomSource $$3) {
                return $$3.nextFloat();
            }
        },
        DAYTIME("daytime") { // from class: net.minecraft.client.renderer.item.properties.numeric.Time.TimeSource.2
            @Override // net.minecraft.client.renderer.item.properties.numeric.Time.TimeSource
            public float get(ClientLevel $$0, ItemStack $$1, ItemOwner $$2, RandomSource $$3) {
                return ((Float) $$0.environmentAttributes().getValue(EnvironmentAttributes.SUN_ANGLE, $$2.position())).floatValue() / 360.0f;
            }
        },
        MOON_PHASE("moon_phase") { // from class: net.minecraft.client.renderer.item.properties.numeric.Time.TimeSource.3
            @Override // net.minecraft.client.renderer.item.properties.numeric.Time.TimeSource
            public float get(ClientLevel $$0, ItemStack $$1, ItemOwner $$2, RandomSource $$3) {
                return ((MoonPhase) $$0.environmentAttributes().getValue(EnvironmentAttributes.MOON_PHASE, $$2.position())).index() / MoonPhase.COUNT;
            }
        };

        public static final Codec<TimeSource> CODEC = StringRepresentable.fromEnum(TimeSource::values);
        private final String name;

        abstract float get(ClientLevel clientLevel, ItemStack itemStack, ItemOwner itemOwner, RandomSource randomSource);

        TimeSource(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}
