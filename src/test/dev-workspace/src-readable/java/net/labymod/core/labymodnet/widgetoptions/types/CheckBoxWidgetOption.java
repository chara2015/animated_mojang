package net.labymod.core.labymodnet.widgetoptions.types;

import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.widgetoptions.CustomWidgetOption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/types/CheckBoxWidgetOption.class */
public class CheckBoxWidgetOption extends CustomWidgetOption {
    private static final String DEBOUNCE_ID = "checkbox-debounce";
    private static final String ON = "1";
    private static final String OFF = "0";

    public CheckBoxWidgetOption(CosmeticOption option, String optionName, int optionIndex) {
        super(option, optionName, optionIndex);
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.WidgetOption
    protected void create(String data, Consumer<Widget> consumer) {
        CheckBoxWidget.State state = data.equals(ON) ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED;
        CheckBoxWidget checkBoxWidget = new CheckBoxWidget();
        checkBoxWidget.setState(state);
        checkBoxWidget.setPressable(() -> {
            setData(DEBOUNCE_ID, checkBoxWidget.state() == CheckBoxWidget.State.CHECKED ? ON : OFF);
        });
        checkBoxWidget.setHoverComponent(Component.text(this.option.first().getCustomKey()));
        consumer.accept(checkBoxWidget);
    }
}
