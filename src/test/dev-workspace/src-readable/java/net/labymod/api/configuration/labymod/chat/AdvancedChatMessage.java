package net.labymod.api.configuration.labymod.chat;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.chat.ChatTrustLevel;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.metadata.MetadataExtension;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/AdvancedChatMessage.class */
public class AdvancedChatMessage implements MetadataExtension {
    private final ChatMessage chatMessage;
    private Metadata metadata;
    private Component component;
    private boolean hidden;
    private RenderableComponent[] cachedRenderableComponents;
    private int cachedWidth;

    private AdvancedChatMessage(ChatMessage chatMessage) {
        this.metadata = Metadata.create();
        this.chatMessage = chatMessage;
    }

    private AdvancedChatMessage(Component component) {
        this(ChatMessage.builder().component(component).visibility(ChatVisibility.COMMANDS_ONLY).build());
    }

    public static AdvancedChatMessage component(Component component) {
        return new AdvancedChatMessage(component);
    }

    public static AdvancedChatMessage text(String text) {
        return new AdvancedChatMessage(Component.text(text));
    }

    public static AdvancedChatMessage chat(ChatMessage chatMessage) {
        return new AdvancedChatMessage(chatMessage);
    }

    public long timestamp() {
        return this.chatMessage.timestamp();
    }

    public Component component() {
        return this.component == null ? this.chatMessage.component() : this.component;
    }

    public Component originalComponent() {
        return this.chatMessage.component();
    }

    public ChatVisibility visibility() {
        return this.chatMessage.visibility();
    }

    public ChatTrustLevel trustLevel() {
        return this.chatMessage.trustLevel();
    }

    @NotNull
    public ChatMessage chatMessage() {
        return this.chatMessage;
    }

    public void updateComponent(@NotNull Component component) {
        this.component = component;
        invalidateCache();
    }

    public void invalidateCache() {
        this.cachedRenderableComponents = null;
    }

    public void hideMessage() {
        this.hidden = true;
    }

    public boolean isVisible() {
        return !this.hidden && Laby.labyAPI().minecraft().options().chatVisibility().isMessageVisible(visibility());
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }

    public RenderableComponent[] getRenderableComponents(int width) {
        if (this.cachedRenderableComponents != null && this.cachedWidth == width) {
            return this.cachedRenderableComponents;
        }
        List<Component> lines = Laby.labyAPI().renderPipeline().componentRenderer().split(component(), width);
        int size = lines.size();
        RenderableComponent[] renderableComponents = new RenderableComponent[size];
        for (int index = 0; index < size; index++) {
            renderableComponents[index] = RenderableComponent.of(lines.get(index));
        }
        this.cachedRenderableComponents = renderableComponents;
        this.cachedWidth = width;
        return renderableComponents;
    }
}
