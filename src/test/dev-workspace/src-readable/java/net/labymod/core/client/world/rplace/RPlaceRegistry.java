package net.labymod.core.client.world.rplace;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.core.client.world.rplace.art.ColoredBlock;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.labyconnect.session.message.MessageListener;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/RPlaceRegistry.class */
@Singleton
@Referenceable
public class RPlaceRegistry extends DefaultRegistry<PixelArt> implements MessageListener {
    private boolean enabled;
    private int mapMinX;
    private int mapMinZ;
    private int mapMaxX;
    private int mapMaxZ;
    private int mapY;
    private ServerData serverData;
    private String lobbyName;
    private String targetLobbyName;
    private String mapUrl;
    private boolean isOnTargetLobby;
    private JsonArray lastArtworksArray;
    private List<ColoredBlock> palette = new ArrayList();
    private boolean announced = false;
    private boolean onTestServer = false;

    @Inject
    public RPlaceRegistry() {
    }

    public PixelArt registerFromUrl(URL url, int x, int z, int size, boolean labyConnect) throws IOException {
        InputStream in = url.openStream();
        PixelArt pixelArt = new PixelArt(in, x, this.mapY, z, size, labyConnect);
        register(pixelArt);
        in.close();
        return pixelArt;
    }

    @Override // net.labymod.api.service.Registry
    public void register(PixelArt art) {
        if (art.getMinX() < this.mapMinX || art.getMinZ() < this.mapMinZ || art.getMaxX() > this.mapMaxX || art.getMaxZ() > this.mapMaxZ) {
            throw new IllegalArgumentException("Image of size " + art.getWidth() + "x" + art.getHeight() + " is placed out of map!");
        }
        for (PixelArt otherArt : values()) {
            if (otherArt.intersects(art)) {
                throw new IllegalArgumentException("Image of size " + art.getWidth() + "x" + art.getHeight() + " intersects with other image that is placed at " + otherArt.getCenterX() + ", " + otherArt.getCenterZ() + "!");
            }
        }
        art.load();
        super.register(art);
    }

