package com.mojang.realmsclient.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsError;
import com.mojang.realmsclient.dto.BackupList;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.OutboundPlayer;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.PendingInvitesList;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.PreferredRegionsDto;
import com.mojang.realmsclient.dto.RealmsConfigurationDto;
import com.mojang.realmsclient.dto.RealmsDescriptionDto;
import com.mojang.realmsclient.dto.RealmsJoinInformation;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsNotification;
import com.mojang.realmsclient.dto.RealmsRegion;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RealmsSetting;
import com.mojang.realmsclient.dto.RealmsSlotUpdateDto;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.RealmsWorldResetDto;
import com.mojang.realmsclient.dto.RegionSelectionPreference;
import com.mojang.realmsclient.dto.RegionSelectionPreferenceDto;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsHttpException;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.UploadTokenCache;
import com.mojang.util.UndashedUuid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.util.LenientJsonParser;
import net.minecraft.util.Util;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsClient.class */
public class RealmsClient {
    private final CompletableFuture<Set<String>> featureFlags;
    private final String sessionId;
    private final String username;
    private final Minecraft minecraft;
    private static final String WORLDS_RESOURCE_PATH = "worlds";
    private static final String INVITES_RESOURCE_PATH = "invites";
    private static final String MCO_RESOURCE_PATH = "mco";
    private static final String SUBSCRIPTION_RESOURCE = "subscriptions";
    private static final String ACTIVITIES_RESOURCE = "activities";
    private static final String OPS_RESOURCE = "ops";
    private static final String REGIONS_RESOURCE = "regions/ping/stat";
    private static final String PREFERRED_REGION_RESOURCE = "regions/preferredRegions";
    private static final String TRIALS_RESOURCE = "trial";
    private static final String NOTIFICATIONS_RESOURCE = "notifications";
    private static final String FEATURE_FLAGS_RESOURCE = "feature/v1";
    private static final String PATH_LIST_ALL_REALMS = "/listUserWorldsOfType/any";
    private static final String PATH_CREATE_SNAPSHOT_REALM = "/$PARENT_WORLD_ID/createPrereleaseRealm";
    private static final String PATH_SNAPSHOT_ELIGIBLE_REALMS = "/listPrereleaseEligibleWorlds";
    private static final String PATH_INITIALIZE = "/$WORLD_ID/initialize";
    private static final String PATH_GET_LIVESTATS = "/liveplayerlist";
    private static final String PATH_GET_SUBSCRIPTION = "/$WORLD_ID";
    private static final String PATH_OP = "/$WORLD_ID/$PROFILE_UUID";
    private static final String PATH_PUT_INTO_MINIGAMES_MODE = "/minigames/$MINIGAME_ID/$WORLD_ID";
    private static final String PATH_AVAILABLE = "/available";
    private static final String PATH_TEMPLATES = "/templates/$WORLD_TYPE";
    private static final String PATH_WORLD_JOIN = "/v1/$ID/join/pc";
    private static final String PATH_WORLD_GET = "/$ID";
    private static final String PATH_WORLD_INVITES = "/$WORLD_ID";
    private static final String PATH_WORLD_UNINVITE = "/$WORLD_ID/invite/$UUID";
    private static final String PATH_PENDING_INVITES = "/pending";
    private static final String PATH_ACCEPT_INVITE = "/accept/$INVITATION_ID";
    private static final String PATH_REJECT_INVITE = "/reject/$INVITATION_ID";
    private static final String PATH_UNINVITE_MYSELF = "/$WORLD_ID";
    private static final String PATH_WORLD_CONFIGURE = "/$WORLD_ID/configuration";
    private static final String PATH_SLOT = "/$WORLD_ID/slot/$SLOT_ID";
    private static final String PATH_WORLD_OPEN = "/$WORLD_ID/open";
    private static final String PATH_WORLD_CLOSE = "/$WORLD_ID/close";
    private static final String PATH_WORLD_RESET = "/$WORLD_ID/reset";
    private static final String PATH_DELETE_WORLD = "/$WORLD_ID";
    private static final String PATH_WORLD_BACKUPS = "/$WORLD_ID/backups";
    private static final String PATH_WORLD_DOWNLOAD = "/$WORLD_ID/slot/$SLOT_ID/download";
    private static final String PATH_WORLD_UPLOAD = "/$WORLD_ID/backups/upload";
    private static final String PATH_CLIENT_COMPATIBLE = "/client/compatible";
    private static final String PATH_TOS_AGREED = "/tos/agreed";
    private static final String PATH_NEWS = "/v1/news";
    private static final String PATH_MARK_NOTIFICATIONS_SEEN = "/seen";
    private static final String PATH_DISMISS_NOTIFICATIONS = "/dismiss";
    public static final Environment ENVIRONMENT = (Environment) Optional.ofNullable(System.getenv("realms.environment")).or(() -> {
        return Optional.ofNullable(System.getProperty("realms.environment"));
    }).flatMap(Environment::byName).orElse(Environment.PRODUCTION);
    private static final Logger LOGGER = LogUtils.getLogger();
    private static volatile RealmsClient realmsClientInstance = null;
    private static final GuardedSerializer GSON = new GuardedSerializer();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsClient$CompatibleVersionResponse.class */
    public enum CompatibleVersionResponse {
        COMPATIBLE,
        OUTDATED,
        OTHER
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/RealmsClient$Environment.class */
    public enum Environment {
        PRODUCTION("pc.realms.minecraft.net", "java.frontendlegacy.realms.minecraft-services.net", "https"),
        STAGE("pc-stage.realms.minecraft.net", "java.frontendlegacy.stage-c2a40e62.realms.minecraft-services.net", "https"),
        LOCAL("localhost:8080", "localhost:8080", "http");

