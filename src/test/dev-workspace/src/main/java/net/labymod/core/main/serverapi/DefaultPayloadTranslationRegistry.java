package net.labymod.core.main.serverapi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.serverapi.PayloadTranslationRegistry;
import net.labymod.api.serverapi.TranslationProtocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/DefaultPayloadTranslationRegistry.class */
public class DefaultPayloadTranslationRegistry implements PayloadTranslationRegistry {
    private final Set<TranslationProtocol> protocols = new HashSet();
    private final Set<TranslationProtocol> unmodifiableProtocols = Collections.unmodifiableSet(this.protocols);

    @Override // net.labymod.api.serverapi.PayloadTranslationRegistry
    public void register(TranslationProtocol protocol) {
        this.protocols.add(protocol);
        Laby.references().payloadRegistry().registerPayloadChannel(protocol.identifier());
    }

    @Override // net.labymod.api.serverapi.PayloadTranslationRegistry
    public void unregister(TranslationProtocol protocol) {
        this.protocols.remove(protocol);
    }

    @Override // net.labymod.api.serverapi.PayloadTranslationRegistry
    public Set<TranslationProtocol> getProtocols() {
        return this.unmodifiableProtocols;
    }
}
