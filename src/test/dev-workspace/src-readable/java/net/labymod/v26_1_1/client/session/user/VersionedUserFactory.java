package net.labymod.v26_1_1.client.session.user;

import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.UserFactory;
import net.minecraft.client.User;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/session/user/VersionedUserFactory.class */
@Singleton
@Implements(UserFactory.class)
public class VersionedUserFactory implements UserFactory {
    @Inject
    public VersionedUserFactory() {
    }

    @Override // net.labymod.core.client.session.UserFactory
    public User createUser(Session session) {
        UUID newUuidObject = UUID.fromString(session.getUniqueId().toString());
        return new User(session.getUsername(), newUuidObject, session.getAccessToken(), Optional.empty(), Optional.empty());
    }
}
