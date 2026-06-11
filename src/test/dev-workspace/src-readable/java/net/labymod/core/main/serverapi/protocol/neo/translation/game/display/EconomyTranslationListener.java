package net.labymod.core.main.serverapi.protocol.neo.translation.game.display;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import net.labymod.api.serverapi.KeyedTranslationListener;
import net.labymod.api.serverapi.LabyModProtocolService;
import net.labymod.serverapi.api.packet.Packet;
import net.labymod.serverapi.core.LabyModProtocol;
import net.labymod.serverapi.core.model.display.EconomyDisplay;
import net.labymod.serverapi.core.packet.clientbound.game.display.EconomyDisplayPacket;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/translation/game/display/EconomyTranslationListener.class */
public class EconomyTranslationListener extends KeyedTranslationListener {
    private final LabyModProtocol protocol;

    public EconomyTranslationListener(LabyModProtocol protocol) {
        super("economy");
        this.protocol = protocol;
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected Packet translateIncomingMessage(JsonElement economyElement) {
        if (!economyElement.isJsonObject()) {
            return null;
        }
        EconomyDisplayPacket economyDisplayPacket = null;
        JsonObject economyObject = economyElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : economyObject.entrySet()) {
            String key = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("visible") && jsonObject.has("balance")) {
                    boolean visible = jsonObject.get("visible").getAsBoolean();
                    double balance = jsonObject.get("balance").getAsDouble();
                    String icon = null;
                    if (jsonObject.has("icon")) {
                        icon = jsonObject.get("icon").getAsString();
                    }
                    EconomyDisplay.DecimalFormat decimalFormat = null;
                    if (jsonObject.has("decimal") && jsonObject.get("decimal").isJsonObject()) {
                        JsonObject decimalObject = jsonObject.get("decimal").getAsJsonObject();
                        if (decimalObject.has("divisor") && decimalObject.has("format")) {
                            String format = decimalObject.get("format").getAsString();
                            double divisor = decimalObject.get("divisor").getAsDouble();
                            decimalFormat = new EconomyDisplay.DecimalFormat(format, divisor);
                        }
                    }
                    EconomyDisplayPacket packet = new EconomyDisplayPacket(new EconomyDisplay(key, visible, balance, icon, decimalFormat));
                    if (economyDisplayPacket == null) {
                        economyDisplayPacket = packet;
                    } else {
                        this.protocol.handlePacket(LabyModProtocolService.DUMMY_UUID, packet);
                    }
                }
            }
        }
        return economyDisplayPacket;
    }

    @Override // net.labymod.api.serverapi.KeyedTranslationListener
    protected JsonElement translateOutgoingMessage(Packet packet) {
        return null;
    }
}
