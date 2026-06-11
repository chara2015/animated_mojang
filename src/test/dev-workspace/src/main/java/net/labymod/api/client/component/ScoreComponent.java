package net.labymod.api.client.component;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.scoreboard.ScoreboardObjective;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/ScoreComponent.class */
public interface ScoreComponent extends BaseComponent<ScoreComponent> {
    String getName();

    String getObjective();

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    ScoreComponent plainCopy();

    ScoreComponent name(String str);

    ScoreComponent objective(String str);

    Component value();

    ScoreboardObjective getScoreboardObjective();

    static Builder builder() {
        return new Builder();
    }

    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder */
    default Component.Builder<?, ?> toBuilder2() {
        return new Builder(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/ScoreComponent$Builder.class */
    public static class Builder extends Component.Builder<ScoreComponent, Builder> {
        protected String name;
        protected String objective;

        protected Builder() {
            this.name = "";
            this.objective = "";
        }

        protected Builder(ScoreComponent component) {
            super(component);
            this.name = "";
            this.objective = "";
            this.name = component.getName();
            this.objective = component.getObjective();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder objective(String objective) {
            this.objective = objective;
            return this;
        }

        @Override // net.labymod.api.client.component.builder.StyleableBuilder
        public ScoreComponent build() {
            Objects.requireNonNull(this.name, "Name cannot be null!");
            Objects.requireNonNull(this.objective, "Objective cannot be null!");
            return ComponentService.scoreComponent(this.name, this.objective, isEmpty() ? null : buildStyle(), this.children);
        }
    }
}
