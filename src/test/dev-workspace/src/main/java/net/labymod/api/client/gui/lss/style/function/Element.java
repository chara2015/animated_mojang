package net.labymod.api.client.gui.lss.style.function;

import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/function/Element.class */
public interface Element {
    @NotNull
    ProcessedObject[] computeValue(@NotNull Widget widget, @NotNull String str, @NotNull Class<?> cls) throws Exception;

    String getRawValue();

    void forEach(Consumer<Element> consumer);
}
