package net.labymod.api.client.chat.command;

import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/command/InjectedSubCommand.class */
public abstract class InjectedSubCommand extends Command {
    private final String injectionPrefix;

    protected InjectedSubCommand(@NotNull String injectionPrefix, @NotNull String prefix, @NotNull String... aliases) {
        super(prefix, aliases);
        Objects.requireNonNull(injectionPrefix, "injectionPrefix");
        this.injectionPrefix = injectionPrefix.toLowerCase(Locale.ROOT);
    }

    @NotNull
    public String getInjectionPrefix() {
        return this.injectionPrefix;
    }
}
