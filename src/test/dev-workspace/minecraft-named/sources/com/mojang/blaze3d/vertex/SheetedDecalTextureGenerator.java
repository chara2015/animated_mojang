package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/vertex/SheetedDecalTextureGenerator.class */
public class SheetedDecalTextureGenerator implements VertexConsumer {
    private final VertexConsumer delegate;
    private final Matrix4f cameraInversePose;
    private final Matrix3f normalInversePose;
    private final float textureScale;
    private final Vector3f worldPos = new Vector3f();
    private final Vector3f normal = new Vector3f();
    private float x;
    private float y;
    private float z;

    public SheetedDecalTextureGenerator(VertexConsumer $$0, PoseStack.Pose $$1, float $$2) {
        this.delegate = $$0;
        this.cameraInversePose = new Matrix4f($$1.pose()).invert();
        this.normalInversePose = new Matrix3f($$1.normal()).invert();
        this.textureScale = $$2;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer addVertex(float $$0, float $$1, float $$2) {
        this.x = $$0;
        this.y = $$1;
        this.z = $$2;
        this.delegate.addVertex($$0, $$1, $$2);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setColor(int $$0, int $$1, int $$2, int $$3) {
        this.delegate.setColor(-1);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setColor(int $$0) {
        this.delegate.setColor(-1);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setUv(float $$0, float $$1) {
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setUv1(int $$0, int $$1) {
        this.delegate.setUv1($$0, $$1);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setUv2(int $$0, int $$1) {
        this.delegate.setUv2($$0, $$1);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setNormal(float $$0, float $$1, float $$2) {
        this.delegate.setNormal($$0, $$1, $$2);
        Vector3f $$3 = this.normalInversePose.transform($$0, $$1, $$2, this.normal);
        Direction $$4 = Direction.getApproximateNearest($$3.x(), $$3.y(), $$3.z());
        Vector3f $$5 = this.cameraInversePose.transformPosition(this.x, this.y, this.z, this.worldPos);
        $$5.rotateY(3.1415927f);
        $$5.rotateX(-1.5707964f);
        $$5.rotate($$4.getRotation());
        this.delegate.setUv((-$$5.x()) * this.textureScale, (-$$5.y()) * this.textureScale);
        return this;
    }

    @Override // com.mojang.blaze3d.vertex.VertexConsumer
    public VertexConsumer setLineWidth(float $$0) {
        this.delegate.setLineWidth($$0);
        return this;
    }
}
