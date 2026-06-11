package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/Lighting.class */
public class Lighting implements AutoCloseable {
    private static final Vector3f DIFFUSE_LIGHT_0 = new Vector3f(0.2f, 1.0f, -0.7f).normalize();
    private static final Vector3f DIFFUSE_LIGHT_1 = new Vector3f(-0.2f, 1.0f, 0.7f).normalize();
    private static final Vector3f NETHER_DIFFUSE_LIGHT_0 = new Vector3f(0.2f, 1.0f, -0.7f).normalize();
    private static final Vector3f NETHER_DIFFUSE_LIGHT_1 = new Vector3f(-0.2f, -1.0f, 0.7f).normalize();
    private static final Vector3f INVENTORY_DIFFUSE_LIGHT_0 = new Vector3f(0.2f, -1.0f, 1.0f).normalize();
    private static final Vector3f INVENTORY_DIFFUSE_LIGHT_1 = new Vector3f(-0.2f, -1.0f, 0.0f).normalize();
    public static final int UBO_SIZE = new Std140SizeCalculator().putVec3().putVec3().get();
    private final GpuBuffer buffer;
    private final long paddedSize;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/platform/Lighting$Entry.class */
    public enum Entry {
        LEVEL,
        ITEMS_FLAT,
        ITEMS_3D,
        ENTITY_IN_UI,
        PLAYER_SKIN
    }

    public Lighting() {
        GpuDevice $$0 = RenderSystem.getDevice();
        this.paddedSize = Mth.roundToward(UBO_SIZE, $$0.getUniformOffsetAlignment());
        this.buffer = $$0.createBuffer(() -> {
            return "Lighting UBO";
        }, 136, this.paddedSize * ((long) Entry.values().length));
        Matrix4f $$1 = new Matrix4f().rotationY(-0.3926991f).rotateX(2.3561945f);
        updateBuffer(Entry.ITEMS_FLAT, $$1.transformDirection(DIFFUSE_LIGHT_0, new Vector3f()), $$1.transformDirection(DIFFUSE_LIGHT_1, new Vector3f()));
        Matrix4f $$2 = new Matrix4f().scaling(1.0f, -1.0f, 1.0f).rotateYXZ(1.0821041f, 3.2375858f, 0.0f).rotateYXZ(-0.3926991f, 2.3561945f, 0.0f);
        updateBuffer(Entry.ITEMS_3D, $$2.transformDirection(DIFFUSE_LIGHT_0, new Vector3f()), $$2.transformDirection(DIFFUSE_LIGHT_1, new Vector3f()));
        updateBuffer(Entry.ENTITY_IN_UI, INVENTORY_DIFFUSE_LIGHT_0, INVENTORY_DIFFUSE_LIGHT_1);
        Matrix4f $$3 = new Matrix4f();
        updateBuffer(Entry.PLAYER_SKIN, $$3.transformDirection(INVENTORY_DIFFUSE_LIGHT_0, new Vector3f()), $$3.transformDirection(INVENTORY_DIFFUSE_LIGHT_1, new Vector3f()));
    }

    public void updateLevel(DimensionType.CardinalLightType $$0) {
        switch ($$0) {
            case DEFAULT:
                updateBuffer(Entry.LEVEL, DIFFUSE_LIGHT_0, DIFFUSE_LIGHT_1);
                break;
            case NETHER:
                updateBuffer(Entry.LEVEL, NETHER_DIFFUSE_LIGHT_0, NETHER_DIFFUSE_LIGHT_1);
                break;
        }
    }

    private void updateBuffer(Entry $$0, Vector3f $$1, Vector3f $$2) {
        MemoryStack $$3 = MemoryStack.stackPush();
        try {
            ByteBuffer $$4 = Std140Builder.onStack($$3, UBO_SIZE).putVec3($$1).putVec3($$2).get();
            RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.buffer.slice(((long) $$0.ordinal()) * this.paddedSize, this.paddedSize), $$4);
            if ($$3 != null) {
                $$3.close();
            }
        } catch (Throwable th) {
            if ($$3 != null) {
                try {
                    $$3.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void setupFor(Entry $$0) {
        RenderSystem.setShaderLights(this.buffer.slice(((long) $$0.ordinal()) * this.paddedSize, UBO_SIZE));
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.buffer.close();
    }
}
