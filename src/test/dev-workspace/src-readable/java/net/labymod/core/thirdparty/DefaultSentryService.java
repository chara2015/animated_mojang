package net.labymod.core.thirdparty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.sentry.Attachment;
import io.sentry.ScopeCallback;
import io.sentry.Sentry;
import io.sentry.protocol.User;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.ConfigLoader;
import net.labymod.api.configuration.loader.impl.AbstractConfigLoader;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.Implements;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.thirdparty.SentryService;
import net.labymod.api.util.Pair;
import net.labymod.api.util.UUIDHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.configuration.labymod.main.DefaultLabyConfig;
import net.labymod.core.labyconnect.util.Snooper;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/DefaultSentryService.class */
@Singleton
@Implements(SentryService.class)
public final class DefaultSentryService implements SentryService {
    private static final Gson GSON = new GsonBuilder().create();
    private ScreenInstance previousScreen;
    private ScreenInstance currentScreen;
    LabyModLoader loader = DefaultLabyModLoader.getInstance();
    private final long initializeTime = TimeUtil.getCurrentTimeMillis();

    public DefaultSentryService() {
        if (this.loader.isLabyModDevelopmentEnvironment()) {
            return;
        }
        initialize();
    }

    @Override // net.labymod.api.thirdparty.SentryService
    public void capture(Throwable throwable) {
        if (Sentry.isEnabled()) {
            Sentry.captureException(throwable);
        }
    }

    public void initialize() {
        Sentry.init(options -> {
            options.setDsn("https://ee56a4c224624431bf9c659d7e1856de@sentry.laby.tech/14");
            options.setTracesSampleRate(Double.valueOf(1.0d));
            options.setEnvironment(BuildData.releaseType());
            options.setBeforeSend((event, hint) -> {
                try {
                    if (!Snooper.isInternalReleaseChannel()) {
                        if (!isAnonymousStatisticsEnabled()) {
                            return null;
                        }
                    }
                } catch (Exception e) {
                }
                if (event.getServerName() != null) {
                    event.setServerName((String) null);
                }
                try {
                    Pair<String, UUID> currentUser = getUser();
                    User user = new User();
                    user.setId(currentUser.getSecond().hashCode());
                    user.setEmail(Snooper.getIdentifier());
                    if (Snooper.isInternalReleaseChannel()) {
                        user.setUsername(currentUser.getSecond().toString());
                    }
                    event.setUser(user);
                } catch (Exception e2) {
                }
                try {
                    event.setExtra("screen-current", getScreenName(this.currentScreen));
                    event.setExtra("screen-previous", getScreenName(this.previousScreen));
                } catch (Exception e3) {
                }
                try {
                    long millis = TimeUtil.getCurrentTimeMillis() - this.initializeTime;
                    if (millis < 1000) {
                        event.setExtra("age", millis + "ms");
                    } else {
                        event.setExtra("age", (millis / 1000) + "s");
                    }
                } catch (Exception e4) {
                }
                try {
                    Path path = DefaultLabyModLoader.getInstance().getGameDirectory().resolve("logs/latest.log");
                    List<String> strings = Files.readAllLines(path, StandardCharsets.UTF_8);
                    StringBuilder builder = new StringBuilder();
                    for (String string : strings) {
                        String censoredLogLine = getCensoredLogLine(string);
                        if (censoredLogLine != null) {
                            builder.append(censoredLogLine).append('\n');
                        }
                    }
                    hint.addAttachment(new Attachment(builder.toString().getBytes(), "latest.txt"));
                } catch (Exception e5) {
                }
                return event;
            });
        });
        configureScope();
    }

    private Pair<String, UUID> getUser() {
        Session session = Laby.labyAPI().minecraft().sessionAccessor().getSession();
        String userName = null;
        UUID uuid = null;
        if (session == null) {
            List<String> arguments = DefaultLabyModLoader.getInstance().getArguments();
            for (int i = 0; i < arguments.size(); i++) {
                try {
                    String argument = arguments.get(i);
                    if (argument.equals("--username")) {
                        userName = arguments.get(i + 1);
                    } else if (argument.equals("--uuid")) {
                        uuid = UUID.fromString(arguments.get(i + 1));
                    }
                } catch (Exception e) {
                }
            }
        } else {
            userName = session.getUsername();
            uuid = session.getUniqueId();
        }
        if (uuid == null && userName != null) {
            uuid = UUIDHelper.createUniqueId(userName);
        }
        return Pair.of(userName == null ? "Unknown" : userName, uuid == null ? new UUID(0L, 0L) : uuid);
    }

