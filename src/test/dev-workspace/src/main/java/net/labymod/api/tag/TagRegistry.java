package net.labymod.api.tag;

import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/tag/TagRegistry.class */
public final class TagRegistry {
    static final AtomicInteger ID_COUNTER = new AtomicInteger(0);
    private static final Map<String, Tag> TAGS = new HashMap();

    static Tag getOrRegister(String namespace, String name, BiFunction<String, String, Tag> tagFactory) {
        String key = namespace + ":" + name;
        Tag tag = TAGS.get(key);
        if (tag == null) {
            Tag newTag = tagFactory.apply(namespace, name);
            TAGS.put(key, newTag);
            tag = newTag;
        }
        return tag;
    }

    static List<Tag> getTags(IntList ids) {
        List<Tag> tags = new ArrayList<>();
        for (int index = 0; index < ids.size(); index++) {
            int tagId = ids.getInt(index);
            Tag tag = findTag(tagId);
            if (tag != null) {
                tags.add(tag);
            }
        }
        return tags;
    }

    private static Tag findTag(int id) {
        for (Tag tag : TAGS.values()) {
            if (tag.getId() == id) {
                return tag;
            }
        }
        return null;
    }
}
