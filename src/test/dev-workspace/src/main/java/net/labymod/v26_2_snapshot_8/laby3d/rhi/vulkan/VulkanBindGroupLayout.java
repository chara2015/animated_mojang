package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.pipeline.BindGroupLayout;
import com.mojang.blaze3d.shaders.UniformType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayout;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayoutDescriptor;
import net.labymod.laby3d.api.rhi.bind.BindGroupLayoutEntry;
import net.labymod.laby3d.api.rhi.bind.BindingType;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanBindGroupLayout.class */
class VulkanBindGroupLayout implements BindGroupLayout {
    private final BindGroupLayoutDescriptor descriptor;
    private final com.mojang.blaze3d.pipeline.BindGroupLayout mcLayout;
    private final Map<Integer, BindGroupLayoutEntry> entriesByBinding = new HashMap();

    VulkanBindGroupLayout(BindGroupLayoutDescriptor descriptor) {
        this.descriptor = descriptor;
        for (BindGroupLayoutEntry entry : descriptor.entries()) {
            this.entriesByBinding.put(Integer.valueOf(entry.binding()), entry);
        }
        this.mcLayout = buildMcLayout(descriptor);
    }

    BindGroupLayoutDescriptor descriptor() {
        return this.descriptor;
    }

    com.mojang.blaze3d.pipeline.BindGroupLayout mcLayout() {
        return this.mcLayout;
    }

    @Nullable
    BindGroupLayoutEntry findEntry(int binding) {
        return this.entriesByBinding.get(Integer.valueOf(binding));
    }

    static String bindingName(BindGroupLayoutEntry entry) {
        return entry.name() != null ? entry.name() : "_rhi_b" + entry.binding();
    }

    private static com.mojang.blaze3d.pipeline.BindGroupLayout buildMcLayout(BindGroupLayoutDescriptor descriptor) {
        BindGroupLayout.Builder builder = com.mojang.blaze3d.pipeline.BindGroupLayout.builder();
        Set<String> samplers = new HashSet<>();
        for (BindGroupLayoutEntry entry : descriptor.entries()) {
            String name = bindingName(entry);
            switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType[entry.type().ordinal()]) {
                case 1:
                    builder.withUniform(name, UniformType.UNIFORM_BUFFER);
                    break;
                case 2:
                case 3:
                    if (samplers.add(name)) {
                        builder.withSampler(name);
                    }
                    break;
                case 4:
                case 5:
                    throw new UnsupportedOperationException("BindingType " + String.valueOf(entry.type()) + " not exposed by Mojang's 26.2 BindGroupLayout");
            }
        }
        return builder.build();
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan.VulkanBindGroupLayout$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanBindGroupLayout$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType = new int[BindingType.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType[BindingType.UNIFORM_BUFFER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType[BindingType.SAMPLED_TEXTURE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType[BindingType.SAMPLER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType[BindingType.STORAGE_BUFFER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$bind$BindingType[BindingType.STORAGE_TEXTURE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void close() {
    }
}
