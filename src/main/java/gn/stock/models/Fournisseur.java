package gn.stock.models;

public class Fournisseur {
    private int idF;
    private int idP;
    private String nomPrenomF;
    private long telF;
    private String adresseF;
    private String emailF;

    // Constructeurs
    public Fournisseur() {}

    public Fournisseur(int idF, int idP, String nomPrenomF, long telF, String adresseF, String emailF) {
        this.idF = idF;
        this.idP = idP;
        this.nomPrenomF = nomPrenomF;
        this.telF = telF;
        this.adresseF = adresseF;
        this.emailF = emailF;
    }

    // Getters et Setters
    public int getIdF() {
        return idF;
    }

    public void setIdF(int idF) {
        this.idF = idF;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNomPrenomF() {
        return nomPrenomF;
    }

    public void setNomPrenomF(String nomPrenomF) {
        this.nomPrenomF = nomPrenomF;
    }

    public long getTelF() {
        return telF;
    }

    public void setTelF(long telF) {
        this.telF = telF;
    }

    public String getAdresseF() {
        return adresseF;
    }

    public void setAdresseF(String adresseF) {
        this.adresseF = adresseF;
    }

    public String getEmailF() {
        return emailF;
    }

    public void setEmailF(String emailF) {
        this.emailF = emailF;
    }
}
