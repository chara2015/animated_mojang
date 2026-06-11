package net.labymod.core.main.user.serverfeature.subtitle;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.serverapi.core.model.display.Subtitle;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/subtitle/SubtitleComponent.class */
public class SubtitleComponent {
    private final Subtitle subtitle;
    private final Component component;
    private final RenderableComponent renderableComponent;

    public SubtitleComponent(Subtitle subtitle) {
        this.subtitle = subtitle;
        this.component = Laby.references().labyModProtocolService().mapComponent(subtitle.getText());
        this.renderableComponent = RenderableComponent.of(this.component);
    }

    public Subtitle getSubtitle() {
        return this.subtitle;
    }

    @Nullable
    public Component getComponent() {
        return this.component;
    }

    @Nullable
    public RenderableComponent getRenderableComponent() {
        return this.renderableComponent;
    }
}
