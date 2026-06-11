package net.labymod.core.labynet.insight.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labynet.insight.model.ScreenshotInsight;
import net.labymod.core.labynet.insight.model.ServerInsight;
import net.labymod.core.labynet.insight.model.WindowInsight;
import net.labymod.core.labynet.insight.model.camera.CameraInsight;
import net.labymod.core.labynet.insight.model.camera.PivotPointInsight;
import net.labymod.core.labynet.insight.model.player.PlayerInsight;
import net.labymod.core.labynet.insight.util.ImageCodec;
import net.labymod.core.main.LabyMod;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/controller/InsightWriter.class */
public class InsightWriter {
    public static final String KEY = "Insight";

    @Deprecated
    public static final String LEGACY_KEY = "Screenshot Metadata";
    protected static final Logging LOGGER = Logging.create((Class<?>) InsightWriter.class);
    private final Map<String, ScreenshotInsight> insights = new HashMap();
    private float fovModifier;

    @Subscribe
    public void onCaptureScreenshot(CaptureScreenshotEvent event) {
        try {
            File file = event.getDestination();
            this.insights.put(file.getName(), captureInsight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onWriteScreenshot(WriteScreenshotEvent event) throws Exception {
        if (event.getPhase() != Phase.PRE) {
            return;
        }
        ImageCodec codec = new ImageCodec(event.getImage());
        codec.set("Minecraft Version", Laby.labyAPI().minecraft().getVersion());
        codec.set("LabyMod Version", LabyMod.getInstance().getVersion());
        String username = Laby.references().sessionAccessor().getSession().getUsername();
        codec.set("Minecraft Account", username);
        ScreenshotInsight insight = this.insights.remove(event.getDestination().getName());
        if (insight != null) {
            codec.set(KEY, insight.toJsonObject().toString());
            CameraInsight camera = insight.getCamera();
            if (camera != null) {
                codec.set("Fov", Double.toString(camera.getFov()));
                DoubleVector3 position = camera.getPosition();
                Quaternionf rotation = camera.getRotation();
                Vector3f euler = rotation.getEulerAnglesXYZ(new Vector3f());
                codec.set("Coordinates", String.format("%.2f, %.2f, %.2f (%s, %s)", Double.valueOf(position.getX()), Double.valueOf(position.getY()), Double.valueOf(position.getZ()), Float.valueOf(MathHelper.toDegreesFloat(euler.x())), Float.valueOf(MathHelper.toDegreesFloat(euler.y()))));
            }
            ServerInsight server = insight.getServer();
            if (server != null && server.getServerData() != null) {
                ServerAddress address = server.getServerData().address();
                codec.set("Server Address", address.getHost() + ":" + address.getPort());
            }
            List<String> list = new ArrayList<>();
            for (PlayerInsight playerMeta : insight.getPlayers()) {
                String name = playerMeta.getName();
                list.add(name);
            }
            if (!list.isEmpty()) {
                String[] players = (String[]) list.toArray(new String[0]);
                codec.set("Players", String.join(",", players));
            }
        }
        event.setImage(codec.compileWithMeta());
    }

    @Subscribe(126)
    public void onFov(FieldOfViewModifierEvent event) {
        this.fovModifier = event.getFieldOfView();
    }

    public Map<String, ScreenshotInsight> getInsights() {
        return this.insights;
    }

    private ScreenshotInsight captureInsight() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        MinecraftCamera camera = minecraft.getCamera();
        ClientWorld world = minecraft.clientWorld();
        ClientPlayer clientPlayer = minecraft.getClientPlayer();
        Perspective perspective = minecraft.options().perspective();
        Window window = minecraft.minecraftWindow();
        float partialTicks = minecraft.getPartialTicks();
        ScreenshotInsight insight = new ScreenshotInsight();
        insight.setWindow(new WindowInsight(window.getRawWidth(), window.getRawHeight()));
        if (camera != null && clientPlayer != null) {
            CameraInsight cameraMeta = new CameraInsight(camera.position(), camera.cameraRotation(), minecraft.options().getFov() * ((double) this.fovModifier));
            if (perspective != Perspective.FIRST_PERSON) {
                Position position = clientPlayer.position();
                cameraMeta.setPivotPoint(new PivotPointInsight(position.getX(), position.getY() + ((double) clientPlayer.getEyeHeight()), position.getZ()));
            }
            insight.setCamera(cameraMeta);
        }
        ServerData serverData = Laby.labyAPI().serverController().getCurrentServerData();
        if (serverData != null) {
            insight.setServerData(new ServerInsight(serverData));
        }
        if (world != null && clientPlayer != null && camera != null) {
            for (Player player : world.getPlayers()) {
                UUID uuid = player.getUniqueId();
                if (uuid.getMostSignificantBits() != 0 && uuid.getLeastSignificantBits() != 0) {
                    double distance = player.getDistanceSquared(clientPlayer);
                    if (distance <= 256.0d && (perspective != Perspective.FIRST_PERSON || clientPlayer != player)) {
                        if (!world.isBlockInBetween(camera.position(), player.position().toDoubleVector3()) || !world.isBlockInBetween(camera.position(), new DoubleVector3(player.eyePosition()))) {
                            if (player.isRendered()) {
                                insight.addPlayer(player, partialTicks);
                            }
                        }
                    }
                }
            }
        }
        return insight;
    }
}
