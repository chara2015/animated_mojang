package net.minecraft.server.dialog.input;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/input/InputControlTypes.class */
public class InputControlTypes {
    public static MapCodec<? extends InputControl> bootstrap(Registry<MapCodec<? extends InputControl>> $$0) {
        Registry.register($$0, Identifier.withDefaultNamespace("boolean"), BooleanInput.MAP_CODEC);
        Registry.register($$0, Identifier.withDefaultNamespace("number_range"), NumberRangeInput.MAP_CODEC);
        Registry.register($$0, Identifier.withDefaultNamespace("single_option"), SingleOptionInput.MAP_CODEC);
        return (MapCodec) Registry.register($$0, Identifier.withDefaultNamespace(Display.TextDisplay.TAG_TEXT), TextInput.MAP_CODEC);
    }
}
