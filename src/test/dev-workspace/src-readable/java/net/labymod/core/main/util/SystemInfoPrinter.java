package net.labymod.core.main.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.util.Processor;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/util/SystemInfoPrinter.class */
public class SystemInfoPrinter {
    public static void printSystemInfo(@NotNull Logging log, @NotNull String version) {
        try {
            try {
                OperatingSystem os = OperatingSystem.getPlatform();
                String osVersion = System.getProperty("os.version");
                String osArch = System.getProperty("os.arch");
                String javaVersion = System.getProperty("java.version");
                String javaVendor = System.getProperty("java.vendor");
                SystemInfo systemInfo = Laby.references().systemInfo();
                Processor processor = systemInfo.processor();
                long allocated = (Runtime.getRuntime().maxMemory() / 1024) / 1024;
                long sysFree = (systemInfo.getFreeMemorySize() / 1024) / 1024;
                long sysCurrentUsage = ((systemInfo.getTotalMemorySize() / 1024) / 1024) - sysFree;
                long totalMem = (systemInfo.getTotalMemorySize() / 1024) / 1024;
                String ramWarning = (sysFree < allocated || sysFree < 2048) ? "| Low RAM warning: The system may not have enough free RAM to run Minecraft properly!" : "";
                log.info("##########################  System Information  ##########################", new Object[0]);
                log.info("# Timestamp: {}", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                log.info("# Minecraft Version: {}", MinecraftVersions.current().version().toString());
                log.info("# {} Version: {}", Constants.Branding.NAME, version);
                log.info("# OS: {} ({}) version {} ({})", os, os.getName(), osVersion, osArch);
                if (os == OperatingSystem.WINDOWS) {
                    log.info("# Windows Version: {}", getDetailedWindowsVersion());
                }
                log.info("# Working Directory: {}", getWorkingDirectory());
                log.info("# Java: {} ({})", javaVersion, javaVendor);
                if (processor != null) {
                    log.info("# CPU: {}", processor.name());
                }
                log.info("# Memory: {}MB total, {}MB used (Allocated: {}MB) {}", Long.valueOf(totalMem), Long.valueOf(sysCurrentUsage), Long.valueOf(allocated), ramWarning);
                log.info("# GPU: {}", fetchGpuInfoSafe());
                log.info("###########################################################################", new Object[0]);
            } catch (Throwable t) {
                log.warn("Failed to log system information", t);
                log.info("###########################################################################", new Object[0]);
            }
        } catch (Throwable th) {
            log.info("###########################################################################", new Object[0]);
            throw th;
        }
    }

    @NotNull
    private static String getWorkingDirectory() {
        String userHome = System.getProperty("user.home");
        String workingDir = System.getProperty("user.dir");
        if (userHome == null) {
            return workingDir;
        }
        return workingDir.replace(userHome, "${user.home}");
    }

    @NotNull
    private static String fetchGpuInfoSafe() {
        String nvidiaSmiInfo = getNvidiaSmiInfo();
        if (nvidiaSmiInfo != null) {
            return nvidiaSmiInfo;
        }
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        try {
            if (os.contains("win")) {
                return getWindowsGpu();
            }
            if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                return getLinuxGpu();
            }
            if (os.contains("mac")) {
                return getMacGpu();
            }
            return "Unknown OS for GPU check";
        } catch (Exception e) {
            return "Unknown (Error fetching info)";
        }
    }

    @NotNull
    private static String getWindowsGpu() throws Exception {
        Process process = Runtime.getRuntime().exec("wmic path win32_VideoController get Name,DriverVersion");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        boolean headerSkipped = false;
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            String line2 = line.trim();
            if (!line2.isEmpty()) {
                if (!headerSkipped && (line2.toLowerCase().startsWith("driverversion") || line2.toLowerCase().startsWith("name"))) {
                    headerSkipped = true;
                } else {
                    if (!sb.isEmpty()) {
                        sb.append(" | ");
                    }
                    sb.append(line2.replaceAll("\\s{2,}", " "));
                }
            }
        }
        if (!sb.isEmpty()) {
            return "Windows Generic: " + String.valueOf(sb);
        }
        return "Unknown (Windows)";
    }

    @NotNull
    private static String getLinuxGpu() throws Exception {
        Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "lspci | grep -E 'VGA|3D'"});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        if (line != null) {
            int index = line.indexOf(": ");
            if (index != -1 && index + 2 < line.length()) {
                return line.substring(index + 2).trim();
            }
            return line;
        }
        return "Unknown (Linux)";
    }

    @NotNull
    private static String getMacGpu() throws Exception {
        String line;
        Process process = Runtime.getRuntime().exec("system_profiler SPDisplaysDataType");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        do {
            String line2 = reader.readLine();
            if (line2 != null) {
                line = line2.trim();
            } else {
                return "Unknown (Mac)";
            }
        } while (!line.startsWith("Chipset Model:"));
        return line.substring("Chipset Model:".length()).trim();
    }

    @Nullable
    private static String getNvidiaSmiInfo() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"nvidia-smi", "--query-gpu=name,driver_version", "--format=csv,noheader"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                return "NVIDIA: " + line.trim();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @NotNull
    public static String getDetailedWindowsVersion() {
        try {
            String productName = readRegistry("ProductName");
            String displayVersion = readRegistry("DisplayVersion");
            String currentBuild = readRegistry("CurrentBuild");
            String ubr = readRegistry("UBR");
            if (productName.isEmpty()) {
                return System.getProperty("os.name");
            }
            StringBuilder sb = new StringBuilder(productName);
            if (!displayVersion.isEmpty()) {
                sb.append(" ").append(displayVersion);
            }
            if (!currentBuild.isEmpty()) {
                sb.append(" (Build ").append(currentBuild);
                if (!ubr.isEmpty()) {
                    sb.append(".").append(ubr);
                }
                sb.append(")");
            }
            return sb.toString();
        } catch (Exception e) {
            return System.getProperty("os.name") + " (Legacy)";
        }
    }

    @NotNull
    private static String readRegistry(String key) {
        try {
            Process process = Runtime.getRuntime().exec("reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v " + key);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    if (line.contains(key)) {
                        String[] parts = line.split("REG_\\w+");
                        if (parts.length > 1) {
                            return parts[1].trim();
                        }
                    }
                } else {
                    return "";
                }
            }
        } catch (Exception e) {
            return "";
        }
    }
}
