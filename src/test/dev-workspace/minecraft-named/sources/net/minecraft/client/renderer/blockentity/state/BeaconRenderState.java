package net.minecraft.client.renderer.blockentity.state;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BeaconRenderState.class */
public class BeaconRenderState extends BlockEntityRenderState {
    public float animationTime;
    public float beamRadiusScale;
    public List<Section> sections = new ArrayList();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section.class */
    public static final class Section extends Record {
        private final int color;
        private final int height;

        public Section(int $$0, int $$1) {
            this.color = $$0;
            this.height = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Section.class), Section.class, "color;height", "FIELD:Lnet/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section;->color:I", "FIELD:Lnet/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Section.class), Section.class, "color;height", "FIELD:Lnet/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section;->color:I", "FIELD:Lnet/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section;->height:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Section.class, Object.class), Section.class, "color;height", "FIELD:Lnet/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section;->color:I", "FIELD:Lnet/minecraft/client/renderer/blockentity/state/BeaconRenderState$Section;->height:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int color() {
            return this.color;
        }

        public int height() {
            return this.height;
        }
    }
}
