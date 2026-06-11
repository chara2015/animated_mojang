package net.labymod.core.client.session;

import java.util.Objects;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.session.Session;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.session.SessionRefresher;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.util.concurrent.task.Task;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/DefaultAbstractSessionAccessor.class */
public abstract class DefaultAbstractSessionAccessor<T> implements SessionAccessor {
    private final Task refreshProfileTask = Task.builder(this::refreshProfile).build();

    public abstract T toGameSession(Session session);

    @Nullable
    public abstract Session fromGameSession(@Nullable T t);

    @Nullable
    public abstract T getGameSession();

    @Override // net.labymod.api.client.session.SessionAccessor
    @Nullable
    public Session getSession() {
        return fromGameSession(getGameSession());
    }

    @Override // net.labymod.api.client.session.SessionAccessor
    public void updateSession(Session session) {
        Session previousSession = getSession();
        setGameSession(toGameSession(session));
        Laby.labyAPI().eventBus().fire(new SessionUpdateEvent(previousSession, session));
    }

    public void updateSession(Account account) {
        Session session = createSession(account.getUsername(), account.getUUID(), account.getAccessToken());
        updateSession(session);
    }

    public boolean isAccount(Account account) {
        Session session = getSession();
        if (session == null) {
            return false;
        }
        if (session.getUniqueId() != null && !session.getUniqueId().equals(account.getUUID())) {
            return false;
        }
        return Objects.equals(session.getAccessToken(), account.getAccessToken());
    }

    @Override // net.labymod.api.client.session.SessionAccessor
    public boolean isPremium() {
        Session session = getSession();
        return session != null && session.isPremium();
    }

    protected final void setGameSession(T gameSession) {
        Laby.labyAPI().minecraft().setSessionRaw(gameSession);
        this.refreshProfileTask.execute();
    }

    @MustBeInvokedByOverriders
    protected void refreshProfile() {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        minecraft.refreshRealmsClient();
        if (minecraft instanceof SessionRefresher) {
            SessionRefresher sessionRefresher = (SessionRefresher) minecraft;
            sessionRefresher.refresh();
        }
    }
}
