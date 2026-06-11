package net.labymod.api.client.entity.player.tag.tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/tags/ComponentNameTag.class */
public abstract class ComponentNameTag extends AbstractNameTag {
    protected static final int DEFAULT_BACKGROUND_COLOR = ColorFormat.ARGB32.pack(0.0f, 0.0f, 0.0f, 0.25f);
    protected static final int DEFAULT_TEXT_COLOR = ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, 1.0f);
    protected static final int DEFAULT_TEXT_COLOR_SEE_THROUGH = ColorFormat.ARGB32.withAlpha(DEFAULT_TEXT_COLOR, 128);
    protected FontRenderer fontRenderer;
    private List<Component> components;
    private float width;
    private float height;

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public void begin(EntitySnapshot snapshot) {
        super.begin(snapshot);
        this.fontRenderer = Laby.references().textRendererProvider().getRenderer().getCurrent();
        this.components = Collections.unmodifiableList(buildComponents(snapshot));
        this.width = calculateWidth(this.components);
        this.height = calculateHeight(this.components);
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public void render(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot snapshot) {
        int size = this.components.size();
        if (size > 1) {
            renderMultiple(stack, submissionCollector, snapshot, this.components);
        } else {
            renderSingle(stack, submissionCollector, snapshot, (Component) this.components.getFirst());
        }
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public boolean isVisible() {
        return !this.components.isEmpty();
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getWidth() {
        return this.width;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getHeight() {
        return this.height;
    }

    protected void renderSingle(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot snapshot, Component component) {
        submitText(stack, submissionCollector, snapshot, component, 0.0f, 0.0f);
    }

    protected void renderMultiple(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot snapshot, List<Component> components) {
        float yOffset = 0.0f;
        for (Component component : components) {
            submitText(stack, submissionCollector, snapshot, component, 0.0f, yOffset);
            yOffset += this.fontRenderer.getLineHeight();
        }
    }

    protected void submitText(Stack stack, SubmissionCollector submissionCollector, EntitySnapshot snapshot, Component component, float xOffset, float yOffset) {
        int backgroundArgb = getBackgroundColor(snapshot);
        boolean standing = !isDiscrete(snapshot);
        if (standing) {
            submissionCollector.order(2).submitComponent(stack, component, xOffset, yOffset, DEFAULT_TEXT_COLOR_SEE_THROUGH, snapshot.lightCoords(), backgroundArgb, 8);
            submissionCollector.order(3).submitComponent(stack, component, xOffset, yOffset, DEFAULT_TEXT_COLOR, snapshot.lightCoords(), 0, 4);
        } else {
            submissionCollector.order(3).submitComponent(stack, component, xOffset, yOffset, DEFAULT_TEXT_COLOR_SEE_THROUGH, snapshot.lightCoords(), backgroundArgb, 4);
        }
    }

    protected int getBackgroundColor(EntitySnapshot snapshot) {
        return Laby.labyAPI().minecraft().options().getBackgroundColorWithOpacity(DEFAULT_BACKGROUND_COLOR);
    }

    @NotNull
    protected List<Component> buildComponents(EntitySnapshot snapshot) {
        return Collections.emptyList();
    }

    protected float calculateWidth(Collection<Component> components) {
        float maxWidth = 0.0f;
        for (Component component : components) {
            float width = this.fontRenderer.getWidth(component);
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    protected float calculateHeight(Collection<Component> components) {
        return this.fontRenderer.getLineHeight() * components.size();
    }
}
