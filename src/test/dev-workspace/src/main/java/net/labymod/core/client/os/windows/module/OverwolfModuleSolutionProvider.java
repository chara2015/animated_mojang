package net.labymod.core.client.os.windows.module;

import com.sun.jna.platform.win32.VerRsrc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.io.locator.PathResourceLocator;
import net.labymod.api.util.io.locator.ResourceLocator;
import net.labymod.core.client.os.module.ModuleSolutionProvider;
import net.labymod.core.client.os.module.ModuleVersion;
import net.labymod.core.client.os.windows.util.VersionUtil;
import net.labymod.core.client.os.windows.util.version.VersionInfo;
import net.labymod.util.property.SystemProperties;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/module/OverwolfModuleSolutionProvider.class */
public class OverwolfModuleSolutionProvider extends ModuleSolutionProvider {
    private static final String OVERWOLF_BROWSER_NAME = "OverwolfBrowser.exe";
    private static final String OVERWOLF_MARKER_FILE = "overwolf-warning/.marker";
    private static final ModuleVersion MIN_INCOMPATIBLE_VERSION = ModuleVersion.simple(0, 275, 0, 13);
    private static final boolean INCOMPATIBLE_VERSION = MinecraftVersions.V1_20_5.orNewer();
    private final ResourceLocator<Path> resourceLocator;

    public OverwolfModuleSolutionProvider() {
        super("OWExplorer.dll");
        this.resourceLocator = new PathResourceLocator(getClass().getClassLoader(), OVERWOLF_MARKER_FILE);
    }

    @Override // net.labymod.core.client.os.module.ModuleSolutionProvider
    public void execute() {
        if (!INCOMPATIBLE_VERSION || SystemProperties.ALLOW_OVERWOLF.get().booleanValue()) {
            return;
        }
        ModuleVersion overwolfVersion = getOverwolfVersion();
        if (isIncompatible(overwolfVersion)) {
            showIncompatiblePage();
        }
    }

    private void showIncompatiblePage() {
        try {
            Path targetFolder = Files.createTempDirectory("labymod-overwolf-warning", new FileAttribute[0]);
            Path indexHtml = getIndexHtml(targetFolder);
            if (indexHtml == null) {
                LOGGER.error("No index.html found", new Object[0]);
            } else {
                OperatingSystem.getPlatform().openUrl(indexHtml.toUri().toURL());
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to open Overwolf warning page", exception);
        }
    }

    private Path getIndexHtml(Path targetFolder) throws IOException {
        Path indexHtml = null;
        List<Path> resources = this.resourceLocator.locate();
        for (Path resource : resources) {
            if (!resource.endsWith(".marker")) {
                Path destination = targetFolder.resolve(resource.getFileName().toString());
                Files.copy(resource, destination, StandardCopyOption.REPLACE_EXISTING);
                if (resource.endsWith("index.html")) {
                    indexHtml = destination;
                }
            }
        }
        return indexHtml;
    }

    private boolean isIncompatible(@Nullable ModuleVersion version) {
        if (version == null) {
            return true;
        }
        return version.getMajor() >= MIN_INCOMPATIBLE_VERSION.getMajor() && version.getMinor() >= MIN_INCOMPATIBLE_VERSION.getMinor() && version.getRevision() >= MIN_INCOMPATIBLE_VERSION.getRevision() && version.getBuild() >= MIN_INCOMPATIBLE_VERSION.getBuild();
    }

    private ModuleVersion getOverwolfVersion() {
        String fileName = getFileName();
        Path modulePath = Path.of(fileName, new String[0]);
        Path moduleDirectory = modulePath.getParent();
        LOGGER.info("Searching directory: {}", moduleDirectory);
        Path executablePath = moduleDirectory.resolve(OVERWOLF_BROWSER_NAME);
        if (!Files.exists(executablePath, new LinkOption[0])) {
            LOGGER.warn("Overwolf executable not found: {}", executablePath);
            return null;
        }
        LOGGER.info("Parsing version of Overwolf executable: {}", executablePath);
        VersionInfo version = VersionUtil.getModuleVersionInfo(executablePath.toAbsolutePath());
        try {
            VerRsrc.VS_FIXEDFILEINFO fileInfo = version.queryFixedFileInfo();
            ModuleVersion moduleVersionFileVersion = WindowsModuleVersion.fileVersion(fileInfo);
            if (version != null) {
                version.close();
            }
            return moduleVersionFileVersion;
        } catch (Throwable th) {
            if (version != null) {
                try {
                    version.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
