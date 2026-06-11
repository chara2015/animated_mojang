package net.minecraft.stats;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.UnaryOperator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.RecipeBookType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/stats/RecipeBookSettings.class */
public final class RecipeBookSettings {
    public static final StreamCodec<FriendlyByteBuf, RecipeBookSettings> STREAM_CODEC = StreamCodec.composite(TypeSettings.STREAM_CODEC, $$0 -> {
        return $$0.crafting;
    }, TypeSettings.STREAM_CODEC, $$02 -> {
        return $$02.furnace;
    }, TypeSettings.STREAM_CODEC, $$03 -> {
        return $$03.blastFurnace;
    }, TypeSettings.STREAM_CODEC, $$04 -> {
        return $$04.smoker;
    }, RecipeBookSettings::new);
    public static final MapCodec<RecipeBookSettings> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(TypeSettings.CRAFTING_MAP_CODEC.forGetter($$0 -> {
            return $$0.crafting;
        }), TypeSettings.FURNACE_MAP_CODEC.forGetter($$02 -> {
            return $$02.furnace;
        }), TypeSettings.BLAST_FURNACE_MAP_CODEC.forGetter($$03 -> {
            return $$03.blastFurnace;
        }), TypeSettings.SMOKER_MAP_CODEC.forGetter($$04 -> {
            return $$04.smoker;
        })).apply($$0, RecipeBookSettings::new);
    });
    private TypeSettings crafting;
    private TypeSettings furnace;
    private TypeSettings blastFurnace;
    private TypeSettings smoker;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/stats/RecipeBookSettings$TypeSettings.class */
    public static final class TypeSettings extends Record {
        private final boolean open;
        private final boolean filtering;
        public static final TypeSettings DEFAULT = new TypeSettings(false, false);
        public static final MapCodec<TypeSettings> CRAFTING_MAP_CODEC = codec("isGuiOpen", "isFilteringCraftable");
        public static final MapCodec<TypeSettings> FURNACE_MAP_CODEC = codec("isFurnaceGuiOpen", "isFurnaceFilteringCraftable");
        public static final MapCodec<TypeSettings> BLAST_FURNACE_MAP_CODEC = codec("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable");
        public static final MapCodec<TypeSettings> SMOKER_MAP_CODEC = codec("isSmokerGuiOpen", "isSmokerFilteringCraftable");
        public static final StreamCodec<ByteBuf, TypeSettings> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, (v0) -> {
            return v0.open();
        }, ByteBufCodecs.BOOL, (v0) -> {
            return v0.filtering();
        }, (v1, v2) -> {
            return new TypeSettings(v1, v2);
        });

        public TypeSettings(boolean $$0, boolean $$1) {
            this.open = $$0;
            this.filtering = $$1;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TypeSettings.class), TypeSettings.class, "open;filtering", "FIELD:Lnet/minecraft/stats/RecipeBookSettings$TypeSettings;->open:Z", "FIELD:Lnet/minecraft/stats/RecipeBookSettings$TypeSettings;->filtering:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TypeSettings.class, Object.class), TypeSettings.class, "open;filtering", "FIELD:Lnet/minecraft/stats/RecipeBookSettings$TypeSettings;->open:Z", "FIELD:Lnet/minecraft/stats/RecipeBookSettings$TypeSettings;->filtering:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public boolean open() {
            return this.open;
        }

        public boolean filtering() {
            return this.filtering;
        }

        @Override // java.lang.Record
        public String toString() {
            return "[open=" + this.open + ", filtering=" + this.filtering + "]";
        }

        public TypeSettings setOpen(boolean $$0) {
            return new TypeSettings($$0, this.filtering);
        }

        public TypeSettings setFiltering(boolean $$0) {
            return new TypeSettings(this.open, $$0);
        }

        private static MapCodec<TypeSettings> codec(String $$0, String $$1) {
            return RecordCodecBuilder.mapCodec($$2 -> {
                return $$2.group(Codec.BOOL.optionalFieldOf($$0, false).forGetter((v0) -> {
                    return v0.open();
                }), Codec.BOOL.optionalFieldOf($$1, false).forGetter((v0) -> {
                    return v0.filtering();
                })).apply($$2, (v1, v2) -> {
                    return new TypeSettings(v1, v2);
                });
            });
        }
    }

    public RecipeBookSettings() {
        this(TypeSettings.DEFAULT, TypeSettings.DEFAULT, TypeSettings.DEFAULT, TypeSettings.DEFAULT);
    }

    private RecipeBookSettings(TypeSettings $$0, TypeSettings $$1, TypeSettings $$2, TypeSettings $$3) {
        this.crafting = $$0;
        this.furnace = $$1;
        this.blastFurnace = $$2;
        this.smoker = $$3;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @VisibleForTesting
    public TypeSettings getSettings(RecipeBookType $$0) throws MatchException {
        switch ($$0) {
            case CRAFTING:
                return this.crafting;
            case FURNACE:
                return this.furnace;
            case BLAST_FURNACE:
                return this.blastFurnace;
            case SMOKER:
                return this.smoker;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private void updateSettings(RecipeBookType $$0, UnaryOperator<TypeSettings> $$1) {
        switch ($$0) {
            case CRAFTING:
                this.crafting = (TypeSettings) $$1.apply(this.crafting);
                break;
            case FURNACE:
                this.furnace = (TypeSettings) $$1.apply(this.furnace);
                break;
            case BLAST_FURNACE:
                this.blastFurnace = (TypeSettings) $$1.apply(this.blastFurnace);
                break;
            case SMOKER:
                this.smoker = (TypeSettings) $$1.apply(this.smoker);
                break;
        }
    }

    public boolean isOpen(RecipeBookType $$0) {
        return getSettings($$0).open;
    }

    public void setOpen(RecipeBookType $$0, boolean $$1) {
        updateSettings($$0, $$12 -> {
            return $$12.setOpen($$1);
        });
    }

    public boolean isFiltering(RecipeBookType $$0) {
        return getSettings($$0).filtering;
    }

    public void setFiltering(RecipeBookType $$0, boolean $$1) {
        updateSettings($$0, $$12 -> {
            return $$12.setFiltering($$1);
        });
    }

    public RecipeBookSettings copy() {
        return new RecipeBookSettings(this.crafting, this.furnace, this.blastFurnace, this.smoker);
    }

    public void replaceFrom(RecipeBookSettings $$0) {
        this.crafting = $$0.crafting;
        this.furnace = $$0.furnace;
        this.blastFurnace = $$0.blastFurnace;
        this.smoker = $$0.smoker;
    }
}
