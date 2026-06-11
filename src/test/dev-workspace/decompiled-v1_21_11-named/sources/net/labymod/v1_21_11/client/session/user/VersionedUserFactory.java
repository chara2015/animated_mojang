package net.labymod.v1_21_11.client.session.user;

import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.session.Session;
import net.labymod.api.models.Implements;
import net.labymod.core.client.session.UserFactory;
import net.minecraft.client.User;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/session/user/VersionedUserFactory.class */
@Singleton
@Implements(UserFactory.class)
public class VersionedUserFactory implements UserFactory {
    @Inject
    public VersionedUserFactory() {
    }

    /* JADX INFO: renamed from: createUser, reason: merged with bridge method [inline-methods] */
    public User m36createUser(Session session) {
        UUID newUuidObject = UUID.fromString(session.getUniqueId().toString());
        return new User(session.getUsername(), newUuidObject, session.getAccessToken(), Optional.empty(), Optional.empty());
    }
}
