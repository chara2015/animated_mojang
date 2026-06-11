package net.labymod.core.labynet.insight.model.player.animation.emote;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.labynet.insight.Insight;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/player/animation/emote/EmoteInsight.class */
public class EmoteInsight implements Insight {
    private Map<String, EmotePartInsight> parts;

    public EmoteInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public EmoteInsight(Player player) {
        AnimationController animationController;
        ModelAnimation modelAnimation;
        EmoteService emoteService = LabyMod.references().emoteService();
        EmoteAnimationStorage animationStorage = emoteService.getAnimationStorage(player);
        if (animationStorage != null && (modelAnimation = (animationController = animationStorage.animationController()).getPlaying()) != null) {
            Map<String, EmotePartInsight> parts = new HashMap<>();
            for (ModelAnimation.ModelPartAnimationDefinition entry : modelAnimation.getPartAnimations()) {
                String name = entry.name();
                FloatVector3 position = animationController.getCurrentPosition(name);
                FloatVector3 rotation = animationController.getCurrentRotation(name);
                FloatVector3 scale = animationController.getCurrentScale(name);
                EmotePartInsight part = new EmotePartInsight(position, rotation, scale);
                if (!part.isDefault()) {
                    parts.put(name, part);
                }
            }
            this.parts = parts;
            return;
        }
        this.parts = new HashMap();
    }

    public boolean isPlaying() {
        return !this.parts.isEmpty();
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject parts = new JsonObject();
        for (Map.Entry<String, EmotePartInsight> entry : this.parts.entrySet()) {
            parts.add(entry.getKey(), entry.getValue().toJsonObject());
        }
        return parts;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        Map<String, EmotePartInsight> parts = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            parts.put(entry.getKey(), new EmotePartInsight(entry.getValue().getAsJsonObject()));
        }
        this.parts = parts;
    }
}
