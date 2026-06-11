package net.labymod.v1_21_4.client.session.user;

import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.UserFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/session/user/VersionedUserFactory.class */
@Singleton
@Implements(UserFactory.class)
public class VersionedUserFactory implements UserFactory {
    @Inject
    public VersionedUserFactory() {
    }

    @Override // net.labymod.core.client.session.UserFactory
    public flw createUser(Session session) {
        UUID newUuidObject = UUID.fromString(session.getUniqueId().toString());
        return new flw(session.getUsername(), newUuidObject, session.getAccessToken(), Optional.empty(), Optional.empty(), session.isPremium() ? a.c : a.a);
    }
}
