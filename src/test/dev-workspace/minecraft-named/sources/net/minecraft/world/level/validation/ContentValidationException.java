package net.minecraft.world.level.validation;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.network.chat.ComponentUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/validation/ContentValidationException.class */
public class ContentValidationException extends Exception {
    private final Path directory;
    private final List<ForbiddenSymlinkInfo> entries;

    public ContentValidationException(Path $$0, List<ForbiddenSymlinkInfo> $$1) {
        this.directory = $$0;
        this.entries = $$1;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return getMessage(this.directory, this.entries);
    }

    public static String getMessage(Path $$0, List<ForbiddenSymlinkInfo> $$1) {
        return "Failed to validate '" + String.valueOf($$0) + "'. Found forbidden symlinks: " + ((String) $$1.stream().map($$02 -> {
            return String.valueOf($$02.link()) + "->" + String.valueOf($$02.target());
        }).collect(Collectors.joining(ComponentUtils.DEFAULT_SEPARATOR_TEXT)));
    }
}
