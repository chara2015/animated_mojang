package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dedicated/DedicatedServerSettings.class */
public class DedicatedServerSettings {
    private final Path source;
    private DedicatedServerProperties properties;

    public DedicatedServerSettings(Path $$0) {
        this.source = $$0;
        this.properties = DedicatedServerProperties.fromFile($$0);
    }

    public DedicatedServerProperties getProperties() {
        return this.properties;
    }

    public void forceSave() {
        this.properties.store(this.source);
    }

    public DedicatedServerSettings update(UnaryOperator<DedicatedServerProperties> $$0) {
        DedicatedServerProperties dedicatedServerProperties = (DedicatedServerProperties) $$0.apply(this.properties);
        this.properties = dedicatedServerProperties;
        dedicatedServerProperties.store(this.source);
        return this;
    }
}
