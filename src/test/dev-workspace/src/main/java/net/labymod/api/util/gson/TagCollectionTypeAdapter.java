package net.labymod.api.util.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/gson/TagCollectionTypeAdapter.class */
public class TagCollectionTypeAdapter extends LabyGsonTypeAdapter<TagInputWidget.TagCollection> {
    /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
    /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public TagInputWidget.TagCollection m566deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonArray()) {
            throw new JsonParseException("Tag Collection element is not a json array!");
        }
        TagInputWidget.TagCollection tagCollection = new TagInputWidget.TagCollection();
        JsonArray<JsonElement> jsonArray = json.getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isString()) {
                    String asString = jsonPrimitive.getAsString();
                    if (!asString.trim().isEmpty()) {
                        tagCollection.add(asString);
                    }
                }
            }
        }
        return tagCollection;
    }

    public JsonElement serialize(TagInputWidget.TagCollection src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        for (TagInputWidget.TagCollection.Tag tag : src.getTags()) {
            String content = tag.getContent();
            if (!content.trim().isEmpty()) {
                jsonArray.add(new JsonPrimitive(content));
            }
        }
        return jsonArray;
    }
}
