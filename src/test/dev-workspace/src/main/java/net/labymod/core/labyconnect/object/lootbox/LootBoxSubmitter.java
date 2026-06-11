package net.labymod.core.labyconnect.object.lootbox;

import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.client.world.object.submit.WorldObjectSubmitter;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.LevelMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.laby3d.render.queue.submissions.IconSubmission;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxShopItem;
import net.labymod.core.labyconnect.object.lootbox.content.PoolCategory;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/LootBoxSubmitter.class */
public final class LootBoxSubmitter extends WorldObjectSubmitter<LootBoxObject, LootBoxSnapshot> {
    private static final int GRADIENT_TOP_COLOR = -11513776;
    private final ComponentRenderer componentRenderer = Laby.references().componentRenderer();

    @Override // net.labymod.api.client.world.object.submit.WorldObjectSubmitter
    @NotNull
    public LootBoxSnapshot createSnapshot(@NotNull LootBoxObject lootBox, double x, double y, double z, int lightCoords, @NotNull CameraSnapshot camera) {
        AnimationController controller = lootBox.getAnimationController();
        long animationProgress = controller != null ? controller.getProgress() : 0L;
        boolean wasPriceRevealed = lootBox.isPriceRevealed();
        boolean shouldRevealNow = !wasPriceRevealed && animationProgress > LootBoxContent.REVEAL_TIME;
        if (shouldRevealNow) {
            lootBox.markPriceRevealed();
        }
        return new LootBoxSnapshot(x, y, z, lightCoords, lootBox.getOwner() == Laby.labyAPI().minecraft().getClientPlayer(), lootBox.getRotation(), lootBox.getContent(), lootBox.getModel(), lootBox.getTextureLocation(), controller, animationProgress, wasPriceRevealed || shouldRevealNow, shouldRevealNow, camera.getYaw(), camera.getPitch());
    }

