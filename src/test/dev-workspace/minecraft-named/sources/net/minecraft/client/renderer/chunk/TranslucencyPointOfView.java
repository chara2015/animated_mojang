package net.minecraft.client.renderer.chunk;

import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/TranslucencyPointOfView.class */
public final class TranslucencyPointOfView {
    private int x;
    private int y;
    private int z;

    public static TranslucencyPointOfView of(Vec3 $$0, long $$1) {
        return new TranslucencyPointOfView().set($$0, $$1);
    }

    public TranslucencyPointOfView set(Vec3 $$0, long $$1) {
        this.x = getCoordinate($$0.x(), SectionPos.x($$1));
        this.y = getCoordinate($$0.y(), SectionPos.y($$1));
        this.z = getCoordinate($$0.z(), SectionPos.z($$1));
        return this;
    }

    private static int getCoordinate(double $$0, int $$1) {
        int $$2 = SectionPos.blockToSectionCoord($$0) - $$1;
        return Mth.clamp($$2, -1, 1);
    }

    public boolean isAxisAligned() {
        return this.x == 0 || this.y == 0 || this.z == 0;
    }

    public boolean equals(Object $$0) {
        if ($$0 == this) {
            return true;
        }
        if (!($$0 instanceof TranslucencyPointOfView)) {
            return false;
        }
        TranslucencyPointOfView $$1 = (TranslucencyPointOfView) $$0;
        return this.x == $$1.x && this.y == $$1.y && this.z == $$1.z;
    }
}
