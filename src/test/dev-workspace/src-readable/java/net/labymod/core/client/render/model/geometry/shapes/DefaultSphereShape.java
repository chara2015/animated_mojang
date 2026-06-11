package net.labymod.core.client.render.model.geometry.shapes;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.render.model.geometry.BoundingBox;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.SphereShape;
import net.labymod.core.client.render.model.geometry.DefaultBoundingBox;
import net.labymod.core.client.render.model.geometry.DefaultShapeQuad;
import net.labymod.core.client.render.model.geometry.DefaultShapeVertex;
import net.labymod.core.client.render.model.geometry.DefaultUVBounds;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultSphereShape.class */
public class DefaultSphereShape implements SphereShape {
    private final ShapeQuad[] quads;
    private final BoundingBox bounds;
    private final float radius;
    private final int longitudeSegments;
    private final int latitudeSegments;

    public DefaultSphereShape(float centerX, float centerY, float centerZ, float radius, int longitudeSegments, int latitudeSegments, int texU, int texV, int texWidth, int texHeight) {
        this.radius = radius;
        this.longitudeSegments = Math.max(3, longitudeSegments);
        this.latitudeSegments = Math.max(2, latitudeSegments);
        List<ShapeQuad> quadList = new ArrayList<>();
        float lonStep = (float) (6.283185307179586d / ((double) this.longitudeSegments));
        float latStep = (float) (3.141592653589793d / ((double) this.latitudeSegments));
        generatePoleCap(quadList, centerX, centerY, centerZ, radius, latStep, lonStep, true);
        for (int lat = 1; lat < this.latitudeSegments - 1; lat++) {
            float theta1 = lat * latStep;
            float theta2 = (lat + 1) * latStep;
            for (int lon = 0; lon < this.longitudeSegments; lon++) {
                float phi1 = lon * lonStep;
                float phi2 = (lon + 1) * lonStep;
                ShapeVertex v00 = createVertex(centerX, centerY, centerZ, radius, theta1, phi1, lon / this.longitudeSegments, lat / this.latitudeSegments);
                ShapeVertex v01 = createVertex(centerX, centerY, centerZ, radius, theta1, phi2, (lon + 1) / this.longitudeSegments, lat / this.latitudeSegments);
                ShapeVertex v10 = createVertex(centerX, centerY, centerZ, radius, theta2, phi1, lon / this.longitudeSegments, (lat + 1) / this.latitudeSegments);
                ShapeVertex v11 = createVertex(centerX, centerY, centerZ, radius, theta2, phi2, (lon + 1) / this.longitudeSegments, (lat + 1) / this.latitudeSegments);
                Vector3f normal = new Vector3f();
                normal.add(calculateNormal(theta1, phi1));
                normal.add(calculateNormal(theta1, phi2));
                normal.add(calculateNormal(theta2, phi1));
                normal.add(calculateNormal(theta2, phi2));
                normal.normalize();
                ShapeVertex[] vertices = {v00, v10, v11, v01};
                quadList.add(new DefaultShapeQuad(vertices, normal, new DefaultUVBounds(lon / this.longitudeSegments, lat / this.latitudeSegments, (lon + 1) / this.longitudeSegments, (lat + 1) / this.latitudeSegments)));
            }
        }
        generatePoleCap(quadList, centerX, centerY, centerZ, radius, latStep, lonStep, false);
        this.quads = (ShapeQuad[]) quadList.toArray(new ShapeQuad[0]);
        this.bounds = new DefaultBoundingBox(centerX - radius, centerY - radius, centerZ - radius, centerX + radius, centerY + radius, centerZ + radius);
    }

