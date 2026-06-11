package net.labymod.v1_16_5.client.session.user;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.UserFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/session/user/VersionedUserFactory.class */
@Singleton
@Implements(UserFactory.class)
public class VersionedUserFactory implements UserFactory {
    @Inject
    public VersionedUserFactory() {
    }

    @Override // net.labymod.core.client.session.UserFactory
    public Object createUser(Session session) {
        Session.Type type = session.getType();
        if (type == Session.Type.MICROSOFT) {
            type = Session.Type.MOJANG;
        }
        return new dkm(session.getUsername(), session.getUniqueId().toString(), session.getAccessToken(), type.toString());
    }
}
