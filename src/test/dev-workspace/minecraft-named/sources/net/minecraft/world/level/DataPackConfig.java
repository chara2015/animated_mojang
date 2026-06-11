package net.minecraft.world.level;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/DataPackConfig.class */
public class DataPackConfig {
    public static final DataPackConfig DEFAULT = new DataPackConfig(ImmutableList.of("vanilla"), ImmutableList.of());
    public static final Codec<DataPackConfig> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.STRING.listOf().fieldOf("Enabled").forGetter($$0 -> {
            return $$0.enabled;
        }), Codec.STRING.listOf().fieldOf("Disabled").forGetter($$02 -> {
            return $$02.disabled;
        })).apply($$0, DataPackConfig::new);
    });
    private final List<String> enabled;
    private final List<String> disabled;

    public DataPackConfig(List<String> $$0, List<String> $$1) {
        this.enabled = ImmutableList.copyOf($$0);
        this.disabled = ImmutableList.copyOf($$1);
    }

    public List<String> getEnabled() {
        return this.enabled;
    }

    public List<String> getDisabled() {
        return this.disabled;
    }
}
