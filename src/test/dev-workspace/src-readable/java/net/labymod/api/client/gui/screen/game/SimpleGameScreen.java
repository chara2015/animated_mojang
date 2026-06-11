package net.labymod.api.client.gui.screen.game;

import net.labymod.api.tag.Tag;
import net.labymod.api.tag.TaggedObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/game/SimpleGameScreen.class */
public class SimpleGameScreen implements GameScreen {
    private final String id;
    private final boolean allowCustomFont;
    private final TaggedObject taggedObject = new TaggedObject();

    public SimpleGameScreen(String id, boolean allowCustomFont, Tag... tags) {
        this.id = id;
        this.allowCustomFont = allowCustomFont;
        for (Tag tag : tags) {
            this.taggedObject.setTag(tag);
        }
    }

    @Override // net.labymod.api.client.gui.screen.game.GameScreen
    public String getId() {
        return this.id;
    }

    @Override // net.labymod.api.client.gui.screen.game.GameScreen
    public boolean allowCustomFont() {
        return this.allowCustomFont;
    }

    @Override // net.labymod.api.tag.Taggable
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
}
