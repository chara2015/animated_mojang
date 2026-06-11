package net.labymod.core.main.quicklauncher.link.implementation;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import mslinks.ShellLink;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.main.quicklauncher.link.Link;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/quicklauncher/link/implementation/WindowsLink.class */
public class WindowsLink implements Link {
    @Override // net.labymod.core.main.quicklauncher.link.Link
    public Path create(String id, Path backendDirectory, String[] command, String displayName, BufferedImage icon) throws IOException {
        Path batPath = backendDirectory.resolve(String.format(Locale.ROOT, "%s.bat", id));
        String content = buildCommand(command, "\"", " ");
        writeToFile(batPath, content);
        Path icoPath = backendDirectory.resolve(String.format(Locale.ROOT, "%s.ico", id));
        writeIco(icoPath, icon);
        Path desktopPath = Paths.get(System.getProperty("user.home"), "Desktop");
        Path lnkPath = desktopPath.resolve(String.format(Locale.ROOT, "%s.lnk", displayName));
        int i = 1;
        while (Files.exists(lnkPath, new LinkOption[0])) {
            lnkPath = desktopPath.resolve(String.format(Locale.ROOT, "%s (%d).lnk", displayName, Integer.valueOf(i)));
            i++;
        }
        writeLnkFile(lnkPath, batPath, icoPath);
        return lnkPath;
    }

    public void writeLnkFile(Path lnkPath, Path targetPath, Path icoPath) throws IOException {
        ShellLink link = new ShellLink().setIconLocation(icoPath.toAbsolutePath().toString());
        link.setTarget(targetPath.toAbsolutePath().toString());
        link.saveTo(lnkPath.toAbsolutePath().toString());
    }

    private void writeIco(Path path, BufferedImage image) throws IOException {
        if (image.getWidth() > 64 || image.getHeight() > 64) {
            BufferedImage resizedImage = new BufferedImage(64, 64, 2);
            resizedImage.getGraphics().drawImage(image, 0, 0, 64, 64, (ImageObserver) null);
            image = resizedImage;
        }
        try {
            OutputStream os = IOUtil.newOutputStream(path);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(os);
                bos.write(new byte[]{0, 0, 1, 0, 1, 0});
                bos.write(new byte[]{16, 0, 16, 0, 1, 0, 32, 32});
                int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), (int[]) null, 0, image.getWidth());
                os.write(new byte[]{66, 77});
                os.write(intToBytes(54 + (4 * pixels.length)));
                os.write(new byte[]{0, 0});
                os.write(new byte[]{0, 0});
                os.write(intToBytes(54));
                os.write(intToBytes(40));
                os.write(intToBytes(image.getWidth()));
                os.write(intToBytes(image.getHeight()));
                os.write(new byte[]{1, 0});
                os.write(new byte[]{32, 0});
                os.write(new byte[]{0, 0, 0, 0});
                os.write(intToBytes(4 * pixels.length));
                os.write(new byte[]{19, 11, 0, 0});
                os.write(new byte[]{19, 11, 0, 0});
                os.write(new byte[]{0, 0, 0, 0});
                os.write(new byte[]{0, 0, 0, 0});
                for (int i = pixels.length - 1; i >= 0; i--) {
                    os.write(intToBytes(pixels[i] & 16777215));
                }
                if (os != null) {
                    os.close();
                }
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] intToBytes(int value) {
        byte[] bytes = {(byte) (value & 255), (byte) ((value >> 8) & 255), (byte) ((value >> 16) & 255), (byte) ((value >> 24) & 255)};
        return bytes;
    }
}
