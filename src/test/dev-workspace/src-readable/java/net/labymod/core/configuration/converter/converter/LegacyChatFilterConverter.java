package net.labymod.core.configuration.converter.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.filter.ChatFilter;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.ChatWindowConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.chat.advanced.DefaultAdvancedChatController;
import net.labymod.core.client.chat.advanced.DefaultChatWindow;
import net.labymod.core.configuration.converter.models.LegacyChatFilter;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/converter/LegacyChatFilterConverter.class */
public class LegacyChatFilterConverter extends LegacyConverter<JsonObject> {
    private static final float DUMMY_WINDOW_WIDTH = 960.0f;
    private static final float DUMMY_WINDOW_HEIGHT = 540.0f;
    private static final float CHAT_WIDTH = 300.0f;
    private static final float CHAT_HEIGHT = 150.0f;
    private final LegacySettingConverter settingConverter;

    public LegacyChatFilterConverter(LegacySettingConverter settingConverter) {
        super("filters.json", JsonObject.class);
        this.settingConverter = settingConverter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject jsonObject) throws Exception {
        JsonArray<JsonElement> filters = jsonObject.getAsJsonArray("filters");
        List<LegacyChatFilter> legacyChatFilters = new ArrayList<>();
        for (JsonElement chatFilter : filters) {
            legacyChatFilters.add((LegacyChatFilter) fromJson(chatFilter, LegacyChatFilter.class));
        }
        Map<LegacyChatFilter, ChatFilter> legacyFilters = new HashMap<>();
        for (LegacyChatFilter legacyChatFilter : legacyChatFilters) {
            ChatFilter filter = legacyChatFilter.convert();
            if (filter != null) {
                legacyFilters.put(legacyChatFilter, filter);
            }
        }
        ChatConfigAccessor chatConfig = Laby.labyAPI().chatProvider().chatConfigAccessor();
        boolean hasSecondaryChat = legacyChatFilters.stream().anyMatch((v0) -> {
            return v0.shouldDisplayInSecondChat();
        });
        ChatWindowConfig mainChat = new ChatWindowConfig();
        ChatWindowConfig secondaryChat = null;
        if (hasSecondaryChat) {
            secondaryChat = new ChatWindowConfig(new RootChatTabConfig(0, RootChatTabConfig.Type.CUSTOM, new GeneralChatTabConfig("Second chat")));
        }
        for (Map.Entry<LegacyChatFilter, ChatFilter> entry : legacyFilters.entrySet()) {
            LegacyChatFilter legacyFilter = entry.getKey();
            ChatFilter filter2 = entry.getValue();
            ChatWindowConfig targetWindow = legacyFilter.shouldDisplayInSecondChat() ? secondaryChat : mainChat;
            targetWindow.getTabs().get(0).config().filters().get().add(filter2);
        }
        JsonObject labySettings = this.settingConverter.getValue();
        if (labySettings != null) {
            convertChatSettings(labySettings, mainChat, secondaryChat);
        }
        chatConfig.getWindows().clear();
        DefaultAdvancedChatController advancedChatController = (DefaultAdvancedChatController) LabyMod.references().advancedChatController();
        advancedChatController.getWindows().clear();
        advancedChatController.addWindow(new DefaultChatWindow(mainChat));
        if (hasSecondaryChat) {
            advancedChatController.addWindow(new DefaultChatWindow(secondaryChat));
        }
    }

    private void convertChatSettings(JsonObject labySettings, ChatWindowConfig mainChat, ChatWindowConfig secondaryChat) {
        ChatWindowConfig leftChat = 0 != 0 ? secondaryChat : mainChat;
        ChatWindowConfig rightChat = 0 != 0 ? mainChat : secondaryChat;
        Bounds window = new PositionedBounds(0.0f, 0.0f, DUMMY_WINDOW_WIDTH, DUMMY_WINDOW_HEIGHT);
        if (leftChat != null) {
            leftChat.setPosition(window, Rectangle.relative(0.0f, 390.0f, CHAT_WIDTH, CHAT_HEIGHT));
        }
        if (rightChat != null) {
            float width = labySettings.get("secondChatWidth").getAsFloat();
            float height = labySettings.get("secondChatHeight").getAsFloat();
            rightChat.setPosition(window, Rectangle.relative(DUMMY_WINDOW_WIDTH - width, DUMMY_WINDOW_HEIGHT - height, width, height));
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public boolean hasStuffToConvert() {
        return (getValue() == null || getValue().getAsJsonArray("filters").size() == 0) ? false : true;
    }
}
