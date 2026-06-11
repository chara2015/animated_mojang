package net.labymod.v1_21_11.client.util;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.util.Processor;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.models.Implements;
import oshi.hardware.CentralProcessor;
import oshi.hardware.PowerSource;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/VersionedSystemInfo.class */
@Singleton
@Implements(SystemInfo.class)
public final class VersionedSystemInfo implements SystemInfo {
    private final oshi.SystemInfo systemInfo = new oshi.SystemInfo();
    private final Processor processor;

    @Inject
    public VersionedSystemInfo() {
        String name;
        CentralProcessor centralProcessor = this.systemInfo.getHardware().getProcessor();
        if (centralProcessor == null) {
            name = "None";
        } else {
            name = centralProcessor.getProcessorIdentifier().getName();
        }
        this.processor = new Processor(name);
    }

    public Processor processor() {
        return this.processor;
    }

    public long getTotalMemorySize() {
        return this.systemInfo.getHardware().getMemory().getTotal();
    }

    public long getFreeMemorySize() {
        return this.systemInfo.getHardware().getMemory().getAvailable();
    }

    public double getBatteryLevel() {
        List<PowerSource> powerSources = this.systemInfo.getHardware().getPowerSources();
        if (powerSources.isEmpty()) {
            return -1.0d;
        }
        return powerSources.get(0).getRemainingCapacityPercent() * 100.0d;
    }

    public double getCPUTemperature() {
        return this.systemInfo.getHardware().getSensors().getCpuTemperature();
    }
}
