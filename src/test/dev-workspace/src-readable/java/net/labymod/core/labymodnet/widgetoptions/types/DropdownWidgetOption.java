package net.labymod.core.labymodnet.widgetoptions.types;

import java.util.Iterator;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.util.I18n;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.models.CosmeticOptionEntry;
import net.labymod.core.labymodnet.widgetoptions.CustomWidgetOption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/types/DropdownWidgetOption.class */
public class DropdownWidgetOption extends CustomWidgetOption {
    private static final String DEBOUNCE_ID = "dropdown-debounce";

    public DropdownWidgetOption(CosmeticOption option, String optionName, int optionIndex) {
        super(option, optionName, optionIndex);
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.WidgetOption
    public void begin(Cosmetic cosmetic, CosmeticSettingsWidget.CosmeticSettingsListener listener, String translationKeyPrefix) {
        super.begin(cosmetic, listener, translationKeyPrefix);
        this.translationKeyPrefix += "dropdown.";
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.WidgetOption
    protected void create(String data, Consumer<Widget> consumer) {
        CosmeticOptionEntry selectedEntry = null;
        Iterator<CosmeticOptionEntry> it = this.option.options().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CosmeticOptionEntry entry = it.next();
            if (entry.getData().equals(data)) {
                selectedEntry = entry;
                break;
            }
        }
        if (selectedEntry == null) {
            selectedEntry = this.option.first();
        }
        Iterator<CosmeticOptionEntry> it2 = this.option.options().iterator();
        while (it2.hasNext()) {
            translateName(it2.next());
        }
        DropdownWidget<CosmeticOptionEntry> dropdownWidget = new DropdownWidget<>();
        dropdownWidget.addAll(this.option.options());
        dropdownWidget.setSelected(selectedEntry);
        dropdownWidget.setChangeListener(entry2 -> {
            setData(DEBOUNCE_ID, entry2.getData());
        });
        consumer.accept(dropdownWidget);
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.WidgetOption
    protected long getDebounceLength() {
        return 0L;
    }

    private void translateName(CosmeticOptionEntry entry) {
        String key = entry.getCustomKey();
        String name = entry.getName();
        String translation = I18n.getTranslation(this.translationKeyPrefix + key + "." + name, new Object[0]);
        entry.setCustomName(translation);
    }
}
