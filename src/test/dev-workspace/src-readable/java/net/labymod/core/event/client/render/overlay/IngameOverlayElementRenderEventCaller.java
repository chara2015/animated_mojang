package net.labymod.core.event.client.render.overlay;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/overlay/IngameOverlayElementRenderEventCaller.class */
public final class IngameOverlayElementRenderEventCaller {
    public static boolean callAttackIndicatorPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.ATTACK_INDICATOR);
    }

    public static void callAttackIndicatorPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.ATTACK_INDICATOR);
    }

    public static boolean callBossBarPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.BOSS_BAR);
    }

    public static void callBossBarPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.BOSS_BAR);
    }

    public static boolean callCrossHairPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR);
    }

    public static void callCrossHairPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR);
    }

    public static boolean callOffhandTexturePre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE);
    }

    public static void callOffhandTexturePost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE);
    }

    public static boolean callOffhandItemPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM);
    }

    public static void callOffhandItemPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM);
    }

    public static boolean callPotionEffectsPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.POTION_EFFECTS);
    }

    public static void callPotionEffectsPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.POTION_EFFECTS);
    }

    public static boolean callTitlePre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.TITLE);
    }

    public static void callTitlePost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.TITLE);
    }

    public static boolean callScoreboardPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.SCOREBOARD);
    }

    public static void callScoreboardPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.SCOREBOARD);
    }

    public static boolean callActionBarPre(ScreenContext screenContext) {
        return call(screenContext, Phase.PRE, IngameOverlayElementRenderEvent.OverlayElementType.ACTION_BAR);
    }

    public static void callActionBarPost(ScreenContext screenContext) {
        call(screenContext, Phase.POST, IngameOverlayElementRenderEvent.OverlayElementType.ACTION_BAR);
    }

    private static boolean call(ScreenContext screenContext, Phase phase, IngameOverlayElementRenderEvent.OverlayElementType type) {
        IngameOverlayElementRenderEvent event = (IngameOverlayElementRenderEvent) Laby.fireEvent(new IngameOverlayElementRenderEvent(type, screenContext, phase));
        return event.isCancelled();
    }
}
