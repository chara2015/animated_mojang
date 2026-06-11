package net.labymod.api.client.entity.player.tag;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.renderer.PositionRenderer;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.entity.player.tag.renderer.stack.HorizontalStackRenderer;
import net.labymod.api.client.entity.player.tag.renderer.stack.VerticalStackRenderer;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/PositionType.class */
public enum PositionType {
    ABOVE_NAME(new VerticalStackRenderer() { // from class: net.labymod.api.client.entity.player.tag.renderer.positions.AboveNameRenderer
        @Override // net.labymod.api.client.entity.player.tag.renderer.stack.VerticalStackRenderer
        protected float getEntryYOffset() {
            Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
            Metadata metadata = currentTheme.metadata();
            return ((Float) metadata.getSupplied(DefaultThemeVariables.ABOVE_NAME_ENTRY_Y_OFFSET, Float.valueOf(0.0f))).floatValue();
        }
    }, 1),
    BELOW_NAME(new VerticalStackRenderer() { // from class: net.labymod.api.client.entity.player.tag.renderer.positions.BelowNameRenderer
        @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer
        protected boolean keepUsernamePosition() {
            return false;
        }

        @Override // net.labymod.api.client.entity.player.tag.renderer.stack.VerticalStackRenderer
        protected float getEntryYOffset() {
            Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
            Metadata metadata = currentTheme.metadata();
            return ((Float) metadata.get(DefaultThemeVariables.BELOW_NAME_ENTRY_Y_OFFSET, Float.valueOf(0.0f))).floatValue();
        }
    }, 0),
    RIGHT_TO_NAME(new HorizontalStackRenderer() { // from class: net.labymod.api.client.entity.player.tag.renderer.positions.RightToNameRenderer
        {
            HorizontalStackRenderer.HorizontalPosition horizontalPosition = HorizontalStackRenderer.HorizontalPosition.RIGHT;
        }
    }, 1),
    LEFT_TO_NAME(new HorizontalStackRenderer() { // from class: net.labymod.api.client.entity.player.tag.renderer.positions.LeftToNameRenderer
        {
            HorizontalStackRenderer.HorizontalPosition horizontalPosition = HorizontalStackRenderer.HorizontalPosition.LEFT;
        }

        @Override // net.labymod.api.client.entity.player.tag.renderer.stack.HorizontalStackRenderer, net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer, net.labymod.api.client.entity.player.tag.renderer.PositionRenderer
        public void render(Stack stack, EntitySnapshot snapshot, SubmissionCollector submissionCollector, Registry<TagRenderer> tags, float usernameWidth, TagType tagType) {
            super.render(stack, snapshot, submissionCollector, tags, usernameWidth, tagType);
        }
    }, 1);

    private final PositionRenderer renderer;
    private final int priority;

    PositionType(PositionRenderer renderer, int priority) {
        this.renderer = renderer;
        this.priority = priority;
    }

    public PositionRenderer renderer() {
        return this.renderer;
    }

    public int getPriority() {
        return this.priority;
    }

    public boolean isHorizontal() {
        return this == LEFT_TO_NAME || this == RIGHT_TO_NAME;
    }
}
