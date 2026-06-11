package net.labymod.api.client.render.font;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/TextColorStripper.class */
@Referenceable
public interface TextColorStripper {
    String stripColorCodes(String str);

    String stripColorCodes(String str, char c);
}
