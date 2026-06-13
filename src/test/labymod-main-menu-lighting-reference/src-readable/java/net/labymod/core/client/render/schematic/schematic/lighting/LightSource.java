package net.labymod.core.client.render.schematic.lighting;

import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/LightSource.class */
public interface LightSource {
    Vector3f getPosition();

    default void tick() {
    }
}
