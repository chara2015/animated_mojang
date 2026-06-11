package net.labymod.core.labyconnect.lanworld;

import java.util.UUID;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.core.main.LabyMod;
import net.labymod.labypeer.client.ice.credentials.IceCredentialsHandler;
import net.labymod.labypeer.client.ice.credentials.IceCredentialsTransmitException;
import net.labymod.labypeer.client.ice.credentials.IceCredentialsTransmitter;
import net.labymod.labypeer.ice.IceCredentials;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/lanworld/LabyConnectIceCredentialsTransmitter.class */
public class LabyConnectIceCredentialsTransmitter implements IceCredentialsTransmitter {
    private IceCredentialsHandler handler;

    public void credentialsReceived(IceCredentials credentials) {
        if (this.handler != null) {
            this.handler.remoteCredentialsReceived(credentials);
        }
    }

    public void setHandler(IceCredentialsHandler handler) {
        this.handler = handler;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.labypeer.client.ice.credentials.IceCredentialsTransmitException */
    public void sendCredentials(UUID targetUser, IceCredentials credentials) throws IceCredentialsTransmitException {
        DefaultLabyConnectSession session = (DefaultLabyConnectSession) LabyMod.references().labyConnect().getSession();
        if (session != null) {
            session.sendIceCredentials(targetUser, credentials);
            return;
        }
        throw new IceCredentialsTransmitException("Not connected to the LabyMod chat");
    }
}
