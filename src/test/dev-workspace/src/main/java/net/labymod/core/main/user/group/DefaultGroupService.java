package net.labymod.core.main.user.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Singleton;
import net.laby.lib.user.RoleService;
import net.laby.lib.user.role.RemoteRole;
import net.labymod.api.Laby;
import net.labymod.api.models.Implements;
import net.labymod.api.service.Service;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import net.labymod.api.user.group.Group;
import net.labymod.api.user.group.GroupService;
import net.labymod.core.main.user.DefaultGameUserProfile;
import net.labymod.core.main.user.shop.JsonBodyDeserializer;
import net.labymod.core.main.util.LabyLibHttpClients;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/group/DefaultGroupService.class */
@Singleton
@Implements(GroupService.class)
public class DefaultGroupService extends Service implements GroupService {
    private final RoleService roleService;
    private final Map<Integer, Group> groups = new HashMap();

    public DefaultGroupService(LabyLibHttpClients httpClients) {
        this.roleService = new RoleService(httpClients.createValidatedHttpClient(JsonBodyDeserializer.defaultGson()));
    }

    @Override // net.labymod.api.service.Service
    protected void prepare() {
        registerDefaultGroups();
        this.roleService.getRoles().ifSuccess(remoteRoles -> {
            Iterator i$ = remoteRoles.iterator();
            while (i$.hasNext()) {
                RemoteRole remoteRole = (RemoteRole) i$.next();
                registerRole(remoteRole);
            }
        }).ifFailure(error -> {
            LOGGER.error("Failed to load roles, {}", error);
        });
    }

    @Override // net.labymod.api.service.Service
    public void onServiceCompleted() {
        refreshUserGroups();
    }

    @Override // net.labymod.api.service.Service
    public void onServiceError(Exception exception) {
        refreshUserGroups();
        super.onServiceError(exception);
    }

    @Override // net.labymod.api.service.Service
    public void onServiceUnload() {
        this.groups.clear();
    }

    @Override // net.labymod.api.user.group.GroupService
    public Group getGroup(int id) {
        return this.groups.getOrDefault(Integer.valueOf(id), DefaultGroups.DEFAULT_GROUP);
    }

    @Override // net.labymod.api.user.group.GroupService
    public Collection<Group> getGroups() {
        return this.groups.values();
    }

    private void registerDefaultGroups() {
        registerGroup(DefaultGroups.LEGACY_GROUP);
        registerGroup(DefaultGroups.DEFAULT_GROUP);
    }

    private void registerGroup(Group group) {
        this.groups.put(Integer.valueOf(group.getIdentifier()), group);
    }

    private void registerRole(RemoteRole role) {
        Group roleGroup = new Group(role.id(), role.name(), role.displayName(), role.colorHex(), role.colorMinecraft().charAt(0), role.tagName(), role.displayType().toString(), role.isStaff());
        roleGroup.initialize();
        registerGroup(roleGroup);
    }

    private void refreshUserGroups() {
        GameUserService gameUserService = Laby.references().gameUserService();
        for (GameUser user : gameUserService.getGameUsers().values()) {
            ((DefaultGameUserProfile) user.profile()).groupHolder().refresh();
        }
    }
}
