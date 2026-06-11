package net.labymod.api.notification;

import java.util.function.Consumer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.notification.Notification;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Debounce;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/NotificationController.class */
@Referenceable
public interface NotificationController {
    void push(Notification notification);

    void pop(Notification notification);

    default void pushDelayed(ResourceLocation id, long delay, Consumer<Notification.Builder> builderConsumer) {
        Debounce.of(id, delay, () -> {
            Notification.Builder builder = Notification.builder();
            builderConsumer.accept(builder);
            push(builder.build());
        });
    }
}
