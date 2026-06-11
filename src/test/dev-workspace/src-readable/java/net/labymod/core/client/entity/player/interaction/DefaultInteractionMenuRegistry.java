package net.labymod.core.client.entity.player.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.entity.player.interaction.InteractionMenuPlaceholder;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.KeyValue;
import net.labymod.core.client.entity.player.interaction.defaults.AddFriendBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.CopyNameBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.LabyNetBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.NameHistoryBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.ReportCloakBulletPoint;
import net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/DefaultInteractionMenuRegistry.class */
@Singleton
@Implements(InteractionMenuRegistry.class)
public class DefaultInteractionMenuRegistry extends DefaultRegistry<BulletPoint> implements InteractionMenuRegistry {
    private final List<InteractionMenuPlaceholder> placeholders = new ArrayList();

    @Inject
    public DefaultInteractionMenuRegistry() {
        registerPlaceHolder(InteractionMenuPlaceholder.of("name", (v0) -> {
            return v0.getName();
        }));
        registerPlaceHolder(InteractionMenuPlaceholder.of("uuid", player -> {
            return player.getUniqueId().toString();
        }));
        register("labynet", new LabyNetBulletPoint());
        register("copy_name", new CopyNameBulletPoint());
        register("name_history", new NameHistoryBulletPoint());
        register("report_cape", new ReportCloakBulletPoint());
        register("add_labymod_friend", new AddFriendBulletPoint());
    }

    @Override // net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry
    public void unregisterServerBulletPoints() {
        for (KeyValue<BulletPoint> pair : (KeyValue[]) getElements().toArray(new KeyValue[0])) {
            BulletPoint point = pair.getValue();
            if (point instanceof ServerBulletPoint) {
                unregister(pair.getKey());
            }
        }
    }

    @Override // net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry
    public void registerPlaceHolder(@NotNull InteractionMenuPlaceholder placeholder) {
        Objects.requireNonNull(placeholder, "placeholder");
        if (this.placeholders.contains(placeholder)) {
            return;
        }
        this.placeholders.add(placeholder);
    }

    public String replacePlaceholders(@NotNull String text, @NotNull Player player) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(player, "player");
        String value = text;
        for (InteractionMenuPlaceholder placeholder : this.placeholders) {
            value = value.replace(String.format(Locale.ROOT, "{%s}", placeholder.getIdentifier()), placeholder.getReplacement(player));
        }
        return value;
    }
}
