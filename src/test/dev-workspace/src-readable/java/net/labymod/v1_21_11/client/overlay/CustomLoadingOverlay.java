package net.labymod.v1_21_11.client.overlay;

import com.mojang.blaze3d.platform.Window;
import java.util.Optional;
import java.util.function.Consumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.server.packs.resources.ReloadInstance;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/overlay/CustomLoadingOverlay.class */
public class CustomLoadingOverlay extends LoadingOverlay {
    private final Minecraft minecraft;
    private final ReloadInstance reload;
    private final Consumer<Optional<Throwable>> onFinish;
    private final AbstractBootLogoRenderer loadingRenderer;
    private final DynamicBackgroundController caveRenderer;

    public CustomLoadingOverlay(Minecraft minecraft, ReloadInstance reload, Consumer<Optional<Throwable>> onFinish, boolean fadeIn) {
        super(minecraft, reload, onFinish, fadeIn);
        this.minecraft = minecraft;
        this.reload = reload;
        this.onFinish = onFinish;
        this.loadingRenderer = LabyMod.references().bootLogoController().renderer();
        this.caveRenderer = LabyMod.references().dynamicBackgroundController();
        this.loadingRenderer.initialize();
        this.loadingRenderer.preloadResources();
    }

    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        Window window = this.minecraft.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        float progress = this.reload.getActualProgress();
        this.loadingRenderer.updateProgress(progress, true);
        Stack apiStack = Stack.create(JomlMatrix3x2fStackProvider.fromGuiGraphics(gui));
        LabyMod.references().renderEnvironmentContext().screenContext().runInContext(apiStack, mouseX, mouseY, partialTicks, context -> {
            this.loadingRenderer.render(context, 0.0f, 0.0f, width, height);
        });
        if (this.reload.isDone()) {
            this.minecraft.setOverlay((Overlay) null);
            try {
                this.reload.checkExceptions();
                this.onFinish.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.onFinish.accept(Optional.of(throwable));
            }
            if (this.minecraft.screen != null) {
                this.minecraft.screen.init(width, height);
            }
            this.caveRenderer.playOpening();
        }
    }
}
