package net.labymod.core.main.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.models.OperatingSystem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/util/JavaLauncher.class */
public final class JavaLauncher {
    private static final String DEFAULT_JAVA_HOME = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

    public static void launch(List<String> arguments) {
        String property;
        String property2 = System.getProperty("sun.boot.library.path");
        if (property2 == null || property2.isEmpty()) {
            launch(DEFAULT_JAVA_HOME, arguments);
            return;
        }
        OperatingSystem platform = OperatingSystem.getPlatform();
        Path directory = Paths.get(property2, new String[0]);
        if (platform == OperatingSystem.MACOS) {
            property = directory.getParent().resolve("bin").resolve("java").toAbsolutePath().toString();
        } else {
            property = directory.resolve("java").toAbsolutePath().toString();
        }
        launch(property, arguments);
    }

    private static void launch(String java, List<String> arguments) {
        List<String> finalArguments = new ArrayList<>();
        finalArguments.add(java);
        finalArguments.addAll(arguments);
        ProcessBuilder processBuilder = new ProcessBuilder(finalArguments);
        try {
            processBuilder.start();
        } catch (Exception exception) {
            if (!java.equals(DEFAULT_JAVA_HOME)) {
                launch(DEFAULT_JAVA_HOME, arguments);
            } else {
                exception.printStackTrace();
            }
        }
    }
}
