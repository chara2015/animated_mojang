package net.labymod.v1_12_2.mixins.cosmetics;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/cosmetics/MixinRenderManager.class */
@Mixin({bzw.class})
public class MixinRenderManager {
    private vp labyMod$livingEntity;
    private b labyMod$transformType = b.h;

    @Inject(method = {"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(aip lvt_1_1_, vp lvt_2_1_, b lvt_3_1_, boolean lvt_4_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = lvt_2_1_;
        this.labyMod$transformType = lvt_3_1_;
    }

    @Inject(method = {"renderItemAndEffectIntoGUI(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;II)V"}, at = {@At("HEAD")})
    private void labyMod$beginRenderGui(vp lvt_1_1_, aip lvt_2_1_, int lvt_3_1_, int lvt_4_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = lvt_1_1_;
        this.labyMod$transformType = b.g;
    }

    @Inject(method = {"renderItemAndEffectIntoGUI(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;II)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderGui(vp lvt_1_1_, aip lvt_2_1_, int lvt_3_1_, int lvt_4_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = b.h;
    }

    @Inject(method = {"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(aip itemStack, vp livingEntity, b transformType, boolean lvt_4_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = b.h;
    }

    @Inject(method = {"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(aip itemStack, cfy model, CallbackInfo ci) {
        if (this.labyMod$transformType == b.h) {
            return;
        }
        bud budVar = this.labyMod$livingEntity;
        if (budVar == null) {
            budVar = bib.z().h;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && (budVar instanceof Player)) {
            Player player = (Player) budVar;
            cct cctVarA = bib.z().ac().a(budVar);
            if (cctVarA instanceof cct) {
                cct playerRenderer = cctVarA;
                PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(VersionedStackProvider.DEFAULT_STACK, apiItemStack, MinecraftUtil.fromMinecraft(this.labyMod$transformType), player, playerRenderer.h(), Laby.references().renderEnvironmentContext().getPackedLight());
                if (event.isCancelled()) {
                    bus.H();
                    ci.cancel();
                }
            }
        }
    }
}
