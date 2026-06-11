package net.labymod.serverapi.protocol.payload.identifier;

import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/payload/identifier/PayloadChannelIdentifier.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public final class PayloadChannelIdentifier {
    private final String namespace;
    private final String path;

    private PayloadChannelIdentifier(@NotNull String namespace, @NotNull String path) {
        this.namespace = namespace;
        this.path = path;
    }

    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static PayloadChannelIdentifier create(@NotNull String namespace, @NotNull String path) {
        return new PayloadChannelIdentifier(namespace, path);
    }

    @NotNull
    public String getNamespace() {
        return this.namespace;
    }

    @NotNull
    public String getPath() {
        return this.path;
    }

    @Contract(pure = true)
    @NotNull
    public String toString() {
        return this.namespace + ":" + this.path;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PayloadChannelIdentifier that = (PayloadChannelIdentifier) o;
        return Objects.equals(this.namespace, that.namespace) && Objects.equals(this.path, that.path);
    }

    public int hashCode() {
        return Objects.hash(this.namespace, this.path);
    }
}
