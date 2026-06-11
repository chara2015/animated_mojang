package net.minecraft.world.effect;

import net.minecraft.ChatFormatting;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/effect/MobEffectCategory.class */
public enum MobEffectCategory {
    BENEFICIAL(ChatFormatting.BLUE),
    HARMFUL(ChatFormatting.RED),
    NEUTRAL(ChatFormatting.BLUE);

    private final ChatFormatting tooltipFormatting;

    MobEffectCategory(ChatFormatting $$0) {
        this.tooltipFormatting = $$0;
    }

    public ChatFormatting getTooltipFormatting() {
        return this.tooltipFormatting;
    }
}
