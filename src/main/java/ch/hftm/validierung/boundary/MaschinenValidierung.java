package ch.hftm.validierung.boundary;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import ch.hftm.validierung.entity.Maschine;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MaschinenValidierung {

    @Incoming("maschinen")
    @Outgoing("validierte-maschinen")
    public Maschine validiereMaschine(Maschine maschine) {

        if (maschine.getMaschinenBezeichnung() == null || maschine.getMaschinenBezeichnung().isEmpty()) {
            maschine.setValid(false);
        }
        if (maschine.getMaschinenTyp() == null || maschine.getMaschinenTyp().isEmpty()) {
            maschine.setValid(false);
        }
        if (maschine.getMaschinenHersteller() == null || maschine.getMaschinenHersteller().isEmpty()) {
            maschine.setValid(false);
        }
        if (maschine.getMaschinenNummer() == null
                || maschine.getMaschinenNummer().isEmpty() && maschine.getMaschinenNummer().startsWith("9007")) {
            maschine.setValid(false);
        }

        maschine.setValid(true);

        return maschine;
    }

}
