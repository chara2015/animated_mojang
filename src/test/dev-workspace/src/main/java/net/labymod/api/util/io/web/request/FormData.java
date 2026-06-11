package net.labymod.api.util.io.web.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.UUID;
import net.labymod.api.util.io.IOUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/FormData.class */
public class FormData {
    private final String name;
    private final InputStream value;
    private final String contentType;
    private final String fileName;
    private final long length;
    public static final String BOUNDARY = "----WebKitFormBoundary" + UUID.randomUUID().toString().replace("-", "");
    public static final byte[] BOUNDARY_BYTES = ("--" + BOUNDARY + "\r\n").getBytes(StandardCharsets.UTF_8);
    public static final byte[] BOUNDARY_END_BYTES = ("--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8);
    public static final String NEW_LINE = "\r\n";
    public static final byte[] NEW_LINE_BYTES = NEW_LINE.getBytes(StandardCharsets.UTF_8);

    private FormData(String name, InputStream value, String contentType, String fileName, long length) {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
        this.fileName = fileName;
        this.length = length;
    }

    public static FormData of(String key, Object value) {
        return builder().name(key).value(value).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return this.name;
    }

    public InputStream getValue() {
        return this.value;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public long getLength() {
        return this.length;
    }

    public String getContentDispositionHeader() {
        return "Content-Disposition: form-data; name=\"" + this.name + "\"" + (this.fileName != null ? "; filename=\"" + this.fileName + "\"" : "");
    }

    public String getContentTypeHeader() {
        return "Content-Type: " + this.contentType;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/FormData$Builder.class */
    public static class Builder {
        private String name;
        private InputStream value;
        private String contentType;
        private String fileName;
        private long length;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String value) {
            byte[] bytes = value.getBytes();
            return value(new ByteArrayInputStream(bytes), bytes.length);
        }

        public Builder value(Object value) {
            return value(value.toString());
        }

        public Builder value(Path path) throws IOException {
            return value(IOUtil.newInputStream(path), IOUtil.size(path));
        }

        public Builder value(InputStream value, long length) {
            this.value = value;
            this.length = length;
            return this;
        }

        public Builder value(byte[] value) {
            this.value = new ByteArrayInputStream(value);
            this.length = value.length;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public FormData build() {
            return new FormData(this.name, this.value, this.contentType, this.fileName, this.length);
        }
    }
}
