package net.labymod.api.util.io.execption;

import java.io.IOException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/execption/InsufficientStorageSpace.class */
public class InsufficientStorageSpace extends IOException {
    private final long availableSpace;
    private final long requiredSpace;

    public InsufficientStorageSpace(long availableSpace, long requiredSpace) {
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }

    public InsufficientStorageSpace(String message, long availableSpace, long requiredSpace) {
        super(message);
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }

    public InsufficientStorageSpace(String message, Throwable cause, long availableSpace, long requiredSpace) {
        super(message, cause);
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }

    public InsufficientStorageSpace(Throwable cause, long availableSpace, long requiredSpace) {
        super(cause);
        this.availableSpace = availableSpace;
        this.requiredSpace = requiredSpace;
    }

    public long getAvailableSpace() {
        return this.availableSpace;
    }

    public long getRequiredSpace() {
        return this.requiredSpace;
    }
}
