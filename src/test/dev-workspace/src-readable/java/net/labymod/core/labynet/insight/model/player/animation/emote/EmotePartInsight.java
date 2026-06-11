package net.labymod.core.labynet.insight.model.player.animation.emote;

import com.google.gson.JsonObject;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.labynet.insight.Insight;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/player/animation/emote/EmotePartInsight.class */
public class EmotePartInsight implements Insight {
    private FloatVector3 position;
    private FloatVector3 rotation;
    private FloatVector3 scale;

    public EmotePartInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public EmotePartInsight(FloatVector3 position, FloatVector3 rotation, FloatVector3 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public boolean isPositionDefault() {
        return this.position.getX() == 0.0f && this.position.getY() == 0.0f && this.position.getZ() == 0.0f;
    }

    public boolean isRotationDefault() {
        return this.rotation.getX() == 0.0f && this.rotation.getY() == 0.0f && this.rotation.getZ() == 0.0f;
    }

    public boolean isScaleDefault() {
        return this.scale.getX() == 1.0f && this.scale.getY() == 1.0f && this.scale.getZ() == 1.0f;
    }

    public boolean isDefault() {
        return isPositionDefault() && isRotationDefault() && isScaleDefault();
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        if (!isRotationDefault()) {
            JsonObject rotation = new JsonObject();
            rotation.addProperty("x", Float.valueOf(this.rotation.getX()));
            rotation.addProperty("y", Float.valueOf(this.rotation.getY()));
            rotation.addProperty("z", Float.valueOf(this.rotation.getZ()));
            jsonObject.add("rotation", rotation);
        }
        if (!isPositionDefault()) {
            JsonObject position = new JsonObject();
            position.addProperty("x", Float.valueOf(this.position.getX()));
            position.addProperty("y", Float.valueOf(this.position.getY()));
            position.addProperty("z", Float.valueOf(this.position.getZ()));
            jsonObject.add("position", position);
        }
        if (!isScaleDefault()) {
            JsonObject scale = new JsonObject();
            scale.addProperty("x", Float.valueOf(this.scale.getX()));
            scale.addProperty("y", Float.valueOf(this.scale.getY()));
            scale.addProperty("z", Float.valueOf(this.scale.getZ()));
            jsonObject.add("scale", scale);
        }
        return jsonObject;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        if (object.has("rotation")) {
            JsonObject rotation = object.getAsJsonObject("rotation");
            this.rotation = new FloatVector3(rotation.get("x").getAsFloat(), rotation.get("y").getAsFloat(), rotation.get("z").getAsFloat());
        } else {
            this.rotation = new FloatVector3(0.0f, 0.0f, 0.0f);
        }
        if (object.has("position")) {
            JsonObject position = object.getAsJsonObject("position");
            this.position = new FloatVector3(position.get("x").getAsFloat(), position.get("y").getAsFloat(), position.get("z").getAsFloat());
        } else {
            this.position = new FloatVector3(0.0f, 0.0f, 0.0f);
        }
        if (object.has("scale")) {
            JsonObject scale = object.getAsJsonObject("scale");
            this.scale = new FloatVector3(scale.get("x").getAsFloat(), scale.get("y").getAsFloat(), scale.get("z").getAsFloat());
        } else {
            this.scale = new FloatVector3(1.0f, 1.0f, 1.0f);
        }
    }
}
