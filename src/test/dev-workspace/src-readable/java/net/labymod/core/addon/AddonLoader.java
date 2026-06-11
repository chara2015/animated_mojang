package net.labymod.core.addon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.labymod.api.Constants;
import net.labymod.api.addon.exception.AddonLoadException;
import net.labymod.api.models.addon.info.AddonEntrypoint;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.property.Property;
import net.labymod.api.service.Service;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.HashUtil;
import net.labymod.api.util.gson.AddonEntrypointTypeAdapter;
import net.labymod.api.util.io.zip.ZipException;
import net.labymod.api.util.io.zip.Zips;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import net.labymod.core.addon.loader.AddonLoaderSubService;
import net.labymod.core.addon.loader.AddonValidator;
import net.labymod.core.addon.loader.download.AdditionalAddonDownloader;
import net.labymod.core.addon.loader.download.AddonDependencyDownloader;
import net.labymod.core.addon.loader.download.DefaultAddonDownloader;
import net.labymod.core.addon.loader.initial.AdditionalAddonLoader;
import net.labymod.core.addon.loader.initial.ClasspathAddonLoader;
import net.labymod.core.addon.loader.initial.InstalledAddonLoader;
import net.labymod.core.addon.loader.prepare.AddonPreparer;
import net.labymod.core.addon.loader.update.AddonUpdater;
import net.labymod.core.addon.loader.verify.AddonCompatibilityVerifier;
import net.labymod.core.addon.loader.verify.AddonDependencyVerifier;
import net.labymod.core.addon.loader.verify.AddonSortingVerifier;
import net.labymod.core.addon.loader.verify.IncompatibleAddonVerifier;
import net.labymod.core.addon.loader.verify.UnusedDependencyVerifier;
import net.labymod.core.main.LibraryLoader;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/AddonLoader.class */
public class AddonLoader extends Service {
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(VersionCompatibility.class, new VersionCompatibilityDeserializer()).registerTypeAdapter(AddonEntrypoint.class, new AddonEntrypointTypeAdapter()).create();
    private static final Logging LOGGER = Logging.create((Class<?>) AddonLoader.class);
    private final DefaultAddonService addonService;
    private final AddonPreparer addonPreparer;
    private LibraryLoader libraryLoader;
    private final List<AddonLoaderSubService> subServices = new ArrayList();
    private final List<InstalledAddonInfo> addonsToBeLoaded = new ArrayList();
    private final ClasspathAddonLoader classpathAddonLoader = new ClasspathAddonLoader(this);
    private final IncompatibleAddonVerifier incompatibleAddonVerifier = new IncompatibleAddonVerifier(this);

    public AddonLoader(DefaultAddonService addonService) {
        this.addonService = addonService;
        this.addonPreparer = new AddonPreparer(this, this.addonService);
        initializeSubServices();
        this.subServices.sort(Comparator.comparingInt(a -> {
            return a.stage().ordinal();
        }));
    }

    public LibraryLoader libraryLoader() {
        if (this.libraryLoader == null) {
            this.libraryLoader = new LibraryLoader();
        }
        return this.libraryLoader;
    }

    private void initializeSubServices() {
        this.subServices.add(this.classpathAddonLoader);
        this.subServices.add(new InstalledAddonLoader(this));
        AdditionalAddonLoader additionalAddonLoader = new AdditionalAddonLoader(this);
        this.subServices.add(additionalAddonLoader);
        this.subServices.add(new AddonUpdater(this));
        this.subServices.add(new AdditionalAddonDownloader(this, additionalAddonLoader));
        this.subServices.add(new DefaultAddonDownloader(this));
        this.subServices.add(new AddonDependencyDownloader(this));
        this.subServices.add(new AddonSortingVerifier(this));
        this.subServices.add(new AddonCompatibilityVerifier(this));
        this.subServices.add(this.incompatibleAddonVerifier);
        this.subServices.add(new AddonDependencyVerifier(this));
        this.subServices.add(new UnusedDependencyVerifier(this, this.incompatibleAddonVerifier));
        this.subServices.add(this.addonPreparer);
    }

