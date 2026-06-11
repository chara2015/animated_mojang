package net.labymod.api.tag;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/tag/TaggedObject.class */
public class TaggedObject {
    private final IntList tags;

    public TaggedObject() {
        this(new IntArrayList());
    }

    public TaggedObject(IntList tags) {
        this.tags = tags;
    }

    public void setTag(Tag tag) {
        int id = tag.getId();
        if (this.tags.contains(id)) {
            return;
        }
        this.tags.add(id);
    }

    public boolean removeTag(Tag tag) {
        if (this.tags.contains(tag.getId())) {
            return this.tags.rem(tag.getId());
        }
        return false;
    }

    public boolean hasTag(Tag tag) {
        return this.tags.contains(tag.getId());
    }

    public IntList getTags() {
        return this.tags;
    }

    public void clearTags() {
        this.tags.clear();
    }

    public List<Tag> fetchTags() {
        return TagRegistry.getTags(this.tags);
    }
}
