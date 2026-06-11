package net.labymod.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/Ice4jSocketFix.class */
public final class Ice4jSocketFix {
    public static InputStream getInputStream(Socket socket, InputStream delegate) throws IOException {
        if (socket.isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (!socket.isConnected()) {
            throw new SocketException("Socket is not connected");
        }
        if (socket.isInputShutdown()) {
            throw new SocketException("Socket is input shutdown");
        }
        return new CustomSocketInputStream(socket, delegate);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/Ice4jSocketFix$CustomSocketInputStream.class */
    private static class CustomSocketInputStream extends InputStream {
        private final Socket parent;
        private final InputStream delegate;

        CustomSocketInputStream(Socket parent, InputStream delegate) {
            this.parent = parent;
            this.delegate = delegate;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            byte[] a = new byte[1];
            int n = read(a, 0, 1);
            if (n > 0) {
                return a[0] & 255;
            }
            return -1;
        }

        @Override // java.io.InputStream
        public int read(byte[] buffer, int off, int len) throws IOException {
            try {
                return this.delegate.read(buffer, off, len);
            } catch (SocketTimeoutException e) {
                throw e;
            } catch (InterruptedIOException e2) {
                Thread thread = Thread.currentThread();
                if (thread.isVirtual() && thread.isInterrupted()) {
                    close();
                    throw new SocketException("Closed by interrupt");
                }
                throw e2;
            }
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.delegate.available();
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.parent.close();
        }
    }
}
