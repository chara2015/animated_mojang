package net.labymod.core.flint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.flint.downloader.FlintDownloadTask;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.flint.marketplace.FlintOrganization;
import net.labymod.core.flint.marketplace.FlintPermission;
import net.labymod.core.flint.marketplace.FlintTag;
import net.labymod.core.main.LabyMod;
import net.labymod.core.platform.launcher.DefaultLauncherService;
import net.labymod.core.platform.launcher.communication.LauncherCommunicationClient;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherModLoaderChangePacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/FlintController.class */
@Singleton
@Referenceable
public class FlintController {
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(VersionCompatibility.class, new VersionCompatibilityDeserializer()).create();
    private static final Map<Integer, FlintOrganization> ORGANIZATIONS = new HashMap();
    private static final Map<String, FlintModification> MODIFICATIONS = new HashMap();
    private static final Map<String, List<FlintModification.Review>> REVIEWS = new HashMap();
    private static final Map<String, List<FlintModification.Changelog>> CHANGELOGS = new HashMap();
    private static final Map<String, List<ResultCallback<?>>> CURRENT_QUERIES = new HashMap();
    private final LabyAPI labyAPI;
    private final Logging logging;
    private final Map<Integer, FlintTag> tags = new HashMap();
    private final List<FlintPermission> permissions = new ArrayList();
    private final String version = LabyMod.getInstance().labyModLoader().version().toString();
    private final FlintIndex flintIndex = new FlintIndex(this);
    private final FlintDownloader flintDownloader = LabyMod.references().flintDownloader();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/FlintController$SetModLoaderResult.class */
    public enum SetModLoaderResult {
        CONTINUE,
        CONTINUE_NO_SUCCESS,
        SUCCESS,
        CANCEL
    }

    @Inject
    public FlintController(Logging.Factory loggingFactory, LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.logging = loggingFactory.create(FlintController.class);
        loadTags();
        loadPermissions();
    }