    @Override // net.labymod.api.client.world.object.submit.WorldObjectSubmitter
    public void submit(@NotNull Stack stack, @NotNull SubmissionCollector collector, @NotNull LootBoxSnapshot snapshot) {
        if (!snapshot.hasModel()) {
            return;
        }
        LabyAPI api = Laby.labyAPI();
        GFXRenderPipeline pipeline = api.gfxRenderPipeline();
        RenderEnvironmentContext envContext = pipeline.renderEnvironmentContext();
        Model model = snapshot.model();
        AnimationController controller = snapshot.animationController();
        if (controller != null) {
            controller.applyAnimation(model, new String[0]);
        }
        Map<String, ModelPart> itemParts = model.getPartsStartingWith("item");
        for (ModelPart part : itemParts.values()) {
            part.setVisible(false);
        }
        boolean hasSpin = model.getPart("spin") != null;
        envContext.setPackedLight(snapshot.lightCoords());
        stack.push();
        stack.translate(snapshot.x(), snapshot.y(), snapshot.z());
        stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        stack.push();
        stack.rotate(snapshot.rotation(), 0.0f, 1.0f, 0.0f);
        Material material = LevelMaterial.builder(RenderStates.TRANSLUCENT_EMOTES).setTexture(0, snapshot.textureLocation()).useLightmap().useOverlay().build();
        collector.submitModel(stack, model, material, snapshot.lightCoords());
        for (ModelPart part2 : itemParts.values()) {
            part2.setVisible(true);
        }
        for (Map.Entry<String, ModelPart> entry : itemParts.entrySet()) {
            String name = entry.getKey();
            ModelPart part3 = entry.getValue();
            Transformation animationTransformation = part3.getAnimationTransformation();
            FloatVector3 animationScale = animationTransformation.getScale();
            if (animationScale.getX() != 0.0f || animationScale.getY() != 0.0f || animationScale.getZ() != 0.0f) {
                try {
                    int index = Integer.parseInt(name.substring(4)) - 1;
                    Matrix4f worldTransform = model.getPartWorldTransform(name);
                    if (worldTransform != null) {
                        stack.push();
                        if (hasSpin) {
                            stack.rotate(snapshot.cameraYaw() - snapshot.rotation(), 0.0f, 1.0f, 0.0f);
                            stack.rotate((-snapshot.cameraPitch()) / 4.0f, 1.0f, 0.0f, 0.0f);
                        }
                        stack.mul(worldTransform);
                        submitShopItem(stack, collector, index, snapshot);
                        stack.pop();
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        stack.pop();
        if (snapshot.shouldRevealPrice()) {
            onPriceReveal(snapshot);
        }
        stack.pop();
    }

    private void submitShopItem(Stack stack, SubmissionCollector collector, int index, LootBoxSnapshot snapshot) {
        LootBoxContent content = snapshot.content();
        List<LootBoxShopItem> pool = content.pool();
        int poolSize = pool.size();
        if (index < 0 || poolSize <= index) {
            return;
        }
        LootBoxShopItem item = pool.get(index);
        Integer itemColor = item.getColor();
        PoolCategory category = item.category();
        float iconSize = 32.0f * 0.0109375f;
        boolean isPrice = index == 29;
        long timeSinceReveal = snapshot.animationProgress() - LootBoxContent.REVEAL_TIME;
        if (isPrice && snapshot.priceRevealed()) {
            float fadeIn = MathHelper.clamp(timeSinceReveal / 200.0f, 0.0f, 1.0f);
            stack.push();
            if (!PlatformEnvironment.isAncientOpenGL()) {
                stack.scale(1.0f, 1.0f, -1.0f);
            }
            Component nameComponent = Component.text(item.name(), category.getTextColor());
            float nameWidth = this.componentRenderer.width(nameComponent);
            float nameScale = 0.0109375f * fadeIn;
            stack.push();
            stack.scale(nameScale, nameScale, 1.0f);
            collector.submitComponent(stack, nameComponent, (-nameWidth) / 2.0f, (((-iconSize) / 2.0f) - ((13.0f * 0.0109375f) * fadeIn)) / nameScale, -1, RenderEnvironmentContext.FULL_BRIGHT, 0, 5);
            stack.pop();
            if (content.hasDuration()) {
                Component durationComponent = content.getDurationComponent();
                float durationWidth = this.componentRenderer.width(durationComponent);
                float durationScale = (0.0109375f * fadeIn) / 4.0f;
                stack.push();
                stack.scale(durationScale, durationScale, 1.0f);
                collector.submitComponent(stack, durationComponent, (-durationWidth) / 2.0f, (((-iconSize) / 2.0f) - ((3.0f * 0.0109375f) * fadeIn)) / durationScale, -1, RenderEnvironmentContext.FULL_BRIGHT, 0, 4);
                stack.pop();
            }
            stack.pop();
        }
        int color = itemColor == null ? category.getColor() : itemColor.intValue();
        collector.submitRectangle(stack, (-iconSize) / 2.0f, (-iconSize) / 2.0f, iconSize, iconSize, color, RenderEnvironmentContext.FULL_BRIGHT);
        stack.translate(0.0f, 0.0f, -6.25E-4f);
        float gradientX = ((-iconSize) / 2.0f) + (0.0109375f / 2.0f);
        float gradientY = ((-iconSize) / 2.0f) + (0.0109375f / 2.0f);
        float gradientSize = iconSize - 0.0109375f;
        collector.submitGradientRectangle(stack, GradientDirection.TOP_TO_BOTTOM, gradientX, gradientY, gradientSize, gradientSize, GRADIENT_TOP_COLOR, color, RenderEnvironmentContext.FULL_BRIGHT);
        stack.translate(0.0f, 0.0f, -6.25E-4f);
        Icon icon = item.getIcon();
        int width = icon.getResolutionWidth();
        int height = icon.getResolutionHeight();
        float horizontalOffset = 0.0f;
        float verticalOffset = 0.0f;
        if (height > width) {
            verticalOffset = (iconSize / height) * (height - width);
        } else {
            horizontalOffset = (iconSize / width) * (width - height);
        }
        collector.submitIcon(stack, icon, IconSubmission.DisplayMode.NORMAL, ((-iconSize) / 2.0f) + (verticalOffset / 2.0f) + (0.0109375f / 2.0f), ((-iconSize) / 2.0f) + (horizontalOffset / 2.0f) + (0.0109375f / 2.0f), (iconSize - verticalOffset) - 0.0109375f, (iconSize - horizontalOffset) - 0.0109375f, -1);
    }

    private void onPriceReveal(LootBoxSnapshot snapshot) {
        if (snapshot.isClientPlayer()) {
            LootBoxShopItem price = snapshot.content().getPriceShopItem();
            Laby.labyAPI().minecraft().sounds().playSound(price.category().getSound(), 0.05f, 1.0f, new DoubleVector3(snapshot.x(), snapshot.y(), snapshot.z()));
        }
    }
}
