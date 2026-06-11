package net.labymod.api.client.util;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/util/SystemInfo.class */
@Referenceable
public interface SystemInfo {
    Processor processor();

    long getTotalMemorySize();

    long getFreeMemorySize();

    double getBatteryLevel();

    double getCPUTemperature();
}
