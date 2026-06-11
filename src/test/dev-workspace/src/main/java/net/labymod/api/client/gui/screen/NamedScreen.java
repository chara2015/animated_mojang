package net.labymod.api.client.gui.screen;

import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.ScreenTags;
import net.labymod.api.tag.Tag;
import net.labymod.api.tag.TaggedObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/NamedScreen.class */
public enum NamedScreen implements GameScreen {
    MAIN_MENU("main_menu"),
    SINGLEPLAYER("singleplayer"),
    MULTIPLAYER("multiplayer"),
    EDIT_SERVER("edit_server"),
    CHAT_INPUT("chat_input", false),
    CHAT_INPUT_IN_BED("chat_input_in_bed", false),
    INGAME_MENU("ingame_menu"),
    CREATE_WORLD("create_world"),
    DIRECT_CONNECT("direct_connect"),
    INVENTORY("inventory", false),
    CREATIVE_INVENTORY("creative_inventory", false),
    CONNECTING("connecting"),
    DISCONNECTED("disconnected"),
    CREDITS("credits"),
    REALMS("realms"),
    LEVEL_LOADING("level_loading"),
    RECEIVING_LEVEL("receiving_level"),
    PACK_CONFIRM("pack_confirm"),
    PROGRESS("progress"),
    GENERIC_MESSAGE("generic_message"),
    OPEN_LAN_WORLD("open_lan_world"),
    STATISTICS("statistics"),
    ADVANCEMENTS("advancements"),
    CONFIRM("confirm"),
    SOCIAL_INTERACTIONS("social_interactions"),
    EDIT_BOOK("book", false),
    FRIENDS("friends"),
    OPTIONS("options", ScreenTags.OPTIONS),
    SKIN_CUSTOMIZATION("skin_customization", ScreenTags.OPTIONS),
    VIDEO_SETTINGS("video_settings", ScreenTags.OPTIONS),
    LANGUAGE_SELECTION("language_selection", ScreenTags.OPTIONS),
    RESOURCE_PACK_SETTINGS("resource_pack_settings", ScreenTags.OPTIONS),
    AUDIO_SETTINGS("audio_settings", ScreenTags.OPTIONS),
    CONTROL_SETTINGS("control_settings", ScreenTags.OPTIONS),
    KEYBIND_SETTINGS("keybind_settings", ScreenTags.OPTIONS),
    MOUSE_SETTINGS("mouse_settings", ScreenTags.OPTIONS),
    CHAT_SETTINGS("chat_settings", ScreenTags.OPTIONS),
    ACCESSIBILITY_SETTINGS("accessibility_settings", ScreenTags.OPTIONS),
    SNOOPER_SETTINGS("snooper_settings", ScreenTags.OPTIONS);

    private final String id;
    private final TaggedObject taggedObject;

    NamedScreen(String id) {
        this(id, true);
    }

    NamedScreen(String id, boolean allowCustomFont) {
        this(id, allowCustomFont, new Tag[0]);
    }

    NamedScreen(String id, Tag... tags) {
        this(id, true, tags);
    }

    NamedScreen(String id, boolean allowCustomFont, Tag... tags) {
        this.taggedObject = new TaggedObject();
        this.id = id;
        if (allowCustomFont) {
            this.taggedObject.setTag(ScreenTags.ALLOW_CUSTOM_FONT);
        }
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
        return this.taggedObject.hasTag(ScreenTags.ALLOW_CUSTOM_FONT);
    }

    @Override // net.labymod.api.tag.Taggable
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
}
