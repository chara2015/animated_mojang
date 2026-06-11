package net.labymod.core.mapping.loader.mcp;

import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/loader/mcp/McpConfig.class */
public enum McpConfig {
    V1_8_9("1.8.9", "1.8.9", "22-1.8.9", false, true),
    V1_12_2("1.12.2", "1.12.2-20201025.185735", "39-1.12", true, false);

    private static final String LEGACY_CONFIG_URL_TEMPLATE = "https://files.minecraftforge.net/maven/de/oceanlabs/mcp/mcp/%s/mcp-%s-srg.zip";
    private static final String CONFIG_URL_TEMPLATE = "https://files.minecraftforge.net/maven/de/oceanlabs/mcp/mcp_config/%s/mcp_config-%s.zip";
    private static final String MAPPING_URL_TEMPLATE = "https://files.minecraftforge.net/maven/de/oceanlabs/mcp/mcp_stable/%s/mcp_stable-%s.zip";
    private final String minecraftVersion;
    private final String configVersion;
    private final String mappingVersion;
    private final boolean forge;
    private final boolean legacy;
    private final String configUrl;
    private final String mappingUrl;

    McpConfig(String minecraftVersion, String configVersion, String mappingVersion, boolean forge, boolean legacy) {
        this.minecraftVersion = minecraftVersion;
        this.configVersion = configVersion;
        this.mappingVersion = mappingVersion;
        this.forge = forge;
        this.legacy = legacy;
        this.configUrl = String.format(Locale.ROOT, legacy ? LEGACY_CONFIG_URL_TEMPLATE : CONFIG_URL_TEMPLATE, configVersion, configVersion);
        this.mappingUrl = String.format(Locale.ROOT, MAPPING_URL_TEMPLATE, mappingVersion, mappingVersion);
    }

    public String getMinecraftVersion() {
        return this.minecraftVersion;
    }

    public String getConfigVersion() {
        return this.configVersion;
    }

    public String getMappingVersion() {
        return this.mappingVersion;
    }

    public boolean isForge() {
        return this.forge;
    }

    public boolean isLegacy() {
        return this.legacy;
    }

    public String getConfigUrl() {
        return this.configUrl;
    }

    public String getMappingUrl() {
        return this.mappingUrl;
    }
}
