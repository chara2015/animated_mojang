package net.minecraft.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import org.apache.commons.io.FilenameUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/FileUtil.class */
public class FileUtil {
    private static final int MAX_FILE_NAME = 255;
    private static final Pattern COPY_COUNTER_PATTERN = Pattern.compile("(<name>.*) \\((<count>\\d*)\\)", 66);
    private static final Pattern RESERVED_WINDOWS_FILENAMES = Pattern.compile(".*\\.|(?:COM|CLOCK\\$|CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\..*)?", 2);
    private static final Pattern STRICT_PATH_SEGMENT_CHECK = Pattern.compile("[-._a-z0-9]+");

    public static String sanitizeName(String $$0) {
        for (char $$1 : SharedConstants.ILLEGAL_FILE_CHARACTERS) {
            $$0 = $$0.replace($$1, '_');
        }
        return $$0.replaceAll("[./\"]", "_");
    }

    public static String findAvailableName(Path $$0, String $$1, String $$2) throws IOException {
        String $$12 = sanitizeName($$1);
        if (!isPathPartPortable($$12)) {
            $$12 = "_" + $$12 + "_";
        }
        Matcher $$3 = COPY_COUNTER_PATTERN.matcher($$12);
        int $$4 = 0;
        if ($$3.matches()) {
            $$12 = $$3.group(JigsawBlockEntity.NAME);
            $$4 = Integer.parseInt($$3.group("count"));
        }
        if ($$12.length() > 255 - $$2.length()) {
            $$12 = $$12.substring(0, 255 - $$2.length());
        }
        while (true) {
            String $$5 = $$12;
            if ($$4 != 0) {
                String $$6 = " (" + $$4 + ")";
                int $$7 = 255 - $$6.length();
                if ($$5.length() > $$7) {
                    $$5 = $$5.substring(0, $$7);
                }
                $$5 = $$5 + $$6;
            }
            Path $$8 = $$0.resolve($$5 + $$2);
            try {
                Path $$9 = Files.createDirectory($$8, new FileAttribute[0]);
                Files.deleteIfExists($$9);
                return $$0.relativize($$9).toString();
            } catch (FileAlreadyExistsException e) {
                $$4++;
            }
        }
    }

    public static boolean isPathNormalized(Path $$0) {
        Path $$1 = $$0.normalize();
        return $$1.equals($$0);
    }

    public static boolean isPathPortable(Path $$0) {
        for (Path $$1 : $$0) {
            if (!isPathPartPortable($$1.toString())) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPathPartPortable(String $$0) {
        return !RESERVED_WINDOWS_FILENAMES.matcher($$0).matches();
    }

    public static Path createPathToResource(Path $$0, String $$1, String $$2) {
        String $$3 = $$1 + $$2;
        Path $$4 = Paths.get($$3, new String[0]);
        if ($$4.endsWith($$2)) {
            throw new InvalidPathException($$3, "empty resource name");
        }
        return $$0.resolve($$4);
    }

    public static String getFullResourcePath(String $$0) {
        return FilenameUtils.getFullPath($$0).replace(File.separator, "/");
    }

    public static String normalizeResourcePath(String $$0) {
        return FilenameUtils.normalize($$0).replace(File.separator, "/");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:282)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:65)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:102)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public static com.mojang.serialization.DataResult<java.util.List<java.lang.String>> decomposePath(java.lang.String r4) {
        /*
            Method dump skipped, instruction units count: 383
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.util.FileUtil.decomposePath(java.lang.String):com.mojang.serialization.DataResult");
    }

    public static Path resolvePath(Path $$0, List<String> $$1) {
        int $$2 = $$1.size();
        switch ($$2) {
            case 0:
                return $$0;
            case 1:
                return $$0.resolve($$1.get(0));
            default:
                String[] $$3 = new String[$$2 - 1];
                for (int $$4 = 1; $$4 < $$2; $$4++) {
                    $$3[$$4 - 1] = $$1.get($$4);
                }
                return $$0.resolve($$0.getFileSystem().getPath($$1.get(0), $$3));
        }
    }

    private static boolean containsAllowedCharactersOnly(String $$0) {
        return STRICT_PATH_SEGMENT_CHECK.matcher($$0).matches();
    }

    public static boolean isValidPathSegment(String $$0) {
        return ($$0.equals("..") || $$0.equals(".") || !containsAllowedCharactersOnly($$0)) ? false : true;
    }

    public static void validatePath(String... $$0) {
        if ($$0.length == 0) {
            throw new IllegalArgumentException("Path must have at least one element");
        }
        for (String $$1 : $$0) {
            if (!isValidPathSegment($$1)) {
                throw new IllegalArgumentException("Illegal segment " + $$1 + " in path " + Arrays.toString($$0));
            }
        }
    }

    public static void createDirectoriesSafe(Path $$0) throws IOException {
        Files.createDirectories(Files.exists($$0, new LinkOption[0]) ? $$0.toRealPath(new LinkOption[0]) : $$0, new FileAttribute[0]);
    }
}
