package net.labymod.core.labynet.insight.model.player;

import com.google.gson.JsonObject;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.mojang.Property;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.labynet.insight.Insight;
import net.labymod.core.labynet.insight.model.player.animation.AnimationInsight;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/player/PlayerInsight.class */
public class PlayerInsight implements Insight {
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
    private UUID uuid;
    private String name;
    private double positionX;
    private double positionY;
    private double positionZ;
    private float rotationYaw;
    private float rotationPitch;
    private AnimationInsight animation;
    private JsonObject textures;
    private UserInsight user;
    private FloatVector2 screenPosition;

    public PlayerInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public PlayerInsight(Player player, float partialTicks) {
        this.uuid = player.getUniqueId();
        this.name = ColorUtil.removeLegacyColors(player.getName());
        Position position = player.position();
        Position previousPosition = player.previousPosition();
        this.positionX = position.lerpX(previousPosition, partialTicks);
        this.positionY = position.lerpY(previousPosition, partialTicks);
        this.positionZ = position.lerpZ(previousPosition, partialTicks);
        this.rotationYaw = MathHelper.lerp(player.getRotationYaw(), player.getPreviousRotationYaw(), partialTicks);
        this.rotationPitch = MathHelper.lerp(player.getRotationPitch(), player.getPreviousRotationPitch(), partialTicks);
        this.animation = new AnimationInsight(player, partialTicks);
        try {
            Collection<Property> textures = player.profile().getProperties().get("textures");
            if (textures != null) {
                Iterator<Property> it = textures.iterator();
                if (it.hasNext()) {
                    Property texture = it.next();
                    String json = new String(BASE64_DECODER.decode(texture.getValue()));
                    this.textures = ((JsonObject) GsonUtil.DEFAULT_GSON.fromJson(json, JsonObject.class)).getAsJsonObject("textures");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserInsight user = new UserInsight(player);
        if (user.isLoaded()) {
            this.user = user;
        }
    }

    public void setScreenPosition(FloatVector2 position) {
        this.screenPosition = position;
    }

    public FloatVector2 getScreenPosition() {
        return this.screenPosition;
    }

    public String getName() {
        return this.name;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject player = new JsonObject();
        player.addProperty("uuid", this.uuid.toString());
        player.addProperty("name", this.name);
        JsonObject position = new JsonObject();
        position.addProperty("x", Double.valueOf(this.positionX));
        position.addProperty("y", Double.valueOf(this.positionY));
        position.addProperty("z", Double.valueOf(this.positionZ));
        player.add("position", position);
        JsonObject rotation = new JsonObject();
        rotation.addProperty("yaw", Float.valueOf(this.rotationYaw));
        rotation.addProperty("pitch", Float.valueOf(this.rotationPitch));
        player.add("rotation", rotation);
        player.add("animation", this.animation.toJsonObject());
        if (this.textures != null) {
            player.add("textures", this.textures);
        }
        if (this.user != null) {
            player.add("user", this.user.toJsonObject());
        }
        if (this.screenPosition != null) {
            JsonObject screenPosition = new JsonObject();
            screenPosition.addProperty("x", Float.valueOf(this.screenPosition.getX()));
            screenPosition.addProperty("y", Float.valueOf(this.screenPosition.getY()));
            player.add("screen_position", screenPosition);
        }
        return player;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        this.uuid = UUID.fromString(object.get("uuid").getAsString());
        this.name = object.get("name").getAsString();
        JsonObject position = object.get("position").getAsJsonObject();
        this.positionX = position.get("x").getAsDouble();
        this.positionY = position.get("y").getAsDouble();
        this.positionZ = position.get("z").getAsDouble();
        JsonObject rotation = object.get("rotation").getAsJsonObject();
        this.rotationYaw = rotation.get("yaw").getAsFloat();
        this.rotationPitch = rotation.get("pitch").getAsFloat();
        this.animation = new AnimationInsight(object.get("animation").getAsJsonObject());
        if (object.has("textures")) {
            this.textures = object.get("textures").getAsJsonObject();
        }
        if (object.has("user")) {
            this.user = new UserInsight(object.get("user").getAsJsonObject());
        }
        if (object.has("screen_position")) {
            JsonObject screenPosition = object.get("screen_position").getAsJsonObject();
            this.screenPosition = new FloatVector2(screenPosition.get("x").getAsFloat(), screenPosition.get("y").getAsFloat());
        }
    }
}
