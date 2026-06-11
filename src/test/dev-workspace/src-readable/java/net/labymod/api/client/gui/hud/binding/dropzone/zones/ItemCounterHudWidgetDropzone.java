package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import java.util.Locale;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.item.ItemHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.render.font.RenderableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/ItemCounterHudWidgetDropzone.class */
public class ItemCounterHudWidgetDropzone extends HotbarWidgetDropzone {
    private final boolean rightBound;

    /* JADX WARN: Illegal instructions before constructor call */
    public ItemCounterHudWidgetDropzone(boolean rightBound) {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[1];
        objArr[0] = rightBound ? "right" : "left";
        super(String.format(locale, "item_counter_%s", objArr), rightBound);
        this.rightBound = rightBound;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getX(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        float itemOffset = getItemOffset(renderer);
        float hotbarOffset = getOffset(renderer, hudWidgetSize);
        return renderer.getArea().getCenterX() + hotbarOffset + itemOffset;
    }

    private float getItemOffset(HudWidgetRendererAccessor renderer) {
        ItemHudWidget<?> hudWidget;
        float itemHudWidgetWidth = 0.0f;
        for (HudWidgetDropzone zone : NamedHudWidgetDropzones.ITEMS) {
            ItemHudWidgetDropzone itemZone = (ItemHudWidgetDropzone) zone;
            if (itemZone.type().isLeft() == this.rightBound && (hudWidget = (ItemHudWidget) renderer.getRelevantHudWidgetForDropzone(zone)) != null) {
                HudWidgetWidget widget = renderer.getWidget(hudWidget);
                float width = widget.scaledBounds().getWidth();
                RenderableComponent component = hudWidget.getRenderableItemName(renderer.isEditor());
                if (component != null) {
                    width += component.getWidth();
                }
                itemHudWidgetWidth = Math.max(itemHudWidgetWidth, width + 4.0f);
            }
        }
        return itemHudWidgetWidth * (this.rightBound ? -1 : 1);
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public float getY(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        return (renderer.getArea().getBottom() - hudWidgetSize.getScaledHeight()) - 2.0f;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetDropzone copy() {
        return new ItemCounterHudWidgetDropzone(this.rightBound);
    }
}
