package net.labymod.core.main.user.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.Laby;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/util/CosmeticUtil.class */
public class CosmeticUtil {
    private static final Logging LOGGER = Logging.getLogger();

    public static void addCosmetic(JsonElement element, int id) {
        if (!DefaultLabyModLoader.getInstance().isLabyModDevelopmentEnvironment() || !element.isJsonObject()) {
            return;
        }
        DefaultGameUserService service = (DefaultGameUserService) Laby.references().gameUserService();
        CosmeticDefinition definition = service.cosmeticIndexService().getDefinitionById(id);
        if (definition == null) {
            LOGGER.warn("Could not find a cosmetic with id {}", Integer.valueOf(id));
            return;
        }
        String[] defaultData = definition.details().getDefaultData();
        JsonObject cosmeticObject = new JsonObject();
        cosmeticObject.addProperty("i", Integer.valueOf(id));
        JsonArray options = new JsonArray();
        for (String data : defaultData) {
            options.add(data);
        }
        cosmeticObject.add("d", options);
        JsonObject object = element.getAsJsonObject();
        JsonArray cosmetics = new JsonArray();
        if (!object.has("c")) {
            object.add("c", cosmetics);
        } else {
            cosmetics = object.getAsJsonArray("c");
        }
        cosmetics.add(cosmeticObject);
    }
}
