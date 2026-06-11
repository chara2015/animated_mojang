package net.labymod.core.configuration.converter.models;

import net.labymod.api.client.chat.filter.ChatFilter;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/models/LegacyChatFilter.class */
public class LegacyChatFilter {
    private String filterName;
    private String[] wordsContains;
    private String[] wordsContainsNot;
    private boolean playSound;
    private boolean highlightMessage;
    private int highlightColorR;
    private int highlightColorG;
    private int highlightColorB;
    private boolean hideMessage;
    private boolean displayInSecondChat;
    private boolean filterTooltips;

    public ChatFilter convert() {
        ChatFilter chatFilter = new ChatFilter();
        chatFilter.name().set(this.filterName);
        for (String word : this.wordsContains) {
            chatFilter.getIncludedTags().add(word);
        }
        for (String word2 : this.wordsContainsNot) {
            chatFilter.getExcludedTags().add(word2);
        }
        chatFilter.shouldPlaySound().set(Boolean.valueOf(this.playSound));
        chatFilter.shouldChangeBackground().set(Boolean.valueOf(this.highlightMessage));
        chatFilter.backgroundColor().set(Integer.valueOf(ColorFormat.ARGB32.pack(this.highlightColorR, this.highlightColorG, this.highlightColorB)));
        chatFilter.shouldHideMessage().set(Boolean.valueOf(this.hideMessage));
        chatFilter.shouldFilterTooltip().set(Boolean.valueOf(this.filterTooltips));
        return chatFilter;
    }

    public boolean shouldDisplayInSecondChat() {
        return this.displayInSecondChat;
    }
}
