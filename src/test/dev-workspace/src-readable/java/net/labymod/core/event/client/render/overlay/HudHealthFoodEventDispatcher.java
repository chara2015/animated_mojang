package net.labymod.core.event.client.render.overlay;

import java.util.concurrent.ThreadLocalRandom;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.food.FoodData;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/overlay/HudHealthFoodEventDispatcher.class */
public final class HudHealthFoodEventDispatcher {
    private static boolean foodCancelled;
    private static boolean foodPreFired;
    private static int cachedFoodX;
    private static int cachedFoodY;
    private static boolean heartsCancelled;
    private static boolean heartsPreFired;
    private static int cachedHeartsX;
    private static int cachedHeartsY;
    private static int cachedHeartsRowHeight;
    private static int cachedHeartsOffsetHeartIndex;
    private static float cachedHeartsMaxHealth;
    private static int cachedHeartsPlayerHealth;
    private static int cachedHeartsDisplayHealth;
    private static int cachedHeartsAbsorption;
    private static boolean cachedHeartsRenderHighlight;
    private static boolean cachedHeartsHardcore;

    private HudHealthFoodEventDispatcher() {
    }

    public static boolean dispatchFoodPre(ScreenContext context, Player player, int x, int y, int tickCount) {
        FoodData foodData = player.foodData();
        int foodLevel = foodData.getFoodLevel();
        float saturation = foodData.getSaturationLevel();
        int jitter = computeFoodJitter(saturation, foodLevel, tickCount);
        boolean cancelled = FoodBarRenderEventCaller.callPre(context, player, x, y, jitter, foodLevel, saturation);
        foodCancelled = cancelled;
        foodPreFired = true;
        cachedFoodX = x;
        cachedFoodY = y;
        return cancelled;
    }

    public static void dispatchFoodPost(ScreenContext context, Player player) {
        dispatchFoodPost(context, player, cachedFoodX, cachedFoodY);
    }

    public static void dispatchFoodPost(ScreenContext context, Player player, int x, int y) {
        boolean preFired = foodPreFired;
        boolean cancelled = foodCancelled;
        foodPreFired = false;
        foodCancelled = false;
        if (!preFired || cancelled) {
            return;
        }
        FoodData foodData = player.foodData();
        FoodBarRenderEventCaller.callPost(context, player, x, y, 0, foodData.getFoodLevel(), foodData.getSaturationLevel());
    }

    public static boolean shouldSkipFoodRender() {
        return foodCancelled;
    }

    public static boolean dispatchHeartsPre(ScreenContext context, Player player, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, boolean hardcore) {
        int lowHealthJitter = computeLowHealthJitter(playerHealth, absorptionAmount);
        boolean cancelled = HeartBarRenderEventCaller.callPre(context, player, x, y, rowHeight, offsetHeartIndex, lowHealthJitter, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        heartsCancelled = cancelled;
        heartsPreFired = true;
        cachedHeartsX = x;
        cachedHeartsY = y;
        cachedHeartsRowHeight = rowHeight;
        cachedHeartsOffsetHeartIndex = offsetHeartIndex;
        cachedHeartsMaxHealth = maxHealth;
        cachedHeartsPlayerHealth = playerHealth;
        cachedHeartsDisplayHealth = displayHealth;
        cachedHeartsAbsorption = absorptionAmount;
        cachedHeartsRenderHighlight = renderHighlight;
        cachedHeartsHardcore = hardcore;
        return cancelled;
    }

    public static void dispatchHeartsPost(ScreenContext context, Player player, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, boolean hardcore) {
        boolean preFired = heartsPreFired;
        boolean cancelled = heartsCancelled;
        heartsPreFired = false;
        heartsCancelled = false;
        if (!preFired || cancelled) {
            return;
        }
        HeartBarRenderEventCaller.callPost(context, player, x, y, rowHeight, offsetHeartIndex, 0, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
    }

    public static void dispatchHeartsPost(ScreenContext context, Player player) {
        dispatchHeartsPost(context, player, cachedHeartsX, cachedHeartsY, cachedHeartsRowHeight, cachedHeartsOffsetHeartIndex, cachedHeartsMaxHealth, cachedHeartsPlayerHealth, cachedHeartsDisplayHealth, cachedHeartsAbsorption, cachedHeartsRenderHighlight, cachedHeartsHardcore);
    }

    public static boolean shouldSkipHeartRender() {
        return heartsCancelled;
    }

    public static int adjustVehicleHeartRows(int rows) {
        HudWidgetDropzone attachedDropzone;
        HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        if (saturation != null && saturation.isEnabled() && saturation.isVisibleInGame() && (attachedDropzone = saturation.getAttachedDropzone()) != null && "saturation".equals(attachedDropzone.getId())) {
            return rows + 1;
        }
        return rows;
    }

    private static int computeFoodJitter(float saturation, int foodLevel, int tickCount) {
        if (saturation > 0.0f || tickCount % ((foodLevel * 3) + 1) != 0) {
            return 0;
        }
        return ThreadLocalRandom.current().nextInt(3) - 1;
    }

    private static int computeLowHealthJitter(int playerHealth, int absorptionAmount) {
        if (playerHealth + absorptionAmount > 4) {
            return 0;
        }
        return ThreadLocalRandom.current().nextInt(2);
    }
}
