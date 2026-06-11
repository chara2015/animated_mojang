package net.labymod.core.flint.downloader;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.api.util.io.web.request.FormData;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.AbstractFileRequest;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.AddonLoader;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.index.FlintIndexLoader;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.main.LabyMod;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherAddonInstalledPacket;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/downloader/AddonDownloadRequest.class */
public class AddonDownloadRequest extends AbstractFileRequest<AddonDownloadResult, AddonDownloadRequest> {
    public static final List<String> UNSUPPORTED_ADDONS;
    private static final Logging LOGGER = Logging.getLogger();
    private static final FlintIndexLoader INDEX_LOADER = FlintIndexLoader.getInstance();
    private static final DefaultWebResolver WEB_RESOLVER = DefaultWebResolver.instance();
    private static final Supplier<Object> SESSION_SUPPLIER = () -> {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null || minecraft.sessionAccessor() == null) {
            return null;
        }
        return minecraft.sessionAccessor().getSession();
    };
    private static final BiPredicate<String, InstalledAddonInfo> DEFAULT_EXISTS_CALLBACK = (existingNamespace, info) -> {
        if (info.getNamespace().equals(existingNamespace)) {
            return false;
        }
        throw new IllegalStateException("Addon " + info.getNamespace() + " is on the same path as " + existingNamespace + " (" + info.getFileName() + ")");
    };
    private BiPredicate<String, InstalledAddonInfo> existsCallback;
    private String namespace;
    private String hash;
    private Path finalPath;
    private boolean downloadDependencies;
    private boolean downloadOptionalDependencies;
    private List<String> skippedDependencies;
    private boolean loadUniqueIdFromLoader;
    private BiPredicate<String, InstalledAddonInfo> dependencyExistsCallback;
    private boolean ignoreUnsupported;
    private boolean throwNotInIndexException;
    private boolean skippedDownload;
    private InstalledAddonInfo currentAddonInfo;

    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    protected /* bridge */ /* synthetic */ Object handle(Response response, WebInputStream webInputStream) throws Exception {
        return handle((Response<AddonDownloadResult>) response, webInputStream);
    }

    static {
        String unsupportedAddons = System.getProperty("net.labymod.unsupported-addons");
        if (unsupportedAddons == null) {
            UNSUPPORTED_ADDONS = List.of();
        } else {
            UNSUPPORTED_ADDONS = List.of((Object[]) unsupportedAddons.split(","));
        }
    }

    private AddonDownloadRequest() {
        super(null, null);
        this.existsCallback = DEFAULT_EXISTS_CALLBACK;
        this.skippedDependencies = List.of();
        this.skippedDownload = false;
    }

    public static AddonDownloadRequest create() {
        return new AddonDownloadRequest();
    }

    public AddonDownloadRequest namespace(String namespace) throws IllegalArgumentException {
        setNamespace(namespace);
        JsonObject modificationObject = INDEX_LOADER.getModificationObject(namespace);
        if (modificationObject == null) {
            throw new IllegalArgumentException("No modification with namespace " + namespace + " found!");
        }
        return pathIfUnset(namespace + ".jar");
    }

    public AddonDownloadRequest throwNotInIndexException() {
        this.throwNotInIndexException = true;
        return this;
    }

    public AddonDownloadRequest namespace(InstalledAddonInfo addonInfo) {
        return namespace(addonInfo.getNamespace());
    }

    public AddonDownloadRequest namespace(FlintModification modification) {
        setNamespace(modification.getNamespace());
        return pathIfUnset(this.namespace + ".jar");
    }

    private AddonDownloadRequest pathIfUnset(String fileName) {
        if (this.path == null) {
            path(fileName);
        }
        return this;
    }

    public AddonDownloadRequest path(Path directory, String fileName) {
        this.finalPath = directory.resolve(fileName);
        this.path = directory.resolve(fileName + ".download");
        return this;
    }

    public AddonDownloadRequest path(String fileName) {
        return path(Constants.Files.ADDONS, fileName);
    }

    public AddonDownloadRequest hash(String hash) {
        if (hash != null && hash.isEmpty()) {
            hash = null;
        }
        this.hash = hash;
        if (hash != null) {
            url(FlintUrls.FETCH_JAR_BY_HASH_URL, hash);
        }
        return this;
    }

    public AddonDownloadRequest percentageConsumer(Consumer<Double> percentageConsumer) {
        this.percentageConsumer = percentageConsumer;
        return this;
    }

    public AddonDownloadRequest existsStrategy(BiPredicate<String, InstalledAddonInfo> existsCallback) {
        this.existsCallback = existsCallback;
        return this;
    }

    public AddonDownloadRequest downloadDependencies(boolean downloadDependencies, BiPredicate<String, InstalledAddonInfo> dependencyExistsCallback) {
        this.downloadDependencies = downloadDependencies;
        if (dependencyExistsCallback == null) {
            this.dependencyExistsCallback = DEFAULT_EXISTS_CALLBACK;
        } else {
            this.dependencyExistsCallback = dependencyExistsCallback;
        }
        return this;
    }

    public AddonDownloadRequest downloadDependencies(boolean downloadDependencies) {
        return downloadDependencies(downloadDependencies, null);
    }

    public AddonDownloadRequest downloadOptionalDependencies(boolean downloadOptionalDependencies) {
        if (!this.downloadDependencies && downloadOptionalDependencies) {
            this.downloadDependencies = true;
        }
        this.downloadOptionalDependencies = downloadOptionalDependencies;
        return this;
    }

    public AddonDownloadRequest skipDependencies(List<String> skippedDependencies) {
        this.skippedDependencies = List.copyOf(skippedDependencies);
        return this;
    }

    public AddonDownloadRequest skipDependencies(String... skippedDependencies) {
        return skipDependencies(List.of((Object[]) skippedDependencies));
    }

    public AddonDownloadRequest loadUniqueIdFromLoader() {
        this.loadUniqueIdFromLoader = true;
        return this;
    }

    public AddonDownloadRequest ignoreUnsupported() {
        return ignoreUnsupported(true);
    }

    public AddonDownloadRequest ignoreUnsupported(boolean ignoreUnsupported) {
        this.ignoreUnsupported = ignoreUnsupported;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    public AddonDownloadRequest prepareCopy() {
        AddonDownloadRequest request = new AddonDownloadRequest();
        request.namespace = this.namespace;
        request.hash = this.hash;
        request.finalPath = this.finalPath;
        request.existsCallback = this.existsCallback;
        request.downloadDependencies = this.downloadDependencies;
        request.dependencyExistsCallback = this.dependencyExistsCallback;
        request.loadUniqueIdFromLoader = this.loadUniqueIdFromLoader;
        request.ignoreUnsupported = this.ignoreUnsupported;
        return applyRequestDataTo(request);
    }

    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    protected AddonDownloadResult handle(Response<AddonDownloadResult> result, WebInputStream inputStream) throws Exception {
        Path downloadPath;
        if (this.namespace == null) {
            throw new IllegalStateException("Namespace cannot be null");
        }
        boolean unsupported = !this.ignoreUnsupported && UNSUPPORTED_ADDONS.contains(this.namespace);
        if (unsupported) {
            downloadPath = null;
        } else {
            downloadPath = download(result, inputStream);
        }
        AddonDownloadResult addonDownloadResult = new AddonDownloadResult(this, this.skippedDownload);
        if (!unsupported && !this.skippedDownload) {
            if (this.finalPath != null) {
                Files.move(downloadPath, this.finalPath, StandardCopyOption.REPLACE_EXISTING);
            }
            loadAddonInfo(this.finalPath == null ? this.path : this.finalPath);
            addonDownloadResult.addAddonInfo(this.currentAddonInfo);
            Path path = this.finalPath == null ? this.path : this.finalPath;
            if (path.getParent().equals(Constants.Files.ADDONS)) {
                LabyMod.getInstance().sendLauncherPacket(new LauncherAddonInstalledPacket(this.currentAddonInfo.getNamespace(), path.getFileName().toString()));
            }
            sendDownloadCount(this.namespace);
        } else {
            addonDownloadResult.addAddonInfo(this.currentAddonInfo);
        }
        if (!this.downloadDependencies) {
            return addonDownloadResult;
        }
        if (this.currentAddonInfo != null) {
            DefaultAddonService addonService = DefaultAddonService.getInstance();
            for (AddonDependency addonDependency : this.currentAddonInfo.getCompatibleAddonDependencies(Laby.labyAPI().labyModLoader().version())) {
                String namespace = addonDependency.getNamespace();
                if ((!addonDependency.isOptional() || this.downloadOptionalDependencies) && !this.skippedDependencies.contains(namespace) && !addonService.getAddon(namespace).isPresent()) {
                    String hash = null;
                    FlintIndex flintIndex = LabyMod.references().flintController().getFlintIndex();
                    FlintModification modification = flintIndex.getModification(namespace);
                    if (modification != null) {
                        hash = modification.getFileHash();
                    }
                    try {
                        Response<AddonDownloadResult> response = new AddonDownloadRequest().hash(hash).namespace(namespace).existsStrategy(this.dependencyExistsCallback).downloadDependencies(true, this.dependencyExistsCallback).downloadOptionalDependencies(this.downloadOptionalDependencies).skipDependencies(this.skippedDependencies).ignoreUnsupported(this.ignoreUnsupported).executeSync();
                        if (response.hasException()) {
                            throw new IOException(response.exception());
                        }
                        Objects.requireNonNull(addonDownloadResult);
                        response.ifPresent(addonDownloadResult::addonDownloadResult);
                    } catch (Exception e) {
                        if (this.throwNotInIndexException) {
                            throw e;
                        }
                    }
                }
            }
        }
        return addonDownloadResult;
    }

    private InstalledAddonInfo loadAddonInfo(Path path) {
        AddonLoader addonLoader = DefaultAddonService.getInstance().addonLoader();
        try {
            this.currentAddonInfo = addonLoader.loadAddonInfo(path);
        } catch (Exception exception) {
            LOGGER.error("Could not load addon info from {}", path, exception);
            this.currentAddonInfo = null;
        }
        return this.currentAddonInfo;
    }

    @Override // net.labymod.api.util.io.web.request.types.AbstractFileRequest
    protected boolean continueDownload() throws IOException {
        this.skippedDownload = false;
        this.currentAddonInfo = null;
        Path path = this.finalPath == null ? this.path : this.finalPath;
        if (!IOUtil.exists(path)) {
            return true;
        }
        loadAddonInfo(path);
        if (this.currentAddonInfo == null) {
            return true;
        }
        if (this.existsCallback == null) {
            throw new FileAlreadyExistsException("File already exists but no strategy callback is set!");
        }
        boolean continueDownload = this.existsCallback.test(this.namespace, this.currentAddonInfo);
        this.skippedDownload = !continueDownload;
        return continueDownload;
    }

    private void sendDownloadCount(String namespace) {
        UUID uniqueId = getUniqueId();
        if (uniqueId == null) {
            return;
        }
        StringRequest request = Request.ofString().method(Request.Method.POST).url(FlintUrls.ADD_DOWNLOAD_COUNT, namespace).form(FormData.builder().name("uuid").value(uniqueId.toString()).build()).async();
        WEB_RESOLVER.resolveConnection(request, response -> {
        });
    }

    private UUID getUniqueId() {
        if (!this.loadUniqueIdFromLoader) {
            Object sessionObject = SESSION_SUPPLIER.get();
            if (!(sessionObject instanceof Session)) {
                return null;
            }
            Session session = (Session) sessionObject;
            if (!session.isPremium() || !session.hasUniqueId()) {
                return null;
            }
            return session.getUniqueId();
        }
        DefaultLabyModLoader instance = (DefaultLabyModLoader) DefaultLabyModLoader.getInstance();
        if (instance.getRawUniqueId() == null) {
            return null;
        }
        String uniqueId = instance.getRawUniqueId();
        if (uniqueId.contains("-")) {
            return UUID.fromString(uniqueId);
        }
        return UUIDTypeAdapter.fromString(uniqueId);
    }

    private void setNamespace(String namespace) {
        this.namespace = namespace;
        if (this.hash == null) {
            FlintIndex flintIndex = LabyMod.references().flintController().getFlintIndex();
            FlintModification modification = flintIndex.getModification(namespace);
            if (modification != null) {
                hash(modification.getFileHash());
            }
        }
        if (this.url == null) {
            url(FlintUrls.FETCH_JAR_URL, namespace, DefaultLabyModLoader.getInstance().version().toString());
        }
    }

    @Override // net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request
    public Response<AddonDownloadResult> executeSync() {
        return WEB_RESOLVER.resolveConnection(this);
    }

    @Override // net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request
    public void execute(Callback<AddonDownloadResult> callback) {
        WEB_RESOLVER.resolveConnection(this, callback);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/downloader/AddonDownloadRequest$AddonDownloadResult.class */
    public class AddonDownloadResult {
        private final List<InstalledAddonInfo> addonInfos = new ArrayList();
        private final boolean skippedMainAddon;

        private AddonDownloadResult(AddonDownloadRequest this$0, boolean skipped) {
            this.skippedMainAddon = skipped;
        }

        private AddonDownloadResult addAddonInfo(InstalledAddonInfo addonInfo) {
            this.addonInfos.add(addonInfo);
            return this;
        }

        private AddonDownloadResult addonDownloadResult(AddonDownloadResult result) {
            for (InstalledAddonInfo addonInfo : result.addonInfos) {
                if (addonInfo != null) {
                    boolean found = false;
                    Iterator<InstalledAddonInfo> it = this.addonInfos.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        InstalledAddonInfo info = it.next();
                        if (info.getNamespace().equals(addonInfo.getNamespace())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        this.addonInfos.add(0, addonInfo);
                    }
                }
            }
            return this;
        }

        public List<InstalledAddonInfo> getAddonInfos() {
            return Collections.unmodifiableList(this.addonInfos);
        }

        public boolean hasSkippedMainAddon() {
            return this.skippedMainAddon;
        }
    }
}