        public final String baseUrl;
        public final String alternativeUrl;
        public final String protocol;

        Environment(String $$0, String $$1, String $$2) {
            this.baseUrl = $$0;
            this.alternativeUrl = $$1;
            this.protocol = $$2;
        }

        public static Optional<Environment> byName(String $$0) {
            switch ($$0.toLowerCase(Locale.ROOT)) {
                case "production":
                    return Optional.of(PRODUCTION);
                case "local":
                    return Optional.of(LOCAL);
                case "stage":
                case "staging":
                    return Optional.of(STAGE);
                default:
                    return Optional.empty();
            }
        }
    }

    public static RealmsClient getOrCreate() {
        Minecraft $$0 = Minecraft.getInstance();
        return getOrCreate($$0);
    }

    public static RealmsClient getOrCreate(Minecraft $$0) {
        String $$1 = $$0.getUser().getName();
        String $$2 = $$0.getUser().getSessionId();
        RealmsClient $$3 = realmsClientInstance;
        if ($$3 != null) {
            return $$3;
        }
        synchronized (RealmsClient.class) {
            RealmsClient $$4 = realmsClientInstance;
            if ($$4 != null) {
                return $$4;
            }
            RealmsClient $$42 = new RealmsClient($$2, $$1, $$0);
            realmsClientInstance = $$42;
            return $$42;
        }
    }

    private RealmsClient(String $$0, String $$1, Minecraft $$2) {
        this.sessionId = $$0;
        this.username = $$1;
        this.minecraft = $$2;
        RealmsClientConfig.setProxy($$2.getProxy());
        this.featureFlags = CompletableFuture.supplyAsync(this::fetchFeatureFlags, Util.nonCriticalIoPool());
    }

    public Set<String> getFeatureFlags() {
        return this.featureFlags.join();
    }

    private Set<String> fetchFeatureFlags() {
        if (Minecraft.getInstance().isOfflineDeveloperMode()) {
            return Set.of();
        }
        String $$0 = url(FEATURE_FLAGS_RESOURCE, null, false);
        try {
            String $$1 = execute(Request.get($$0, 5000, 10000));
            JsonArray $$2 = LenientJsonParser.parse($$1).getAsJsonArray();
            Set<String> $$3 = (Set) $$2.asList().stream().map((v0) -> {
                return v0.getAsString();
            }).collect(Collectors.toSet());
            LOGGER.debug("Fetched Realms feature flags: {}", $$3);
            return $$3;
        } catch (RealmsServiceException $$4) {
            LOGGER.error("Failed to fetch Realms feature flags", $$4);
            return Set.of();
        } catch (Exception $$5) {
            LOGGER.error("Could not parse Realms feature flags", $$5);
            return Set.of();
        }
    }

