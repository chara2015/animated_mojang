package net.minecraft.client.renderer.block.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BakedQuad.class */
public final class BakedQuad extends Record {
    private final Vector3fc position0;
    private final Vector3fc position1;
    private final Vector3fc position2;
    private final Vector3fc position3;
    private final long packedUV0;
    private final long packedUV1;
    private final long packedUV2;
    private final long packedUV3;
    private final int tintIndex;
    private final Direction direction;
    private final TextureAtlasSprite sprite;
    private final boolean shade;
    private final int lightEmission;
    public static final int VERTEX_COUNT = 4;

    public BakedQuad(Vector3fc $$0, Vector3fc $$1, Vector3fc $$2, Vector3fc $$3, long $$4, long $$5, long $$6, long $$7, int $$8, Direction $$9, TextureAtlasSprite $$10, boolean $$11, int $$12) {
        this.position0 = $$0;
        this.position1 = $$1;
        this.position2 = $$2;
        this.position3 = $$3;
        this.packedUV0 = $$4;
        this.packedUV1 = $$5;
        this.packedUV2 = $$6;
        this.packedUV3 = $$7;
        this.tintIndex = $$8;
        this.direction = $$9;
        this.sprite = $$10;
        this.shade = $$11;
        this.lightEmission = $$12;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BakedQuad.class), BakedQuad.class, "position0;position1;position2;position3;packedUV0;packedUV1;packedUV2;packedUV3;tintIndex;direction;sprite;shade;lightEmission", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position0:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position1:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position2:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position3:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV0:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV1:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV2:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV3:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->tintIndex:I", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->direction:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->shade:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->lightEmission:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BakedQuad.class), BakedQuad.class, "position0;position1;position2;position3;packedUV0;packedUV1;packedUV2;packedUV3;tintIndex;direction;sprite;shade;lightEmission", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position0:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position1:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position2:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position3:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV0:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV1:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV2:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV3:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->tintIndex:I", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->direction:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->shade:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->lightEmission:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BakedQuad.class, Object.class), BakedQuad.class, "position0;position1;position2;position3;packedUV0;packedUV1;packedUV2;packedUV3;tintIndex;direction;sprite;shade;lightEmission", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position0:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position1:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position2:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->position3:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV0:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV1:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV2:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->packedUV3:J", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->tintIndex:I", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->direction:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->shade:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BakedQuad;->lightEmission:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vector3fc position0() {
        return this.position0;
    }

    public Vector3fc position1() {
        return this.position1;
    }

    public Vector3fc position2() {
        return this.position2;
    }

    public Vector3fc position3() {
        return this.position3;
    }

    public long packedUV0() {
        return this.packedUV0;
    }

    public long packedUV1() {
        return this.packedUV1;
    }

    public long packedUV2() {
        return this.packedUV2;
    }

    public long packedUV3() {
        return this.packedUV3;
    }

    public int tintIndex() {
        return this.tintIndex;
    }

    public Direction direction() {
        return this.direction;
    }

    public TextureAtlasSprite sprite() {
        return this.sprite;
    }

    public boolean shade() {
        return this.shade;
    }

    public int lightEmission() {
        return this.lightEmission;
    }

    public boolean isTinted() {
        return this.tintIndex != -1;
    }

    public Vector3fc position(int $$0) {
        switch ($$0) {
            case 0:
                return this.position0;
            case 1:
                return this.position1;
            case 2:
                return this.position2;
            case 3:
                return this.position3;
            default:
                throw new IndexOutOfBoundsException($$0);
        }
    }

    public long packedUV(int $$0) {
        switch ($$0) {
            case 0:
                return this.packedUV0;
            case 1:
                return this.packedUV1;
            case 2:
                return this.packedUV2;
            case 3:
                return this.packedUV3;
            default:
                throw new IndexOutOfBoundsException($$0);
        }
    }
}
