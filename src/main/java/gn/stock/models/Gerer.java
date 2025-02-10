package gn.stock.models;

public class Gerer {
    private int idUtilisateur;
    private int idP;

    // Constructeurs
    public Gerer() {}

    public Gerer(int idUtilisateur, int idP) {
        this.idUtilisateur = idUtilisateur;
        this.idP = idP;
    }

    // Getters et Setters
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }
}
