package gn.stock.models;

public class Contenir {
    private int idT;
    private int idP;

    // Constructeurs
    public Contenir() {}

    public Contenir(int idT, int idP) {
        this.idT = idT;
        this.idP = idP;
    }

    // Getters et Setters
    public int getIdT() {
        return idT;
    }

    public void setIdT(int idT) {
        this.idT = idT;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }
}
