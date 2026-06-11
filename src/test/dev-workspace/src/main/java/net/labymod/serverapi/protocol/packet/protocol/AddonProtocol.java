package net.labymod.serverapi.protocol.packet.protocol;

import net.labymod.serverapi.protocol.payload.identifier.PayloadChannelIdentifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/serverapi/protocol/packet/protocol/AddonProtocol.class */
@Deprecated(forRemoval = true, since = "4.2.24")
public class AddonProtocol extends Protocol {
    private final String addonId;

    public AddonProtocol(String addonId) {
        this(addonId, null);
    }

    public AddonProtocol(String addonId, PayloadChannelIdentifier legacyId) {
        super(PayloadChannelIdentifier.create("labymod", "neo/addons/" + addonId), legacyId);
        this.addonId = addonId;
    }

    public String getAddonId() {
        return this.addonId;
    }
}
