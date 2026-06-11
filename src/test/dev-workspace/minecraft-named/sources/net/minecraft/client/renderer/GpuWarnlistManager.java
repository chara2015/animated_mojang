package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.Zone;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/GpuWarnlistManager.class */
public class GpuWarnlistManager extends SimplePreparableReloadListener<Preparations> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Identifier GPU_WARNLIST_LOCATION = Identifier.withDefaultNamespace("gpu_warnlist.json");
    private ImmutableMap<String, String> warnings = ImmutableMap.of();
    private boolean showWarning;
    private boolean warningDismissed;

    public boolean hasWarnings() {
        return !this.warnings.isEmpty();
    }

    public boolean willShowWarning() {
        return hasWarnings() && !this.warningDismissed;
    }

    public void showWarning() {
        this.showWarning = true;
    }

    public void dismissWarning() {
        this.warningDismissed = true;
    }

    public boolean isShowingWarning() {
        return this.showWarning && !this.warningDismissed;
    }

    public void resetWarnings() {
        this.showWarning = false;
        this.warningDismissed = false;
    }

    public String getRendererWarnings() {
        return (String) this.warnings.get("renderer");
    }

    public String getVersionWarnings() {
        return (String) this.warnings.get("version");
    }

    public String getVendorWarnings() {
        return (String) this.warnings.get("vendor");
    }

    public String getAllWarnings() {
        StringBuilder $$0 = new StringBuilder();
        this.warnings.forEach(($$1, $$2) -> {
            $$0.append($$1).append(": ").append($$2);
        });
        if ($$0.isEmpty()) {
            return null;
        }
        return $$0.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public Preparations prepare(ResourceManager $$0, ProfilerFiller $$1) {
        List<Pattern> $$2 = Lists.newArrayList();
        List<Pattern> $$3 = Lists.newArrayList();
        List<Pattern> $$4 = Lists.newArrayList();
        JsonObject $$5 = parseJson($$0, $$1);
        if ($$5 != null) {
            Zone $$6 = $$1.zone("compile_regex");
            try {
                compilePatterns($$5.getAsJsonArray("renderer"), $$2);
                compilePatterns($$5.getAsJsonArray("version"), $$3);
                compilePatterns($$5.getAsJsonArray("vendor"), $$4);
                if ($$6 != null) {
                    $$6.close();
                }
            } catch (Throwable th) {
                if ($$6 != null) {
                    try {
                        $$6.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        return new Preparations($$2, $$3, $$4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.packs.resources.SimplePreparableReloadListener
    public void apply(Preparations $$0, ResourceManager $$1, ProfilerFiller $$2) {
        this.warnings = $$0.apply();
    }

    private static void compilePatterns(JsonArray $$0, List<Pattern> $$1) {
        $$0.forEach($$12 -> {
            $$1.add(Pattern.compile($$12.getAsString(), 2));
        });
    }

    private static JsonObject parseJson(ResourceManager $$0, ProfilerFiller $$1) {
        try {
            Zone $$2 = $$1.zone("parse_json");
            try {
                Reader $$3 = $$0.openAsReader(GPU_WARNLIST_LOCATION);
                try {
                    JsonObject asJsonObject = StrictJsonParser.parse($$3).getAsJsonObject();
                    if ($$3 != null) {
                        $$3.close();
                    }
                    if ($$2 != null) {
                        $$2.close();
                    }
                    return asJsonObject;
                } catch (Throwable th) {
                    if ($$3 != null) {
                        try {
                            $$3.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException | JsonSyntaxException $$4) {
            LOGGER.warn("Failed to load GPU warnlist", $$4);
            return null;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/GpuWarnlistManager$Preparations.class */
    protected static final class Preparations {
        private final List<Pattern> rendererPatterns;
        private final List<Pattern> versionPatterns;
        private final List<Pattern> vendorPatterns;

        Preparations(List<Pattern> $$0, List<Pattern> $$1, List<Pattern> $$2) {
            this.rendererPatterns = $$0;
            this.versionPatterns = $$1;
            this.vendorPatterns = $$2;
        }

        private static String matchAny(List<Pattern> $$0, String $$1) {
            List<String> $$2 = Lists.newArrayList();
            for (Pattern $$3 : $$0) {
                Matcher $$4 = $$3.matcher($$1);
                while ($$4.find()) {
                    $$2.add($$4.group());
                }
            }
            return String.join(ComponentUtils.DEFAULT_SEPARATOR_TEXT, $$2);
        }

        ImmutableMap<String, String> apply() {
            ImmutableMap.Builder<String, String> $$0 = new ImmutableMap.Builder<>();
            GpuDevice $$1 = RenderSystem.getDevice();
            if ($$1.getBackendName().equals("OpenGL")) {
                String $$2 = matchAny(this.rendererPatterns, $$1.getRenderer());
                if (!$$2.isEmpty()) {
                    $$0.put("renderer", $$2);
                }
                String $$3 = matchAny(this.versionPatterns, $$1.getVersion());
                if (!$$3.isEmpty()) {
                    $$0.put("version", $$3);
                }
                String $$4 = matchAny(this.vendorPatterns, $$1.getVendor());
                if (!$$4.isEmpty()) {
                    $$0.put("vendor", $$4);
                }
            }
            return $$0.build();
        }
    }
}
