package gn.stock.models;

public class Recu {
    private int idR;
    private int idT;
    private int numR;

    // Constructeurs
    public Recu() {}

    public Recu(int idR, int idT, int numR) {
        this.idR = idR;
        this.idT = idT;
        this.numR = numR;
    }

    // Getters et Setters
    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getIdT() {
        return idT;
    }

    public void setIdT(int idT) {
        this.idT = idT;
    }

    public int getNumR() {
        return numR;
    }

    public void setNumR(int numR) {
        this.numR = numR;
    }
}