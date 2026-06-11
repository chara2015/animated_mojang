package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/inventory/EnchantmentNames.class */
public class EnchantmentNames {
    private static final FontDescription ALT_FONT = new FontDescription.Resource(Identifier.withDefaultNamespace("alt"));
    private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);
    private static final EnchantmentNames INSTANCE = new EnchantmentNames();
    private final RandomSource random = RandomSource.create();
    private final String[] words = {"the", "elder", "scrolls", "klaatu", "berata", "niktu", "xyzzy", "bless", "curse", "light", "darkness", "fire", "air", "earth", "water", "hot", "dry", "cold", "wet", "ignite", "snuff", "embiggen", "twist", "shorten", "stretch", "fiddle", "destroy", "imbue", "galvanize", "enchant", "free", "limited", "range", "of", "towards", "inside", "sphere", PartNames.CUBE, "self", "other", "ball", "mental", "physical", "grow", "shrink", "demon", "elemental", "spirit", "animal", "creature", "beast", "humanoid", "undead", "fresh", "stale", "phnglui", "mglwnafh", "cthulhu", "rlyeh", "wgahnagl", "fhtagn", "baguette"};

    private EnchantmentNames() {
    }

    public static EnchantmentNames getInstance() {
        return INSTANCE;
    }

    public FormattedText getRandomName(Font $$0, int $$1) {
        StringBuilder $$2 = new StringBuilder();
        int $$3 = this.random.nextInt(2) + 3;
        for (int $$4 = 0; $$4 < $$3; $$4++) {
            if ($$4 != 0) {
                $$2.append(" ");
            }
            $$2.append((String) Util.getRandom(this.words, this.random));
        }
        return $$0.getSplitter().headByWidth(Component.literal($$2.toString()).withStyle(ROOT_STYLE), $$1, Style.EMPTY);
    }

    public void initSeed(long $$0) {
        this.random.setSeed($$0);
    }
}
