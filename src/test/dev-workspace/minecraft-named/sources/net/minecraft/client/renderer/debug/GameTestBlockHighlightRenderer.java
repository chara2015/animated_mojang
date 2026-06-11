package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.gizmos.TextGizmo;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer.class */
public class GameTestBlockHighlightRenderer {
    private static final int SHOW_POS_DURATION_MS = 10000;
    private static final float PADDING = 0.02f;
    private final Map<BlockPos, Marker> markers = Maps.newHashMap();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker.class */
    static final class Marker extends Record {
        private final int color;
        private final String text;
        private final long removeAtTime;

        Marker(int $$0, String $$1, long $$2) {
            this.color = $$0;
            this.text = $$1;
            this.removeAtTime = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Marker.class), Marker.class, "color;text;removeAtTime", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->color:I", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->text:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->removeAtTime:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Marker.class), Marker.class, "color;text;removeAtTime", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->color:I", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->text:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->removeAtTime:J").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Marker.class, Object.class), Marker.class, "color;text;removeAtTime", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->color:I", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->text:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/debug/GameTestBlockHighlightRenderer$Marker;->removeAtTime:J").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int color() {
            return this.color;
        }

        public String text() {
            return this.text;
        }

        public long removeAtTime() {
            return this.removeAtTime;
        }
    }

    public void highlightPos(BlockPos $$0, BlockPos $$1) {
        String $$2 = $$1.toShortString();
        this.markers.put($$0, new Marker(1610678016, $$2, Util.getMillis() + 10000));
    }

    public void clear() {
        this.markers.clear();
    }

    public void emitGizmos() {
        long $$0 = Util.getMillis();
        this.markers.entrySet().removeIf($$1 -> {
            return $$0 > ((Marker) $$1.getValue()).removeAtTime;
        });
        this.markers.forEach(($$02, $$12) -> {
            renderMarker($$02, $$12);
        });
    }

    private void renderMarker(BlockPos $$0, Marker $$1) {
        Gizmos.cuboid($$0, 0.02f, GizmoStyle.fill($$1.color()));
        if (!$$1.text.isEmpty()) {
            Gizmos.billboardText($$1.text, Vec3.atLowerCornerWithOffset($$0, 0.5d, 1.2d, 0.5d), TextGizmo.Style.whiteAndCentered().withScale(0.16f)).setAlwaysOnTop();
        }
    }
}
