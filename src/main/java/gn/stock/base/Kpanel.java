package gn.stock.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JPanel;

public class Kpanel extends JPanel {

    // Instance de connexion à la base de données
    protected Connection connection;

    public Kpanel() {
        connectToDatabase();
    }

    public void connectToDatabase() {
        try {
            // Chargement de la classe JDBC d'Oracle
            Class.forName("oracle.jdbc.OracleDriver");

            // Connexion à la base de données
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:FREE", // URL de connexion
                    "c##Hasna", // Nom d'utilisateur
                    "664116362292"  // Mot de passe
            );

            System.out.println("Connexion réussie à la base de données.");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    // Méthode pour insérer un produit dans la base de données
    public void insertProduct(String name, double price, int quantity, String state, String category, String corbeille) {
        String sql = "INSERT INTO PRODUIT_G1J (nom_p, prix_p, quantite_p, etat_p, category_p, corbeille_p) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, state);
            pstmt.setString(5, category);
            pstmt.setString(6, corbeille);
            pstmt.executeUpdate();
            System.out.println("Produit inséré avec succès : " + name);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    // Méthode pour mettre à jour un produit dans la base de données
    public void updateProduct(String name, double price, int quantity, String state, String category, String corbeille) {
        String sql = "UPDATE PRODUIT_G1J SET prix_p = ?, quantite_p = ?, etat_p = ?, category_p = ?, corbeille_p = ? WHERE nom_p = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, state);
            pstmt.setString(4, category);
            pstmt.setString(5, corbeille);
            pstmt.setString(6, name);
            pstmt.executeUpdate();
            System.out.println("Produit mis à jour avec succès : " + name);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    // Méthode pour supprimer un produit de la base de données
    public void deleteProduct(String name) {
        String sql = "DELETE FROM PRODUIT_G1J WHERE nom_p = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Produit supprimé avec succès : " + name);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
