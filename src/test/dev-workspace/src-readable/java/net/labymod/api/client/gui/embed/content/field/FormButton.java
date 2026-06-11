package net.labymod.api.client.gui.embed.content.field;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/embed/content/field/FormButton.class */
public interface FormButton extends FormField<Void> {
    @Override // net.labymod.api.client.gui.embed.content.field.FormField
    default boolean isSubmit() {
        return true;
    }
}