    private String getScreenName(ScreenInstance screen) {
        if (screen == null) {
            return "none";
        }
        if (screen instanceof ScreenWrapper) {
            ScreenWrapper wrapper = (ScreenWrapper) screen;
            Object versionedScreen = wrapper.getVersionedScreen();
            if (versionedScreen instanceof LabyScreenAccessor) {
                LabyScreenAccessor accessor = (LabyScreenAccessor) versionedScreen;
                return accessor.screen().getClass().getName();
            }
            return wrapper.getVersionedScreen().getClass().getName();
        }
        if (screen instanceof LabyScreen) {
            return ((LabyScreen) screen).getClass().getName();
        }
        return screen.getClass().getName();
    }

    private void configureScope() {
        configureScope(scope -> {
            scope.setTag("operating_system", OperatingSystem.getPlatform().getName());
            scope.setTag("mcversion", System.getProperty("net.labymod.running-version", this.loader.version().toString()));
            scope.setTag("version", LabyMod.getInstance().getVersion());
            scope.setTag("launcher", System.getProperty("net.labymod.launcher", System.getProperty("minecraft.launcher.brand", "Unknown")));
            SystemInfo systemInfo = Laby.references().systemInfo();
            scope.setExtra("processor", systemInfo.processor().name());
        });
    }

    private String getCensoredLogLine(String line) {
        if (line.contains("[CHAT]") || line.contains("Setting user: ")) {
            return null;
        }
        return censorServerAddress(line);
    }

    private String censorServerAddress(String line) {
        int connectingTo = line.indexOf("Connecting to");
        if (connectingTo == -1) {
            return line;
        }
        int nextCommaIndex = line.indexOf(",");
        if (nextCommaIndex == -1) {
            return line;
        }
        int lastSpaceIndex = line.lastIndexOf(32, nextCommaIndex);
        if (lastSpaceIndex == -1) {
            return line;
        }
        StringBuilder builder = new StringBuilder();
        builder.append((CharSequence) line, 0, lastSpaceIndex + 1);
        String address = line.substring(lastSpaceIndex + 1, nextCommaIndex);
        builder.append("*".repeat(address.length() / 2));
        builder.append((CharSequence) address, address.length() / 2, address.length());
        builder.append((CharSequence) line, nextCommaIndex, line.length());
        return builder.toString();
    }

    public void configureScope(ScopeCallback callback) {
        Sentry.configureScope(callback);
    }

    @Subscribe
    public void onScreenOpen(ScreenDisplayEvent event) {
        this.previousScreen = event.getPreviousScreen();
        this.currentScreen = event.getScreen();
    }

    private boolean isAnonymousStatisticsEnabled() {
        LabyConfigProvider provider;
        LabyConfig config;
        try {
            provider = LabyConfigProvider.INSTANCE;
            config = provider.get();
        } catch (Exception e) {
            return false;
        }
        if (config != null) {
            return config.other().anonymousStatistics().get().booleanValue();
        }
        ConfigLoader loader = provider.getLoader();
        Path file = ((AbstractConfigLoader) loader).getPath(DefaultLabyConfig.class);
        BufferedReader reader = Files.newBufferedReader(file);
        try {
            JsonElement element = (JsonElement) GSON.fromJson(reader, JsonElement.class);
            if (!element.isJsonObject()) {
                if (reader != null) {
                    reader.close();
                }
                return false;
            }
            JsonObject object = element.getAsJsonObject();
            JsonObject otherObject = object.getAsJsonObject("other");
            if (otherObject == null) {
                if (reader != null) {
                    reader.close();
                }
                return false;
            }
            JsonPrimitive anonymousStatistics = otherObject.getAsJsonPrimitive("anonymousStatistics");
            if (anonymousStatistics == null) {
                if (reader != null) {
                    reader.close();
                }
                return false;
            }
            boolean zIsBoolean = anonymousStatistics.isBoolean();
            if (reader != null) {
                reader.close();
            }
            return zIsBoolean;
        } finally {
        }
        return false;
    }
}
