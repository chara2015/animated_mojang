package net.labymod.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/BuildData.class */
public class BuildData {
    private static final String PRODUCTION_NAME = "production";
    private static BuildDataModel model;
    private static final String OPERATING_SYSTEM_NAME = System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version", "");
    private static String qualifiedVersion = null;

    private static BuildDataModel model() {
        if (model != null) {
            return model;
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(Version.class, new SchematicVersionTypeAdapterFactory()).create();
        ClassLoader classLoader = PlatformEnvironment.getPlatformClassloader().getPlatformClassloader();
        try {
            InputStream stream = classLoader.getResourceAsStream("build-data.json");
            try {
                InputStreamReader reader = new InputStreamReader((InputStream) Objects.requireNonNull(stream));
                try {
                    model = (BuildDataModel) gson.fromJson(reader, BuildDataModel.class);
                    BuildDataModel buildDataModel = model;
                    reader.close();
                    if (stream != null) {
                        stream.close();
                    }
                    return buildDataModel;
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read build-data.json");
        }
    }

    private static String qualifiedVersion() {
        BuildDataModel model2 = model();
        String releaseType = model2.getReleaseType();
        String branchName = model2.getBranchName();
        String commitReference = model2.getCommitReference();
        if (!branchName.equals(releaseType)) {
            qualifiedVersion = String.format(Locale.ROOT, "%s+%s %s/%s", model2.version().toString(), releaseType, branchName, commitReference);
        } else if (qualifiedVersion == null) {
            if (PRODUCTION_NAME.equals(releaseType)) {
                qualifiedVersion = model2.version().toString();
            } else {
                qualifiedVersion = String.format(Locale.ROOT, "%s+%s %s/%s", model2.version().toString(), Integer.valueOf(model2.getBuildNumber()), getReleaseType(releaseType), commitReference);
            }
        }
        return qualifiedVersion;
    }

    private static String getReleaseType(String releaseType) {
        if (Laby.labyAPI().labyModLoader().isAddonDevelopmentEnvironment()) {
            return "addon-environment";
        }
        return releaseType;
    }

    public static String commitReference() {
        return model().getCommitReference();
    }

    public static String branchName() {
        return model().getBranchName();
    }

    public static String releaseType() {
        return model().getReleaseType();
    }

    public static String getVersion() {
        return qualifiedVersion();
    }

    public static Version version() {
        return model().version();
    }

    public static String getUserAgent() {
        return "LabyMod " + getVersion() + " (" + OPERATING_SYSTEM_NAME + ")";
    }

    public static int getBuildNumber() {
        return model().getBuildNumber();
    }

    public static boolean hasRealms() {
        return MinecraftVersions.V23w41a.orNewer();
    }

    public static boolean isMarkedForRemoval(Version version) {
        BuildDataModel model2 = model();
        if (model2 == null) {
            return false;
        }
        for (Version scheduledForRemoval : model2.scheduledForRemovals()) {
            if (version.isCompatible(scheduledForRemoval)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/BuildData$SchematicVersionTypeAdapterFactory.class */
    private static class SchematicVersionTypeAdapterFactory extends TypeAdapter<Version> {
        private SchematicVersionTypeAdapterFactory() {
        }

        public void write(JsonWriter out, Version value) throws IOException {
            out.value(value.toString());
        }

        /* JADX INFO: renamed from: read, reason: merged with bridge method [inline-methods] */
        public Version m1read(JsonReader in) throws IOException {
            return VersionDeserializer.from(in.nextString());
        }
    }
}