    @Override // net.labymod.api.service.Service
    protected void prepare() {
        forEachSubService(AddonLoaderSubService.SubServiceStage.INITIAL, this.addonsToBeLoaded);
        forEachSubService(AddonLoaderSubService.SubServiceStage.UPDATE, this.addonsToBeLoaded);
        forEachSubService(AddonLoaderSubService.SubServiceStage.DOWNLOAD, this.addonsToBeLoaded);
        forEachSubService(AddonLoaderSubService.SubServiceStage.VERIFY, this.addonsToBeLoaded);
        forEachSubService(AddonLoaderSubService.SubServiceStage.PREPARE, this.addonsToBeLoaded);
        for (AddonLoaderSubService subService : this.subServices) {
            try {
                subService.completed();
            } catch (Exception exception) {
                LOGGER.error("An exception occurred while completing sub addon loader " + subService.getClass().getSimpleName(), exception);
            }
        }
    }

    public AddonPreparer addonPreparer() {
        return this.addonPreparer;
    }

    public ClasspathAddonLoader classpathAddonLoader() {
        return this.classpathAddonLoader;
    }

    public boolean isAdditionalAddon(InstalledAddonInfo addonInfo) {
        String directory;
        return (addonInfo == null || (directory = addonInfo.getDirectory()) == null || directory.equals(Constants.Files.ADDONS.toString())) ? false : true;
    }

    @NotNull
    public InstalledAddonInfo loadAddonInfo(Path path) throws ZipException {
        Property<byte[]> addonInfoBytesProperty = new Property<>(null);
        Zips.read(path, (entry, bytes) -> {
            if (!entry.getName().equals("addon.json")) {
                return false;
            }
            addonInfoBytesProperty.set(bytes);
            return true;
        });
        byte[] addonInfoBytes = addonInfoBytesProperty.get();
        if (addonInfoBytes == null) {
            throw new AddonLoadException("No addon.json found in " + String.valueOf(path));
        }
        JsonObject object = (JsonObject) GSON.fromJson(new String(addonInfoBytes, StandardCharsets.UTF_8), JsonObject.class);
        if (object == null) {
            throw new AddonLoadException("Invalid addon.json: not a json object");
        }
        object.addProperty("fileName", path.getFileName().toString());
        object.addProperty("directory", path.getParent().toString());
        try {
            object.addProperty("fileHash", getSHA1Hash(path));
        } catch (Exception e) {
            LOGGER.warn("Could not calculate the hash of the addon " + String.valueOf(path), e);
        }
        InstalledAddonInfo addonInfo = (InstalledAddonInfo) GSON.fromJson(object, InstalledAddonInfo.class);
        AddonValidator.validateAddonInfo(addonInfo);
        return addonInfo;
    }

    public boolean isAddonInList(List<InstalledAddonInfo> list, String namespace) {
        return CollectionHelper.anyMatch(list, addon -> {
            return addon.getNamespace().equals(namespace);
        });
    }

    public String getSHA1Hash(Path path) throws IOException {
        return HashUtil.sha1Hex(Files.readAllBytes(path));
    }

    private void forEachSubService(AddonLoaderSubService.SubServiceStage stage, List<InstalledAddonInfo> addons) {
        for (AddonLoaderSubService subService : this.subServices) {
            if (subService.stage() == stage) {
                try {
                    LOGGER.info("Executing sub addon loader " + subService.getClass().getSimpleName() + " with stage " + String.valueOf(stage), new Object[0]);
                    subService.setAddons(addons);
                    subService.handle();
                } catch (Exception exception) {
                    LOGGER.error("An exception occurred while executing sub addon loader " + subService.getClass().getSimpleName(), exception);
                }
            }
        }
    }
}
