package net.labymod.api.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/OperatingSystem.class */
public enum OperatingSystem {
    LINUX("linux", "lib", "so", new String[]{"linux", "unix"}),
    SOLARIS("solaris", "", "so", new String[]{"solaris", "sunos"}),
    WINDOWS("windows", "", "dll", new String[]{"win"}) { // from class: net.labymod.api.models.OperatingSystem.1
        @Override // net.labymod.api.models.OperatingSystem
        protected String[] getOpenUrlArguments(URL url) {
            return new String[]{"rundll32", "url.dll,FileProtocolHandler", url.toString()};
        }
    },
    MACOS("macos", "lib", "dylib", new String[]{"mac"}) { // from class: net.labymod.api.models.OperatingSystem.2
        @Override // net.labymod.api.models.OperatingSystem
        protected String[] getOpenUrlArguments(URL url) {
            return new String[]{"open", url.toString()};
        }
    },
    UNKNOWN(null, null, null, new String[0]);

    private static final OperatingSystem PLATFORM = getPlatform0();
    private final String name;
    private final String libraryPrefix;
    private final String libraryExtensionName;
    private final String[] ids;
    private boolean is64Bit = checkIs64Bit();

    OperatingSystem(String name, String libraryPrefix, String libraryExtensionName, String[] ids) {
        this.name = name;
        this.libraryPrefix = libraryPrefix;
        this.libraryExtensionName = libraryExtensionName;
        this.ids = ids;
    }

    public String[] getIds() {
        return this.ids;
    }

    public static boolean isOSX() {
        return PLATFORM == MACOS;
    }

    public static OperatingSystem getPlatform() {
        return PLATFORM;
    }

    private static OperatingSystem getPlatform0() {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        for (OperatingSystem os : values()) {
            if (os != UNKNOWN) {
                for (String name : os.getIds()) {
                    if (osName.contains(name)) {
                        return os;
                    }
                }
            }
        }
        return UNKNOWN;
    }

    public static boolean is64Bit() {
        return getPlatform().is64BitSystem();
    }

    public boolean is64BitSystem() {
        return this.is64Bit;
    }

    public String getLibraryFileName(String name) {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[4];
        objArr[0] = this.libraryPrefix;
        objArr[1] = name;
        objArr[2] = is64Bit() ? "64" : "32";
        objArr[3] = this.libraryExtensionName;
        return String.format(locale, "%s%s-%s.%s", objArr);
    }

    public String getLibraryJarName(String name, String version) {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[4];
        objArr[0] = name;
        objArr[1] = version;
        objArr[2] = this.name;
        objArr[3] = is64Bit() ? "64" : "32";
        return String.format(locale, "%s-%s-natives-%s-x%s.jar", objArr);
    }

    public boolean isLibrary(String name) {
        return name.endsWith(this.libraryExtensionName);
    }

    public String getName() {
        return this.name;
    }

    public String getArch() {
        return System.getProperty("os.arch");
    }

    public String getLibraryPrefix() {
        return this.libraryPrefix;
    }

    public String getLibraryExtensionName() {
        return this.libraryExtensionName;
    }

    public boolean launchUrlProcess(URL url) {
        try {
            Process process = (Process) AccessController.doPrivileged(() -> {
                return Runtime.getRuntime().exec(getOpenUrlArguments(url));
            });
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line != null) {
                        System.err.println(line);
                    } else {
                        reader.close();
                        process.getInputStream().close();
                        process.getErrorStream().close();
                        process.getOutputStream().close();
                        return true;
                    }
                } finally {
                }
            }
        } catch (IOException | PrivilegedActionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openUrl(URL url) {
        launchUrlProcess(url);
    }

    public void openUrl(String url) {
        try {
            openUrl(new URL(url));
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
    }

    public void openFile(File file) {
        try {
            openUrl(file.toURI().toURL());
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
    }

    public void openUri(URI uri) {
        try {
            openUrl(uri.toURL());
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
    }

    public void openUri(String uri) {
        try {
            openUrl(new URI(uri).toURL());
        } catch (IllegalArgumentException | MalformedURLException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }

    protected String[] getOpenUrlArguments(URL param0) {
        String var0 = param0.toString();
        if ("file".equals(param0.getProtocol())) {
            var0 = var0.replace("file:", "file://");
        }
        return new String[]{"xdg-open", var0};
    }

    private boolean checkIs64Bit() {
        String[] properties = {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        for (String property : properties) {
            String propertyValue = System.getProperty(property);
            if (propertyValue != null && propertyValue.contains("64")) {
                return true;
            }
        }
        return false;
    }
}
