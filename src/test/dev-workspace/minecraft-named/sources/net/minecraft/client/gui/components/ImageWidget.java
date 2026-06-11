package net.minecraft.client.gui.components;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/ImageWidget.class */
public abstract class ImageWidget extends AbstractWidget {
    public abstract void updateResource(Identifier identifier);

    ImageWidget(int $$0, int $$1, int $$2, int $$3) {
        super($$0, $$1, $$2, $$3, CommonComponents.EMPTY);
    }

    public static ImageWidget texture(int $$0, int $$1, Identifier $$2, int $$3, int $$4) {
        return new Texture(0, 0, $$0, $$1, $$2, $$3, $$4);
    }

    public static ImageWidget sprite(int $$0, int $$1, Identifier $$2) {
        return new Sprite(0, 0, $$0, $$1, $$2);
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget
    protected void updateWidgetNarration(NarrationElementOutput $$0) {
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget
    public void playDownSound(SoundManager $$0) {
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.narration.NarratableEntry
    public boolean isActive() {
        return false;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
        return null;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/ImageWidget$Sprite.class */
    static class Sprite extends ImageWidget {
        private Identifier sprite;

        public Sprite(int $$0, int $$1, int $$2, int $$3, Identifier $$4) {
            super($$0, $$1, $$2, $$3);
            this.sprite = $$4;
        }

        @Override // net.minecraft.client.gui.components.AbstractWidget
        public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
            $$0.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, getX(), getY(), getWidth(), getHeight());
        }

        @Override // net.minecraft.client.gui.components.ImageWidget
        public void updateResource(Identifier $$0) {
            this.sprite = $$0;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/ImageWidget$Texture.class */
    static class Texture extends ImageWidget {
        private Identifier texture;
        private final int textureWidth;
        private final int textureHeight;

        public Texture(int $$0, int $$1, int $$2, int $$3, Identifier $$4, int $$5, int $$6) {
            super($$0, $$1, $$2, $$3);
            this.texture = $$4;
            this.textureWidth = $$5;
            this.textureHeight = $$6;
        }

        @Override // net.minecraft.client.gui.components.AbstractWidget
        protected void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
            $$0.blit(RenderPipelines.GUI_TEXTURED, this.texture, getX(), getY(), 0.0f, 0.0f, getWidth(), getHeight(), this.textureWidth, this.textureHeight);
        }

        @Override // net.minecraft.client.gui.components.ImageWidget
        public void updateResource(Identifier $$0) {
            this.texture = $$0;
        }
    }
}
