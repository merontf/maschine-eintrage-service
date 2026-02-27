package ch.hftm.validierung.entity;

public class Maschine {

    private String maschinenBezeichnung;
    private String maschinenTyp;
    private String maschinenHersteller;
    private String maschinenNummer;
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Maschine() {
    }

    public String getMaschinenBezeichnung() {
        return maschinenBezeichnung;
    }

    public void setMaschinenBezeichnung(String maschinenBezeichnung) {
        this.maschinenBezeichnung = maschinenBezeichnung;
    }

    public String getMaschinenTyp() {
        return maschinenTyp;
    }

    public void setMaschinenTyp(String maschinenTyp) {
        this.maschinenTyp = maschinenTyp;
    }

    public String getMaschinenHersteller() {
        return maschinenHersteller;
    }

    public void setMaschinenHersteller(String maschinenHersteller) {
        this.maschinenHersteller = maschinenHersteller;
    }

    public String getMaschinenNummer() {
        return maschinenNummer;
    }

    public void setMaschinenNummer(String maschinenNummer) {
        this.maschinenNummer = maschinenNummer;
    }

}