package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/tags/ServerBadgeTag.class */
public class ServerBadgeTag extends AbstractTagRenderer {
    private ServerBadge[] badges;

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public void begin(EntitySnapshot snapshot) {
        super.begin(snapshot);
        this.badges = null;
        if (!snapshot.has(EntityExtraKeys.GAME_USER)) {
            return;
        }
        GameUserSnapshot userSnapshot = (GameUserSnapshot) snapshot.get(EntityExtraKeys.GAME_USER);
        ServerBadge[] serverBadges = userSnapshot.serverBadges();
        if (serverBadges == null || serverBadges.length == 0) {
            return;
        }
        this.badges = serverBadges;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public void render(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot snapshot) {
        if (this.badges == null) {
            return;
        }
        int xOffset = 0;
        boolean first = true;
        for (ServerBadge badge : this.badges) {
            if (badge.isVisible()) {
                Icon icon = badge.icon();
                if (icon != null) {
                    if (!first) {
                        xOffset += 2;
                    }
                    submitBadgeAt(stack, submissionCollector, snapshot, badge, xOffset, 0);
                    xOffset += badge.width();
                    first = false;
                }
            }
        }
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public boolean isVisible() {
        if (!this.snapshot.has(EntityExtraKeys.GAME_USER)) {
            return false;
        }
        GameUserSnapshot gameUserSnapshot = (GameUserSnapshot) this.snapshot.get(EntityExtraKeys.GAME_USER);
        ServerBadge[] serverBadges = gameUserSnapshot.serverBadges();
        return serverBadges != null && serverBadges.length > 0;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getWidth() {
        int width = 0;
        boolean first = true;
        for (ServerBadge badge : this.badges) {
            if (badge.isVisible()) {
                Icon icon = badge.icon();
                if (icon != null) {
                    if (!first) {
                        width += 2;
                    }
                    width += badge.width();
                    first = false;
                }
            }
        }
        if (first) {
            return 0.0f;
        }
        return width + 1;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getHeight() {
        int height = 0;
        for (ServerBadge badge : this.badges) {
            if (badge.isVisible()) {
                Icon icon = badge.icon();
                if (icon != null) {
                    height = Math.max(height, badge.height());
                }
            }
        }
        return height;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getScale() {
        return 1.0f;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public boolean shouldCenterName() {
        return true;
    }

    private void submitBadgeAt(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot snapshot, ServerBadge badge, int x, int y) {
        Icon icon = badge.icon();
        int color = badge.color().getRGB();
        int width = badge.width();
        int height = badge.height();
        boolean standing = !isDiscrete(snapshot);
        if (standing) {
            submissionCollector.submitIcon(stack, icon, IconSubmission.DisplayMode.SEE_THROUGH, x, y, width, height, ColorFormat.ARGB32.withAlpha(color, 64));
            submissionCollector.submitIcon(stack, icon, IconSubmission.DisplayMode.NORMAL, x, y, width, height, color);
        } else {
            submissionCollector.submitIcon(stack, icon, IconSubmission.DisplayMode.NORMAL, x, y, width, height, ColorFormat.ARGB32.withAlpha(color, 64));
        }
    }
}
