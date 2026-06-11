package net.labymod.core.labymodnet.widgetoptions;

import net.labymod.core.labymodnet.models.CosmeticOption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/CustomWidgetOption.class */
public abstract class CustomWidgetOption extends WidgetOption {
    protected final CosmeticOption option;

    protected CustomWidgetOption(CosmeticOption option, String optionName, int optionIndex) {
        super(optionName, optionIndex);
        this.option = option;
    }
}
