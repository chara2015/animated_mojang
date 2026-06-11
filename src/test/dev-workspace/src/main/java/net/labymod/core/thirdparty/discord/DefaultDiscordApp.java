package net.labymod.core.thirdparty.discord;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.GameSDKException;
import de.jcm.discordgamesdk.LogLevel;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityAssets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.labymod.discordrpc.DiscordActivityServerUpdateEvent;
import net.labymod.api.event.labymod.discordrpc.DiscordActivityUpdateEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.models.Implements;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.thirdparty.discord.DiscordApp;
import net.labymod.api.util.AddressUtil;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.thirdparty.discord.natives.DiscordNativeDownloader;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/discord/DefaultDiscordApp.class */
@Singleton
@Implements(DiscordApp.class)
public class DefaultDiscordApp implements DiscordApp {
    private static final long APPLICATION_ID = 576456544942686228L;
    private static final long UPDATE_COOLDOWN = 500;
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger("DiscordRPC Client");
    private final DiscordNativeDownloader nativeDownloader;
    private CreateParams parameters;
    private Core core;
    private Activity lastActivity;
    private long lastRunCallbackTime;
    private boolean running;
    private boolean updateRequired;
    private DiscordActivity displayedActivity;
    private DiscordActivity.Asset userAsset;
    private final DiscordEventAdapter discordEventAdapter = new DiscordEventListener();
    private Instant startTime = Instant.now();
    private final DiscordActivity.Asset defaultAsset = DiscordActivity.Asset.of("labymod", String.format(Locale.ROOT, "Minecraft %s - LabyMod %s", Laby.labyAPI().minecraft().getVersion(), Laby.labyAPI().getVersion()));

    @Inject
    public DefaultDiscordApp(DiscordNativeDownloader nativeDownloader) {
        this.nativeDownloader = nativeDownloader;
    }

    @Override // net.labymod.api.thirdparty.discord.DiscordApp
    public void displayDefaultActivity(boolean startTimeReset) {
        DiscordActivity.Builder builder = defaultActivityBuilder();
        if (startTimeReset) {
            builder.start();
        }
        displayActivity(builder.build());
    }

    @Override // net.labymod.api.thirdparty.discord.DiscordApp
    public void displayActivity(@NotNull DiscordActivity discordActivity) {
        Objects.requireNonNull(discordActivity, "Activity to display cannot be null");
        if (!this.running || discordActivity.equals(this.displayedActivity)) {
            return;
        }
        DiscordActivityUpdateEvent event = new DiscordActivityUpdateEvent(discordActivity);
        if (event.isCancelled()) {
            return;
        }
        displayInternal(event.activity());
    }

    @Override // net.labymod.api.thirdparty.discord.DiscordApp
    @Nullable
    public DiscordActivity getDisplayedActivity() {
        return this.displayedActivity;
    }

    @Override // net.labymod.api.thirdparty.discord.DiscordApp
    public boolean isRunning() {
        return this.running;
    }

    public void initialize() {
        this.nativeDownloader.download(library -> {
            if (library == null) {
                return;
            }
            initialize(library);
            displayDefaultActivity();
        });
    }

    private void initialize(Path discordLibrary) {
        try {
            Core.init(IOUtil.toFile(discordLibrary));
            this.parameters = new CreateParams();
            this.parameters.setClientID(APPLICATION_ID);
            this.parameters.setFlags(new CreateParams.Flags[]{CreateParams.Flags.NO_REQUIRE_DISCORD});
            this.parameters.registerEventHandler(this.discordEventAdapter);
            this.core = new Core(this.parameters);
            this.core.setLogHook(LogLevel.INFO, (level, message) -> {
                switch (AnonymousClass1.$SwitchMap$de$jcm$discordgamesdk$LogLevel[level.ordinal()]) {
                    case 1:
                        LOGGER.error(message, new Object[0]);
                        break;
                    case 2:
                        LOGGER.warn(message, new Object[0]);
                        break;
                    case 3:
                        LOGGER.info(message, new Object[0]);
                        break;
                    case 4:
                        LOGGER.debug(message, new Object[0]);
                        break;
                }
            });
            this.running = true;
        } catch (Throwable throwable) {
            LOGGER.error("Discord integration could not be started {}", throwable.getMessage());
            try {
                Files.deleteIfExists(discordLibrary);
            } catch (IOException e) {
                LOGGER.error("Discord library could not be deleted ({})", throwable.getMessage());
            }
        }
    }

