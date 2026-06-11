package net.labymod.core.main.user.shop.item.geometry.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.laby.lib.bedrock.animation.Animation;
import net.laby.lib.bedrock.animation.AnimationFile;
import net.laby.lib.bedrock.animation.BoneAnimation;
import net.laby.lib.bedrock.animation.ChannelData;
import net.laby.lib.bedrock.animation.Keyframe;
import net.laby.lib.bedrock.animation.LerpMode;
import net.laby.lib.bedrock.molang.MolangValue;
import net.laby.lib.bedrock.molang.MolangVec3;
import net.labymod.api.client.render.model.animation.DynamicValue;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.client.render.model.animation.molang.MolangDynamicValue;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/animation/AnimationLoader.class */
public class AnimationLoader {
    private final JsonObject tree;
    private final Map<String, ModelAnimation> animations;

    public AnimationLoader(JsonObject tree) {
        Objects.requireNonNull(tree, "Json tree cannot be null");
        this.tree = tree;
        this.animations = new HashMap();
    }

    public AnimationLoader(ResourceLocation resourceLocation) throws IOException {
        this(resourceLocation.openStream());
    }

    public AnimationLoader(InputStream inputStream) throws IOException {
        this((JsonObject) GsonUtil.DEFAULT_GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class));
        inputStream.close();
    }

    public AnimationLoader(String json) {
        this((JsonObject) GsonUtil.DEFAULT_GSON.fromJson(json, JsonObject.class));
    }

    public AnimationLoader(File file) throws IOException {
        this(IOUtil.newInputStream(file.toPath()));
    }

    private AnimationLoader(Map<String, ModelAnimation> animations) {
        this.tree = null;
        this.animations = animations;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static AnimationLoader fromAnimationFile(AnimationFile animationFile, Collection<AnimationMeta<?>> supportedMeta) throws MatchException {
        ModelAnimation modelAnimation;
        Map<String, ModelAnimation> animations = new HashMap<>();
        if (animationFile.animations() == null) {
            return new AnimationLoader(animations);
        }
        for (Map.Entry<String, Animation> entry : animationFile.animations().entrySet()) {
            String animationName = entry.getKey();
            Animation animation = entry.getValue();
            if (animation.bones() != null) {
                if (animation.animationLength() != null) {
                    modelAnimation = new ModelAnimation(animationName, (long) (animation.animationLength().doubleValue() * 1000.0d));
                } else {
                    modelAnimation = new ModelAnimation(animationName);
                }
                if (animation.animTimeUpdate() != null) {
                    String meta = animation.animTimeUpdate();
                    if (!meta.isEmpty()) {
                        String[] args = meta.split(" ");
                        AnimationMeta<?> currentMeta = null;
                        for (String arg : args) {
                            if (currentMeta != null) {
                                modelAnimation.parseAndAddMeta(currentMeta, arg);
                                currentMeta = null;
                            } else {
                                String metaName = arg.replace("-", "");
                                for (AnimationMeta<?> animationMeta : supportedMeta) {
                                    if (animationMeta.getKey().equals(metaName) || animationMeta.getShortcut().equals(metaName)) {
                                        currentMeta = animationMeta;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                for (Map.Entry<String, BoneAnimation> boneEntry : animation.bones().entrySet()) {
                    String boneName = boneEntry.getKey();
                    BoneAnimation boneAnimation = boneEntry.getValue();
                    ModelPartAnimation partAnimation = modelAnimation.getPartAnimation(boneName);
                    extractChannelData(partAnimation.rotation(), boneAnimation.rotation(), "rotation");
                    extractChannelData(partAnimation.position(), boneAnimation.position(), "position");
                    extractChannelData(partAnimation.scale(), boneAnimation.scale(), "scale");
                }
                animations.put(animationName, modelAnimation);
            }
        }
        return new AnimationLoader(animations);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static void extractChannelData(ModelPartTransformation transformation, ChannelData channelData, String key) throws MatchException {
        if (channelData == null) {
            return;
        }
        Objects.requireNonNull(channelData);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), ChannelData.StaticValue.class, ChannelData.SingleKeyframe.class, ChannelData.Timeline.class).dynamicInvoker().invoke(channelData, 0) /* invoke-custom */) {
            case 0:
                ChannelData.StaticValue staticValue = (ChannelData.StaticValue) channelData;
                pushMolangVector(transformation, 0L, staticValue.value(), false, key);
                return;
            case 1:
                ChannelData.SingleKeyframe singleKeyframe = (ChannelData.SingleKeyframe) channelData;
                Keyframe kf = singleKeyframe.keyframe();
                boolean smooth = kf.lerpMode() == LerpMode.CATMULLROM;
                pushMolangVector(transformation, 0L, kf.post(), smooth, key);
                return;
            case 2:
                ChannelData.Timeline timeline = (ChannelData.Timeline) channelData;
                for (Map.Entry<Double, Keyframe> entry : timeline.keyframes().entrySet()) {
                    long offset = (long) (entry.getKey().doubleValue() * 1000.0d);
                    Keyframe kf2 = entry.getValue();
                    boolean smooth2 = kf2.lerpMode() == LerpMode.CATMULLROM;
                    pushMolangVector(transformation, offset, kf2.post(), smooth2, key);
                }
                return;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static void pushMolangVector(ModelPartTransformation storage, long offset, MolangVec3 vector, boolean smooth, String key) throws MatchException {
        float sx = resolveStatic(vector.x(), key, "x");
        float sy = resolveStatic(vector.y(), key, "y");
        float sz = resolveStatic(vector.z(), key, "z");
        DynamicValue dx = toDynamicValue(vector.x(), key, "x");
        DynamicValue dy = toDynamicValue(vector.y(), key, "y");
        DynamicValue dz = toDynamicValue(vector.z(), key, "z");
        if (dx != null || dy != null || dz != null) {
            storage.addKeyframe(new ModelPartTransformation.Keyframe(offset, smooth, sx, sy, sz, dx, dy, dz));
        } else {
            storage.addKeyframe(new ModelPartTransformation.Keyframe(offset, smooth, sx, sy, sz));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static float resolveStatic(MolangValue value, String key, String axis) throws MatchException {
        if (value instanceof MolangValue.Numeric) {
            try {
                double v = ((MolangValue.Numeric) value).value();
                if (1 != 0) {
                    float f = (float) v;
                    if (key.equals("rotation")) {
                        f = (float) Math.toRadians(f);
                    } else if (key.equals("position") && axis.equals("y")) {
                        f = -f;
                    }
                    return f;
                }
                return 0.0f;
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        return 0.0f;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Nullable
    private static DynamicValue toDynamicValue(MolangValue value, String key, String axis) throws MatchException {
        if (!(value instanceof MolangValue.Script)) {
            return null;
        }
        try {
            String expression = ((MolangValue.Script) value).expression();
            if (key.equals("rotation")) {
                return MolangDynamicValue.rotation(expression);
            }
            if (key.equals("position") && axis.equals("y")) {
                return MolangDynamicValue.negated(expression);
            }
            return MolangDynamicValue.of(expression);
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    public AnimationLoader load() {
        return load(new ArrayList());
    }

    public AnimationLoader load(Collection<AnimationMeta<?>> supportedAnimationMeta) {
        ModelAnimation animation;
        if (!this.tree.has("animations") || !this.tree.get("animations").isJsonObject()) {
            return this;
        }
        JsonObject animations = this.tree.get("animations").getAsJsonObject();
        for (Map.Entry<String, JsonElement> animationEntry : animations.entrySet()) {
            String animationName = animationEntry.getKey();
            JsonObject animationObject = animationEntry.getValue().getAsJsonObject();
            if (animationObject.has("bones")) {
                JsonObject bones = animationObject.get("bones").getAsJsonObject();
                if (animationObject.has("animation_length")) {
                    float animationLengthSeconds = animationObject.get("animation_length").getAsFloat();
                    animation = new ModelAnimation(animationName, (long) (animationLengthSeconds * 1000.0f));
                } else {
                    animation = new ModelAnimation(animationName);
                }
                if (animationObject.has("anim_time_update")) {
                    String meta = animationObject.get("anim_time_update").getAsString();
                    if (!meta.isEmpty()) {
                        String[] args = meta.split(" ");
                        AnimationMeta<?> currentMeta = null;
                        for (String arg : args) {
                            if (currentMeta != null) {
                                animation.parseAndAddMeta(currentMeta, arg);
                                currentMeta = null;
                            } else {
                                String metaName = arg.replace("-", "");
                                for (AnimationMeta<?> animationMeta : supportedAnimationMeta) {
                                    if (animationMeta.getKey().equals(metaName) || animationMeta.getShortcut().equals(metaName)) {
                                        currentMeta = animationMeta;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (AnimationMeta<?> animationMeta2 : supportedAnimationMeta) {
                        if (animationObject.has(animationMeta2.getKey())) {
                            animation.parseAndAddMeta(animationMeta2, animationObject.get(animationMeta2.getKey()).getAsString());
                        }
                    }
                }
                for (Map.Entry<String, JsonElement> boneEntry : bones.entrySet()) {
                    String boneName = boneEntry.getKey();
                    JsonObject bone = boneEntry.getValue().getAsJsonObject();
                    ModelPartAnimation boneAnimation = animation.getPartAnimation(boneName);
                    extractKeyframes(boneAnimation.rotation(), bone, "rotation");
                    extractKeyframes(boneAnimation.position(), bone, "position");
                    extractKeyframes(boneAnimation.scale(), bone, "scale");
                }
                this.animations.put(animationName, animation);
            }
        }
        return this;
    }

    private void extractKeyframes(ModelPartTransformation transformation, JsonObject bone, String key) {
        JsonArray array;
        if (bone.has(key)) {
            JsonElement type = bone.get(key);
            if (type.isJsonArray()) {
                pushVector(transformation, 0L, type.getAsJsonArray(), false, key);
                return;
            }
            if (!type.isJsonObject()) {
                if (type.isJsonPrimitive() && type.getAsJsonPrimitive().isNumber()) {
                    float value = type.getAsFloat();
                    JsonArray array2 = new JsonArray();
                    array2.add(new JsonPrimitive(Float.valueOf(value)));
                    array2.add(new JsonPrimitive(Float.valueOf(value)));
                    array2.add(new JsonPrimitive(Float.valueOf(value)));
                    pushVector(transformation, 0L, array2, false, key);
                    return;
                }
                return;
            }
            JsonObject object = type.getAsJsonObject();
            if (object.has("post")) {
                boolean smooth = object.has("lerp_mode") && object.get("lerp_mode").getAsString().equals("catmullrom");
                pushVector(transformation, 0L, object.get("post").getAsJsonArray(), smooth, key);
                return;
            }
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                long offset = (long) (Double.parseDouble(entry.getKey()) * 1000.0d);
                JsonElement value2 = entry.getValue();
                boolean smooth2 = false;
                if (value2.isJsonArray()) {
                    array = value2.getAsJsonArray();
                } else {
                    JsonObject entryObject = value2.getAsJsonObject();
                    array = entryObject.get("post").getAsJsonArray();
                    smooth2 = entryObject.has("lerp_mode") && entryObject.get("lerp_mode").getAsString().equals("catmullrom");
                }
                pushVector(transformation, offset, array, smooth2, key);
            }
        }
    }

    private void pushVector(ModelPartTransformation storage, long offset, JsonArray arrayVector, boolean smooth, String key) {
        float x = arrayVector.get(0).getAsFloat();
        float y = arrayVector.get(1).getAsFloat();
        float z = arrayVector.get(2).getAsFloat();
        if (key.equals("rotation")) {
            x = (float) Math.toRadians(x);
            y = (float) Math.toRadians(y);
            z = (float) Math.toRadians(z);
        } else if (key.equals("position")) {
            y = -y;
        }
        storage.addKeyframe(new ModelPartTransformation.Keyframe(offset, smooth, x, y, z));
    }

    public ModelAnimation getAnimation(String name) {
        return this.animations.get(name);
    }

    public Collection<ModelAnimation> getAnimations() {
        return Collections.unmodifiableCollection(this.animations.values());
    }
}
