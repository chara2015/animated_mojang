package net.labymod.v1_17_1.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    private final GameMathMapper mathMapper = MathHelper.mapper();

    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        dvp minecraft = dvp.C();
        dql modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.a();
        modelViewStack.a((d) this.mathMapper.toMatrix4f(context.stack().getProvider().getPose()));
        RenderSystem.applyModelViewMatrix();
        esv itemRenderer = minecraft.ae();
        bqq mcItemStack = (bqq) CastUtil.cast(itemStack);
        itemRenderer.b(mcItemStack, x, y);
        if (decorate) {
            itemRenderer.a(dvp.C().h, mcItemStack, x, y);
        }
        modelViewStack.b();
        RenderSystem.applyModelViewMatrix();
    }
}
