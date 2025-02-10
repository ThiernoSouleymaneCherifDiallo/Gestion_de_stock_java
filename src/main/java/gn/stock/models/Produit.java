package gn.stock.models;

public class Produit {
    private int idP;
    private String nomP;
    private int quantiteP;
    private String etatP;
    private double prixP;
    private String categoryP;
    private String corbeilleP;

    // Constructeurs
    public Produit() {}

    public Produit(int idP, String nomP, int quantiteP, String etatP, double prixP, String categoryP, String corbeilleP) {
        this.idP = idP;
        this.nomP = nomP;
        this.quantiteP = quantiteP;
        this.etatP = etatP;
        this.prixP = prixP;
        this.categoryP = categoryP;
        this.corbeilleP = corbeilleP;
    }

    // Getters et Setters
    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNomP() {
        return nomP;
    }

    public void setNomP(String nomP) {
        this.nomP = nomP;
    }

    public int getQuantiteP() {
        return quantiteP;
    }

    public void setQuantiteP(int quantiteP) {
        this.quantiteP = quantiteP;
    }

    public String getEtatP() {
        return etatP;
    }

    public void setEtatP(String etatP) {
        this.etatP = etatP;
    }

    public double getPrixP() {
        return prixP;
    }

    public void setPrixP(double prixP) {
        this.prixP = prixP;
    }

    public String getCategoryP() {
        return categoryP;
    }

    public void setCategoryP(String categoryP) {
        this.categoryP = categoryP;
    }

    public String getCorbeilleP() {
        return corbeilleP;
    }

    public void setCorbeilleP(String corbeilleP) {
        this.corbeilleP = corbeilleP;
    }
}
