package net.labymod.core.main.user.group;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.user.group.Group;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/group/GroupHolder.class */
public class GroupHolder {
    private int id;
    private Group visibleGroup;

    public GroupHolder() {
        this(DefaultGroups.DEFAULT_GROUP);
    }

    public GroupHolder(Group visibleGroup) {
        this.id = visibleGroup.getIdentifier();
        this.visibleGroup = visibleGroup;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
        updateVisibleGroup();
    }

    public Group visibleGroup() {
        return this.visibleGroup;
    }

    public boolean isLegacy() {
        return this.visibleGroup == DefaultGroups.LEGACY_GROUP;
    }

    public void refresh() {
        updateVisibleGroup();
    }

    private void setVisibleGroup(Group visibleGroup) {
        this.visibleGroup = (Group) Objects.requireNonNullElse(visibleGroup, DefaultGroups.DEFAULT_GROUP);
    }

    private void updateVisibleGroup() {
        setVisibleGroup(Laby.references().groupService().getGroup(this.id));
    }
}
