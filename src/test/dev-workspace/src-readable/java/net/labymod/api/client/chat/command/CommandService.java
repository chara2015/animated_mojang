package net.labymod.api.client.chat.command;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/command/CommandService.class */
@Referenceable
public interface CommandService {
    <T extends Command> T register(T t);

    void unregister(Class<? extends Command> cls);

    boolean fireCommand(String str, String[] strArr);

    boolean fireCommand(Class<? extends Command> cls, String str, String[] strArr);

    /* JADX WARN: Multi-variable type inference failed */
    default void unregister(Command command) {
        unregister((Class<? extends Command>) command.getClass());
    }

    /* JADX WARN: Multi-variable type inference failed */
    default boolean fireCommand(Command command, String usedPrefix, String[] arguments) {
        return fireCommand((Class<? extends Command>) command.getClass(), usedPrefix, arguments);
    }
}
