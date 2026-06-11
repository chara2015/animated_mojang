package net.labymod.api.user.group;

import java.util.Collection;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/user/group/GroupService.class */
@Referenceable
public interface GroupService {
    Group getGroup(int i);

    Collection<Group> getGroups();
}
