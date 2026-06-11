package net.labymod.core.labymodnet.models;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/ChangeResponse.class */
public class ChangeResponse {
    private final int done;

    @SerializedName("msg")
    @Nullable
    private String message;

    public ChangeResponse(int done) {
        this.done = done;
    }

    public int getDone() {
        return this.done;
    }

    public boolean isDone() {
        return this.done == 1;
    }

    @Nullable
    public String getMessage() {
        return this.message;
    }
}
