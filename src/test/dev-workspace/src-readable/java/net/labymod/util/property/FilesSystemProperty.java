package net.labymod.util.property;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/util/property/FilesSystemProperty.class */
public class FilesSystemProperty extends SystemProperty<List<Path>> {
    public FilesSystemProperty(String key) {
        super(key);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.util.property.SystemProperty
    public List<Path> get() {
        String property = System.getProperty(getKey());
        if (property == null || property.isEmpty()) {
            return Collections.emptyList();
        }
        String[] entries = property.split(File.pathSeparator);
        List<Path> files = new ArrayList<>(entries.length);
        for (String entry : entries) {
            Path file = Path.of(entry, new String[0]);
            if (Files.exists(file, new LinkOption[0])) {
                files.add(file);
            }
        }
        return files;
    }
}
