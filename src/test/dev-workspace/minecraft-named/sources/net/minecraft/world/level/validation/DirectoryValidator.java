package net.minecraft.world.level.validation;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/validation/DirectoryValidator.class */
public class DirectoryValidator {
    private final PathMatcher symlinkTargetAllowList;

    public DirectoryValidator(PathMatcher $$0) {
        this.symlinkTargetAllowList = $$0;
    }

    public void validateSymlink(Path $$0, List<ForbiddenSymlinkInfo> $$1) throws IOException {
        Path $$2 = Files.readSymbolicLink($$0);
        if (!this.symlinkTargetAllowList.matches($$2)) {
            $$1.add(new ForbiddenSymlinkInfo($$0, $$2));
        }
    }

    public List<ForbiddenSymlinkInfo> validateSymlink(Path $$0) throws IOException {
        List<ForbiddenSymlinkInfo> $$1 = new ArrayList<>();
        validateSymlink($$0, $$1);
        return $$1;
    }

    public List<ForbiddenSymlinkInfo> validateDirectory(Path $$0, boolean $$1) throws IOException {
        List<ForbiddenSymlinkInfo> $$2 = new ArrayList<>();
        try {
            BasicFileAttributes $$5 = Files.readAttributes($$0, (Class<BasicFileAttributes>) BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            if ($$5.isRegularFile()) {
                throw new IOException("Path " + String.valueOf($$0) + " is not a directory");
            }
            if ($$5.isSymbolicLink()) {
                if ($$1) {
                    $$0 = Files.readSymbolicLink($$0);
                } else {
                    validateSymlink($$0, $$2);
                    return $$2;
                }
            }
            validateKnownDirectory($$0, $$2);
            return $$2;
        } catch (NoSuchFileException e) {
            return $$2;
        }
    }

    public void validateKnownDirectory(Path $$0, final List<ForbiddenSymlinkInfo> $$1) throws IOException {
        Files.walkFileTree($$0, new SimpleFileVisitor<Path>() { // from class: net.minecraft.world.level.validation.DirectoryValidator.1
            private void validateSymlink(Path $$02, BasicFileAttributes $$12) throws IOException {
                if ($$12.isSymbolicLink()) {
                    DirectoryValidator.this.validateSymlink($$02, $$1);
                }
            }

            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public FileVisitResult preVisitDirectory(Path $$02, BasicFileAttributes $$12) throws IOException {
                validateSymlink($$02, $$12);
                return super.preVisitDirectory($$02, $$12);
            }

            @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
            public FileVisitResult visitFile(Path $$02, BasicFileAttributes $$12) throws IOException {
                validateSymlink($$02, $$12);
                return super.visitFile($$02, $$12);
            }
        });
    }
}
