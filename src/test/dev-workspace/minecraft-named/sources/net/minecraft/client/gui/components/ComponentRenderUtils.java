package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.ComponentCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/ComponentRenderUtils.class */
public class ComponentRenderUtils {
    private static final FormattedCharSequence INDENT = FormattedCharSequence.codepoint(32, Style.EMPTY);

    private static String stripColor(String $$0) {
        return Minecraft.getInstance().options.chatColors().get().booleanValue() ? $$0 : ChatFormatting.stripFormatting($$0);
    }

    public static List<FormattedCharSequence> wrapComponents(FormattedText $$0, int $$1, Font $$2) {
        ComponentCollector $$3 = new ComponentCollector();
        $$0.visit(($$12, $$22) -> {
            $$3.append(FormattedText.of(stripColor($$22), $$12));
            return Optional.empty();
        }, Style.EMPTY);
        List<FormattedCharSequence> $$4 = Lists.newArrayList();
        $$2.getSplitter().splitLines($$3.getResultOrEmpty(), $$1, Style.EMPTY, ($$13, $$23) -> {
            FormattedCharSequence $$32 = Language.getInstance().getVisualOrder($$13);
            $$4.add($$23.booleanValue() ? FormattedCharSequence.composite(INDENT, $$32) : $$32);
        });
        return $$4.isEmpty() ? Lists.newArrayList(new FormattedCharSequence[]{FormattedCharSequence.EMPTY}) : $$4;
    }
}
