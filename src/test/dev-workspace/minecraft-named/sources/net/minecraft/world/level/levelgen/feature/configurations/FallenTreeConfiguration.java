package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/FallenTreeConfiguration.class */
public class FallenTreeConfiguration implements FeatureConfiguration {
    public static final Codec<FallenTreeConfiguration> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter($$0 -> {
            return $$0.trunkProvider;
        }), IntProvider.codec(0, 16).fieldOf("log_length").forGetter($$02 -> {
            return $$02.logLength;
        }), TreeDecorator.CODEC.listOf().fieldOf("stump_decorators").forGetter($$03 -> {
            return $$03.stumpDecorators;
        }), TreeDecorator.CODEC.listOf().fieldOf("log_decorators").forGetter($$04 -> {
            return $$04.logDecorators;
        })).apply($$0, FallenTreeConfiguration::new);
    });
    public final BlockStateProvider trunkProvider;
    public final IntProvider logLength;
    public final List<TreeDecorator> stumpDecorators;
    public final List<TreeDecorator> logDecorators;

    protected FallenTreeConfiguration(BlockStateProvider $$0, IntProvider $$1, List<TreeDecorator> $$2, List<TreeDecorator> $$3) {
        this.trunkProvider = $$0;
        this.logLength = $$1;
        this.stumpDecorators = $$2;
        this.logDecorators = $$3;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/configurations/FallenTreeConfiguration$FallenTreeConfigurationBuilder.class */
    public static class FallenTreeConfigurationBuilder {
        private final BlockStateProvider trunkProvider;
        private final IntProvider logLength;
        private List<TreeDecorator> stumpDecorators = new ArrayList();
        private List<TreeDecorator> logDecorators = new ArrayList();

        public FallenTreeConfigurationBuilder(BlockStateProvider $$0, IntProvider $$1) {
            this.trunkProvider = $$0;
            this.logLength = $$1;
        }

        public FallenTreeConfigurationBuilder stumpDecorators(List<TreeDecorator> $$0) {
            this.stumpDecorators = $$0;
            return this;
        }

        public FallenTreeConfigurationBuilder logDecorators(List<TreeDecorator> $$0) {
            this.logDecorators = $$0;
            return this;
        }

        public FallenTreeConfiguration build() {
            return new FallenTreeConfiguration(this.trunkProvider, this.logLength, this.stumpDecorators, this.logDecorators);
        }
    }
}
