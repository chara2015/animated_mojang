package net.labymod.core.configuration.converter.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.core.configuration.converter.models.LegacyPlayerMenuEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/converter/LegacyPlayerMenuConverter.class */
public class LegacyPlayerMenuConverter extends LegacyConverter<JsonObject> {
    public LegacyPlayerMenuConverter() {
        super("playermenu.json", JsonObject.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject jsonObject) throws Exception {
        JsonArray entries = jsonObject.getAsJsonArray("playerMenuEntries");
        List<LegacyPlayerMenuEntry> legacyPlayerMenuEntries = new ArrayList<>();
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            legacyPlayerMenuEntries.add((LegacyPlayerMenuEntry) fromJson((JsonElement) it.next(), LegacyPlayerMenuEntry.class));
        }
        AutoTextService autoTextService = Laby.references().autoTextService();
        for (AutoTextEntry entry : autoTextService.getEntries()) {
            if (entry.displayInInteractionMenu().get().booleanValue()) {
                legacyPlayerMenuEntries.removeIf(e -> {
                    return entry.displayName().get().equals(e.getDisplayName());
                });
            }
        }
        for (LegacyPlayerMenuEntry legacyPlayerMenuEntry : legacyPlayerMenuEntries) {
            autoTextService.addEntry(legacyPlayerMenuEntry.convert());
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public boolean hasStuffToConvert() {
        return (getValue() == null || getValue().getAsJsonArray("playerMenuEntries").size() == 0) ? false : true;
    }
}
