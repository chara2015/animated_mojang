package net.labymod.core.client.render.schematic.lighting;

import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/PointLightSource.class */
public class PointLightSource implements LightSource {
    public static final Vector3f WHITE = new Vector3f(0.5f, 0.5f, 0.5f);
    private final Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
    private final Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
    private float constant = 1.0f;
    private float linear = 0.09f;
    private float quadratic = 0.0512f;

    @Override // net.labymod.core.client.render.schematic.lighting.LightSource
    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public float getConstant() {
        return this.constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return this.linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getQuadratic() {
        return this.quadratic;
    }

    public void setQuadratic(float quadratic) {
        this.quadratic = quadratic;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointLightSource that = (PointLightSource) o;
        return this.position.equals(that.position);
    }

    public int hashCode() {
        return this.position.hashCode();
    }
}
