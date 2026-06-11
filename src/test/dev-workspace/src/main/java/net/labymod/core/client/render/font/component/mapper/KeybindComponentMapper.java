package net.labymod.core.client.render.font.component.mapper;

import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.flattener.ComplexMapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/mapper/KeybindComponentMapper.class */
public class KeybindComponentMapper implements ComplexMapper<KeybindComponent> {
    @Override // net.labymod.api.client.component.flattener.ComplexMapper
    public /* bridge */ /* synthetic */ void map(KeybindComponent keybindComponent, Consumer consumer) {
        map2(keybindComponent, (Consumer<Component>) consumer);
    }

    /* JADX INFO: renamed from: map, reason: avoid collision after fix types in other method */
    public void map2(KeybindComponent value, Consumer<Component> consumer) {
        consumer.accept(value.resolveKeybind());
    }
}
