package net.labymod.core.labyconnect.lanworld;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.inject.Singleton;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import net.labymod.api.util.logging.Logging;
import org.ice4j.Transport;
import org.ice4j.TransportAddress;
import org.ice4j.ice.harvest.CandidateHarvester;
import org.ice4j.ice.harvest.StunCandidateHarvester;
import org.ice4j.ice.harvest.TurnCandidateHarvester;
import org.ice4j.ice.harvest.UPNPHarvester;
import org.ice4j.security.LongTermCredential;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/lanworld/CandidateHarvesterService.class */
@Singleton
@Referenceable
public class CandidateHarvesterService {
    private static final Logging LOGGER = Logging.create((Class<?>) CandidateHarvesterService.class);
    private static final String HARVESTERS_URL = "https://dl.labymod.net/labyconnect/lan/config.json";
    private final List<Supplier<CandidateHarvester>> harvesterSuppliers = new ArrayList();

    /* JADX WARN: Multi-variable type inference failed */
    public CandidateHarvesterService() {
        ((TypeTokenGsonRequest) ((TypeTokenGsonRequest) Request.ofGsonList(String.class).url(HARVESTERS_URL, new Object[0])).async()).execute(harvesters -> {
            if (harvesters.hasException() || harvesters.isEmpty() || (harvesters.isPresent() && ((List) harvesters.get()).isEmpty())) {
                if (harvesters.hasException()) {
                    LOGGER.warn("Failed to load ICE harvester uris, using UPnP as fallback", harvesters.exception());
                } else {
                    LOGGER.warn("Failed to load ICE harvester uris, using UPnP as fallback", new Object[0]);
                }
                addHarvester(URI.create("upnp://labymod"));
                return;
            }
            for (String harvester : (List) harvesters.get()) {
                try {
                    addHarvester(URI.create(harvester));
                } catch (IllegalArgumentException exception) {
                    LOGGER.error("Failed to parse ICE harvester uri: {}", harvester, exception);
                }
            }
        });
    }

    public List<CandidateHarvester> buildHarvesters() {
        List<CandidateHarvester> harvesters = new ArrayList<>(this.harvesterSuppliers.size());
        for (Supplier<CandidateHarvester> supplier : this.harvesterSuppliers) {
            harvesters.add(supplier.get());
        }
        return harvesters;
    }

    public void addHarvester(Supplier<CandidateHarvester> supplier) {
        this.harvesterSuppliers.add(supplier);
    }

    public void addHarvester(URI uri) {
        switch (uri.getScheme()) {
            case "stun":
                try {
                    Transport transport = Transport.parse(uri.getPath().replace("/", ""));
                    addHarvester(() -> {
                        return new StunCandidateHarvester(new TransportAddress(uri.getHost(), uri.getPort(), transport));
                    });
                    break;
                } catch (IllegalArgumentException exception) {
                    LOGGER.error("Unknown transport for STUN ice harvester for uri {}", uri, exception);
                    return;
                }
                break;
            case "turn":
                try {
                    Transport transport2 = Transport.parse(uri.getPath().replace("/", ""));
                    String userInfo = uri.getUserInfo();
                    LongTermCredential credential = null;
                    if (userInfo != null) {
                        String[] components = userInfo.split(":", 2);
                        if (components.length == 2) {
                            credential = new LongTermCredential(components[0], components[1]);
                        }
                    }
                    LongTermCredential finalCredential = credential;
                    addHarvester(() -> {
                        return new TurnCandidateHarvester(new TransportAddress(uri.getHost(), uri.getPort(), transport2), finalCredential);
                    });
                    break;
                } catch (IllegalArgumentException exception2) {
                    LOGGER.error("Unknown transport for TURN ice harvester for uri {}", uri, exception2);
                    return;
                }
                break;
            case "upnp":
                addHarvester(UPNPHarvester::new);
                break;
            default:
                LOGGER.error("Unknown ice harvester {} for uri {}", uri.getScheme(), uri);
                break;
        }
    }
}
