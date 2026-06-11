package net.labymod.api.client.chat;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/Title.class */
public class Title {

    @Nullable
    private final Component title;

    @Nullable
    private final Component subTitle;
    private final int fadeInTicks;
    private final int stayTicks;
    private final int fadeOutTicks;

    public Title(@Nullable Component title, @Nullable Component subTitle, int fadeInTicks, int stayTicks, int fadeOutTicks) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeInTicks = fadeInTicks;
        this.stayTicks = stayTicks;
        this.fadeOutTicks = fadeOutTicks;
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @Nullable
    public Component getTitle() {
        return this.title;
    }

    @Nullable
    public Component getSubTitle() {
        return this.subTitle;
    }

    public int getFadeInTicks() {
        return this.fadeInTicks;
    }

    public int getStayTicks() {
        return this.stayTicks;
    }

    public int getFadeOutTicks() {
        return this.fadeOutTicks;
    }

    public void show() {
        Laby.references().chatExecutor().showTitle(this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title1 = (Title) o;
        if (this.fadeInTicks != title1.fadeInTicks || this.stayTicks != title1.stayTicks || this.fadeOutTicks != title1.fadeOutTicks || !Objects.equals(this.title, title1.title)) {
            return false;
        }
        return Objects.equals(this.subTitle, title1.subTitle);
    }

    public int hashCode() {
        int result = this.title != null ? this.title.hashCode() : 0;
        return (31 * ((31 * ((31 * ((31 * result) + (this.subTitle != null ? this.subTitle.hashCode() : 0))) + this.fadeInTicks)) + this.stayTicks)) + this.fadeOutTicks;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/Title$Builder.class */
    public static class Builder {
        private Component title;
        private Component subTitle;
        private int fadeInTicks;
        private int stayTicks = 20;
        private int fadeOutTicks;

        private Builder() {
        }

        @NotNull
        public Builder title(@NotNull Component title) {
            this.title = title;
            return this;
        }

        @NotNull
        public Builder subTitle(@NotNull Component subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        @NotNull
        public Builder fadeIn(int fadeInTicks) {
            this.fadeInTicks = fadeInTicks;
            return this;
        }

        @NotNull
        public Builder stay(int stayTicks) {
            this.stayTicks = stayTicks;
            return this;
        }

        @NotNull
        public Builder fadeOut(int fadeOutTicks) {
            this.fadeOutTicks = fadeOutTicks;
            return this;
        }

        @NotNull
        public Builder timing(int fadeInTicks, int stayTicks, int fadeOutTicks) {
            this.fadeInTicks = fadeInTicks;
            this.stayTicks = stayTicks;
            this.fadeOutTicks = fadeOutTicks;
            return this;
        }

        public Title build() {
            return new Title(this.title, this.subTitle, this.fadeInTicks, this.stayTicks, this.fadeOutTicks);
        }

        public void show() {
            build().show();
        }
    }
}
