package net.labymod.core.labynet.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/model/Survey.class */
public class Survey {
    private int id;
    private Type type;
    private String question;
    private String description;
    private String url;

    @SerializedName("image_url")
    private String imageUrl;
    private List<SurveyOption> options;
    private boolean participated;

    @SerializedName("participant_count")
    private int participantCount;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/model/Survey$Type.class */
    public enum Type {
        SINGLE_CHOICE,
        MULTIPLE_CHOICE,
        TEXT
    }

    public int getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.url;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public List<SurveyOption> getOptions() {
        return this.options;
    }

    public boolean hasParticipated() {
        return this.participated;
    }

    public void setParticipated(boolean participated) {
        this.participated = participated;
    }
}
