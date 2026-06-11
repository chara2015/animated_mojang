package net.minecraft.server.dialog.action;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/action/Action.class */
public interface Action {
    public static final Codec<Action> CODEC = BuiltInRegistries.DIALOG_ACTION_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.codec();
    }, $$0 -> {
        return $$0;
    });

    MapCodec<? extends Action> codec();

    Optional<ClickEvent> createAction(Map<String, ValueGetter> map);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/action/Action$ValueGetter.class */
    public interface ValueGetter {
        String asTemplateSubstitution();

        Tag asTag();

        static Map<String, String> getAsTemplateSubstitutions(Map<String, ValueGetter> $$0) {
            return Maps.transformValues($$0, (v0) -> {
                return v0.asTemplateSubstitution();
            });
        }

        static ValueGetter of(final String $$0) {
            return new ValueGetter() { // from class: net.minecraft.server.dialog.action.Action.ValueGetter.1
                @Override // net.minecraft.server.dialog.action.Action.ValueGetter
                public String asTemplateSubstitution() {
                    return $$0;
                }

                @Override // net.minecraft.server.dialog.action.Action.ValueGetter
                public Tag asTag() {
                    return StringTag.valueOf($$0);
                }
            };
        }

        static ValueGetter of(final Supplier<String> $$0) {
            return new ValueGetter() { // from class: net.minecraft.server.dialog.action.Action.ValueGetter.2
                @Override // net.minecraft.server.dialog.action.Action.ValueGetter
                public String asTemplateSubstitution() {
                    return (String) $$0.get();
                }

                @Override // net.minecraft.server.dialog.action.Action.ValueGetter
                public Tag asTag() {
                    return StringTag.valueOf((String) $$0.get());
                }
            };
        }
    }
}
