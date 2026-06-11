package net.labymod.api.client.chat.command;

import java.util.Objects;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/command/SubCommand.class */
public abstract class SubCommand extends Command {
    private Command parent;

    protected SubCommand(@NotNull String subPrefix, @NotNull String... aliases) {
        super(subPrefix, aliases);
    }

    @NotNull
    public final Command parent() {
        if (this.parent == null) {
            throw new NullPointerException("Parent is null. Did you register this sub command via Command#withSubCommand(SubCommand)?");
        }
        return this.parent;
    }

    @Deprecated
    @Nullable
    public final Command getParent() {
        return this.parent;
    }

    @ApiStatus.Internal
    protected final SubCommand withParent(@NotNull Command parent) {
        Objects.requireNonNull(parent, "parent");
        if (this.parent != null) {
            throw new IllegalStateException("Parent already set");
        }
        this.parent = parent;
        return this;
    }
}
