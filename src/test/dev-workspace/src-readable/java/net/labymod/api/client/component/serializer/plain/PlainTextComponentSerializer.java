package net.labymod.api.client.component.serializer.plain;

import java.net.URI;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.uri.URIParser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/serializer/plain/PlainTextComponentSerializer.class */
public class PlainTextComponentSerializer {
    private static final ComponentFlattener DEFAULT_FLATTENER = ComponentFlattener.BASIC.toBuilder2().withIdentifier(ComponentFlattener.BASIC_UNKNOWN_FLATTENER_IDENTIFIER).unknownMapper(component -> {
        throw new UnsupportedOperationException("Don't know how to turn " + component.getClass().getSimpleName() + " into a string");
    }).build();
    private static final PlainTextComponentSerializer INSTANCE = new PlainTextComponentSerializer(DEFAULT_FLATTENER, false);
    private static final PlainTextComponentSerializer URL_INSTANCE = new PlainTextComponentSerializer(DEFAULT_FLATTENER, true);
    private final ComponentFlattener flattener;
    private final boolean extractUrls;

    private PlainTextComponentSerializer(ComponentFlattener flattener, boolean extractUrls) {
        this.flattener = flattener;
        this.extractUrls = extractUrls;
    }

    public static PlainTextComponentSerializer plainText() {
        return INSTANCE;
    }

    public static PlainTextComponentSerializer plainUrl() {
        return URL_INSTANCE;
    }

    public static PlainTextComponentSerializer of(ComponentFlattener flattener) {
        return of(flattener, false);
    }

    public static PlainTextComponentSerializer of(ComponentFlattener flattener, boolean extractUrls) {
        return new PlainTextComponentSerializer(flattener, extractUrls);
    }

    public TextComponent deserialize(String input) {
        if (!this.extractUrls) {
            return Component.text(input);
        }
        TextComponent component = Component.empty();
        StringBuilder builder = new StringBuilder();
        String[] segments = input.split(" ");
        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i];
            URI uri = URIParser.parseSegment(segment);
            if (uri == null) {
                builder.append(segment).append(' ');
            } else if (!URIParser.isClickableScheme(uri.getScheme())) {
                builder.append(segment).append(' ');
            } else {
                if (!builder.isEmpty()) {
                    component.append(Component.text(builder.toString()));
                    builder.setLength(0);
                }
                component.append(Component.text(segment, Style.builder().clickEvent(ClickEvent.openUrl(uri.toString())).build()));
                if (i != segments.length - 1) {
                    component.append(Component.space());
                }
            }
        }
        if (!builder.isEmpty()) {
            component.append(Component.text(builder.substring(0, builder.length() - 1)));
            builder.setLength(0);
        }
        return component;
    }

    public String serialize(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Passed null as Component argument");
        }
        StringBuilder stringBuilder = new StringBuilder();
        ComponentFlattener componentFlattener = this.flattener;
        Objects.requireNonNull(stringBuilder);
        componentFlattener.flatten(component, stringBuilder::append);
        return stringBuilder.toString();
    }
}
