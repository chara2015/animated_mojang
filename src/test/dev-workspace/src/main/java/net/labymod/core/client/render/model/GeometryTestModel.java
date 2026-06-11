package net.labymod.core.client.render.model;

import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.builder.ModelBuilder;
import net.labymod.api.client.render.model.builder.ModelPartBuilder;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.CubeShape;
import net.labymod.api.client.render.model.geometry.shapes.CylinderShape;
import net.labymod.api.client.render.model.geometry.shapes.MeshShape;
import net.labymod.api.client.render.model.geometry.shapes.PlaneShape;
import net.labymod.api.client.render.model.geometry.shapes.SphereShape;
import net.labymod.api.util.math.Direction;
import net.labymod.core.client.render.model.geometry.DefaultShapeVertex;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/GeometryTestModel.class */
public final class GeometryTestModel {
    private static final int TEXTURE_SIZE = 64;
    private static final float SPACING = 12.0f;

    private GeometryTestModel() {
    }

    public static Model create() {
        ModelBuilder builder = ModelBuilder.create().textureSize(64, 64);
        ModelPartBuilder rootBuilder = builder.addPart("root");
        rootBuilder.addChild("cube").addShape(CubeShape.builder().origin(-4.0f, 0.0f, -4.0f).size(8.0f, 8.0f, 8.0f).textureOffset(0, 0).textureSize(64, 64).build()).pivot(0.0f, 0.0f, 0.0f).end();
        float offset = 0.0f + SPACING;
        rootBuilder.addChild("cube_inflated").addShape(CubeShape.builder().origin(-4.0f, 0.0f, -4.0f).size(8.0f, 8.0f, 8.0f).grow(0.5f).textureOffset(0, 16).textureSize(64, 64).mirror(true).build()).pivot(offset, 0.0f, 0.0f).end();
        float offset2 = offset + SPACING;
        rootBuilder.addChild("sphere").addShape(SphereShape.builder().origin(0.0f, 4.0f, 0.0f).radius(4.0f).segments(16, 8).textureOffset(0, 0).textureSize(64, 64).build()).pivot(offset2, 0.0f, 0.0f).end();
        float offset3 = offset2 + SPACING;
        rootBuilder.addChild("cylinder_capped").addShape(CylinderShape.builder().origin(0.0f, 0.0f, 0.0f).radius(3.0f).height(8.0f).segments(12).caps(true, true).textureOffset(0, 0).textureSize(64, 64).build()).pivot(offset3, 0.0f, 0.0f).end();
        float offset4 = offset3 + SPACING;
        rootBuilder.addChild("cylinder_open").addShape(CylinderShape.builder().origin(0.0f, 0.0f, 0.0f).radius(3.0f).height(8.0f).segments(12).caps(false, false).textureOffset(0, 0).textureSize(64, 64).build()).pivot(offset4, 0.0f, 0.0f).end();
        float offset5 = offset4 + SPACING;
        rootBuilder.addChild("plane_single").addShape(PlaneShape.builder().origin(-4.0f, 0.0f, -4.0f).size(8.0f, 8.0f).normal(Direction.UP).doubleSided(false).textureOffset(0, 0).textureSize(64, 64).build()).pivot(offset5, 0.0f, 0.0f).end();
        float offset6 = offset5 + SPACING;
        rootBuilder.addChild("plane_double").addShape(PlaneShape.builder().origin(-4.0f, 0.0f, 0.0f).size(8.0f, 8.0f).normal(Direction.NORTH).doubleSided(true).textureOffset(0, 0).textureSize(64, 64).build()).pivot(offset6, 0.0f, 0.0f).end();
        rootBuilder.addChild("mesh").addShape(createDiamondMesh()).pivot(offset6 + SPACING, 0.0f, 0.0f).end();
        return builder.build();
    }

    private static MeshShape createDiamondMesh() {
        ShapeVertex top = new DefaultShapeVertex(0.0f, 8.0f, 0.0f, 0.5f, 0.0f);
        ShapeVertex left = new DefaultShapeVertex(-4.0f, 4.0f, 0.0f, 0.0f, 0.5f);
        ShapeVertex right = new DefaultShapeVertex(4.0f, 4.0f, 0.0f, 1.0f, 0.5f);
        ShapeVertex bottom = new DefaultShapeVertex(0.0f, 0.0f, 0.0f, 0.5f, 1.0f);
        Vector3f normal = new Vector3f(0.0f, 0.0f, 1.0f);
        return MeshShape.builder().addTriangle(top, left, bottom, normal).addTriangle(top, bottom, right, normal).build();
    }
}
