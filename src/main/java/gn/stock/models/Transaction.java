package gn.stock.models;

import java.util.Date;

class Transactions {
    private int idT;
    private int quantiteT;
    private Date dateT;

    // Constructeurs
    public Transactions() {}

    public Transactions(int idT, int quantiteT, Date dateT) {
        this.idT = idT;
        this.quantiteT = quantiteT;
        this.dateT = dateT;
    }

    // Getters et Setters
    public int getIdT() {
        return idT;
    }

    public void setIdT(int idT) {
        this.idT = idT;
    }

    public int getQuantiteT() {
        return quantiteT;
    }

    public void setQuantiteT(int quantiteT) {
        this.quantiteT = quantiteT;
    }

    public Date getDateT() {
        return dateT;
    }

    public void setDateT(Date dateT) {
        this.dateT = dateT;
    }
}
