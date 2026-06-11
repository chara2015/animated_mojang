package net.labymod.api.labynet.models;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/ServerManifest.class */
public class ServerManifest {

    @SerializedName("supported_languages")
    private String[] supportedLanguages;
    private Discord discord;
    private Branding brand;
    private Location location;

    @SerializedName("yt_trailer")
    private String youTubeTrailer;

    @SerializedName("user_stats")
    private String userStatsUrl;

    public String[] getSupportedLanguages() {
        return this.supportedLanguages;
    }

    public Discord getDiscord() {
        return this.discord;
    }

    public Branding getBrand() {
        return this.brand;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getYouTubeTrailer() {
        return "https://youtu.be/" + this.youTubeTrailer;
    }

    public String getUserStatsUrl(String userName) {
        return this.userStatsUrl.replace("{userName}", userName);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/ServerManifest$Branding.class */
    public static class Branding {
        private String primary;
        private String background;
        private String text;

        public String getPrimaryHex() {
            return this.primary;
        }

        public String getBackgroundHex() {
            return this.background;
        }

        public String getTextHex() {
            return this.text;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/ServerManifest$Discord.class */
    public static class Discord {

        @SerializedName("server_id")
        private String serverId;

        @SerializedName("rename_to_minecraft_name")
        private boolean renameToMinecraftName;

        public String getServerId() {
            return this.serverId;
        }

        public boolean isRenameToMinecraftName() {
            return this.renameToMinecraftName;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/ServerManifest$Location.class */
    public static class Location {
        private String city;
        private String country;

        @SerializedName("country_code")
        private String countryCode;

        public String getCity() {
            return this.city;
        }

        public String getCountry() {
            return this.country;
        }

        public String getCountryCode() {
            return this.countryCode;
        }
    }
}
