package net.labymod.core.labymodnet;

import java.util.function.Consumer;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.core.labymodnet.models.ChangeResponse;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.models.CosmeticOptions;
import net.labymod.core.labymodnet.models.UserItems;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/LabyModNetService.class */
@Referenceable
public interface LabyModNetService {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/LabyModNetService$State.class */
    public enum State {
        LOADING,
        NOT_PREMIUM,
        NOT_CONNECTED,
        ERROR,
        ACCOUNT_CHANGED,
        OK
    }

    void reload();

    State getState();

    UserItems getUserItems();

    CosmeticOptions getCosmeticOptions();

    void toggleCosmetic(Cosmetic cosmetic, boolean z, Consumer<ChangeResponse> consumer);

    void updateCosmetic(Cosmetic cosmetic, Consumer<ChangeResponse> consumer);

    void updateEmotes(Consumer<ChangeResponse> consumer);
}
