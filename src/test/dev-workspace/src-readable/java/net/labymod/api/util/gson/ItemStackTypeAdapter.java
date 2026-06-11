package net.labymod.api.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.component.data.DataComponentKey;
import net.labymod.api.component.data.DataComponentPatch;
import net.labymod.api.component.data.NbtDataComponentContainer;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/ItemStackTypeAdapter.class */
@Deprecated(forRemoval = true, since = "4.1.23")
public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    private static final Logging LOGGER = Logging.getLogger();
    private static final String COMPONENT_KEY = "nbt";

    public JsonElement serialize(ItemStack itemStack, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        ResourceLocation identifier = itemStack.getIdentifier();
        obj.addProperty("namespace", identifier.getNamespace());
        obj.addProperty("path", identifier.getPath());
        DataComponentContainer container = itemStack.getDataComponentContainer();
        if (!PlatformEnvironment.ITEM_COMPONENTS && !container.isEmpty()) {
            try {
                obj.add(COMPONENT_KEY, context.serialize(asCompound(container)));
            } catch (Exception exception) {
                LOGGER.warn("Failed to serialize ItemStack", exception);
            }
        }
        int legacyItemData = itemStack.getLegacyItemData();
        if (legacyItemData != -1) {
            obj.addProperty("legacy_data", Integer.valueOf(legacyItemData));
        }
        return obj;
    }

    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public ItemStack m558deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String namespace = obj.get("namespace").getAsString();
        String path = obj.get("path").getAsString();
        ItemStack itemStack = Laby.references().itemStackFactory().create(namespace, path);
        if (!PlatformEnvironment.ITEM_COMPONENTS && obj.has(COMPONENT_KEY)) {
            try {
                JsonObject nbt = obj.get(COMPONENT_KEY).getAsJsonObject();
                NBTTagCompound tag = (NBTTagCompound) context.deserialize(nbt, NBTTagCompound.class);
                NbtDataComponentContainer components = new NbtDataComponentContainer(tag);
                itemStack.applyPatchToDataComponent(DataComponentPatch.createPatch(components));
            } catch (Exception exception) {
                LOGGER.warn("Failed to deserialize ItemStack", exception);
            }
        }
        if (obj.has("legacy_data")) {
            itemStack.setLegacyItemData(obj.get("legacy_data").getAsInt());
        }
        return itemStack;
    }

    private NBTTagCompound asCompound(DataComponentContainer container) {
        NBTTagCompound compound = Laby.references().nbtFactory().compound();
        for (DataComponentKey dataComponentKey : container.keySet()) {
            Object object = container.get(dataComponentKey);
            compound.set(dataComponentKey.name(), (NBTTag) object);
        }
        return compound;
    }
}
