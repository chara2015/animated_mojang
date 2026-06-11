package net.labymod.api.labynet.models;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.io.web.result.ResultCallback;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/ServerGroup.class */
public class ServerGroup {

    @SerializedName("server_name")
    private String serverName;

    @SerializedName("nice_name")
    private String niceName;

    @SerializedName("direct_ip")
    private String directIp;

    @SerializedName("wildcards")
    @Nullable
    private String[] wildcardIps;
    private List<Attachment> attachments;
    private Map<String, String> social;

    @SerializedName("command_delay")
    private int commandDelay;

    @SerializedName("user_stats")
    private String userStatsUrl;
    private final ServerChat chat = new ServerChat();

    @SerializedName("gamemodes")
    private Map<String, GameMode> gameModes = new HashMap();
    private transient List<GameMode> visibleGameModes = null;

    public static boolean addressMatches(String address, String directIp, String[] wildcardIps) {
        if (directIp.equalsIgnoreCase(address)) {
            return true;
        }
        String[] ipSplit = address.split("\\.");
        boolean subdomain = ipSplit.length > 2;
        if (subdomain && StringUtil.endsWithIgnoreCase(address, "." + directIp)) {
            return true;
        }
        if (wildcardIps == null) {
            return false;
        }
        if (address.contains(":")) {
            String[] split = address.split(":");
            if (split.length == 0) {
                return false;
            }
            address = split[0];
        }
        for (String wildcard : wildcardIps) {
            String[] wildcardSplit = wildcard.split("%.");
            String wildcardIp = wildcardSplit.length == 1 ? wildcardSplit[0] : wildcardSplit[1];
            if (subdomain) {
                if (StringUtil.endsWithIgnoreCase(address, "." + wildcardIp)) {
                    return true;
                }
            } else if (wildcardIp.equalsIgnoreCase(address)) {
                return true;
            }
        }
        return false;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getNiceName() {
        return this.niceName;
    }

    public String getDirectIp() {
        return this.directIp;
    }

    public String[] getWildcards() {
        return this.wildcardIps;
    }

    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public Map<String, String> getSocial() {
        return this.social;
    }

    public List<GameMode> getGameModes() {
        if (this.visibleGameModes == null) {
            this.visibleGameModes = Lists.newArrayList();
            Version version = Laby.labyAPI().labyModLoader().version();
            for (GameMode gameMode : this.gameModes.values()) {
                if (gameMode.getVersions() == null || gameMode.getVersions().isCompatible(version)) {
                    this.visibleGameModes.add(gameMode);
                }
            }
        }
        return this.visibleGameModes;
    }

    public Map<String, GameMode> getAllGameModes() {
        return this.gameModes;
    }

    public ServerChat getChat() {
        return this.chat;
    }

    public int getCommandDelay() {
        return this.commandDelay;
    }

    public String getUserStatsUrl() {
        return this.userStatsUrl;
    }

    public void manifest(ResultCallback<ServerManifest> callback) {
        Laby.labyAPI().labyNetController().getOrLoadManifest(this, callback);
    }

    public Optional<Attachment> getAttachment(String name) {
        if (!name.endsWith(".json") && !name.endsWith(".png")) {
            name = name + ".png";
        }
        for (Attachment attachment : this.attachments) {
            if (attachment.getFileName().equals(name)) {
                return Optional.of(attachment);
            }
        }
        return Optional.empty();
    }

    public boolean hasIp(String ip) {
        return addressMatches(ip, this.directIp, this.wildcardIps);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/ServerGroup$Attachment.class */
    public static class Attachment {
        private static final TextureRepository TEXTURE_REPOSITORY = Laby.references().textureRepository();

        @SerializedName("file_name")
        private String fileName;
        private String url;
        private String hash;

        @Exclude
        private CompletableResourceLocation resourceLocation;

        public String getFileName() {
            return this.fileName;
        }

        public String getUrl() {
            return this.url;
        }

        public String getHash() {
            return this.hash;
        }

        public Icon getIcon() {
            if (this.hash == null || this.url == null) {
                return null;
            }
            return Icon.completable(completableResourceLocation());
        }

        public CompletableResourceLocation completableResourceLocation() {
            if (this.hash == null || this.url == null) {
                return null;
            }
            if (this.resourceLocation == null) {
                this.resourceLocation = TEXTURE_REPOSITORY.loadCacheResourceAsync("labymod", this.hash, this.url, Textures.EMPTY);
            }
            return this.resourceLocation;
        }
    }
}
