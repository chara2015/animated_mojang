package net.labymod.core.client.chat.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.advanced.IngameChatTab;
import net.labymod.api.client.chat.filter.ChatFilter;
import net.labymod.api.client.chat.filter.FilterChatService;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.configuration.labymod.chat.ChatWindow;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/filter/DefaultFilterChatService.class */
@Singleton
@Implements(FilterChatService.class)
public class DefaultFilterChatService implements FilterChatService {
    private final MinecraftSounds sounds;
    private final List<ChatFilter> matchingChatFilters = new ArrayList();

    @Inject
    public DefaultFilterChatService(EventBus eventBus, MinecraftSounds sounds) {
        this.sounds = sounds;
        eventBus.registerListener(this);
    }

    @Override // net.labymod.api.client.chat.filter.FilterChatService
    public ChatFilter filter(List<ChatFilter> filters, ChatMessage chatMessage) {
        if (filters.isEmpty()) {
            return null;
        }
        refreshMatchingFilters(filters, chatMessage);
        if (this.matchingChatFilters.isEmpty()) {
            return null;
        }
        return this.matchingChatFilters.get(0);
    }

    private void refreshMatchingFilters(List<ChatFilter> filters, ChatMessage chatMessage) {
        ThreadSafe.ensureRenderThread();
        this.matchingChatFilters.clear();
        String message = chatMessage.getPlainText();
        String messageJson = chatMessage.getSerializedText();
        for (ChatFilter filter : filters) {
            boolean filtered = false;
            boolean advanced = filter.advanced().get().booleanValue();
            if (advanced) {
                if (matchesRegEx(message, messageJson, filter, filter.includeRegEx().get())) {
                    filtered = true;
                }
            } else {
                for (TagInputWidget.TagCollection.Tag tag : filter.getIncludedTags().getTags()) {
                    String content = tag.getContent();
                    if (searchNormal(filter, message, content) || searchTooltip(filter, messageJson, content)) {
                        filtered = true;
                        break;
                    }
                }
            }
            if (filtered) {
                if (advanced) {
                    if (matchesRegEx(message, messageJson, filter, filter.excludeRegEx().get())) {
                        filtered = false;
                    }
                } else {
                    for (TagInputWidget.TagCollection.Tag tag2 : filter.getExcludedTags().getTags()) {
                        String content2 = tag2.getContent();
                        if (searchNormal(filter, message, content2) || searchTooltip(filter, messageJson, content2)) {
                            filtered = false;
                            break;
                        }
                    }
                }
            }
            if (filtered) {
                this.matchingChatFilters.add(filter);
            }
        }
    }

    private boolean matchesRegEx(String message, String messageJson, ChatFilter filter, String regex) {
        Pattern compile;
        if (regex == null || regex.isEmpty() || messageJson == null || (compile = compile(filter.caseSensitive().get().booleanValue(), regex)) == null) {
            return false;
        }
        Matcher messageMatcher = compile.matcher(message);
        Matcher messageJsonMatcher = compile.matcher(messageJson);
        return messageMatcher.matches() || (filter.shouldFilterTooltip().get().booleanValue() && messageJsonMatcher.matches());
    }

    @Nullable
    private Pattern compile(boolean caseSensitive, String regEx) {
        Pattern patternCompile;
        try {
            if (caseSensitive) {
                patternCompile = Pattern.compile(regEx, 2);
            } else {
                patternCompile = Pattern.compile(regEx);
            }
            return patternCompile;
        } catch (PatternSyntaxException e) {
            return null;
        }
    }

    private boolean searchNormal(@NotNull ChatFilter filter, String message, String content) {
        if (filter.caseSensitive().get().booleanValue()) {
            return message.contains(content);
        }
        return StringUtil.containsIgnoreCase(message, content);
    }

    private boolean searchTooltip(@NotNull ChatFilter filter, String messageJson, String content) {
        if (messageJson != null && filter.shouldFilterTooltip().get().booleanValue()) {
            if (filter.caseSensitive().get().booleanValue()) {
                return messageJson.contains(content);
            }
            return StringUtil.containsIgnoreCase(messageJson, content);
        }
        return false;
    }

    @Subscribe(64)
    public void applyChatFilter(AdvancedChatTabMessageEvent event) {
        AdvancedChatMessage message = event.message();
        ChatMessage chatMessage = message.chatMessage();
        List<ChatFilter> tabFilters = event.tab().config().filters().get();
        boolean noFilters = tabFilters.isEmpty();
        if (!noFilters) {
            refreshMatchingFilters(tabFilters, chatMessage);
        }
        if (noFilters || this.matchingChatFilters.isEmpty()) {
            if (noFilters || event.tab().rootConfig().type().get() == RootChatTabConfig.Type.SERVER) {
                for (ChatWindow window : LabyMod.references().advancedChatController().getWindows()) {
                    Iterator<ChatTab> it = window.getTabs().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ChatTab tab = it.next();
                            GeneralChatTabConfig config = tab.config();
                            if (filter(config.filters().get(), chatMessage) != null) {
                                event.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
                return;
            }
            event.setCancelled(true);
            return;
        }
        boolean playedSound = false;
        boolean changedBackground = false;
        for (ChatFilter filter : this.matchingChatFilters) {
            if (filter.shouldHideMessage().get().booleanValue()) {
                event.setCancelled(true);
                return;
            }
            if (!playedSound && filter.shouldPlaySound().get().booleanValue()) {
                playedSound = true;
                this.sounds.playChatFilterSound();
            }
            if (!changedBackground && filter.shouldChangeBackground().get().booleanValue()) {
                changedBackground = true;
                message.metadata().set(IngameChatTab.CUSTOM_BACKGROUND, filter.backgroundColor().get());
            }
        }
    }
}
