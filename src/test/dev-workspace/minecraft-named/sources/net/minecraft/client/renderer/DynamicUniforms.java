package net.minecraft.client.renderer;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.DynamicUniformStorage;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/DynamicUniforms.class */
public class DynamicUniforms implements AutoCloseable {
    public static final int TRANSFORM_UBO_SIZE = new Std140SizeCalculator().putMat4f().putVec4().putVec3().putMat4f().get();
    public static final int CHUNK_SECTION_UBO_SIZE = new Std140SizeCalculator().putMat4f().putFloat().putIVec2().putIVec3().get();
    private static final int INITIAL_CAPACITY = 2;
    private final DynamicUniformStorage<Transform> transforms = new DynamicUniformStorage<>("Dynamic Transforms UBO", TRANSFORM_UBO_SIZE, 2);
    private final DynamicUniformStorage<ChunkSectionInfo> chunkSections = new DynamicUniformStorage<>("Chunk Sections UBO", CHUNK_SECTION_UBO_SIZE, 2);

    public void reset() {
        this.transforms.endFrame();
        this.chunkSections.endFrame();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.transforms.close();
        this.chunkSections.close();
    }

    public GpuBufferSlice writeTransform(Matrix4fc $$0, Vector4fc $$1, Vector3fc $$2, Matrix4fc $$3) {
        return this.transforms.writeUniform(new Transform(new Matrix4f($$0), new Vector4f($$1), new Vector3f($$2), new Matrix4f($$3)));
    }

    public GpuBufferSlice[] writeTransforms(Transform... $$0) {
        return this.transforms.writeUniforms($$0);
    }

    public GpuBufferSlice[] writeChunkSections(ChunkSectionInfo... $$0) {
        return this.chunkSections.writeUniforms($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/DynamicUniforms$Transform.class */
    public static final class Transform extends Record implements DynamicUniformStorage.DynamicUniform {
        private final Matrix4fc modelView;
        private final Vector4fc colorModulator;
        private final Vector3fc modelOffset;
        private final Matrix4fc textureMatrix;

        public Transform(Matrix4fc $$0, Vector4fc $$1, Vector3fc $$2, Matrix4fc $$3) {
            this.modelView = $$0;
            this.colorModulator = $$1;
            this.modelOffset = $$2;
            this.textureMatrix = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Transform.class), Transform.class, "modelView;colorModulator;modelOffset;textureMatrix", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->modelView:Lorg/joml/Matrix4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->colorModulator:Lorg/joml/Vector4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->modelOffset:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->textureMatrix:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Transform.class), Transform.class, "modelView;colorModulator;modelOffset;textureMatrix", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->modelView:Lorg/joml/Matrix4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->colorModulator:Lorg/joml/Vector4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->modelOffset:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->textureMatrix:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Transform.class, Object.class), Transform.class, "modelView;colorModulator;modelOffset;textureMatrix", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->modelView:Lorg/joml/Matrix4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->colorModulator:Lorg/joml/Vector4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->modelOffset:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$Transform;->textureMatrix:Lorg/joml/Matrix4fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4fc modelView() {
            return this.modelView;
        }

        public Vector4fc colorModulator() {
            return this.colorModulator;
        }

        public Vector3fc modelOffset() {
            return this.modelOffset;
        }

        public Matrix4fc textureMatrix() {
            return this.textureMatrix;
        }

        @Override // net.minecraft.client.renderer.DynamicUniformStorage.DynamicUniform
        public void write(ByteBuffer $$0) {
            Std140Builder.intoBuffer($$0).putMat4f(this.modelView).putVec4(this.colorModulator).putVec3(this.modelOffset).putMat4f(this.textureMatrix);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo.class */
    public static final class ChunkSectionInfo extends Record implements DynamicUniformStorage.DynamicUniform {
        private final Matrix4fc modelView;
        private final int x;
        private final int y;
        private final int z;
        private final float visibility;
        private final int textureAtlasWidth;
        private final int textureAtlasHeight;

        public ChunkSectionInfo(Matrix4fc $$0, int $$1, int $$2, int $$3, float $$4, int $$5, int $$6) {
            this.modelView = $$0;
            this.x = $$1;
            this.y = $$2;
            this.z = $$3;
            this.visibility = $$4;
            this.textureAtlasWidth = $$5;
            this.textureAtlasHeight = $$6;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChunkSectionInfo.class), ChunkSectionInfo.class, "modelView;x;y;z;visibility;textureAtlasWidth;textureAtlasHeight", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->modelView:Lorg/joml/Matrix4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->x:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->y:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->z:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->visibility:F", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->textureAtlasWidth:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->textureAtlasHeight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChunkSectionInfo.class), ChunkSectionInfo.class, "modelView;x;y;z;visibility;textureAtlasWidth;textureAtlasHeight", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->modelView:Lorg/joml/Matrix4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->x:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->y:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->z:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->visibility:F", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->textureAtlasWidth:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->textureAtlasHeight:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChunkSectionInfo.class, Object.class), ChunkSectionInfo.class, "modelView;x;y;z;visibility;textureAtlasWidth;textureAtlasHeight", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->modelView:Lorg/joml/Matrix4fc;", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->x:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->y:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->z:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->visibility:F", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->textureAtlasWidth:I", "FIELD:Lnet/minecraft/client/renderer/DynamicUniforms$ChunkSectionInfo;->textureAtlasHeight:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4fc modelView() {
            return this.modelView;
        }

        public int x() {
            return this.x;
        }

        public int y() {
            return this.y;
        }

        public int z() {
            return this.z;
        }

        public float visibility() {
            return this.visibility;
        }

        public int textureAtlasWidth() {
            return this.textureAtlasWidth;
        }

        public int textureAtlasHeight() {
            return this.textureAtlasHeight;
        }

        @Override // net.minecraft.client.renderer.DynamicUniformStorage.DynamicUniform
        public void write(ByteBuffer $$0) {
            Std140Builder.intoBuffer($$0).putMat4f(this.modelView).putFloat(this.visibility).putIVec2(this.textureAtlasWidth, this.textureAtlasHeight).putIVec3(this.x, this.y, this.z);
        }
    }
}
