package net.labymod.autogen.api.lss.properties.accessors;

import java.util.Collection;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/accessors/HorizontalListEntryAlignmentPropertyValueAccessor.class */
public final class HorizontalListEntryAlignmentPropertyValueAccessor implements PropertyValueAccessor<HorizontalListEntry, HorizontalAlignment, HorizontalAlignment> {
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public LssProperty getProperty(HorizontalListEntry widget) {
        return widget.alignment();
    }

    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public Class<?> type() {
        return HorizontalAlignment.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public HorizontalAlignment[] toArray(Object[] objects) {
        HorizontalAlignment[] array = new HorizontalAlignment[objects.length];
        for (int i = 0; i < objects.length; i++) {
            array[i] = (HorizontalAlignment) objects[i];
        }
        return array;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.gui.lss.property.PropertyValueAccessor
    public HorizontalAlignment[] toArray(Collection<HorizontalAlignment> collection) {
        return (HorizontalAlignment[]) collection.toArray(new HorizontalAlignment[0]);
    }
}
