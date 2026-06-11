package net.labymod.v1_16_5.client.util;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.util.Processor;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/util/VersionedSystemInfo.class */
@Singleton
@Implements(SystemInfo.class)
public final class VersionedSystemInfo implements SystemInfo {
    private final oshi.SystemInfo systemInfo = new oshi.SystemInfo();
    private final Processor processor;

    @Inject
    public VersionedSystemInfo() {
        String name;
        oshi.hardware.Processor[] processors = this.systemInfo.getHardware().getProcessors();
        if (processors == null) {
            name = "None";
        } else {
            name = processors[0].getName();
        }
        this.processor = new Processor(name);
    }

    @Override // net.labymod.api.client.util.SystemInfo
    public Processor processor() {
        return this.processor;
    }

    @Override // net.labymod.api.client.util.SystemInfo
    public long getTotalMemorySize() {
        return this.systemInfo.getHardware().getMemory().getTotal();
    }

    @Override // net.labymod.api.client.util.SystemInfo
    public long getFreeMemorySize() {
        return this.systemInfo.getHardware().getMemory().getAvailable();
    }

    @Override // net.labymod.api.client.util.SystemInfo
    public double getBatteryLevel() {
        return -1.0d;
    }

    @Override // net.labymod.api.client.util.SystemInfo
    public double getCPUTemperature() {
        return -1.0d;
    }
}
