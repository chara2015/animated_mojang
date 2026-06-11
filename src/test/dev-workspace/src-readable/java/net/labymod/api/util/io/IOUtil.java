package net.labymod.api.util.io;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import net.labymod.api.models.OperatingSystem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/IOUtil.class */
public final class IOUtil {
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final Pattern STRICT_PATH_SEGMENT_PATTERN = Pattern.compile("[-._a-z0-9]+");

    private IOUtil() {
    }

    @Contract("_ -> new")
    @NotNull
    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, StandardCharsets.UTF_8);
    }

    @Contract("_, _ -> new")
    @NotNull
    public static String toString(@NotNull InputStream inputStream, Charset encoding) throws IOException {
        return new String(readBytes(inputStream), encoding);
    }

    public static byte[] readBytes(@NotNull InputStream inputStream) throws IOException {
        return readBytes(inputStream, true);
    }

    public static byte[] readBytes(@NotNull InputStream inputStream, boolean close) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] data = new byte[1024];
            while (true) {
                int readable = inputStream.read(data, 0, data.length);
                if (readable == -1) {
                    break;
                }
                outputStream.write(data, 0, readable);
            }
            outputStream.flush();
            if (close) {
                inputStream.close();
            }
            byte[] byteArray = outputStream.toByteArray();
            outputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                outputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void writeBytes(@NotNull InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] data = new byte[1024];
        while (true) {
            int readable = inputStream.read(data, 0, data.length);
            if (readable != -1) {
                outputStream.write(data, 0, readable);
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    @Contract(value = "_ -> new", pure = true)
    @NotNull
    public static InputStream writeBytes(byte[] data) {
        return new ByteArrayInputStream(data);
    }

    public static void hideFileInWindowsFileSystem(Path path) {
        try {
            if (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) {
                Files.setAttribute(path, "dos:hidden", true, new LinkOption[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(Path path) {
        if (path.getFileSystem() == FileSystems.getDefault()) {
            return Files.exists(path, new LinkOption[0]);
        }
        return toFile(path).exists();
    }

    public static boolean isDirectory(Path path) {
        return toFile(path).isDirectory();
    }

    public static void createDirectories(Path path) throws IOException {
        if (exists(path)) {
            return;
        }
        File file = toFile(path);
        if (!file.mkdirs()) {
            throw new IOException("Failed to create directories");
        }
    }

    public static void createFile(Path path) throws IOException {
        if (exists(path)) {
            throw new FileAlreadyExistsException("File already exists");
        }
        File file = toFile(path);
        if (!file.createNewFile()) {
            throw new IOException("Failed to create file");
        }
    }

    public static void delete(Path path) throws IOException {
        Files.delete(path);
    }

    public static boolean deleteIfExits(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

    public static InputStream newInputStream(Path path) throws IOException {
        return Files.newInputStream(path, new OpenOption[0]);
    }

    public static OutputStream newOutputStream(Path path) throws IOException {
        return Files.newOutputStream(path, new OpenOption[0]);
    }

    public static long size(Path path) {
        return toFile(path).length();
    }

    public static int getFreePort() throws IOException {
        ServerSocket socket = new ServerSocket(0);
        try {
            int localPort = socket.getLocalPort();
            socket.close();
            return localPort;
        } catch (Throwable th) {
            try {
                socket.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static File toFile(Path path) {
        return path.toFile().getAbsoluteFile();
    }

    public static void atomicMove(Path source, Path target) throws IOException {
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException e) {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void copyAndDelete(Path source, Path target, CopyOption... options) throws IOException {
        Files.copy(source, target, options);
        Files.deleteIfExists(source);
    }

    /* JADX WARN: Unreachable blocks removed: 6, instructions: 10 */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:8:0x0019
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public static boolean isCorrupted(java.io.File r4) {
        /*
            java.util.jar.JarFile r0 = new java.util.jar.JarFile     // Catch: java.io.IOException -> L21
            r1 = r0
            r2 = r4
            r1.<init>(r2)     // Catch: java.io.IOException -> L21
            r5 = r0
            r0 = 0
            r6 = r0
            r0 = r5
            r0.close()     // Catch: java.io.IOException -> L21
            r0 = r6
            return r0
        L11:
            r6 = move-exception
            r0 = r5
            r0.close()     // Catch: java.lang.Throwable -> L19 java.io.IOException -> L21
            goto L1f
        L19:
            r7 = move-exception
            r0 = r6
            r1 = r7
            r0.addSuppressed(r1)     // Catch: java.io.IOException -> L21
        L1f:
            r0 = r6
            throw r0     // Catch: java.io.IOException -> L21
        L21:
            r5 = move-exception
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.util.io.IOUtil.isCorrupted(java.io.File):boolean");
    }

    public static boolean isCorrupted(Path path) {
        return isCorrupted(toFile(path));
    }

    public static BufferedImage base64ToBufferedImage(String base64) throws IOException, IllegalArgumentException {
        byte[] buffer = Base64.getDecoder().decode(base64.substring(base64.indexOf(",") + 1));
        ByteArrayInputStream input = new ByteArrayInputStream(buffer);
        try {
            BufferedImage bufferedImage = SafeImageIO.read(input, 65536);
            input.close();
            return bufferedImage;
        } catch (Throwable th) {
            try {
                input.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void closeSilent(InputStream stream) {
        if (stream == null) {
            return;
        }
        try {
            stream.close();
        } catch (IOException e) {
        }
    }

    public static InputStream copyStream(InputStream stream) throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                stream.transferTo(outputStream);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
                outputStream.close();
                return byteArrayInputStream;
            } finally {
            }
        } catch (IOException exception) {
            throw new IOException("Stream could not be copied.", exception);
        }
    }

    public static long getAvailableSpace(Path file) {
        try {
            return getUsableSpace(file);
        } catch (Exception e) {
            return Long.MAX_VALUE;
        }
    }

    public static long getUsableSpace(Path file) throws IOException {
        if (!exists(file)) {
            createFile(file);
        }
        FileStore store = Files.getFileStore(file);
        return store.getUsableSpace();
    }

    public static String getFileStoreName(Path file) {
        Path normalizedPath = file.toAbsolutePath().normalize();
        Path root = normalizedPath.getRoot();
        if (root == null) {
            return normalizedPath.toString().substring(0, 3);
        }
        return root.toString();
    }

    public static boolean isValidStrictPathSegment(String segment) {
        return STRICT_PATH_SEGMENT_PATTERN.matcher(segment).matches();
    }

    public static void validatePath(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Path must have at least one element");
        }
        for (String arg : args) {
            if (arg.equals("..") || arg.equals(".") || !isValidStrictPathSegment(arg)) {
                throw new IllegalArgumentException("Illegal segment " + arg + " in path " + Arrays.toString(args));
            }
        }
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
    public static net.labymod.api.util.OperationResult<java.util.List<java.lang.String>> decomposePath(java.lang.String r4) {
        /*
            Method dump skipped, instruction units count: 387
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.util.io.IOUtil.decomposePath(java.lang.String):net.labymod.api.util.OperationResult");
    }

    public static Path resolvePath(Path base, List<String> segments) {
        int size = segments.size();
        switch (size) {
            case 0:
                return base;
            case 1:
                return base.resolve((String) segments.getFirst());
            default:
                String[] remainingSegments = new String[size - 1];
                for (int i = 1; i < size; i++) {
                    remainingSegments[i - 1] = segments.get(i);
                }
                return base.resolve(base.getFileSystem().getPath((String) segments.getFirst(), remainingSegments));
        }
    }
}