    @Override // net.labymod.core.labyconnect.session.message.MessageListener
    public void listen(String message) {
        try {
            JsonObject event = (JsonObject) GSON.fromJson(message, JsonObject.class);
            if (!event.has("action")) {
                return;
            }
            String action = event.get("action").getAsString();
            if (action.equals("lobby")) {
                this.targetLobbyName = event.get("targetLobbyName").getAsString();
                updateIsOnTargetLobby();
            }
            if (action.equals("update")) {
                this.enabled = event.get("enabled").getAsBoolean();
                this.mapMinX = event.get("minX").getAsInt();
                this.mapMinZ = event.get("minZ").getAsInt();
                this.mapMaxX = event.get("maxX").getAsInt();
                this.mapMaxZ = event.get("maxZ").getAsInt();
                this.mapY = event.get("y").getAsInt();
                if (event.has("mapUrl")) {
                    this.mapUrl = event.get("mapUrl").getAsString();
                }
                try {
                    if (event.has("palette")) {
                        List<ColoredBlock> list = new ArrayList<>();
                        JsonArray<JsonElement> paletteArray = event.getAsJsonArray("palette");
                        for (JsonElement element : paletteArray) {
                            JsonObject object = element.getAsJsonObject();
                            list.add(new ColoredBlock(object.get("id").getAsString(), object.get("color").getAsInt()));
                        }
                        this.palette = list;
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                this.lastArtworksArray = event.getAsJsonArray("artworks");
                updateIsOnTargetLobby();
            }
        } catch (Throwable t2) {
            t2.printStackTrace();
        }
    }

    private void updateArtworks() {
        List<String> toDelete = new ArrayList<>();
        for (PixelArt pixelArt : values()) {
            if (pixelArt.isLabyConnect()) {
                toDelete.add(pixelArt.getId());
                pixelArt.dispose();
            }
        }
        for (String id : toDelete) {
            unregister(id);
        }
        LabyExecutors.executeBackgroundTask(() -> {
            try {
                if (this.lastArtworksArray != null) {
                    for (JsonElement element : this.lastArtworksArray) {
                        JsonObject object = element.getAsJsonObject();
                        String url = object.get("url").getAsString();
                        int x = object.get("x").getAsInt();
                        int z = object.get("z").getAsInt();
                        int size = -1;
                        if (object.has(ItemMetadata.SIZE_KEY)) {
                            size = object.get(ItemMetadata.SIZE_KEY).getAsInt();
                        }
                        registerFromUrl(URI.create(url).toURL(), x, z, size, true);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void updateIsOnTargetLobby() {
        if (this.serverData == null || this.lobbyName == null) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        String currentAddress = this.serverData.address().getHost();
        if (currentAddress == null) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        Optional<ServerGroup> server = Laby.references().labyNetController().getServerByIp(this.serverData.address());
        boolean onGomme = server.isPresent() && server.get().getServerName().equals("gommehd");
        boolean onTestServer = currentAddress.toLowerCase(Locale.ENGLISH).contains("gomme.laby");
        if (!onGomme && !onTestServer) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        boolean isOnTargetLobby = this.targetLobbyName != null && this.lobbyName.equals(this.targetLobbyName);
        if (!isOnTargetLobby) {
            this.isOnTargetLobby = false;
            this.enabled = false;
            return;
        }
        this.isOnTargetLobby = true;
        this.onTestServer = onTestServer;
        if (isEnabled() && this.lastArtworksArray != null && !this.lastArtworksArray.isEmpty()) {
            JsonObject mainArtwork = this.lastArtworksArray.get(0).getAsJsonObject();
            int mainX = mainArtwork.get("x").getAsInt();
            int mainZ = mainArtwork.get("z").getAsInt();
            if (!this.announced) {
                this.announced = true;
                Laby.labyAPI().minecraft().chatExecutor().displayClientMessage(Component.translatable("labymod.command.command.rplaceoverlay.welcome", NamedTextColor.GREEN, Component.translatable("/place-overlay", NamedTextColor.YELLOW), Component.translatable(String.valueOf(mainX), NamedTextColor.YELLOW), Component.translatable(String.valueOf(mainZ), NamedTextColor.YELLOW)));
            }
            updateArtworks();
            return;
        }
        LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        LabyConnectSession session = labyConnect.getSession();
        if (session == null) {
            return;
        }
        JsonObject payload = new JsonObject();
        payload.addProperty("action", "request");
        ((DefaultLabyConnect) labyConnect).sendPacket(new PacketAddonMessage("rplace", (JsonElement) payload));
    }

    public ColoredBlock getNearestBlock(int argb) {
        ColoredBlock nearest = null;
        double minDistance = Double.MAX_VALUE;
        int a1 = (argb >> 24) & 255;
        int r1 = (argb >> 16) & 255;
        int g1 = (argb >> 8) & 255;
        int b1 = argb & 255;
        if (a1 < 40) {
            return null;
        }
        for (ColoredBlock block : this.palette) {
            if (block.isValid()) {
                int colorRgb = block.getColor();
                int r2 = (colorRgb >> 16) & 255;
                int g2 = (colorRgb >> 8) & 255;
                int b2 = colorRgb & 255;
                double distance = Math.pow(r1 - r2, 2.0d) + Math.pow(g1 - g2, 2.0d) + Math.pow(b1 - b2, 2.0d);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = block;
                }
            }
        }
        return nearest;
    }

    public void clear(boolean includingLabyConnect) {
        try {
            List<PixelArt> toRemove = new ArrayList<>();
            for (PixelArt pixelArt : values()) {
                if (!pixelArt.isLabyConnect() || includingLabyConnect) {
                    pixelArt.dispose();
                    toRemove.add(pixelArt);
                }
            }
            Iterator<PixelArt> it = toRemove.iterator();
            while (it.hasNext()) {
                unregister(it.next().getId());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public boolean isEnabled() {
        return this.enabled && this.isOnTargetLobby && !PlatformEnvironment.isAncientOpenGL();
    }

    public int getMapY() {
        return this.mapY;
    }

    public boolean isOnTargetLobby() {
        return this.isOnTargetLobby;
    }

    public String getMapUrl() {
        return this.mapUrl;
    }

    public void disableOnError() {
        this.enabled = false;
        Laby.labyAPI().minecraft().chatExecutor().displayClientMessage(Component.text("Disabled r/place support because an error occurred", NamedTextColor.RED));
    }

    public void setCurrentLobby(ServerData server, String lobbyName) {
        if (server == null) {
            try {
                this.announced = false;
            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
        }
        this.serverData = server;
        this.lobbyName = lobbyName;
        updateIsOnTargetLobby();
    }

    public boolean isOnTestServer() {
        return this.onTestServer;
    }
}
