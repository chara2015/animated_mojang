package net.labymod.core.client.render.schematic.lighting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.labymod.api.util.math.MathHelper;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/LightSourceRegistry.class */
public final class LightSourceRegistry {
    private static LightSourceRegistry instance;
    private final Vector3f previousCameraPosition = new Vector3f();
    private final List<LightSource> lightSources = new ArrayList();

    public static LightSourceRegistry getInstance() {
        if (instance == null) {
            instance = new LightSourceRegistry();
        }
        return instance;
    }

    public void sort(Vector3f cameraPosition) {
        if (this.previousCameraPosition.equals(cameraPosition)) {
            return;
        }
        this.previousCameraPosition.set(cameraPosition);
        this.lightSources.sort(Comparator.comparingDouble(value -> {
            Vector3f position = value.getPosition();
            float horizontalDistance = MathHelper.square(position.x() - cameraPosition.x()) + MathHelper.square(position.z() - cameraPosition.z());
            float verticalDistance = MathHelper.square(position.y() - cameraPosition.y());
            return (verticalDistance * 100.0f) + horizontalDistance;
        }));
    }

    public void addLightSource(LightSource lightSource) {
        if (this.lightSources.contains(lightSource)) {
            return;
        }
        this.lightSources.add(lightSource);
        this.previousCameraPosition.set(0.0f, 0.0f, 0.0f);
    }

    public void remove(PointLightSource source) {
        this.lightSources.remove(source);
    }

    public void reset() {
        this.lightSources.clear();
        this.previousCameraPosition.set(0.0f, 0.0f, 0.0f);
    }

    public void tick() {
        for (LightSource lightSource : this.lightSources) {
            lightSource.tick();
        }
    }

    public List<LightSource> getLightSources() {
        return this.lightSources;
    }
}
