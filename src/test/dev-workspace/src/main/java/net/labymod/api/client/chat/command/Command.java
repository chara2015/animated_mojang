package net.labymod.api.client.chat.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/command/Command.class */
public abstract class Command {
    protected final LabyAPI labyAPI = Laby.labyAPI();
    protected final String prefix;
    protected final String[] aliases;
    private final List<SubCommand> subCommands;
    protected String translationKeyPrefix;
    protected Component messagePrefix;
    private List<SubCommand> unmodifiableSubCommands;

    public abstract boolean execute(String str, String[] strArr);

    protected Command(@NotNull String prefix, @NotNull String... aliases) {
        Objects.requireNonNull(prefix, "prefix");
        Objects.requireNonNull(aliases, "aliases");
        this.prefix = prefix.toLowerCase(Locale.ROOT);
        this.aliases = new String[aliases.length];
        for (int i = 0; i < aliases.length; i++) {
            this.aliases[i] = aliases[i].toLowerCase(Locale.ROOT);
        }
        this.subCommands = new ArrayList();
    }

    public List<String> complete(String[] arguments) {
        if (arguments.length == 1) {
            List<String> tabCompletions = new ArrayList<>();
            tabCompletions.add(this.prefix);
            tabCompletions.addAll(Arrays.asList(this.aliases));
            return tabCompletions;
        }
        return Collections.emptyList();
    }

    protected final void sendMessage(String message) {
        this.labyAPI.minecraft().chatExecutor().chat(message);
    }

    protected final void displayMessage(String message) {
        displayMessage(Component.text(message));
    }

    protected final void displaySyntax() {
        Objects.requireNonNull(this.translationKeyPrefix, "TranslationKey cannot be null");
        displaySyntaxTranslatable(getTranslationKey("syntax"));
    }

    protected final void displaySyntax(String subCommand) {
        Objects.requireNonNull(this.translationKeyPrefix, "TranslationKey cannot be null");
        displaySyntaxTranslatable(getTranslationKey(subCommand + ".syntax"));
    }

    private void displaySyntaxTranslatable(String translationKey) {
        displayMessage(Component.translatable("labymod.command.usage", new Component[0]).args(Component.text("/").append(Component.text(this.prefix)).append(Component.text(" ")).append(Component.translatable(translationKey, new Component[0])).color(NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false)).color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD));
    }

    protected final void displayMessage(Component message) {
        Component component = Component.empty();
        if (this.messagePrefix != null) {
            component = component.append(this.messagePrefix).append(Component.text(" "));
        }
        this.labyAPI.minecraft().chatExecutor().displayClientMessage(component.append(message));
    }

    protected final void displayTranslatable(String key, TextColor textColor, Object... arguments) {
        String translationKey = key;
        if (this.translationKeyPrefix != null) {
            translationKey = this.translationKeyPrefix + "." + key;
        }
        String message = I18n.translate(translationKey, arguments);
        displayMessage(Component.text(message, textColor));
    }

    protected final void displayTranslatable(String key, Style style, Component... arguments) {
        String translationKey = key;
        if (this.translationKeyPrefix != null) {
            translationKey = this.translationKeyPrefix + "." + key;
        }
        displayMessage(Component.translatable(translationKey, arguments).style(style));
    }

    protected final void displayTranslatable(String key, TextColor textColor, Component... arguments) {
        String translationKey = key;
        if (this.translationKeyPrefix != null) {
            translationKey = this.translationKeyPrefix + "." + key;
        }
        displayMessage(Component.translatable(translationKey, arguments).style(Style.empty().color(textColor)));
    }

    protected final String getTranslationKey() {
        return this.translationKeyPrefix;
    }

    protected String getTranslationKey(String key) {
        Objects.requireNonNull(this.translationKeyPrefix, "TranslationKey cannot be null");
        return String.format(Locale.ROOT, "%s.%s", this.translationKeyPrefix, key);
    }

    protected void openActivity(ScreenInstance screenInstance) {
        this.labyAPI.minecraft().executeNextTick(() -> {
            this.labyAPI.minecraft().minecraftWindow().displayScreen(screenInstance);
        });
    }

    public final String getPrefix() {
        return this.prefix;
    }

    public final String[] getAliases() {
        return this.aliases;
    }

    public final List<SubCommand> getSubCommands() {
        if (this.unmodifiableSubCommands == null) {
            this.unmodifiableSubCommands = Collections.unmodifiableList(this.subCommands);
        }
        return this.unmodifiableSubCommands;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends Command> T messagePrefix(Component messagePrefix) {
        this.messagePrefix = messagePrefix;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends Command> T translationKey(String key) {
        this.translationKeyPrefix = key;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final <T extends Command> T withSubCommand(@NotNull SubCommand subCommand) {
        Objects.requireNonNull(subCommand, "subCommand");
        subCommand.withParent(this);
        this.subCommands.add(subCommand);
        return this;
    }
}
