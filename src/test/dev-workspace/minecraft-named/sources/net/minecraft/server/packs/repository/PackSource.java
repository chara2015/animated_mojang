package net.minecraft.server.packs.repository;

import java.util.function.UnaryOperator;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/repository/PackSource.class */
public interface PackSource {
    public static final UnaryOperator<Component> NO_DECORATION = UnaryOperator.identity();
    public static final PackSource DEFAULT = create(NO_DECORATION, true);
    public static final PackSource BUILT_IN = create(decorateWithSource("pack.source.builtin"), true);
    public static final PackSource FEATURE = create(decorateWithSource("pack.source.feature"), false);
    public static final PackSource WORLD = create(decorateWithSource("pack.source.world"), true);
    public static final PackSource SERVER = create(decorateWithSource("pack.source.server"), true);

    Component decorate(Component component);

    boolean shouldAddAutomatically();

    static PackSource create(final UnaryOperator<Component> $$0, final boolean $$1) {
        return new PackSource() { // from class: net.minecraft.server.packs.repository.PackSource.1
            @Override // net.minecraft.server.packs.repository.PackSource
            public Component decorate(Component $$02) {
                return (Component) $$0.apply($$02);
            }

            @Override // net.minecraft.server.packs.repository.PackSource
            public boolean shouldAddAutomatically() {
                return $$1;
            }
        };
    }

    private static UnaryOperator<Component> decorateWithSource(String $$0) {
        Component $$1 = Component.translatable($$0);
        return $$12 -> {
            return Component.translatable("pack.nameAndSource", $$12, $$1).withStyle(ChatFormatting.GRAY);
        };
    }
}
