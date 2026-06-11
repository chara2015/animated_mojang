package com.mojang.blaze3d.audio;

import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/audio/Listener.class */
public class Listener {
    private ListenerTransform transform = ListenerTransform.INITIAL;

    public void setTransform(ListenerTransform $$0) {
        this.transform = $$0;
        Vec3 $$1 = $$0.position();
        Vec3 $$2 = $$0.forward();
        Vec3 $$3 = $$0.up();
        AL10.alListener3f(4100, (float) $$1.x, (float) $$1.y, (float) $$1.z);
        AL10.alListenerfv(4111, new float[]{(float) $$2.x, (float) $$2.y, (float) $$2.z, (float) $$3.x(), (float) $$3.y(), (float) $$3.z()});
    }

    public void reset() {
        setTransform(ListenerTransform.INITIAL);
    }

    public ListenerTransform getTransform() {
        return this.transform;
    }
}
