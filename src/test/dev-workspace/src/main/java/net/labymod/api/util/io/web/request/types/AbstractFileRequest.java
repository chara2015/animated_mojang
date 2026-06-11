package net.labymod.api.util.io.web.request.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.AbstractFileRequest;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/types/AbstractFileRequest.class */
public abstract class AbstractFileRequest<T, R extends AbstractFileRequest<T, R>> extends AbstractRequest<T, R> {
    protected Path path;
    protected Consumer<Double> percentageConsumer;
    protected Consumer<Path> preDownloadConsumer;

    protected AbstractFileRequest(@Nullable Path path, @Nullable Consumer<Double> percentageConsumer) {
        this.path = path;
        this.percentageConsumer = percentageConsumer;
    }

    public R preDownloadConsumer(Consumer<Path> preDownloadConsumer) {
        this.preDownloadConsumer = preDownloadConsumer;
        return this;
    }

    protected Path download(Response<T> response, WebInputStream inputStream) throws Exception {
        if (this.path == null) {
            throw new UnsupportedOperationException("Cannot download file without a path!");
        }
        if (!continueDownload()) {
            return this.path;
        }
        Path downloadPath = this.path;
        if (IOUtil.isDirectory(downloadPath)) {
            String header = response.getHeaders().get("Content-Disposition");
            if (header == null) {
                header = response.getHeaders().get("content-disposition");
            }
            String fileName = parseFileNameFromHeader(header);
            if (fileName == null) {
                throw new UnsupportedOperationException("Cannot download file as a directory was provided and no filename was provided in the response headers!");
            }
            downloadPath = downloadPath.resolve(fileName);
            this.path = downloadPath;
        }
        Path downloadPath2 = downloadPath.resolveSibling(String.valueOf(downloadPath.getFileName()) + ".tmp");
        if (this.preDownloadConsumer != null) {
            try {
                this.preDownloadConsumer.accept(downloadPath2);
            } catch (Exception exception) {
                throw exception;
            }
        }
        if (IOUtil.exists(downloadPath2)) {
            IOUtil.delete(downloadPath2);
        }
        InputStream fileInputStream = inputStream.getInputStream();
        try {
            OutputStream fileOutputStream = Files.newOutputStream(downloadPath2, StandardOpenOption.CREATE_NEW);
            try {
                int totalLength = 0;
                byte[] buffer = new byte[512];
                while (true) {
                    int length = fileInputStream.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, length);
                    totalLength += length;
                    if (this.percentageConsumer != null) {
                        this.percentageConsumer.accept(Double.valueOf((100.0d / ((double) inputStream.getContentLength())) * ((double) totalLength)));
                    }
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                IOUtil.copyAndDelete(downloadPath2, this.path, StandardCopyOption.REPLACE_EXISTING);
                return this.path;
            } finally {
            }
        } catch (Throwable th) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    protected boolean continueDownload() throws IOException {
        return true;
    }

    protected R applyRequestDataTo(AbstractFileRequest<T, R> request) {
        request.path = this.path;
        request.percentageConsumer = this.percentageConsumer;
        return request;
    }

    private String parseFileNameFromHeader(String header) {
        int utf8NameIndex = header.indexOf("filename*=utf-8''");
        if (utf8NameIndex != -1) {
            String encodedFileName = header.substring(utf8NameIndex + 17);
            StringBuilder decodedFileName = new StringBuilder();
            int length = encodedFileName.length();
            int i = 0;
            while (i < length) {
                char c = encodedFileName.charAt(i);
                if (c == '%') {
                    if (encodedFileName.charAt(i + 3) != '%' || !encodedFileName.startsWith("A7", i + 4)) {
                        String hexCode = encodedFileName.substring(i + 1, i + 3);
                        int codePoint = Integer.parseInt(hexCode, 16);
                        decodedFileName.append((char) codePoint);
                    }
                    i += 2;
                } else {
                    decodedFileName.append(c);
                }
                i++;
            }
            return decodedFileName.toString();
        }
        int fileHeaderIndex = header.indexOf("filename=");
        if (fileHeaderIndex == -1) {
            return null;
        }
        int startIndex = fileHeaderIndex + 9;
        int endIndex = header.length();
        if (header.indexOf(59, startIndex) != -1) {
            endIndex = header.indexOf(59, startIndex);
        }
        String fileName = header.substring(startIndex, endIndex);
        if (fileName.startsWith("\"") && fileName.endsWith("\"")) {
            fileName = fileName.substring(1, fileName.length() - 1);
        }
        return fileName;
    }
}
