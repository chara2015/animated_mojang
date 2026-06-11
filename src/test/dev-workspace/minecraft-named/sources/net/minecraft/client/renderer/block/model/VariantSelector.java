package net.minecraft.client.renderer.block.model;

import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/VariantSelector.class */
public class VariantSelector {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',');
    private static final Splitter EQUAL_SPLITTER = Splitter.on('=').limit(2);

    public static <O, S extends StateHolder<O, S>> Predicate<StateHolder<O, S>> predicate(StateDefinition<O, S> $$0, String $$1) {
        Map<Property<?>, Comparable<?>> $$2 = new HashMap<>();
        for (String $$3 : COMMA_SPLITTER.split($$1)) {
            Iterator<String> $$4 = EQUAL_SPLITTER.split($$3).iterator();
            if ($$4.hasNext()) {
                String $$5 = $$4.next();
                Property<?> $$6 = $$0.getProperty($$5);
                if ($$6 != null && $$4.hasNext()) {
                    String $$7 = $$4.next();
                    Comparable<?> $$8 = getValueHelper($$6, $$7);
                    if ($$8 != null) {
                        $$2.put($$6, $$8);
                    } else {
                        throw new RuntimeException("Unknown value: '" + $$7 + "' for blockstate property: '" + $$5 + "' " + String.valueOf($$6.getPossibleValues()));
                    }
                } else if (!$$5.isEmpty()) {
                    throw new RuntimeException("Unknown blockstate property: '" + $$5 + "'");
                }
            }
        }
        return $$12 -> {
            for (Map.Entry<Property<?>, Comparable<?>> $$22 : $$2.entrySet()) {
                if (!Objects.equals($$12.getValue($$22.getKey()), $$22.getValue())) {
                    return false;
                }
            }
            return true;
        };
    }

    private static <T extends Comparable<T>> T getValueHelper(Property<T> $$0, String $$1) {
        return $$0.getValue($$1).orElse(null);
    }
}
