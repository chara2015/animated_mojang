package net.labymod.core.main.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.group.Group;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/InvalidCosmeticDataDebugger.class */
public final class InvalidCosmeticDataDebugger {
    private static final String PREFIX = "[INVALID COSMETIC DATA] ";
    private static final Logging LOGGER = Logging.create((Class<?>) InvalidCosmeticDataDebugger.class);
    private static final Supplier<String> THREAD_NAME = () -> {
        return Thread.currentThread().getName();
    };
    private static final Map<String, GameUser> USERS = new HashMap();

    public static void start(GameUser user) {
        if (!isStaffOrCosmeticCreator()) {
            return;
        }
        String name = THREAD_NAME.get();
        executeOnRenderThread(() -> {
            addUser(name, user);
        });
    }

    public static void log(String message, Object... args) {
        if (!isStaffOrCosmeticCreator()) {
            return;
        }
        String name = THREAD_NAME.get();
        executeOnRenderThread(() -> {
            GameUser gameUser = USERS.get(name);
            if (gameUser == null) {
                LOGGER.error("[INVALID COSMETIC DATA] Hmm, that should not happen. " + message, args);
                return;
            }
            UUID uniqueId = gameUser.getUniqueId();
            LOGGER.error("[INVALID COSMETIC DATA] [User " + String.valueOf(uniqueId) + "]" + message, args);
            Laby.labyAPI().labyNetController().loadNameByUniqueId(uniqueId, result -> {
                String shortUuid = uniqueId.toString().replace("-", "").substring(0, 16);
                Laby.labyAPI().notificationController().push(Notification.builder().title(Component.text("Invalid Cosmetic Data")).text(Component.text("User \"" + ((String) result.getOrDefault(shortUuid)) + "\" has an invalid cosmetic.").append(Component.newline()).append(Component.text(args.length >= 1 ? String.valueOf(args[0]) : "Unknown Cosmetic"))).build());
            });
        });
    }

    public static void end() {
        if (!isStaffOrCosmeticCreator()) {
            return;
        }
        String name = THREAD_NAME.get();
        executeOnRenderThread(() -> {
            removeUser(name);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addUser(String name, GameUser user) {
        USERS.put(name, user);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void removeUser(String name) {
        USERS.remove(name);
    }

    private static void executeOnRenderThread(Runnable runnable) {
        Laby.labyAPI().minecraft().executeOnRenderThread(runnable);
    }

    private static boolean isStaffOrCosmeticCreator() {
        Group visibleGroup;
        GameUser gameUser = Laby.labyAPI().gameUserService().clientGameUser();
        if (gameUser == null || (visibleGroup = gameUser.visibleGroup()) == null) {
            return false;
        }
        return visibleGroup.isStaffOrCosmeticCreator();
    }
}
