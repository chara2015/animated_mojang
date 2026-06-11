package net.labymod.v1_12_2.client.util;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.util.Processor;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/util/VersionedSystemInfo.class */
@Singleton
@Implements(SystemInfo.class)
public final class VersionedSystemInfo implements SystemInfo {
    private final oshi.SystemInfo systemInfo = new oshi.SystemInfo();
    private final Processor processor;

    @Inject
    public VersionedSystemInfo() {
        String string;
        oshi.hardware.Processor[] processors = new oshi.hardware.Processor[0];
        try {
            processors = this.systemInfo.getHardware().getProcessors();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        if (processors.length == 0) {
            string = "None";
        } else {
            string = processors[0].toString();
        }
        this.processor = new Processor(string);
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
