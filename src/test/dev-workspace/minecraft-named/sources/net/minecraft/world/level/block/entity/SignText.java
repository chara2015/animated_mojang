package net.minecraft.world.level.block.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/SignText.class */
public class SignText {
    private static final Codec<Component[]> LINES_CODEC = ComponentSerialization.CODEC.listOf().comapFlatMap($$0 -> {
        return Util.fixedSize($$0, 4).map($$0 -> {
            return new Component[]{(Component) $$0.get(0), (Component) $$0.get(1), (Component) $$0.get(2), (Component) $$0.get(3)};
        });
    }, $$02 -> {
        return List.of($$02[0], $$02[1], $$02[2], $$02[3]);
    });
    public static final Codec<SignText> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(LINES_CODEC.fieldOf("messages").forGetter($$0 -> {
            return $$0.messages;
        }), LINES_CODEC.lenientOptionalFieldOf("filtered_messages").forGetter((v0) -> {
            return v0.filteredMessages();
        }), DyeColor.CODEC.fieldOf("color").orElse(DyeColor.BLACK).forGetter($$02 -> {
            return $$02.color;
        }), Codec.BOOL.fieldOf("has_glowing_text").orElse(false).forGetter($$03 -> {
            return Boolean.valueOf($$03.hasGlowingText);
        })).apply($$0, (v0, v1, v2, v3) -> {
            return load(v0, v1, v2, v3);
        });
    });
    public static final int LINES = 4;
    private final Component[] messages;
    private final Component[] filteredMessages;
    private final DyeColor color;
    private final boolean hasGlowingText;
    private FormattedCharSequence[] renderMessages;
    private boolean renderMessagedFiltered;

    public SignText() {
        this(emptyMessages(), emptyMessages(), DyeColor.BLACK, false);
    }

    public SignText(Component[] $$0, Component[] $$1, DyeColor $$2, boolean $$3) {
        this.messages = $$0;
        this.filteredMessages = $$1;
        this.color = $$2;
        this.hasGlowingText = $$3;
    }

    private static Component[] emptyMessages() {
        return new Component[]{CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY, CommonComponents.EMPTY};
    }

    private static SignText load(Component[] $$0, Optional<Component[]> $$1, DyeColor $$2, boolean $$3) {
        return new SignText($$0, $$1.orElse((Component[]) Arrays.copyOf($$0, $$0.length)), $$2, $$3);
    }

    public boolean hasGlowingText() {
        return this.hasGlowingText;
    }

    public SignText setHasGlowingText(boolean $$0) {
        if ($$0 == this.hasGlowingText) {
            return this;
        }
        return new SignText(this.messages, this.filteredMessages, this.color, $$0);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public SignText setColor(DyeColor $$0) {
        if ($$0 == getColor()) {
            return this;
        }
        return new SignText(this.messages, this.filteredMessages, $$0, this.hasGlowingText);
    }

    public Component getMessage(int $$0, boolean $$1) {
        return getMessages($$1)[$$0];
    }

    public SignText setMessage(int $$0, Component $$1) {
        return setMessage($$0, $$1, $$1);
    }

    public SignText setMessage(int $$0, Component $$1, Component $$2) {
        Component[] $$3 = (Component[]) Arrays.copyOf(this.messages, this.messages.length);
        Component[] $$4 = (Component[]) Arrays.copyOf(this.filteredMessages, this.filteredMessages.length);
        $$3[$$0] = $$1;
        $$4[$$0] = $$2;
        return new SignText($$3, $$4, this.color, this.hasGlowingText);
    }

    public boolean hasMessage(Player $$0) {
        return Arrays.stream(getMessages($$0.isTextFilteringEnabled())).anyMatch($$02 -> {
            return !$$02.getString().isEmpty();
        });
    }

    public Component[] getMessages(boolean $$0) {
        return $$0 ? this.filteredMessages : this.messages;
    }

    public FormattedCharSequence[] getRenderMessages(boolean $$0, Function<Component, FormattedCharSequence> $$1) {
        if (this.renderMessages == null || this.renderMessagedFiltered != $$0) {
            this.renderMessagedFiltered = $$0;
            this.renderMessages = new FormattedCharSequence[4];
            for (int $$2 = 0; $$2 < 4; $$2++) {
                this.renderMessages[$$2] = $$1.apply(getMessage($$2, $$0));
            }
        }
        return this.renderMessages;
    }

    private Optional<Component[]> filteredMessages() {
        for (int $$0 = 0; $$0 < 4; $$0++) {
            if (!this.filteredMessages[$$0].equals(this.messages[$$0])) {
                return Optional.of(this.filteredMessages);
            }
        }
        return Optional.empty();
    }

    public boolean hasAnyClickCommands(Player $$0) {
        for (Component $$1 : getMessages($$0.isTextFilteringEnabled())) {
            Style $$2 = $$1.getStyle();
            ClickEvent $$3 = $$2.getClickEvent();
            if ($$3 != null && $$3.action() == ClickEvent.Action.RUN_COMMAND) {
                return true;
            }
        }
        return false;
    }
}
