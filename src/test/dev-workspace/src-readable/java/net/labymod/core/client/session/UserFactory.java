package net.labymod.core.client.session;

import net.labymod.api.client.session.Session;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/UserFactory.class */
@Referenceable
public interface UserFactory {
    Object createUser(Session session);
}
