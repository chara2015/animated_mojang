package net.labymod.api.client.component.flattener;

import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.ObjectComponent;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.builder.Buildable;
import net.labymod.api.client.component.flattener.DefaultComponentFlattener;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/flattener/ComponentFlattener.class */
public interface ComponentFlattener extends Buildable<Builder> {
    public static final String BASIC_COMPLEX_FLATTENER_IDENTIFIER = "basic_complex";
    public static final String BASIC_UNKNOWN_FLATTENER_IDENTIFIER = "basic_unknown";
    public static final String BASIC_FLATTENER_IDENTIFIER = "basic";
    public static final ComponentFlattener BASIC = builder().withIdentifier(BASIC_FLATTENER_IDENTIFIER).mapper(TextComponent.class, (v0) -> {
        return v0.getText();
    }).mapper(TranslatableComponent.class, (v0) -> {
        return v0.getKey();
    }).mapper(KeybindComponent.class, (v0) -> {
        return v0.getKeybind();
    }).mapper(IconComponent.class, component -> {
        return "";
    }).mapper(ScoreComponent.class, (v0) -> {
        return v0.getName();
    }).mapper(ObjectComponent.class, objectComponent -> {
        return ObjectComponent.PLACEHOLDER;
    }).build();
    public static final String ONLY_TEXT_FLATTENER_IDENTIFIER = "text_only";
    public static final ComponentFlattener TEXT_ONLY = builder().withIdentifier(ONLY_TEXT_FLATTENER_IDENTIFIER).mapper(TextComponent.class, (v0) -> {
        return v0.getText();
    }).build();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/flattener/ComponentFlattener$Builder.class */
    public interface Builder {
        Builder withIdentifier(String str);

        <T extends Component> Builder mapper(Class<T> cls, Function<T, String> function);

        <T extends Component> Builder complexMapper(Class<T> cls, ComplexMapper<T> complexMapper);

        Builder unknownMapper(Function<Component, String> function);

        ComponentFlattener build();
    }

    void flatten(Component component, FlattenerListener flattenerListener);

    String getIdentifier();

    static Builder builder() {
        return new DefaultComponentFlattener.Builder();
    }

    default void flatten(Component input, final Consumer<Component> componentConsumer, final Consumer<String> textConsumer) {
        flatten(input, new FlattenerListener(this) { // from class: net.labymod.api.client.component.flattener.ComponentFlattener.1
            @Override // net.labymod.api.client.component.flattener.FlattenerListener
            public void push(Component source) {
                componentConsumer.accept(source);
            }

            @Override // net.labymod.api.client.component.flattener.FlattenerListener
            public void component(String text) {
                textConsumer.accept(text);
            }
        });
    }
}
