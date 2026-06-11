package net.labymod.core.labyconnect.session.message;

import com.google.gson.Gson;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/session/message/MessageListener.class */
public interface MessageListener {
    public static final Gson GSON = DefaultLabyConnectSession.GSON;

    void listen(String str);
}
