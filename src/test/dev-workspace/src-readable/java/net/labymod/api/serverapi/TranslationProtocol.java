package net.labymod.api.serverapi;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.labymod.serverapi.api.Protocol;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/serverapi/TranslationProtocol.class */
public class TranslationProtocol {
    private final PayloadChannelIdentifier identifier;
    private final Protocol protocol;
    private final Set<TranslationListener> listeners = new HashSet();

    public TranslationProtocol(PayloadChannelIdentifier identifier, Protocol protocol) {
        this.identifier = identifier;
        this.protocol = protocol;
    }

    @NotNull
    public Protocol targetProtocol() {
        return this.protocol;
    }

    @NotNull
    public PayloadChannelIdentifier identifier() {
        return this.identifier;
    }

    public void registerListener(TranslationListener listener) {
        this.listeners.add(listener);
    }

    public boolean forEachListener(Predicate<TranslationListener> predicate) {
        for (TranslationListener listener : this.listeners) {
            if (predicate.test(listener)) {
                return true;
            }
        }
        return false;
    }
}
