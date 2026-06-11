package net.labymod.core.mapping.loader.mojang;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import net.labymod.api.Constants;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.loader.MappingLoader;
import net.labymod.api.models.version.manifest.VersionManifest;
import net.labymod.api.models.version.manifest.index.VersionEntry;
import net.labymod.api.models.version.manifest.index.VersionIndex;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.FileRequest;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.result.Result;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/loader/mojang/MojangMappingLoader.class */
public class MojangMappingLoader implements MappingLoader {
    private static final String VERSION_INDEX_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    private final Path mappingsPath = Paths.get(String.format(Locale.ROOT, Constants.Files.MOJANG_MAPPINGS_PATH, MinecraftVersions.current().getRawVersion()), new String[0]);

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public String getSourceNamespace() {
        return MappingNamespace.NAMED;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public String getTargetNamespace() {
        return MappingNamespace.MINECRAFT_OBFUSCATED;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public MappingType type() {
        return MappingType.PROGUARD;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public InputStream load() throws Throwable {
        if (!Files.exists(this.mappingsPath, new LinkOption[0])) {
            Result resultExecuteSync = ((GsonRequest) Request.ofGson(VersionIndex.class).url(VERSION_INDEX_URL, new Object[0])).executeSync();
            if (resultExecuteSync.hasException()) {
                throw resultExecuteSync.exception();
            }
            Optional<VersionEntry> found = Optional.empty();
            VersionEntry[] versions = ((VersionIndex) resultExecuteSync.get()).getVersions();
            int length = versions.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                VersionEntry versionEntry = versions[i];
                if (!versionEntry.getId().equals(MinecraftVersions.current().getRawVersion())) {
                    i++;
                } else {
                    found = Optional.of(versionEntry);
                    break;
                }
            }
            VersionEntry currentEntry = found.orElseThrow(() -> {
                return new IllegalStateException("Could not find version entry for " + MinecraftVersions.current().getRawVersion());
            });
            Result resultExecuteSync2 = ((GsonRequest) Request.ofGson(VersionManifest.class).url(currentEntry.getUrl(), new Object[0])).executeSync();
            if (resultExecuteSync2.hasException()) {
                throw resultExecuteSync2.exception();
            }
            Response<Path> response = ((FileRequest) Request.ofFile(this.mappingsPath).url(((VersionManifest) resultExecuteSync2.get()).getDownloads().get(VersionManifest.MAPPINGS_DOWNLOAD_KEY).getUrl(), new Object[0])).executeSync();
            if (response.hasException()) {
                throw response.exception();
            }
        }
        return Files.newInputStream(this.mappingsPath, new OpenOption[0]);
    }
}
