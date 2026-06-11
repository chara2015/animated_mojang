package net.labymod.core.client.gui.screen.widget.widgets.account;

import java.util.Objects;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.accountmanager.storage.account.AccountSessionState;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.session.Session;
import net.labymod.api.util.TextFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/account/AccountWidget.class */
@AutoWidget
public class AccountWidget extends AbstractWidget<Widget> {
    private final Account account;
    private AccountSessionState previousState;

    public AccountWidget(Account account) {
        this.account = account;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        TextColor textColor;
        super.initialize(parent);
        Icon icon = Icon.head(this.account.getUUID());
        IconWidget avatarWidget = new IconWidget(icon);
        avatarWidget.addId("avatar");
        addChild(avatarWidget);
        ComponentWidget usernameWidget = ComponentWidget.component(Component.text(this.account.getUsername()));
        usernameWidget.addId("username");
        addChild(usernameWidget);
        AccountSessionState state = this.account.getSessionState();
        TranslatableComponent translatableComponentTranslatable = Component.translatable("labymod.activity.accountManager.account.state." + TextFormat.SNAKE_CASE.toLowerCamelCase(state.name()), new Component[0]);
        if (state == AccountSessionState.VALID) {
            textColor = NamedTextColor.GREEN;
        } else if (state == AccountSessionState.VALIDATING || state == AccountSessionState.REFRESHING || state == AccountSessionState.RATE_LIMITED) {
            textColor = NamedTextColor.YELLOW;
        } else {
            textColor = NamedTextColor.RED;
        }
        ComponentWidget statusWidget = ComponentWidget.component(translatableComponentTranslatable.color(textColor));
        statusWidget.addId("status");
        addChild(statusWidget);
        Session session = this.labyAPI.minecraft().sessionAccessor().getSession();
        boolean isActive = session != null && session.getUniqueId().equals(this.account.getUUID()) && Objects.equals(session.getAccessToken(), this.account.getAccessToken());
        if (isActive) {
            IconWidget activeWidget = new IconWidget(Textures.SpriteCommon.GREEN_CHECKED);
            activeWidget.addId("active");
            addChild(activeWidget);
        }
        this.previousState = state;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        AccountSessionState state = this.account.getSessionState();
        if (this.previousState != state) {
            this.previousState = state;
            reInitialize();
        }
    }

    public Account account() {
        return this.account;
    }
}