    private void generatePoleCap(List<ShapeQuad> quadList, float cx, float cy, float cz, float r, float latStep, float lonStep, boolean isTop) {
        float f;
        ShapeVertex[] vertices;
        ShapeVertex[] shapeVertexArr;
        float poleTheta = isTop ? 0.0f : 3.1415927f;
        float ringTheta = isTop ? latStep : 3.1415927f - latStep;
        float poleLat = isTop ? 0.0f : 1.0f;
        if (isTop) {
            f = 1.0f / this.latitudeSegments;
        } else {
            f = (this.latitudeSegments - 1) / this.latitudeSegments;
        }
        float ringLat = f;
        ShapeVertex pole = createVertex(cx, cy, cz, r, poleTheta, 0.0f, 0.5f, poleLat);
        Vector3f poleNormal = calculateNormal(poleTheta, 0.0f);
        int lon = 0;
        while (lon + 1 < this.longitudeSegments) {
            ShapeVertex r0 = createRingVertex(cx, cy, cz, r, ringTheta, lon, lonStep, ringLat);
            ShapeVertex r1 = createRingVertex(cx, cy, cz, r, ringTheta, lon + 1, lonStep, ringLat);
            ShapeVertex r2 = createRingVertex(cx, cy, cz, r, ringTheta, lon + 2, lonStep, ringLat);
            Vector3f normal = new Vector3f(poleNormal);
            normal.add(calculateNormal(ringTheta, lon * lonStep));
            normal.add(calculateNormal(ringTheta, (lon + 1) * lonStep));
            normal.add(calculateNormal(ringTheta, (lon + 2) * lonStep));
            normal.normalize();
            float uMin = lon / this.longitudeSegments;
            float uMax = (lon + 2) / this.longitudeSegments;
            if (isTop) {
                shapeVertexArr = new ShapeVertex[]{r0, r1, r2, pole};
            } else {
                shapeVertexArr = new ShapeVertex[]{pole, r2, r1, r0};
            }
            ShapeVertex[] vertices2 = shapeVertexArr;
            quadList.add(new DefaultShapeQuad(vertices2, normal, new DefaultUVBounds(uMin, Math.min(poleLat, ringLat), uMax, Math.max(poleLat, ringLat))));
            lon += 2;
        }
        if (lon < this.longitudeSegments) {
            ShapeVertex r02 = createRingVertex(cx, cy, cz, r, ringTheta, lon, lonStep, ringLat);
            ShapeVertex r12 = createRingVertex(cx, cy, cz, r, ringTheta, lon + 1, lonStep, ringLat);
            float midTheta = (poleTheta + ringTheta) * 0.5f;
            float midPhi = (lon + 0.5f) * lonStep;
            float midU = (lon + 0.5f) / this.longitudeSegments;
            float midV = (poleLat + ringLat) * 0.5f;
            ShapeVertex mid = createVertex(cx, cy, cz, r, midTheta, midPhi, midU, midV);
            Vector3f normal2 = new Vector3f(poleNormal);
            normal2.add(calculateNormal(ringTheta, lon * lonStep));
            normal2.add(calculateNormal(ringTheta, (lon + 1) * lonStep));
            normal2.normalize();
            float uMin2 = lon / this.longitudeSegments;
            float uMax2 = (lon + 1) / this.longitudeSegments;
            if (isTop) {
                vertices = new ShapeVertex[]{r02, r12, pole, mid};
            } else {
                vertices = new ShapeVertex[]{pole, r12, r02, mid};
            }
            quadList.add(new DefaultShapeQuad(vertices, normal2, new DefaultUVBounds(uMin2, Math.min(poleLat, ringLat), uMax2, Math.max(poleLat, ringLat))));
        }
    }

    private ShapeVertex createRingVertex(float cx, float cy, float cz, float r, float theta, int lon, float lonStep, float vCoord) {
        float phi = lon * lonStep;
        float u = lon / this.longitudeSegments;
        return createVertex(cx, cy, cz, r, theta, phi, u, vCoord);
    }

    private ShapeVertex createVertex(float cx, float cy, float cz, float r, float theta, float phi, float u, float v) {
        float sinTheta = (float) Math.sin(theta);
        float cosTheta = (float) Math.cos(theta);
        float sinPhi = (float) Math.sin(phi);
        float cosPhi = (float) Math.cos(phi);
        float x = cx + (r * sinTheta * cosPhi);
        float y = cy + (r * cosTheta);
        float z = cz + (r * sinTheta * sinPhi);
        return new DefaultShapeVertex(x, y, z, u, v);
    }

    private Vector3f calculateNormal(float theta, float phi) {
        float sinTheta = (float) Math.sin(theta);
        float cosTheta = (float) Math.cos(theta);
        float sinPhi = (float) Math.sin(phi);
        float cosPhi = (float) Math.cos(phi);
        return new Vector3f(sinTheta * cosPhi, cosTheta, sinTheta * sinPhi);
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public ShapeQuad[] getQuads() {
        return this.quads;
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public BoundingBox getBounds() {
        return this.bounds;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape
    public float getRadius() {
        return this.radius;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape
    public int getLongitudeSegments() {
        return this.longitudeSegments;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape
    public int getLatitudeSegments() {
        return this.latitudeSegments;
    }
}
