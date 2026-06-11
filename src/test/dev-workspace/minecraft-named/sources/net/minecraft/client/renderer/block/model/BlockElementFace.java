package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.math.Quadrant;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Type;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementFace.class */
public final class BlockElementFace extends Record {
    private final Direction cullForDirection;
    private final int tintIndex;
    private final String texture;
    private final UVs uvs;
    private final Quadrant rotation;
    public static final int NO_TINT = -1;

    public BlockElementFace(Direction $$0, int $$1, String $$2, UVs $$3, Quadrant $$4) {
        this.cullForDirection = $$0;
        this.tintIndex = $$1;
        this.texture = $$2;
        this.uvs = $$3;
        this.rotation = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockElementFace.class), BlockElementFace.class, "cullForDirection;tintIndex;texture;uvs;rotation", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->cullForDirection:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->tintIndex:I", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->texture:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->uvs:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->rotation:Lcom/mojang/math/Quadrant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockElementFace.class), BlockElementFace.class, "cullForDirection;tintIndex;texture;uvs;rotation", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->cullForDirection:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->tintIndex:I", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->texture:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->uvs:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->rotation:Lcom/mojang/math/Quadrant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockElementFace.class, Object.class), BlockElementFace.class, "cullForDirection;tintIndex;texture;uvs;rotation", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->cullForDirection:Lnet/minecraft/core/Direction;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->tintIndex:I", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->texture:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->uvs:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace;->rotation:Lcom/mojang/math/Quadrant;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Direction cullForDirection() {
        return this.cullForDirection;
    }

    public int tintIndex() {
        return this.tintIndex;
    }

    public String texture() {
        return this.texture;
    }

    public UVs uvs() {
        return this.uvs;
    }

    public Quadrant rotation() {
        return this.rotation;
    }

    public static float getU(UVs $$0, Quadrant $$1, int $$2) {
        return $$0.getVertexU($$1.rotateVertexIndex($$2)) / 16.0f;
    }

    public static float getV(UVs $$0, Quadrant $$1, int $$2) {
        return $$0.getVertexV($$1.rotateVertexIndex($$2)) / 16.0f;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementFace$UVs.class */
    public static final class UVs extends Record {
        private final float minU;
        private final float minV;
        private final float maxU;
        private final float maxV;

        public UVs(float $$0, float $$1, float $$2, float $$3) {
            this.minU = $$0;
            this.minV = $$1;
            this.maxU = $$2;
            this.maxV = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UVs.class), UVs.class, "minU;minV;maxU;maxV", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->minU:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->minV:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->maxU:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->maxV:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UVs.class), UVs.class, "minU;minV;maxU;maxV", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->minU:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->minV:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->maxU:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->maxV:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UVs.class, Object.class), UVs.class, "minU;minV;maxU;maxV", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->minU:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->minV:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->maxU:F", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElementFace$UVs;->maxV:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float minU() {
            return this.minU;
        }

        public float minV() {
            return this.minV;
        }

        public float maxU() {
            return this.maxU;
        }

        public float maxV() {
            return this.maxV;
        }

        public float getVertexU(int $$0) {
            return ($$0 == 0 || $$0 == 1) ? this.minU : this.maxU;
        }

        public float getVertexV(int $$0) {
            return ($$0 == 0 || $$0 == 3) ? this.minV : this.maxV;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElementFace$Deserializer.class */
    protected static class Deserializer implements JsonDeserializer<BlockElementFace> {
        private static final int DEFAULT_TINT_INDEX = -1;
        private static final int DEFAULT_ROTATION = 0;

        protected Deserializer() {
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
        public BlockElementFace m809deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException, JsonSyntaxException {
            JsonObject $$3 = $$0.getAsJsonObject();
            Direction $$4 = getCullFacing($$3);
            int $$5 = getTintIndex($$3);
            String $$6 = getTexture($$3);
            UVs $$7 = getUVs($$3);
            Quadrant $$8 = getRotation($$3);
            return new BlockElementFace($$4, $$5, $$6, $$7, $$8);
        }

        private static int getTintIndex(JsonObject $$0) {
            return GsonHelper.getAsInt($$0, "tintindex", -1);
        }

        private static String getTexture(JsonObject $$0) {
            return GsonHelper.getAsString($$0, "texture");
        }

        private static Direction getCullFacing(JsonObject $$0) {
            String $$1 = GsonHelper.getAsString($$0, "cullface", "");
            return Direction.byName($$1);
        }

        private static Quadrant getRotation(JsonObject $$0) {
            int $$1 = GsonHelper.getAsInt($$0, "rotation", 0);
            return Quadrant.parseJson($$1);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private static UVs getUVs(JsonObject $$0) throws JsonParseException, JsonSyntaxException {
            if (!$$0.has("uv")) {
                return null;
            }
            JsonArray $$1 = GsonHelper.getAsJsonArray($$0, "uv");
            if ($$1.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + $$1.size());
            }
            float $$2 = GsonHelper.convertToFloat($$1.get(0), "minU");
            float $$3 = GsonHelper.convertToFloat($$1.get(1), "minV");
            float $$4 = GsonHelper.convertToFloat($$1.get(2), "maxU");
            float $$5 = GsonHelper.convertToFloat($$1.get(3), "maxV");
            return new UVs($$2, $$3, $$4, $$5);
        }
    }
}