    public RealmsServerList listRealms() throws RealmsServiceException {
        String $$0 = url(WORLDS_RESOURCE_PATH);
        if (RealmsMainScreen.isSnapshot()) {
            $$0 = $$0 + "/listUserWorldsOfType/any";
        }
        String $$1 = execute(Request.get($$0));
        return RealmsServerList.parse(GSON, $$1);
    }

    public List<RealmsServer> listSnapshotEligibleRealms() throws RealmsServiceException {
        String $$0 = url("worlds/listPrereleaseEligibleWorlds");
        String $$1 = execute(Request.get($$0));
        return RealmsServerList.parse(GSON, $$1).servers();
    }

    public RealmsServer createSnapshotRealm(Long $$0) throws RealmsServiceException {
        String $$1 = String.valueOf($$0);
        String $$2 = url("worlds" + PATH_CREATE_SNAPSHOT_REALM.replace("$PARENT_WORLD_ID", $$1));
        return RealmsServer.parse(GSON, execute(Request.post($$2, $$1)));
    }

    public List<RealmsNotification> getNotifications() throws RealmsServiceException {
        String $$0 = url(NOTIFICATIONS_RESOURCE);
        String $$1 = execute(Request.get($$0));
        return RealmsNotification.parseList($$1);
    }

    private static JsonArray uuidListToJsonArray(List<UUID> $$0) {
        JsonArray $$1 = new JsonArray();
        for (UUID $$2 : $$0) {
            if ($$2 != null) {
                $$1.add($$2.toString());
            }
        }
        return $$1;
    }

    public void notificationsSeen(List<UUID> $$0) throws RealmsServiceException {
        String $$1 = url("notifications/seen");
        execute(Request.post($$1, GSON.toJson((JsonElement) uuidListToJsonArray($$0))));
    }

    public void notificationsDismiss(List<UUID> $$0) throws RealmsServiceException {
        String $$1 = url("notifications/dismiss");
        execute(Request.post($$1, GSON.toJson((JsonElement) uuidListToJsonArray($$0))));
    }

    public RealmsServer getOwnRealm(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + PATH_WORLD_GET.replace("$ID", String.valueOf($$0)));
        String $$2 = execute(Request.get($$1));
        return RealmsServer.parse(GSON, $$2);
    }

    public PreferredRegionsDto getPreferredRegionSelections() throws RealmsServiceException {
        String $$0 = url(PREFERRED_REGION_RESOURCE);
        String $$1 = execute(Request.get($$0));
        try {
            PreferredRegionsDto $$2 = (PreferredRegionsDto) GSON.fromJson($$1, PreferredRegionsDto.class);
            if ($$2 == null) {
                return PreferredRegionsDto.empty();
            }
            Set<RealmsRegion> $$3 = (Set) $$2.regionData().stream().map((v0) -> {
                return v0.region();
            }).collect(Collectors.toSet());
            for (RealmsRegion $$4 : RealmsRegion.values()) {
                if ($$4 != RealmsRegion.INVALID_REGION && !$$3.contains($$4)) {
                    LOGGER.debug("No realms region matching {} in server response", $$4);
                }
            }
            return $$2;
        } catch (Exception $$5) {
            LOGGER.error("Could not parse PreferredRegionSelections", $$5);
            return PreferredRegionsDto.empty();
        }
    }

    public RealmsServerPlayerLists getLiveStats() throws RealmsServiceException {
        String $$0 = url("activities/liveplayerlist");
        String $$1 = execute(Request.get($$0));
        return RealmsServerPlayerLists.parse($$1);
    }

