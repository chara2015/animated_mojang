package net.labymod.core.labynet.insight.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.labynet.insight.Insight;
import net.labymod.core.labynet.insight.model.camera.CameraInsight;
import net.labymod.core.labynet.insight.model.player.PlayerInsight;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/ScreenshotInsight.class */
public class ScreenshotInsight implements Insight {
    private final List<PlayerInsight> players = new ArrayList();
    private CameraInsight camera;
    private WindowInsight window;
    private ServerInsight server;

    public ScreenshotInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public ScreenshotInsight() {
    }

    public void addPlayer(Player player, float partialTicks) {
        PlayerInsight playerMeta = new PlayerInsight(player, partialTicks);
        if (this.camera != null && this.window != null) {
            Position position = player.position();
            Position previousPosition = player.previousPosition();
            DoubleVector3 playerPosition = new DoubleVector3(position.lerpX(previousPosition, partialTicks), position.lerpY(previousPosition, partialTicks), position.lerpZ(previousPosition, partialTicks));
            DoubleVector3 vector = this.camera.getPosition().copy().sub(playerPosition);
            vector.transform(new Quaternionf(this.camera.getRotation()).conjugate());
            float halfWidth = this.window.getWidth() / 2.0f;
            float halfHeight = this.window.getHeight() / 2.0f;
            double tan = Math.tan(Math.toRadians(this.camera.getFov() / 2.0d));
            double scaleFactor = ((double) halfHeight) / (vector.getZ() * tan);
            float screenX = (float) (((double) halfWidth) - (vector.getX() * scaleFactor));
            float screenY = (float) (((double) halfHeight) - (vector.getY() * scaleFactor));
            float screenX2 = MathHelper.clamp(screenX, 0.0f, this.window.getWidth());
            float screenY2 = MathHelper.clamp(screenY, 0.0f, this.window.getHeight());
            if (!Float.isNaN(screenX2) && !Float.isNaN(screenY2)) {
                playerMeta.setScreenPosition(new FloatVector2(screenX2, screenY2));
            }
        }
        this.players.add(playerMeta);
    }

    public List<PlayerInsight> getPlayers() {
        return this.players;
    }

    public ServerInsight getServer() {
        return this.server;
    }

    public CameraInsight getCamera() {
        return this.camera;
    }

    public WindowInsight getWindow() {
        return this.window;
    }

    public void setCamera(CameraInsight cameraMeta) {
        this.camera = cameraMeta;
    }

    public void setWindow(WindowInsight windowMeta) {
        this.window = windowMeta;
    }

    public void setServerData(ServerInsight server) {
        this.server = server;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        JsonObject metadata = new JsonObject();
        JsonArray players = new JsonArray();
        for (PlayerInsight taggedPlayer : this.players) {
            players.add(taggedPlayer.toJsonObject());
        }
        metadata.add("players", players);
        if (this.server != null) {
            metadata.add("server", this.server.toJsonObject());
        }
        if (this.camera != null) {
            metadata.add("camera", this.camera.toJsonObject());
        }
        if (this.window != null) {
            metadata.add("window", this.window.toJsonObject());
        }
        object.add("metadata", metadata);
        return object;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        JsonObject metaDataObject = object.get("metadata").getAsJsonObject();
        if (metaDataObject.has("players")) {
            JsonArray<JsonElement> playersArray = metaDataObject.get("players").getAsJsonArray();
            for (JsonElement playerElement : playersArray) {
                this.players.add(new PlayerInsight(playerElement.getAsJsonObject()));
            }
        }
        if (metaDataObject.has("server")) {
            this.server = new ServerInsight(metaDataObject.get("server").getAsJsonObject());
        }
        if (metaDataObject.has("camera")) {
            this.camera = new CameraInsight(metaDataObject.get("camera").getAsJsonObject());
        }
        if (metaDataObject.has("window")) {
            this.window = new WindowInsight(metaDataObject.get("window").getAsJsonObject());
        }
    }
}
