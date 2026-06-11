package net.labymod.v1_8_9.mixins.cosmetics;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/cosmetics/MixinCustomItemRenderer.class */
@Mixin({bjh.class})
public class MixinCustomItemRenderer {
    private pr labyMod$livingEntity;
    private b labyMod$transformType = b.f;

    @Inject(method = {"renderItemModelForEntity"}, at = {@At("HEAD")})
    private void labyMod$beginRenderStatic(zx lvt_1_1_, pr lvt_2_1_, b lvt_3_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = lvt_2_1_;
        this.labyMod$transformType = lvt_3_1_;
    }

    @Inject(method = {"renderItemAndEffectIntoGUI"}, at = {@At("HEAD")})
    private void labyMod$beginRenderGui(zx lvt_1_1_, int lvt_2_1_, int lvt_3_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = ave.A().h;
        this.labyMod$transformType = b.e;
    }

    @Inject(method = {"renderItemAndEffectIntoGUI"}, at = {@At("TAIL")})
    private void labyMod$endRenderGui(zx lvt_1_1_, int lvt_2_1_, int lvt_3_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = b.f;
    }

    @Inject(method = {"renderItemModelForEntity"}, at = {@At("TAIL")})
    private void labyMod$endRenderStatic(zx lvt_1_1_, pr lvt_2_1_, b lvt_3_1_, CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = b.f;
    }

    @Inject(method = {"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(zx itemStack, boq model, CallbackInfo ci) {
        if (this.labyMod$transformType == b.f) {
            return;
        }
        bew bewVar = this.labyMod$livingEntity;
        if (bewVar == null) {
            bewVar = ave.A().h;
        }
        ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (itemStack != null && (bewVar instanceof Player)) {
            Player player = (Player) bewVar;
            bln blnVarA = ave.A().af().a(bewVar);
            if (blnVarA instanceof bln) {
                bln playerRenderer = blnVarA;
                PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(VersionedStackProvider.DEFAULT_STACK, apiItemStack, MinecraftUtil.fromMinecraft(this.labyMod$transformType), player, playerRenderer.g(), Laby.references().renderEnvironmentContext().getPackedLight());
                if (event.isCancelled()) {
                    bfl.F();
                    ci.cancel();
                }
            }
        }
    }
}
