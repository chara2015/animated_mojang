package com.mojang.blaze3d;

import com.mojang.blaze3d.systems.GpuDevice;
import java.lang.ref.WeakReference;
import java.util.List;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/GraphicsWorkarounds.class */
public class GraphicsWorkarounds {
    private static final List<String> INTEL_GEN11_CORE = List.of((Object[]) new String[]{"i3-1000g1", "i3-1000g4", "i3-1000ng4", "i3-1005g1", "i3-l13g4", "i5-1030g4", "i5-1030g7", "i5-1030ng7", "i5-1034g1", "i5-1035g1", "i5-1035g4", "i5-1035g7", "i5-1038ng7", "i5-l16g7", "i7-1060g7", "i7-1060ng7", "i7-1065g7", "i7-1068g7", "i7-1068ng7"});
    private static final List<String> INTEL_GEN11_ATOM = List.of("x6211e", "x6212re", "x6214re", "x6413e", "x6414re", "x6416re", "x6425e", "x6425re", "x6427fe");
    private static final List<String> INTEL_GEN11_CELERON = List.of("j6412", "j6413", "n4500", "n4505", "n5095", "n5095a", "n5100", "n5105", "n6210", "n6211");
    private static final List<String> INTEL_GEN11_PENTIUM = List.of("6805", "j6426", "n6415", "n6000", "n6005");
    private static GraphicsWorkarounds instance;
    private final WeakReference<GpuDevice> gpuDevice;
    private final boolean alwaysCreateFreshImmediateBuffer;
    private final boolean isGlOnDx12;
    private final boolean isAmd;

    private GraphicsWorkarounds(GpuDevice $$0) {
        this.gpuDevice = new WeakReference<>($$0);
        this.alwaysCreateFreshImmediateBuffer = isIntelGen11($$0);
        this.isGlOnDx12 = isGlOnDx12($$0);
        this.isAmd = isAmd($$0);
    }

    public static GraphicsWorkarounds get(GpuDevice $$0) {
        GraphicsWorkarounds $$1 = instance;
        if ($$1 == null || $$1.gpuDevice.get() != $$0) {
            GraphicsWorkarounds graphicsWorkarounds = new GraphicsWorkarounds($$0);
            $$1 = graphicsWorkarounds;
            instance = graphicsWorkarounds;
        }
        return $$1;
    }

    public boolean alwaysCreateFreshImmediateBuffer() {
        return this.alwaysCreateFreshImmediateBuffer;
    }

    public boolean isGlOnDx12() {
        return this.isGlOnDx12;
    }

    public boolean isAmd() {
        return this.isAmd;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean isIntelGen11(com.mojang.blaze3d.systems.GpuDevice r4) {
        /*
            Method dump skipped, instruction units count: 224
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mojang.blaze3d.GraphicsWorkarounds.isIntelGen11(com.mojang.blaze3d.systems.GpuDevice):boolean");
    }

    private static boolean isGlOnDx12(GpuDevice $$0) {
        boolean $$1 = Util.getPlatform() == Util.OS.WINDOWS && Util.isAarch64();
        return $$1 || $$0.getRenderer().startsWith("D3D12");
    }

    private static boolean isAmd(GpuDevice $$0) {
        return $$0.getRenderer().contains("AMD");
    }
}
