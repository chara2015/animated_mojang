package net.labymod.core.main.user;

import com.google.gson.JsonElement;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.io.web.WebResponse;
import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/GameUserDataResponse.class */
final class GameUserDataResponse implements WebResponse<JsonElement> {
    private final Logging logging = Laby.references().loggingFactory().create(GameUserDataResponse.class);
    private final GameUser gameUser;

    GameUserDataResponse(GameUser gameUser) {
        this.gameUser = gameUser;
    }

    @Override // net.labymod.api.util.io.web.WebResponse
    public void success(JsonElement result) {
        if (!(this.gameUser instanceof DefaultGameUser)) {
            return;
        }
        ((DefaultGameUser) this.gameUser).updateUserData(result);
    }

    @Override // net.labymod.api.util.io.web.WebResponse
    public void failed(WebRequestException exception) {
        this.logging.error("Response code for {} is {}", this.gameUser.getUniqueId(), Integer.valueOf(exception.getResponseCode()));
        if (!(this.gameUser instanceof DefaultGameUser)) {
            return;
        }
        ((DefaultGameUser) this.gameUser).userDataFailed(exception);
    }
}
