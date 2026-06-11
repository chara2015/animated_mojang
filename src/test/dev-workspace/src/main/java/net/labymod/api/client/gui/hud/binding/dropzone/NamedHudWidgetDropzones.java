package net.labymod.api.client.gui.hud.binding.dropzone;

import net.labymod.api.client.gui.hud.binding.dropzone.zones.ActionBarWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.BossBarWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.CrosshairDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.DangerWarnerHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.DirectionHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ItemCounterHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ItemHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.SaturationHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ScoreboardHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.TitleWidgetDropzone;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/NamedHudWidgetDropzones.class */
public class NamedHudWidgetDropzones {
    public static final HudWidgetDropzone SCOREBOARD_LEFT = new ScoreboardHudWidgetDropzone(false);
    public static final HudWidgetDropzone SCOREBOARD_RIGHT = new ScoreboardHudWidgetDropzone(true);
    public static final HudWidgetDropzone SATURATION = new SaturationHudWidgetDropzone();
    public static final HudWidgetDropzone DIRECTION = new DirectionHudWidgetDropzone();
    public static final HudWidgetDropzone TITLE = new TitleWidgetDropzone();
    public static final HudWidgetDropzone ACTION_BAR = new ActionBarWidgetDropzone();
    public static final HudWidgetDropzone BOSS_BAR = new BossBarWidgetDropzone();
    public static final HudWidgetDropzone ITEM_COUNTER_LEFT = new ItemCounterHudWidgetDropzone(false);
    public static final HudWidgetDropzone ITEM_COUNTER_RIGHT = new ItemCounterHudWidgetDropzone(true);
    public static final HudWidgetDropzone ITEM_TOP_LEFT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.TOP_LEFT);
    public static final HudWidgetDropzone ITEM_TOP_RIGHT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.TOP_RIGHT);
    public static final HudWidgetDropzone ITEM_MIDDLE_LEFT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.MIDDLE_LEFT);
    public static final HudWidgetDropzone ITEM_MIDDLE_RIGHT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.MIDDLE_RIGHT);
    public static final HudWidgetDropzone ITEM_BOTTOM_LEFT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.BOTTOM_LEFT);
    public static final HudWidgetDropzone ITEM_BOTTOM_RIGHT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.BOTTOM_RIGHT);
    public static final HudWidgetDropzone CROSSHAIR_TOP = new CrosshairDropzone(true);
    public static final HudWidgetDropzone CROSSHAIR_BOTTOM = new CrosshairDropzone(false);
    public static final HudWidgetDropzone DANGER_WARNER = new DangerWarnerHudWidgetDropzone();
    public static final HudWidgetDropzone[] ITEMS = {ITEM_TOP_LEFT, ITEM_TOP_RIGHT, ITEM_MIDDLE_LEFT, ITEM_MIDDLE_RIGHT, ITEM_BOTTOM_LEFT, ITEM_BOTTOM_RIGHT};
}
