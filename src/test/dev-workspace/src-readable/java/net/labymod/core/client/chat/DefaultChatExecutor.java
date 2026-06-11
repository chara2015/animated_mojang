package net.labymod.core.client.chat;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.chat.ActionBar;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.theme.ThemeChangeEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.uri.URIParser;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatExecutor.class */
public abstract class DefaultChatExecutor implements ChatExecutor {
    private static final Logging LOGGER = Logging.getLogger();
    private ActionBar currentContinuousActionBar;

    protected abstract void rescaleChat();

    public DefaultChatExecutor() {
        EventBus eventBus = Laby.references().eventBus();
        eventBus.registerListener(new ChatThemeUpdateListener(this));
        eventBus.registerListener(this);
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void performAction(ClickEvent event) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        switch (event.action()) {
            case OPEN_URL:
                MinecraftOptions options = minecraft.options();
                if (!options.isChatLinksEnabled()) {
                    return;
                }
                String link = event.getValue();
                openUrl(link, options.isChatLinkConfirmationEnabled());
                return;
            case OPEN_FILE:
                openFile(event.getValue());
                return;
            case RUN_COMMAND:
                runCommand(event.getValue());
                return;
            case SUGGEST_COMMAND:
                suggestCommand(event.getValue());
                return;
            case COPY_TO_CLIPBOARD:
                copyToClipboard(event.getValue());
                return;
            default:
                if (!minecraft.isIngame()) {
                    throw new IllegalStateException("Cannot perform action " + String.valueOf(event.action()) + " while not in-game.");
                }
                ScreenWrapper currentScreen = minecraft.minecraftWindow().currentScreen();
                if (currentScreen != null) {
                    currentScreen.handleClickEvent(event);
                    return;
                }
                return;
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void openUrl(String value, boolean confirmation) {
        if (!isHttpUrl(value)) {
            LOGGER.warn("Refused to open non-http(s) URL: {}", value);
        } else if (confirmation) {
            Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new ConfirmLinkActivity(value, type -> {
                switch (type) {
                    case COPY:
                        copyToClipboard(value);
                        break;
                    case OPEN:
                        openUrl(value);
                        break;
                }
            }));
        } else {
            openUrl(value);
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void openUrl(String value) {
        if (!isHttpUrl(value)) {
            LOGGER.warn("Refused to open non-http(s) URL: {}", value);
        } else {
            OperatingSystem.getPlatform().openUrl(value);
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void openUrlOrJoinServer(String value) {
        if (value.startsWith("mcserver://")) {
            String ip = value.substring("mcserver://".length());
            Laby.labyAPI().serverController().joinServer(ip);
        } else {
            openUrl(value);
        }
    }

    private static boolean isHttpUrl(String value) {
        if (value == null) {
            return false;
        }
        try {
            return URIParser.isClickableScheme(new URI(value).getScheme());
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public void openFile(String value) {
        OperatingSystem.getPlatform().openFile(new File(value));
    }

    @Override // net.labymod.api.client.chat.ChatExecutor
    public ActionBar displayActionBarContinuous(Component message) {
        AtomicReference<ActionBar> ref = new AtomicReference<>();
        ActionBar actionBar = new ActionBar(() -> {
            if (this.currentContinuousActionBar == ref.get()) {
                this.currentContinuousActionBar = null;
            }
        }, message);
        ref.set(actionBar);
        this.currentContinuousActionBar = actionBar;
        return actionBar;
    }

    @Subscribe
    public void displayActionBar(GameTickEvent event) {
        ActionBar actionBar = this.currentContinuousActionBar;
        if (actionBar == null) {
            return;
        }
        if (actionBar.getTick() % 20 == 0) {
            displayActionBar(actionBar.message());
        }
        actionBar.setTick(actionBar.getTick() + 1);
    }

    protected void runCommand(String command) {
        chat(command, false);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/DefaultChatExecutor$ChatThemeUpdateListener.class */
    public static class ChatThemeUpdateListener {
        private final DefaultChatExecutor executor;

        public ChatThemeUpdateListener(DefaultChatExecutor executor) {
            this.executor = executor;
        }

        @Subscribe
        public void onThemeChange(ThemeChangeEvent event) {
            if (event.phase() == Phase.PRE) {
                return;
            }
            DefaultChatExecutor defaultChatExecutor = this.executor;
            Objects.requireNonNull(defaultChatExecutor);
            ThreadSafe.executeOnRenderThread(defaultChatExecutor::rescaleChat);
        }
    }
}