    public RealmsJoinInformation join(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + PATH_WORLD_JOIN.replace("$ID", $$0));
        String $$2 = execute(Request.get($$1, 5000, 30000));
        return RealmsJoinInformation.parse(GSON, $$2);
    }

    public void initializeRealm(long $$0, String $$1, String $$2) throws RealmsServiceException {
        RealmsDescriptionDto $$3 = new RealmsDescriptionDto($$1, $$2);
        String $$4 = url("worlds" + PATH_INITIALIZE.replace("$WORLD_ID", String.valueOf($$0)));
        String $$5 = GSON.toJson($$3);
        execute(Request.post($$4, $$5, 5000, 10000));
    }

    public boolean hasParentalConsent() throws RealmsServiceException {
        String $$0 = url("mco/available");
        String $$1 = execute(Request.get($$0));
        return Boolean.parseBoolean($$1);
    }

    public CompatibleVersionResponse clientCompatible() throws RealmsServiceException {
        String $$0 = url("mco/client/compatible");
        String $$1 = execute(Request.get($$0));
        try {
            CompatibleVersionResponse $$4 = CompatibleVersionResponse.valueOf($$1);
            return $$4;
        } catch (IllegalArgumentException e) {
            throw new RealmsServiceException(RealmsError.CustomError.unknownCompatibilityResponse($$1));
        }
    }

    public void uninvite(long $$0, UUID $$1) throws RealmsServiceException {
        String $$2 = url("invites" + PATH_WORLD_UNINVITE.replace("$WORLD_ID", String.valueOf($$0)).replace("$UUID", UndashedUuid.toString($$1)));
        execute(Request.delete($$2));
    }

    public void uninviteMyselfFrom(long $$0) throws RealmsServiceException {
        String $$1 = url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
        execute(Request.delete($$1));
    }

    public List<PlayerInfo> invite(long $$0, String $$1) throws RealmsServiceException {
        OutboundPlayer $$2 = new OutboundPlayer();
        $$2.name = $$1;
        String $$3 = url("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
        String $$4 = execute(Request.post($$3, GSON.toJson($$2)));
        return RealmsServer.parse(GSON, $$4).players;
    }

    public BackupList backupsFor(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + PATH_WORLD_BACKUPS.replace("$WORLD_ID", String.valueOf($$0)));
        String $$2 = execute(Request.get($$1));
        return BackupList.parse($$2);
    }

    public void updateConfiguration(long $$0, String $$1, String $$2, RegionSelectionPreferenceDto $$3, int $$4, RealmsWorldOptions $$5, List<RealmsSetting> $$6) throws RealmsServiceException {
        RegionSelectionPreferenceDto $$7 = $$3 != null ? $$3 : new RegionSelectionPreferenceDto(RegionSelectionPreference.DEFAULT_SELECTION, null);
        RealmsDescriptionDto $$8 = new RealmsDescriptionDto($$1, $$2);
        RealmsSlotUpdateDto $$9 = new RealmsSlotUpdateDto($$4, $$5, RealmsSetting.isHardcore($$6));
        RealmsConfigurationDto $$10 = new RealmsConfigurationDto($$9, $$6, $$7, $$8);
        String $$11 = url("worlds" + PATH_WORLD_CONFIGURE.replace("$WORLD_ID", String.valueOf($$0)));
        execute(Request.post($$11, GSON.toJson($$10)));
    }

    public void updateSlot(long $$0, int $$1, RealmsWorldOptions $$2, List<RealmsSetting> $$3) throws RealmsServiceException {
        String $$4 = url("worlds" + PATH_SLOT.replace("$WORLD_ID", String.valueOf($$0)).replace("$SLOT_ID", String.valueOf($$1)));
        String $$5 = GSON.toJson(new RealmsSlotUpdateDto($$1, $$2, RealmsSetting.isHardcore($$3)));
        execute(Request.post($$4, $$5));
    }

    public boolean switchSlot(long $$0, int $$1) throws RealmsServiceException {
        String $$2 = url("worlds" + PATH_SLOT.replace("$WORLD_ID", String.valueOf($$0)).replace("$SLOT_ID", String.valueOf($$1)));
        String $$3 = execute(Request.put($$2, ""));
        return Boolean.valueOf($$3).booleanValue();
    }

    public void restoreWorld(long $$0, String $$1) throws RealmsServiceException {
        String $$2 = url("worlds" + PATH_WORLD_BACKUPS.replace("$WORLD_ID", String.valueOf($$0)), "backupId=" + $$1);
        execute(Request.put($$2, "", 40000, 600000));
    }

    public WorldTemplatePaginatedList fetchWorldTemplates(int $$0, int $$1, RealmsServer.WorldType $$2) throws RealmsServiceException {
        String $$3 = url("worlds" + PATH_TEMPLATES.replace("$WORLD_TYPE", $$2.toString()), String.format(Locale.ROOT, "page=%d&pageSize=%d", Integer.valueOf($$0), Integer.valueOf($$1)));
        String $$4 = execute(Request.get($$3));
        return WorldTemplatePaginatedList.parse($$4);
    }

    public Boolean putIntoMinigameMode(long $$0, String $$1) throws RealmsServiceException {
        String $$2 = PATH_PUT_INTO_MINIGAMES_MODE.replace("$MINIGAME_ID", $$1).replace("$WORLD_ID", String.valueOf($$0));
        String $$3 = url("worlds" + $$2);
        return Boolean.valueOf(execute(Request.put($$3, "")));
    }

    public Ops op(long $$0, UUID $$1) throws RealmsServiceException {
        String $$2 = PATH_OP.replace("$WORLD_ID", String.valueOf($$0)).replace("$PROFILE_UUID", UndashedUuid.toString($$1));
        String $$3 = url("ops" + $$2);
        return Ops.parse(execute(Request.post($$3, "")));
    }

    public Ops deop(long $$0, UUID $$1) throws RealmsServiceException {
        String $$2 = PATH_OP.replace("$WORLD_ID", String.valueOf($$0)).replace("$PROFILE_UUID", UndashedUuid.toString($$1));
        String $$3 = url("ops" + $$2);
        return Ops.parse(execute(Request.delete($$3)));
    }

    public Boolean open(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + PATH_WORLD_OPEN.replace("$WORLD_ID", String.valueOf($$0)));
        String $$2 = execute(Request.put($$1, ""));
        return Boolean.valueOf($$2);
    }

    public Boolean close(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + PATH_WORLD_CLOSE.replace("$WORLD_ID", String.valueOf($$0)));
        String $$2 = execute(Request.put($$1, ""));
        return Boolean.valueOf($$2);
    }

    public Boolean resetWorldWithTemplate(long $$0, String $$1) throws RealmsServiceException {
        RealmsWorldResetDto $$2 = new RealmsWorldResetDto(null, Long.valueOf($$1).longValue(), -1, false, Set.of());
        String $$3 = url("worlds" + PATH_WORLD_RESET.replace("$WORLD_ID", String.valueOf($$0)));
        String $$4 = execute(Request.post($$3, GSON.toJson($$2), 30000, 80000));
        return Boolean.valueOf($$4);
    }

    public Subscription subscriptionFor(long $$0) throws RealmsServiceException {
        String $$1 = url("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
        String $$2 = execute(Request.get($$1));
        return Subscription.parse($$2);
    }

    public int pendingInvitesCount() throws RealmsServiceException {
        return pendingInvites().pendingInvites().size();
    }

    public PendingInvitesList pendingInvites() throws RealmsServiceException {
        String $$0 = url("invites/pending");
        String $$1 = execute(Request.get($$0));
        PendingInvitesList $$2 = PendingInvitesList.parse($$1);
        $$2.pendingInvites().removeIf(this::isBlocked);
        return $$2;
    }

    private boolean isBlocked(PendingInvite $$0) {
        return this.minecraft.getPlayerSocialManager().isBlocked($$0.realmOwnerUuid());
    }

    public void acceptInvitation(String $$0) throws RealmsServiceException {
        String $$1 = url("invites" + PATH_ACCEPT_INVITE.replace("$INVITATION_ID", $$0));
        execute(Request.put($$1, ""));
    }

    public WorldDownload requestDownloadInfo(long $$0, int $$1) throws RealmsServiceException {
        String $$2 = url("worlds" + PATH_WORLD_DOWNLOAD.replace("$WORLD_ID", String.valueOf($$0)).replace("$SLOT_ID", String.valueOf($$1)));
        String $$3 = execute(Request.get($$2));
        return WorldDownload.parse($$3);
    }

    public UploadInfo requestUploadInfo(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + PATH_WORLD_UPLOAD.replace("$WORLD_ID", String.valueOf($$0)));
        String $$2 = UploadTokenCache.get($$0);
        UploadInfo $$3 = UploadInfo.parse(execute(Request.put($$1, UploadInfo.createRequest($$2))));
        if ($$3 != null) {
            UploadTokenCache.put($$0, $$3.token());
        }
        return $$3;
    }

    public void rejectInvitation(String $$0) throws RealmsServiceException {
        String $$1 = url("invites" + PATH_REJECT_INVITE.replace("$INVITATION_ID", $$0));
        execute(Request.put($$1, ""));
    }

    public void agreeToTos() throws RealmsServiceException {
        String $$0 = url("mco/tos/agreed");
        execute(Request.post($$0, ""));
    }

    public RealmsNews getNews() throws RealmsServiceException {
        String $$0 = url("mco/v1/news");
        String $$1 = execute(Request.get($$0, 5000, 10000));
        return RealmsNews.parse($$1);
    }

    public void sendPingResults(PingResult $$0) throws RealmsServiceException {
        String $$1 = url(REGIONS_RESOURCE);
        execute(Request.post($$1, GSON.toJson($$0)));
    }

    public Boolean trialAvailable() throws RealmsServiceException {
        String $$0 = url(TRIALS_RESOURCE);
        String $$1 = execute(Request.get($$0));
        return Boolean.valueOf($$1);
    }

    public void deleteRealm(long $$0) throws RealmsServiceException {
        String $$1 = url("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf($$0)));
        execute(Request.delete($$1));
    }

    private String url(String $$0) throws RealmsServiceException {
        return url($$0, null);
    }

    private String url(String $$0, String $$1) {
        return url($$0, $$1, getFeatureFlags().contains("realms_in_aks"));
    }

    private static String url(String $$0, String $$1, boolean $$2) {
        try {
            return new URI(ENVIRONMENT.protocol, $$2 ? ENVIRONMENT.alternativeUrl : ENVIRONMENT.baseUrl, "/" + $$0, $$1, null).toASCIIString();
        } catch (URISyntaxException $$3) {
            throw new IllegalArgumentException($$0, $$3);
        }
    }

    private String execute(Request<?> $$0) throws RealmsServiceException {
        $$0.cookie("sid", this.sessionId);
        $$0.cookie("user", this.username);
        $$0.cookie("version", SharedConstants.getCurrentVersion().name());
        $$0.addSnapshotHeader(RealmsMainScreen.isSnapshot());
        try {
            int $$1 = $$0.responseCode();
            if ($$1 == 503 || $$1 == 277) {
                int $$2 = $$0.getRetryAfterHeader();
                throw new RetryCallException($$2, $$1);
            }
            String $$3 = $$0.text();
            if ($$1 < 200 || $$1 >= 300) {
                if ($$1 == 401) {
                    String $$4 = $$0.getHeader("WWW-Authenticate");
                    LOGGER.info("Could not authorize you against Realms server: {}", $$4);
                    throw new RealmsServiceException(new RealmsError.AuthenticationError($$4));
                }
                String $$5 = $$0.connection.getContentType();
                if ($$5 != null && $$5.startsWith("text/html")) {
                    throw new RealmsServiceException(RealmsError.CustomError.htmlPayload($$1, $$3));
                }
                RealmsError $$6 = RealmsError.parse($$1, $$3);
                throw new RealmsServiceException($$6);
            }
            return $$3;
        } catch (RealmsHttpException $$7) {
            throw new RealmsServiceException(RealmsError.CustomError.connectivityError($$7));
        }
    }
}
