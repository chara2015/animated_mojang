package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Type;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElement.class */
public final class BlockElement extends Record {
    private final Vector3fc from;
    private final Vector3fc to;
    private final Map<Direction, BlockElementFace> faces;
    private final BlockElementRotation rotation;
    private final boolean shade;
    private final int lightEmission;
    private static final boolean DEFAULT_RESCALE = false;
    private static final float MIN_EXTENT = -16.0f;
    private static final float MAX_EXTENT = 32.0f;

    public BlockElement(Vector3fc $$0, Vector3fc $$1, Map<Direction, BlockElementFace> $$2, BlockElementRotation $$3, boolean $$4, int $$5) {
        this.from = $$0;
        this.to = $$1;
        this.faces = $$2;
        this.rotation = $$3;
        this.shade = $$4;
        this.lightEmission = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockElement.class), BlockElement.class, "from;to;faces;rotation;shade;lightEmission", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->from:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->to:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->faces:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->rotation:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->shade:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->lightEmission:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockElement.class), BlockElement.class, "from;to;faces;rotation;shade;lightEmission", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->from:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->to:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->faces:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->rotation:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->shade:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->lightEmission:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockElement.class, Object.class), BlockElement.class, "from;to;faces;rotation;shade;lightEmission", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->from:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->to:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->faces:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->rotation:Lnet/minecraft/client/renderer/block/model/BlockElementRotation;", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->shade:Z", "FIELD:Lnet/minecraft/client/renderer/block/model/BlockElement;->lightEmission:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vector3fc from() {
        return this.from;
    }

    public Vector3fc to() {
        return this.to;
    }

    public Map<Direction, BlockElementFace> faces() {
        return this.faces;
    }

    public BlockElementRotation rotation() {
        return this.rotation;
    }

    public boolean shade() {
        return this.shade;
    }

    public int lightEmission() {
        return this.lightEmission;
    }

    public BlockElement(Vector3fc $$0, Vector3fc $$1, Map<Direction, BlockElementFace> $$2) {
        this($$0, $$1, $$2, null, true, 0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockElement$Deserializer.class */
    protected static class Deserializer implements JsonDeserializer<BlockElement> {
        private static final boolean DEFAULT_SHADE = true;
        private static final int DEFAULT_LIGHT_EMISSION = 0;
        private static final String FIELD_SHADE = "shade";
        private static final String FIELD_LIGHT_EMISSION = "light_emission";
        private static final String FIELD_ROTATION = "rotation";
        private static final String FIELD_ORIGIN = "origin";
        private static final String FIELD_ANGLE = "angle";
        private static final String FIELD_X = "x";
        private static final String FIELD_Y = "y";
        private static final String FIELD_Z = "z";
        private static final String FIELD_AXIS = "axis";
        private static final String FIELD_RESCALE = "rescale";
        private static final String FIELD_FACES = "faces";
        private static final String FIELD_TO = "to";
        private static final String FIELD_FROM = "from";

        protected Deserializer() {
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
        public BlockElement m808deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException, JsonSyntaxException {
            JsonObject $$3 = $$0.getAsJsonObject();
            Vector3f $$4 = getPosition($$3, FIELD_FROM);
            Vector3f $$5 = getPosition($$3, FIELD_TO);
            BlockElementRotation $$6 = getRotation($$3);
            Map<Direction, BlockElementFace> $$7 = getFaces($$2, $$3);
            if ($$3.has(FIELD_SHADE) && !GsonHelper.isBooleanValue($$3, FIELD_SHADE)) {
                throw new JsonParseException("Expected 'shade' to be a Boolean");
            }
            boolean $$8 = GsonHelper.getAsBoolean($$3, FIELD_SHADE, true);
            int $$9 = 0;
            if ($$3.has(FIELD_LIGHT_EMISSION)) {
                boolean $$10 = GsonHelper.isNumberValue($$3, FIELD_LIGHT_EMISSION);
                if ($$10) {
                    $$9 = GsonHelper.getAsInt($$3, FIELD_LIGHT_EMISSION);
                }
                if (!$$10 || $$9 < 0 || $$9 > 15) {
                    throw new JsonParseException("Expected 'light_emission' to be an Integer between (inclusive) 0 and 15");
                }
            }
            return new BlockElement($$4, $$5, $$7, $$6, $$8, $$9);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private BlockElementRotation getRotation(JsonObject $$0) throws JsonParseException, JsonSyntaxException {
            BlockElementRotation.RotationValue $$10;
            if ($$0.has(FIELD_ROTATION)) {
                JsonObject $$1 = GsonHelper.getAsJsonObject($$0, FIELD_ROTATION);
                Vector3f $$2 = getVector3f($$1, FIELD_ORIGIN);
                $$2.mul(0.0625f);
                if ($$1.has(FIELD_AXIS) || $$1.has(FIELD_ANGLE)) {
                    Direction.Axis $$3 = getAxis($$1);
                    float $$4 = GsonHelper.getAsFloat($$1, FIELD_ANGLE);
                    $$10 = new BlockElementRotation.SingleAxisRotation($$3, $$4);
                } else if ($$1.has(FIELD_X) || $$1.has(FIELD_Y) || $$1.has(FIELD_Z)) {
                    float $$6 = GsonHelper.getAsFloat($$1, FIELD_X, 0.0f);
                    float $$7 = GsonHelper.getAsFloat($$1, FIELD_Y, 0.0f);
                    float $$8 = GsonHelper.getAsFloat($$1, FIELD_Z, 0.0f);
                    $$10 = new BlockElementRotation.EulerXYZRotation($$6, $$7, $$8);
                } else {
                    throw new JsonParseException("Missing rotation value, expected either 'axis' and 'angle' or 'x', 'y' and 'z'");
                }
                boolean $$11 = GsonHelper.getAsBoolean($$1, FIELD_RESCALE, false);
                return new BlockElementRotation($$2, $$10, $$11);
            }
            return null;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private Direction.Axis getAxis(JsonObject $$0) throws JsonParseException, JsonSyntaxException {
            String $$1 = GsonHelper.getAsString($$0, FIELD_AXIS);
            Direction.Axis $$2 = Direction.Axis.byName($$1.toLowerCase(Locale.ROOT));
            if ($$2 == null) {
                throw new JsonParseException("Invalid rotation axis: " + $$1);
            }
            return $$2;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private Map<Direction, BlockElementFace> getFaces(JsonDeserializationContext $$0, JsonObject $$1) throws JsonParseException, JsonSyntaxException {
            Map<Direction, BlockElementFace> $$2 = filterNullFromFaces($$0, $$1);
            if ($$2.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return $$2;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private Map<Direction, BlockElementFace> filterNullFromFaces(JsonDeserializationContext $$0, JsonObject $$1) throws JsonParseException, JsonSyntaxException {
            Map<Direction, BlockElementFace> $$2 = Maps.newEnumMap(Direction.class);
            JsonObject $$3 = GsonHelper.getAsJsonObject($$1, FIELD_FACES);
            for (Map.Entry<String, JsonElement> $$4 : $$3.entrySet()) {
                Direction $$5 = getFacing($$4.getKey());
                $$2.put($$5, (BlockElementFace) $$0.deserialize($$4.getValue(), BlockElementFace.class));
            }
            return $$2;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        private Direction getFacing(String $$0) throws JsonParseException {
            Direction $$1 = Direction.byName($$0);
            if ($$1 == null) {
                throw new JsonParseException("Unknown facing: " + $$0);
            }
            return $$1;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private static Vector3f getPosition(JsonObject $$0, String $$1) throws JsonParseException, JsonSyntaxException {
            Vector3f $$2 = getVector3f($$0, $$1);
            if ($$2.x() < BlockElement.MIN_EXTENT || $$2.y() < BlockElement.MIN_EXTENT || $$2.z() < BlockElement.MIN_EXTENT || $$2.x() > BlockElement.MAX_EXTENT || $$2.y() > BlockElement.MAX_EXTENT || $$2.z() > BlockElement.MAX_EXTENT) {
                throw new JsonParseException("'" + $$1 + "' specifier exceeds the allowed boundaries: " + String.valueOf($$2));
            }
            return $$2;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private static Vector3f getVector3f(JsonObject $$0, String $$1) throws JsonParseException, JsonSyntaxException {
            JsonArray $$2 = GsonHelper.getAsJsonArray($$0, $$1);
            if ($$2.size() != 3) {
                throw new JsonParseException("Expected 3 " + $$1 + " values, found: " + $$2.size());
            }
            float[] $$3 = new float[3];
            for (int $$4 = 0; $$4 < $$3.length; $$4++) {
                $$3[$$4] = GsonHelper.convertToFloat($$2.get($$4), $$1 + "[" + $$4 + "]");
            }
            return new Vector3f($$3[0], $$3[1], $$3[2]);
        }
    }
}
