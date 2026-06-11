package net.labymod.api.loader;

import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/NamedVersion.class */
@Deprecated(forRemoval = true, since = "4.1.3")
public enum NamedVersion {
    V23w40a("23w40a", 764),
    V23w31a("23w31a", 764),
    V1_20_PRE3("1.20-pre3", 763),
    V1_19_4("1.19.4", 762),
    V1_19_3("1.19.3", 761),
    V1_19_2("1.19.2", 760),
    V1_19_1("1.19.1", 760),
    V1_19("1.19", 759),
    V1_18_2("1.18.2", 758),
    V1_18_1("1.18.1", 757),
    V1_18("1.18", 757),
    V1_17_1("1.17.1", 756),
    V1_17("1.17", 755),
    V1_16_5("1.16.5", 754),
    V1_16_4("1.16.4", 754),
    V1_16_3("1.16.3", 753),
    V1_16_2("1.16.2", 751),
    V1_16_1("1.16.1", 750),
    V1_16("1.16", 750),
    V1_15_2("1.15.2", 749),
    V1_15_1("1.15.1", 748),
    V1_15("1.15", 747),
    V1_14_4("1.14.4", 754),
    V1_14_3("1.14.3", 472),
    V1_14_2("1.14.2", 469),
    V1_14_1("1.14.1", 467),
    V1_14("1.14", 480),
    S19w13a("19w13a", 477),
    V1_13_2("1.13.2", 404),
    V1_13_1("1.13.1", 401),
    V1_13("1.13", 393),
    V1_12_2("1.12.2", 340),
    V1_12_1("1.12.1", 338),
    V1_12("1.12", 335),
    V1_11_2("1.11.2", 324),
    V1_11_1("1.11.1", 321),
    V1_11("1.11", 317),
    V1_10_2("1.10.2", 316),
    V1_10_1("1.10.1", 315),
    V1_10("1.10", 314),
    V1_9_4("1.9.4", 110),
    V1_9_2("1.9.2", 109),
    V1_9("1.9", 107),
    V1_8_9("1.8.9", 47);

    private final Version version;
    private final int protocol;
    private final boolean isCurrent;
    private final boolean older;
    private final boolean newer;
    public static final Set<NamedVersion> VALUES = CollectionHelper.asUnmodifiableSet(values());

    NamedVersion(String version, int protocol) {
        this.version = VersionDeserializer.from(version);
        this.protocol = protocol;
        int currentProtocol = Laby.labyAPI().minecraft().getProtocolVersion();
        this.isCurrent = currentProtocol == protocol;
        this.older = currentProtocol <= protocol;
        this.newer = currentProtocol >= protocol;
    }

    public Version getVersion() {
        return this.version;
    }

    public int getProtocol() {
        return this.protocol;
    }

    public boolean orNewer() {
        return this.newer;
    }

    public boolean orOlder() {
        return this.older;
    }

    public boolean isCurrent() {
        return this.isCurrent;
    }
}
