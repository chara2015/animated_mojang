package net.labymod.core.main.user.shop.bridge;

import java.util.function.Supplier;
import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.debug.DebugFlags;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.renderer.GeometrySubmitter;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.core.client.render.model.GeometryTestModel;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.main.LabyMod;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/ShopLayerVisualizer.class */
public class ShopLayerVisualizer {
    private static final AssetId PASS_ID = AssetId.of("labymod", "entity_in_ui");
    private final ShopItemLayer layer;
    private final GeometrySubmitter submitter;
    private final RenderEnvironmentContext renderEnvironmentContext;
    private final Laby3D laby3D;
    private final TestShowcase testShowcase;

    public ShopLayerVisualizer() {
        DefaultReferenceStorage references = LabyMod.references();
        this.layer = references.shopItemLayer();
        this.submitter = references.geometrySubmitter();
        this.renderEnvironmentContext = references.renderEnvironmentContext();
        this.laby3D = references.laby3D();
        this.testShowcase = new TestShowcase();
    }

    public void renderLayerForPlayer(Stack stack, Player player, PlayerModel model, int lightCoords, float partialTicks) {
        GeometrySubmitter.Type prevType = this.submitter.getType();
        boolean screenContext = this.renderEnvironmentContext.isScreenContext();
        if (screenContext) {
            this.submitter.setType(GeometrySubmitter.Type.ENTITY_IN_UI);
        }
        FrameProfiler.push((Supplier<String>) () -> {
            return "player_layer_cosmetics(" + player.getName() + ")";
        });
        this.layer.render(stack, player, model, lightCoords, partialTicks);
        FrameProfiler.pop();
        this.testShowcase.renderModel(this.submitter, stack, lightCoords);
        if (screenContext) {
            CommandBuffer cmd = this.laby3D.renderDevice().createCommandBuffer(1);
            try {
                cmd.beginPass(this.laby3D.resolveDrawRenderTarget(), LoadOp.LOAD);
                this.submitter.render(cmd, true);
                cmd.endPass();
                cmd.submit();
                if (cmd != null) {
                    cmd.close();
                }
                this.submitter.setType(prevType);
            } catch (Throwable th) {
                if (cmd != null) {
                    try {
                        cmd.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/ShopLayerVisualizer$TestShowcase.class */
    public static class TestShowcase {
        private Model model;
        private ModelInstance<?> modelInstance;

        public void renderModel(GeometrySubmitter submitter, Stack stack, int lightCoords) {
            if (!DebugFlags.TEST_CUSTOM_GEOMETRY_MESHES) {
                return;
            }
            stack.push();
            stack.translate(0.0f, -1.5f, 0.0f);
            ensureModel();
            submitter.submitModel(null, this.modelInstance, stack, RenderStates.TRANSLUCENT_EMOTES, TextureBindingSet.builder().setTexture(0, Textures.WHITE).build(), lightCoords, RenderEnvironmentContext.NO_OVERLAY);
            stack.pop();
        }

        private void ensureModel() {
            if (this.model == null) {
                this.model = GeometryTestModel.create();
                this.modelInstance = new ModelInstance.Simple(this.model);
            }
        }
    }
}
