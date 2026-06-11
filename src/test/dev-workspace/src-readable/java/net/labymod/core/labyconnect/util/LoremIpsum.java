package net.labymod.core.labyconnect.util;

import net.labymod.api.Laby;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.labyconnect.protocol.model.chat.DefaultTextChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/util/LoremIpsum.class */
public class LoremIpsum {
    private static final String[] WORDS = {"Lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "Sed", "non", "risus", "nisl", "nec", "diam", "eu", "nunc", "consequat", "viverra", "Suspendisse", "potenti", "augue", "mauris", "pellentesque", "quis", "ultricies", "interdum", "aliquet", "tempus", "fringilla", "vehicula", "lectus", "vitae", "varius", "orci", "tristique", "facilisis", "scelerisque", "eget", "sollicitudin", "lobortis", "porttitor", "mollis", "iaculis", "venenatis", "turpis", "imperdiet", "gravida", "tempor", "dictum", "fermentum", "quam", "eleifend", "tincidunt", "urna", "congue", "lobortis", "hendrerit", "euismod", "sapien"};

    public static void addMessages(Chat chat) {
        ThreadSafe.executeOnRenderThread(() -> {
            User userSelf;
            LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
            if (session != null) {
                for (int i = 0; i < 600; i++) {
                    if (Math.random() < 0.5d) {
                        userSelf = chat.getParticipants().get(0);
                    } else {
                        userSelf = session.self();
                    }
                    User sender = userSelf;
                    StringBuilder generatedText = new StringBuilder("[" + i + ".] ");
                    int wordsAmount = ((int) (Math.random() * 50.0d)) + 1;
                    for (int m = 0; m < wordsAmount; m++) {
                        generatedText.append(WORDS[(int) (Math.random() * ((double) WORDS.length))]);
                        if (Math.random() < 0.20000000298023224d && m < wordsAmount - 5) {
                            generatedText.append(",");
                        }
                        generatedText.append(" ");
                    }
                    generatedText.setCharAt(0, Character.toUpperCase(generatedText.charAt(0)));
                    generatedText.setLength(generatedText.length() - 1);
                    generatedText.append(".");
                    DefaultTextChatMessage message = new DefaultTextChatMessage(chat, sender, TimeUtil.getCurrentTimeMillis(), generatedText.toString());
                    message.markAsRead();
                    chat.getMessages().add(message);
                }
            }
        });
    }
}
