package net.labymod.core.configuration.converter.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.core.configuration.converter.models.LegacyAutoText;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/converter/LegacyAutoTextConverter.class */
public class LegacyAutoTextConverter extends LegacyConverter<JsonObject> {
    public LegacyAutoTextConverter() {
        super("autotext.json", JsonObject.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject jsonObject) throws Exception {
        JsonArray<JsonElement> keyBinds = jsonObject.getAsJsonArray("autoTextKeyBinds");
        List<LegacyAutoText> legacyAutoTexts = new ArrayList<>();
        for (JsonElement keyBind : keyBinds) {
            legacyAutoTexts.add((LegacyAutoText) fromJson(keyBind, LegacyAutoText.class));
        }
        List<AutoTextEntry> legacyEntries = new ArrayList<>();
        for (LegacyAutoText legacyAutoText : legacyAutoTexts) {
            AutoTextEntry autoTextEntry = legacyAutoText.convert();
            if (autoTextEntry != null) {
                legacyEntries.add(autoTextEntry);
            }
        }
        AutoTextService autoTextService = Laby.references().autoTextService();
        for (AutoTextEntry entry : autoTextService.getEntries()) {
            if (!entry.displayInInteractionMenu().get().booleanValue()) {
                legacyEntries.removeIf(e -> {
                    return Arrays.equals(e.requiredKeys().get(), entry.requiredKeys().get());
                });
            }
        }
        for (AutoTextEntry legacyEntry : legacyEntries) {
            autoTextService.addEntry(legacyEntry);
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public boolean hasStuffToConvert() {
        return (getValue() == null || getValue().getAsJsonArray("autoTextKeyBinds").size() == 0) ? false : true;
    }
}
