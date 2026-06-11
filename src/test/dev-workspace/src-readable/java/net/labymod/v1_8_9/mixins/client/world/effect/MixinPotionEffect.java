package net.labymod.v1_8_9.mixins.client.world.effect;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/world/effect/MixinPotionEffect.class */
@Mixin({pf.class})
@Implements({@Interface(iface = PotionEffect.class, prefix = "mobEffect$", remap = Interface.Remap.NONE)})
public abstract class MixinPotionEffect implements PotionEffect {
    private static final jy INVENTORY_BACKGROUND = new jy("textures/gui/container/inventory.png");

    @Shadow
    private int b;

    @Shadow
    private int c;

    @Shadow
    private int d;

    @Shadow
    public abstract boolean h();

    @Intrinsic
    public int mobEffect$getDuration() {
        return this.c;
    }

    @Intrinsic
    public int mobEffect$getAmplifier() {
        return this.d;
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public String getTranslationKey() {
        pe potion = pe.a[this.b];
        return potion.a();
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public Icon getIcon() {
        pe potion = pe.a[this.b];
        int index = potion.f();
        return Icon.sprite(Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createMinecraft("textures/gui/container/inventory.png"), (index % 8) * 18, 198 + ((index / 8) * 18), 18, 18, 256, 256);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public void renderIcon(ScreenContext context, int x, int y, int width, int height) {
        pe potion = pe.a[this.b];
        if (!potion.e()) {
            return;
        }
        bfl.f();
        bfl.l();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        ave.A().P().a(INVENTORY_BACKGROUND);
        int index = potion.f();
        MinecraftUtil.drawTexturedModalRect(x, y, (index % 8) * 18, 198 + ((index / 8) * 18), 18, 18);
    }

    @Override // net.labymod.api.client.world.effect.PotionEffect
    public boolean hasMaxDuration() {
        return h();
    }
}
