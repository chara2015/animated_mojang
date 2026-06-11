package net.labymod.core.labynet.insight.model.player.animation;

import com.google.gson.JsonObject;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.labynet.insight.Insight;
import net.labymod.core.labynet.insight.model.player.animation.emote.EmoteInsight;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/player/animation/AnimationInsight.class */
public class AnimationInsight implements Insight {
    private float rotationHeadYaw;
    private float rotationRenderYawOffset;
    private float limbSwing;
    private float limbSwingStrength;
    private float walkingSpeed;
    private boolean crouching;
    private float armSwingProgress;
    private EmoteInsight emote;

    public AnimationInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public AnimationInsight(Player player, float partialTicks) {
        this.rotationHeadYaw = MathHelper.lerp(player.getRotationHeadYaw(), player.getPreviousRotationHeadYaw(), partialTicks);
        this.rotationRenderYawOffset = MathHelper.lerp(player.getBodyRotationY(), player.getPreviousBodyRotationY(), partialTicks);
        this.limbSwing = player.getLimbSwing();
        this.limbSwingStrength = MathHelper.lerp(player.getLimbSwingStrength(), player.getPreviousLimbSwingStrength(), partialTicks);
        this.walkingSpeed = player.getWalkingSpeed(partialTicks);
        this.crouching = player.isCrouching();
        this.armSwingProgress = player.getArmSwingProgress(partialTicks);
        EmoteInsight emote = new EmoteInsight(player);
        if (emote.isPlaying()) {
            this.emote = emote;
        }
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject animation = new JsonObject();
        animation.addProperty("head_yaw", Float.valueOf(this.rotationHeadYaw));
        animation.addProperty("render_yaw_offset", Float.valueOf(this.rotationRenderYawOffset));
        animation.addProperty("limb_swing", Float.valueOf(this.limbSwing));
        animation.addProperty("limb_swing_strength", Float.valueOf(this.limbSwingStrength));
        animation.addProperty("walking_speed", Float.valueOf(this.walkingSpeed));
        animation.addProperty("crouching", Boolean.valueOf(this.crouching));
        animation.addProperty("arm_swing_progress", Float.valueOf(this.armSwingProgress));
        if (this.emote != null) {
            animation.add("emote", this.emote.toJsonObject());
        }
        return animation;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        this.rotationHeadYaw = object.get("head_yaw").getAsFloat();
        this.rotationRenderYawOffset = object.get("render_yaw_offset").getAsFloat();
        this.limbSwing = object.get("limb_swing").getAsFloat();
        this.limbSwingStrength = object.get("limb_swing_strength").getAsFloat();
        this.walkingSpeed = object.get("walking_speed").getAsFloat();
        this.crouching = object.get("crouching").getAsBoolean();
        this.armSwingProgress = object.get("arm_swing_progress").getAsFloat();
        if (object.has("emote")) {
            this.emote = new EmoteInsight(object.get("emote").getAsJsonObject());
        }
    }
}
