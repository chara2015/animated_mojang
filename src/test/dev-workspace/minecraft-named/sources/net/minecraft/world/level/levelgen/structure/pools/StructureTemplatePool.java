package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.GravityProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool.class */
public class StructureTemplatePool {
    private static final int SIZE_UNSET = Integer.MIN_VALUE;
    private static final MutableObject<Codec<Holder<StructureTemplatePool>>> CODEC_REFERENCE = new MutableObject<>();
    public static final Codec<StructureTemplatePool> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.lazyInitialized(CODEC_REFERENCE).fieldOf("fallback").forGetter((v0) -> {
            return v0.getFallback();
        }), Codec.mapPair(StructurePoolElement.CODEC.fieldOf("element"), Codec.intRange(1, 150).fieldOf("weight")).codec().listOf().fieldOf("elements").forGetter($$0 -> {
            return $$0.rawTemplates;
        })).apply($$0, StructureTemplatePool::new);
    });
    public static final Codec<Holder<StructureTemplatePool>> CODEC;
    private final List<Pair<StructurePoolElement, Integer>> rawTemplates;
    private final ObjectArrayList<StructurePoolElement> templates;
    private final Holder<StructureTemplatePool> fallback;
    private int maxSize;

    static {
        RegistryFileCodec registryFileCodecCreate = RegistryFileCodec.create(Registries.TEMPLATE_POOL, DIRECT_CODEC);
        MutableObject<Codec<Holder<StructureTemplatePool>>> mutableObject = CODEC_REFERENCE;
        Objects.requireNonNull(mutableObject);
        CODEC = (Codec) Util.make(registryFileCodecCreate, (v1) -> {
            r1.setValue(v1);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool$Projection.class */
    public enum Projection implements StringRepresentable {
        TERRAIN_MATCHING("terrain_matching", ImmutableList.of(new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1))),
        RIGID("rigid", ImmutableList.of());

        public static final StringRepresentable.EnumCodec<Projection> CODEC = StringRepresentable.fromEnum(Projection::values);
        private final String name;
        private final ImmutableList<StructureProcessor> processors;

        Projection(String $$0, ImmutableList immutableList) {
            this.name = $$0;
            this.processors = immutableList;
        }

        public String getName() {
            return this.name;
        }

        public static Projection byName(String $$0) {
            return (Projection) CODEC.byName($$0);
        }

        public ImmutableList<StructureProcessor> getProcessors() {
            return this.processors;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    public StructureTemplatePool(Holder<StructureTemplatePool> $$0, List<Pair<StructurePoolElement, Integer>> $$1) {
        this.maxSize = Integer.MIN_VALUE;
        this.rawTemplates = $$1;
        this.templates = new ObjectArrayList<>();
        for (Pair<StructurePoolElement, Integer> $$2 : $$1) {
            StructurePoolElement $$3 = (StructurePoolElement) $$2.getFirst();
            for (int $$4 = 0; $$4 < ((Integer) $$2.getSecond()).intValue(); $$4++) {
                this.templates.add($$3);
            }
        }
        this.fallback = $$0;
    }

    public StructureTemplatePool(Holder<StructureTemplatePool> $$0, List<Pair<Function<Projection, ? extends StructurePoolElement>, Integer>> $$1, Projection $$2) {
        this.maxSize = Integer.MIN_VALUE;
        this.rawTemplates = Lists.newArrayList();
        this.templates = new ObjectArrayList<>();
        for (Pair<Function<Projection, ? extends StructurePoolElement>, Integer> $$3 : $$1) {
            StructurePoolElement $$4 = (StructurePoolElement) ((Function) $$3.getFirst()).apply($$2);
            this.rawTemplates.add(Pair.of($$4, (Integer) $$3.getSecond()));
            for (int $$5 = 0; $$5 < ((Integer) $$3.getSecond()).intValue(); $$5++) {
                this.templates.add($$4);
            }
        }
        this.fallback = $$0;
    }

    public int getMaxSize(StructureTemplateManager $$0) {
        if (this.maxSize == Integer.MIN_VALUE) {
            this.maxSize = this.templates.stream().filter($$02 -> {
                return $$02 != EmptyPoolElement.INSTANCE;
            }).mapToInt($$1 -> {
                return $$1.getBoundingBox($$0, BlockPos.ZERO, Rotation.NONE).getYSpan();
            }).max().orElse(0);
        }
        return this.maxSize;
    }

    @VisibleForTesting
    public List<Pair<StructurePoolElement, Integer>> getTemplates() {
        return this.rawTemplates;
    }

    public Holder<StructureTemplatePool> getFallback() {
        return this.fallback;
    }

    public StructurePoolElement getRandomTemplate(RandomSource $$0) {
        if (this.templates.isEmpty()) {
            return EmptyPoolElement.INSTANCE;
        }
        return (StructurePoolElement) this.templates.get($$0.nextInt(this.templates.size()));
    }

    public List<StructurePoolElement> getShuffledTemplates(RandomSource $$0) {
        return Util.shuffledCopy(this.templates, $$0);
    }

    public int size() {
        return this.templates.size();
    }
}