    /* JADX INFO: renamed from: net.labymod.core.thirdparty.discord.DefaultDiscordApp$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/discord/DefaultDiscordApp$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$de$jcm$discordgamesdk$LogLevel = new int[LogLevel.values().length];

        static {
            try {
                $SwitchMap$de$jcm$discordgamesdk$LogLevel[LogLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$de$jcm$discordgamesdk$LogLevel[LogLevel.WARN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$de$jcm$discordgamesdk$LogLevel[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$de$jcm$discordgamesdk$LogLevel[LogLevel.DEBUG.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void updateServerInfo(boolean resetStart) {
        ServerData currentServerData = Laby.labyAPI().serverController().getCurrentServerData();
        if (currentServerData == null) {
            return;
        }
        updateServerInfo(currentServerData, resetStart);
    }

    public void updateServerInfo(ServerData serverData, boolean resetStart) {
        boolean showServerAddress = Laby.labyAPI().config().other().discord().showServerAddress().get().booleanValue();
        updateServerInfo(serverData, showServerAddress, resetStart);
    }

    public void dispose() {
        try {
            if (this.core != null) {
                this.core.close();
                this.core = null;
            }
            if (this.parameters != null) {
                this.parameters.close();
                this.parameters = null;
            }
            this.running = false;
        } catch (GameSDKException exception) {
            LOGGER.error(exception.getMessage(), new Object[0]);
            this.running = false;
        }
    }

    public void updateServerInfo(ServerData serverData, boolean showServerAddress, boolean resetStart) {
        if (!this.running) {
            return;
        }
        if (serverData == null) {
            displayDefaultActivity(resetStart);
            return;
        }
        DiscordActivity.Builder builder = DiscordActivity.builder(this);
        if (resetStart) {
            builder.start();
        }
        if (showServerAddress) {
            UUID lanHost = LabyMod.references().sharedLanWorldService().getCurrentHost();
            LabyConnectSession session = Laby.references().labyConnect().getSession();
            if (lanHost != null && session != null) {
                Friend friend = session.getFriend(lanHost);
                builder.details("LAN world of " + (friend == null ? "Unknown" : friend.getName()));
            } else {
                String host = serverData.address().toString();
                if (AddressUtil.isLocalHost(host)) {
                    host = "localhost";
                }
                builder.details(host);
                Optional<ServerGroup> serverGroup = Laby.labyAPI().labyNetController().getServerByIp(host);
                serverGroup.ifPresent(group -> {
                    group.getAttachment("icon").ifPresent(icon -> {
                        builder.largeAsset(DiscordActivity.Asset.ofWeeklyCached(icon.getUrl(), group.getNiceName()));
                    });
                });
            }
        }
        displayServerActivity(serverData, builder.build());
    }

    public void displayServerActivity(ServerData serverData, DiscordActivity activity) {
        if (serverData == null || activity.equals(this.displayedActivity)) {
            return;
        }
        DiscordActivityUpdateEvent event = new DiscordActivityServerUpdateEvent(serverData, activity);
        if (event.isCancelled()) {
            return;
        }
        displayActivity(event.activity());
    }

    public void tick() {
        if (this.core == null || this.lastActivity == null) {
            return;
        }
        long currentTimeMillis = TimeUtil.getMillis();
        if (this.lastRunCallbackTime < currentTimeMillis) {
            try {
                this.core.runCallbacks();
            } catch (GameSDKException e) {
                LOGGER.error("Failed to run Discord callbacks", e);
            }
            this.lastRunCallbackTime = currentTimeMillis + UPDATE_COOLDOWN;
        }
        if (this.updateRequired) {
            this.core.activityManager().updateActivity(this.lastActivity);
            this.updateRequired = false;
        }
    }

    public void updateUserAsset(String userName, UUID uniqueId) {
        this.userAsset = DiscordActivity.Asset.ofWeeklyCached(String.format(Locale.ROOT, Constants.Urls.LABYNET_HEAD, uniqueId), userName);
        displayInternal(this.displayedActivity);
    }

    public void displayInternal(DiscordActivity discordActivity) {
        if (!this.running) {
            return;
        }
        if (this.lastActivity != null) {
            this.lastActivity.close();
        }
        this.displayedActivity = discordActivity;
        this.lastActivity = convertDisplayedActivity(discordActivity);
        this.updateRequired = true;
    }

    private DiscordActivity.Builder defaultActivityBuilder() {
        return DiscordActivity.builder(this).details("Menu");
    }

    private Activity convertDisplayedActivity(DiscordActivity discordActivity) {
        Activity activity = new Activity();
        String details = discordActivity.getDetails();
        activity.setDetails(details == null ? "" : details);
        String state = discordActivity.getState();
        activity.setState(state == null ? "" : state);
        if (discordActivity.getEndTime() != null) {
            activity.timestamps().setEnd(discordActivity.getEndTime());
        } else {
            this.startTime = discordActivity.getStartTime() == null ? this.startTime : discordActivity.getStartTime();
            activity.timestamps().setStart(this.startTime);
        }
        applyActivityAssets(activity.assets(), discordActivity.getLargeAsset(), discordActivity.getSmallAsset());
        return activity;
    }

    private void applyActivityAssets(ActivityAssets activityAssets, DiscordActivity.Asset largeAsset, DiscordActivity.Asset smallAsset) {
        if (largeAsset == null) {
            largeAsset = this.defaultAsset;
        } else {
            smallAsset = this.defaultAsset;
        }
        if (smallAsset == null && Laby.labyAPI().config().other().discord().displayAccount().get().booleanValue()) {
            smallAsset = this.userAsset;
        }
        if (largeAsset.isValid()) {
            activityAssets.setLargeImage(getAssetKey(largeAsset));
            activityAssets.setLargeText(largeAsset.getText());
        }
        if (smallAsset != null && smallAsset.isValid()) {
            activityAssets.setSmallImage(getAssetKey(smallAsset));
            activityAssets.setSmallText(smallAsset.getText());
        }
    }

    private String getAssetKey(DiscordActivity.Asset asset) {
        String key = asset.getKey();
        if (!asset.isWeeklyCached() || !key.startsWith("http")) {
            return key;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            return key + "?cacheWeek=" + calendar.get(1) + "-" + calendar.get(3);
        } catch (Exception e) {
            return key;
        }
    }
}
