package net.labymod.core.client.gui.screen.widget.widgets.title.ui;

import java.util.List;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.core.labynet.model.Survey;
import net.labymod.core.labynet.model.SurveyOption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/ui/SurveysWidget.class */
@AutoWidget
public class SurveysWidget extends VerticalListWidget<Widget> {
    private final List<Survey> surveys;
    private final DefaultLabyNetController labyNetController;
    private final Pressable closeAction;
    private final Pressable submitAction;

    public SurveysWidget(List<Survey> surveys, DefaultLabyNetController labyNetController, Pressable closeAction, Pressable submitAction) {
        addId("survey-wrapper");
        this.surveys = surveys;
        this.labyNetController = labyNetController;
        this.closeAction = closeAction;
        this.submitAction = submitAction;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Survey survey = getSurvey();
        if (survey == null) {
            return;
        }
        VerticalListWidget<Widget> surveyWidget = new VerticalListWidget<>();
        surveyWidget.addId("survey");
        if (survey.hasParticipated()) {
            surveyWidget.addId("survey-voted");
        }
        DivWidget titleWrapper = new DivWidget();
        titleWrapper.addId("survey-title-wrapper");
        ComponentWidget title = ComponentWidget.component(Component.translatable("labymod.survey.title", new Component[0]));
        title.addId("survey-title");
        titleWrapper.addChild(title);
        IconWidget closeIcon = new IconWidget(Textures.SpriteCommon.WHITE_X);
        closeIcon.addId("survey-close");
        closeIcon.setPressable(() -> {
            this.closeAction.press();
            this.submitAction.press();
        });
        titleWrapper.addChild(closeIcon);
        surveyWidget.addChild(titleWrapper);
        ComponentWidget question = ComponentWidget.component(Component.text(survey.getQuestion()));
        question.addId("survey-question");
        surveyWidget.addChild(question);
        if (survey.getImageUrl() != null) {
            DivWidget imageWrapper = new DivWidget();
            imageWrapper.addId("survey-image-wrapper");
            IconWidget image = new IconWidget(Icon.url(survey.getImageUrl()));
            image.addId("survey-image");
            imageWrapper.addChild(image);
            surveyWidget.addChild(imageWrapper);
        }
        switch (survey.getType()) {
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE:
                for (SurveyOption answer : survey.getOptions()) {
                    TextComponent text = Component.text(answer.getText());
                    if (survey.hasParticipated()) {
                        int voteCount = answer.getVoteCount();
                        if (answer.isVoted()) {
                            text.color(NamedTextColor.GREEN);
                            text.append(Component.icon(Textures.SpriteCommon.GREEN_CHECKED));
                        }
                        text.append(Component.text(" (").color(NamedTextColor.GRAY)).append(Component.text(Integer.valueOf(voteCount)).color(NamedTextColor.GRAY)).append(Component.text(")").color(NamedTextColor.GRAY));
                    }
                    ButtonWidget surveyButton = ButtonWidget.component(text);
                    if (answer.isVoted()) {
                        surveyButton.addId("survey-button-voted");
                    }
                    surveyButton.addId("survey-button");
                    surveyButton.setPressable(() -> {
                        this.labyNetController.sendSurveyAnswer(survey, Integer.valueOf(answer.getId()), null, result -> {
                            result.ifPresent(surveyAnswer -> {
                                if (!surveyAnswer.isSuccess()) {
                                    return;
                                }
                                for (SurveyOption a : survey.getOptions()) {
                                    a.setVoted(false);
                                }
                                answer.setVoted(true);
                                survey.setParticipated(true);
                                this.labyNetController.clearSurveyCache();
                                this.submitAction.press();
                            });
                        });
                    });
                    surveyWidget.addChild(surveyButton);
                }
                break;
            case TEXT:
                TextFieldWidget textField = new TextFieldWidget();
                textField.addId("survey-text-input");
                textField.maximalLength(250);
                ButtonWidget submitButton = ButtonWidget.component(Component.translatable("labymod.ui.button.submit", new Component[0]));
                submitButton.setPressable(() -> {
                    if (textField.getText().isEmpty()) {
                        return;
                    }
                    submitButton.setEnabled(false);
                    this.labyNetController.sendSurveyAnswer(survey, null, textField.getText(), result -> {
                        result.ifPresent(surveyAnswer -> {
                            if (surveyAnswer.isSuccess()) {
                                survey.setParticipated(true);
                                this.submitAction.press();
                            }
                        });
                    });
                });
                surveyWidget.addChild(textField);
                surveyWidget.addChild(submitButton);
                break;
        }
        addChild(surveyWidget);
    }

    private Survey getSurvey() {
        for (Survey s : this.surveys) {
            if (!s.hasParticipated()) {
                return s;
            }
        }
        return null;
    }
}
