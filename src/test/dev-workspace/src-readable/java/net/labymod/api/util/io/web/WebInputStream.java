package net.labymod.api.util.io.web;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/WebInputStream.class */
public class WebInputStream extends InputStream {
    private final InputStream inputStream;
    private final int contentLength;

    public WebInputStream(InputStream inputStream) {
        this(inputStream, 0);
    }

    public WebInputStream(InputStream inputStream, int contentLength) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.inputStream.read();
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public int getContentLength() {
        return this.contentLength;
    }
}
