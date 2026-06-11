package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.systems.GpuDevice;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.laby3d.api.rhi.Capability;
import net.labymod.laby3d.api.rhi.DeviceCapabilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanDeviceCapabilities.class */
class VulkanDeviceCapabilities implements DeviceCapabilities {
    private final boolean persistentMapping;

    VulkanDeviceCapabilities(GpuDevice gpuDevice) {
        this.persistentMapping = gpuDevice.getDeviceInfo().features().persistentMapping();
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan.VulkanDeviceCapabilities$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanDeviceCapabilities$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$rhi$Capability = new int[Capability.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.UNIFORM_BUFFERS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.VERTEX_ARRAY_OBJECTS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.SEPARATE_SAMPLERS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.INSTANCED_RENDERING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.DRAW_INDEXED_BASE_VERTEX.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.MULTIPLE_RENDER_TARGETS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.DEBUG_MARKERS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.PERSISTENT_MAPPED_BUFFERS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.TEXTURE_ARRAYS.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.STORAGE_BUFFERS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.COMPUTE_SHADERS.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.GEOMETRY_SHADERS.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.TESSELLATION.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.MSAA_RESOLVE.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.SRGB_FRAMEBUFFERS.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.OCCLUSION_QUERIES.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.PUSH_CONSTANTS.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.BLEND_CONSTANT.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.STENCIL_REFERENCE.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$rhi$Capability[Capability.DRAW_INDIRECT.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public boolean supports(Capability capability) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$rhi$Capability[capability.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return true;
            case 8:
                return this.persistentMapping;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case ImGuiFlags.StyleColors.ScrollbarGrab /* 15 */:
            case 16:
            case ImGuiFlags.StyleColors.ScrollbarGrabActive /* 17 */:
            case 18:
            case ImGuiFlags.StyleColors.SliderGrab /* 19 */:
            case ImGuiFlags.StyleColors.SliderGrabActive /* 20 */:
                return false;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
