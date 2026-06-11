package net.labymod.api.serverapi;

import java.util.Set;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/serverapi/PayloadTranslationRegistry.class */
public interface PayloadTranslationRegistry {
    void register(TranslationProtocol translationProtocol);

    void unregister(TranslationProtocol translationProtocol);

    Set<TranslationProtocol> getProtocols();
}