    public static String getVariableBrandUrl(String baseUrl, int width, int height) {
        String url;
        String url2 = baseUrl;
        if (height != 0) {
            url2 = url2 + "?height=" + height;
        }
        if (width != 0) {
            if (height == 0) {
                url = url2 + "?";
            } else {
                url = url2 + "&";
            }
            url2 = url + "width=" + width;
        }
        return url2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void loadPermissions() {
        this.permissions.clear();
        ((GsonRequest) ((GsonRequest) Request.ofGson(JsonArray.class).url(FlintUrls.GET_PERMISSIONS, new Object[0])).async()).execute(response -> {
            if (!response.isPresent()) {
                return;
            }
            JsonArray<JsonElement> array = (JsonArray) response.get();
            for (JsonElement jsonElement : array) {
                this.permissions.add((FlintPermission) GSON.fromJson(jsonElement, FlintPermission.class));
            }
        });
    }

    public FlintPermission getPermission(String key) {
        for (FlintPermission permission : this.permissions) {
            if (permission.getKey().equals(key)) {
                return permission;
            }
        }
        FlintPermission flintPermission = new FlintPermission(key);
        this.permissions.add(flintPermission);
        return flintPermission;
    }

    public void setup() {
        this.flintIndex.setupIndex();
    }

    public FlintIndex getFlintIndex() {
        return this.flintIndex;
    }

    public Collection<FlintTag> getTags() {
        return this.tags.values();
    }

    public Optional<FlintTag> getTag(int id) {
        return Optional.ofNullable(this.tags.isEmpty() ? null : this.tags.get(Integer.valueOf(id)));
    }

    public boolean isInstalled(FlintModification modification) {
        for (LoadedAddon loadedAddon : this.labyAPI.addonService().getLoadedAddons()) {
            String namespace = loadedAddon.info().getNamespace();
            if (namespace.equalsIgnoreCase(modification.getNamespace()) && !this.flintDownloader.isScheduledForRemoval(namespace)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInstalled(Path path) {
        for (LoadedAddon loadedAddon : this.labyAPI.addonService().getLoadedAddons()) {
            InstalledAddonInfo info = loadedAddon.info();
            String fileName = info.getFileName();
            if (fileName != null && fileName.equals(path.getFileName().toString()) && !this.flintDownloader.isScheduledForRemoval(info.getNamespace())) {
                return true;
            }
        }
        return false;
    }

    public Optional<FlintModification> getCachedModification(String namespace) {
        FlintModification modification = MODIFICATIONS.get(namespace);
        if (modification != null) {
            return Optional.of(modification);
        }
        return Optional.empty();
    }

    public Optional<Setting> getSettings(FlintModification modification) {
        return getSettings(modification.getNamespace());
    }

    public Optional<Setting> getSettings(String namespace) {
        for (KeyValue<Setting> element : this.labyAPI.coreSettingRegistry().getElements()) {
            Setting setting = element.getValue();
            if (setting.getId().equals(namespace)) {
                return Optional.of(setting);
            }
        }
        return Optional.empty();
    }

    public Pressable displaySettings(Setting setting) {
        return () -> {
            this.labyAPI.showSetting(setting);
        };
    }

    public Optional<FlintDownloadTask> getDownloadTask(FlintModification modification) {
        return this.flintDownloader.getDownloadTask(modification);
    }

    public FlintDownloadTask downloadModification(FlintModification modification, Consumer<FlintDownloadTask> finished) {
        return downloadModification(modification, null, finished);
    }

    public FlintDownloadTask downloadModification(FlintModification modification, List<String> skippedDependencies, Consumer<FlintDownloadTask> finished) {
        return this.flintDownloader.downloadModification(modification, skippedDependencies, finished);
    }

    public void uninstallModification(FlintModification modification, Runnable finished) throws IOException {
        String namespace = modification.getNamespace();
        if (this.flintIndex.filter().isHidden(modification)) {
            DefaultAddonService addonService = DefaultAddonService.getInstance();
            InstalledAddonInfo addonInfo = addonService.getAddonInfo(namespace);
            if (addonService.addonLoader().isAdditionalAddon(addonInfo)) {
                setModLoader(namespace, false, true, result -> {
                    try {
                        if (result == SetModLoaderResult.CONTINUE) {
                            try {
                                this.flintDownloader.scheduleForRemoval(namespace);
                                Minecraft minecraft = this.labyAPI.minecraft();
                                Objects.requireNonNull(finished);
                                minecraft.executeOnRenderThread(finished::run);
                            } catch (IOException e) {
                                e.printStackTrace();
                                Minecraft minecraft2 = this.labyAPI.minecraft();
                                Objects.requireNonNull(finished);
                                minecraft2.executeOnRenderThread(finished::run);
                            }
                        }
                    } catch (Throwable th) {
                        Minecraft minecraft3 = this.labyAPI.minecraft();
                        Objects.requireNonNull(finished);
                        minecraft3.executeOnRenderThread(finished::run);
                        throw th;
                    }
                });
                return;
            }
        }
        try {
            this.flintDownloader.scheduleForRemoval(namespace);
            Minecraft minecraft = this.labyAPI.minecraft();
            Objects.requireNonNull(finished);
            minecraft.executeOnRenderThread(finished::run);
        } catch (Throwable th) {
            Minecraft minecraft2 = this.labyAPI.minecraft();
            Objects.requireNonNull(finished);
            minecraft2.executeOnRenderThread(finished::run);
            throw th;
        }
    }

    public void setModLoader(String namespace, boolean installed, boolean restartPopup, Consumer<SetModLoaderResult> callback) {
        Component buttonText;
        Component message;
        String message2;
        if (!this.flintIndex.filter().isHidden(namespace)) {
            callback.accept(SetModLoaderResult.CONTINUE);
            return;
        }
        DefaultLauncherService launcherService = DefaultLauncherService.getInstance();
        Component modPackComponent = Component.text(launcherService.getModPackName() == null ? "Unknown" : launcherService.getModPackName());
        if (!launcherService.isConnectedToLauncher()) {
            if (!installed) {
                message2 = "labymod.addons.store.profile.modLoader.no-communicator." + "uninstall";
            } else {
                message2 = "labymod.addons.store.profile.modLoader.no-communicator." + "install";
            }
            SimpleAdvancedPopup.builder().title(Component.translatable("labymod.addons.store.profile.modLoader.title", new Component[0])).description(Component.translatable(message2, modPackComponent)).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.ui.button.close", new Component[0]), (Consumer<SimpleAdvancedPopup.SimplePopupButton>) button -> {
                callback.accept(SetModLoaderResult.CONTINUE_NO_SUCCESS);
            })).build().displayInOverlay();
            return;
        }
        Component addonNameComponent = Component.text(getName(namespace));
        if (!installed) {
            buttonText = Component.translatable("labymod.addons.store.profile.modLoader.communicator." + "uninstall-button", new Component[0]);
            message = Component.translatable("labymod.addons.store.profile.modLoader.communicator." + "uninstall", addonNameComponent, modPackComponent);
        } else {
            buttonText = Component.translatable("labymod.addons.store.profile.modLoader.communicator." + "install-button", new Component[0]);
            if (launcherService.isUsingModLoader()) {
                message = Component.translatable("labymod.addons.store.profile.modLoader.communicator." + "install-loader", modPackComponent, Component.text(getName(launcherService.getModLoader())), addonNameComponent);
            } else {
                message = Component.translatable("labymod.addons.store.profile.modLoader.communicator." + "install-no-loader", modPackComponent, addonNameComponent);
            }
        }
        SimpleAdvancedPopup.builder().title(Component.translatable("labymod.addons.store.profile.modLoader.title", new Component[0])).description(message).addButton(SimpleAdvancedPopup.SimplePopupButton.cancel(button2 -> {
            callback.accept(SetModLoaderResult.CANCEL);
        })).addButton(SimpleAdvancedPopup.SimplePopupButton.create(buttonText, (Consumer<SimpleAdvancedPopup.SimplePopupButton>) button3 -> {
            LauncherCommunicationClient communicator = launcherService.getCommunicator();
            communicator.sendPacket(new LauncherModLoaderChangePacket(installed ? namespace : null));
            callback.accept(SetModLoaderResult.SUCCESS);
            if (!restartPopup) {
                return;
            }
            SimpleAdvancedPopup.builder().title(Component.translatable("labymod.addons.store.profile.restart.alternative-title", new Component[0])).description(Component.translatable("labymod.addons.store.profile.restart.alternative-title", new Component[0])).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.addons.store.profile.restart.later", new Component[0]))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.addons.store.profile.restart.now", new Component[0]), (Consumer<SimpleAdvancedPopup.SimplePopupButton>) simplePopupButton -> {
                launcherService.restart();
            })).build().displayInOverlay();
        })).build().displayInOverlay();
    }

    private String getName(String namespace) {
        FlintModification modification = this.flintIndex.getModification(namespace);
        return modification == null ? namespace : modification.getName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FlintOrganization getOrganization(int id, ResultCallback<FlintOrganization> callback) {
        FlintOrganization organization = ORGANIZATIONS.get(Integer.valueOf(id));
        if (organization != null) {
            return organization;
        }
        if (callback != null && !addQuery("org", Integer.valueOf(id), callback, false)) {
            return null;
        }
        ((GsonRequest) ((GsonRequest) Request.ofGson(FlintOrganization.class).url(FlintUrls.GET_ORGANIZATION_URL, Integer.valueOf(id))).async()).execute(response -> {
            if (response.hasException()) {
                this.logging.error("Failed to load organization (id:" + id + ")", response.exception());
            } else if (response.isPresent()) {
                ORGANIZATIONS.put(Integer.valueOf(id), (FlintOrganization) response.get());
            }
            callQueries("org", Integer.valueOf(id), response);
        });
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void loadTags() {
        this.logging.debug("Loading Tags...", new Object[0]);
        this.tags.clear();
        ((GsonRequest) ((GsonRequest) Request.ofGson(JsonObject.class).url(FlintUrls.GET_TAGS_URL, new Object[0])).async(true)).execute(response -> {
            if (response.hasException()) {
                this.logging.error("Failed to load tags", response.exception());
                return;
            }
            for (Map.Entry<String, JsonElement> entry : ((JsonObject) response.get()).entrySet()) {
                FlintTag flintTag = (FlintTag) GSON.fromJson(entry.getValue(), FlintTag.class);
                this.tags.put(Integer.valueOf(flintTag.getId()), flintTag);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Optional<List<FlintModification.Review>> getOrLoadReviews(String namespace, ResultCallback<List<FlintModification.Review>> callback) {
        List<FlintModification.Review> cache = REVIEWS.get(namespace);
        if (cache != null) {
            return Optional.of(cache);
        }
        if (!addQuery("reviews", namespace, callback, true)) {
            return Optional.empty();
        }
        Result<List<FlintModification.Review>> result = Result.empty();
        ((GsonRequest) ((GsonRequest) Request.ofGson(JsonArray.class).url(FlintUrls.GET_MODIFICATION_RATINGS, namespace)).async()).execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                clearQueries("reviews", namespace);
                return;
            }
            ArrayList arrayListNewArrayList = Lists.newArrayList();
            for (JsonElement jsonElement : (JsonArray) response.get()) {
                arrayListNewArrayList.add((FlintModification.Review) GSON.fromJson(jsonElement, FlintModification.Review.class));
            }
            REVIEWS.put(namespace, arrayListNewArrayList);
            result.set(arrayListNewArrayList);
            callQueries("reviews", namespace, result);
        });
        return Optional.empty();
    }

    public void loadDescription(String namespace, ResultCallback<String> callback) {
        if (!addQuery("description", namespace, callback, true)) {
            return;
        }
        Result<String> result = Result.empty();
        Request.ofString().url(FlintUrls.GET_MODIFICATION_DESCRIPTION, namespace).async().execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                clearQueries("description", namespace);
            } else {
                String rawDescription = (String) response.get();
                String description = rawDescription.substring(1, rawDescription.length() - 1).replace("\\r\\n", "\n").replace("\\n", "\n").replace("\\/", "/");
                result.set(StringUtil.parseEscapedUnicode(description));
                callQueries("description", namespace, result);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Optional<List<FlintModification.Changelog>> getOrLoadChangelog(String namespace, ResultCallback<List<FlintModification.Changelog>> callback) {
        List<FlintModification.Changelog> cache = CHANGELOGS.get(namespace);
        if (cache != null) {
            return Optional.of(cache);
        }
        if (!addQuery("changelogs", namespace, callback, true)) {
            return Optional.empty();
        }
        Result<List<FlintModification.Changelog>> result = Result.empty();
        ((GsonRequest) ((GsonRequest) Request.ofGson(JsonArray.class).url(FlintUrls.GET_MODIFICATION_CHANGELOGS, namespace, FlintUrls.getCurrentReleaseChannel())).async()).execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                clearQueries("changelogs", namespace);
                return;
            }
            ArrayList arrayListNewArrayList = Lists.newArrayList();
            for (JsonElement jsonElement : (JsonArray) response.get()) {
                arrayListNewArrayList.add(0, (FlintModification.Changelog) GSON.fromJson(jsonElement, FlintModification.Changelog.class));
            }
            CHANGELOGS.put(namespace, arrayListNewArrayList);
            result.set(arrayListNewArrayList);
            callQueries("changelogs", namespace, result);
        });
        return Optional.empty();
    }

    public void getModification(String namespace, ResultCallback<FlintModification> callback) {
        FlintModification cached = loadModification(namespace, callback);
        if (cached != null && callback != null) {
            callback.acceptRaw(cached);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FlintModification loadModification(String namespace, ResultCallback<FlintModification> callback) {
        FlintModification cached = MODIFICATIONS.get(namespace);
        if (cached != null) {
            return cached;
        }
        if (callback != null && !addQuery("mod", namespace, callback, false)) {
            return null;
        }
        ((GsonRequest) ((GsonRequest) Request.ofGson(FlintModification.class, GSON).url(FlintUrls.GET_MODIFICATION, namespace, FlintUrls.getCurrentReleaseChannel())).async()).execute(response -> {
            if (response.isPresent()) {
                FlintModification value = (FlintModification) response.get();
                MODIFICATIONS.put(namespace, value);
                value.setCompatible(value.getVersionCompatibility().isCompatible(Laby.labyAPI().labyModLoader().version()));
            }
            callQueries("mod", namespace, response);
        });
        return null;
    }

    private boolean addQuery(String type, Object identifier, ResultCallback<?> callback, boolean single) {
        List<ResultCallback<?>> queries = CURRENT_QUERIES.get(type + ":" + String.valueOf(identifier));
        if (single) {
            CURRENT_QUERIES.put(type + ":" + String.valueOf(identifier), Lists.newArrayList(callback));
            return queries == null;
        }
        if (queries == null) {
            CURRENT_QUERIES.put(type + ":" + String.valueOf(identifier), Lists.newArrayList(callback));
            return true;
        }
        queries.add(callback);
        return false;
    }

    private <T> void callQueries(String type, Object identifier, Result<T> result) {
        List<ResultCallback<?>> queries = CURRENT_QUERIES.get(type + ":" + String.valueOf(identifier));
        if (queries == null || queries.isEmpty()) {
            return;
        }
        for (ResultCallback<?> query : queries) {
            query.accept((Result<?>) result);
        }
        CURRENT_QUERIES.remove(type + ":" + String.valueOf(identifier));
    }

    private void clearQueries(String type, Object identifier) {
        CURRENT_QUERIES.remove(type + ":" + String.valueOf(identifier));
    }
}
