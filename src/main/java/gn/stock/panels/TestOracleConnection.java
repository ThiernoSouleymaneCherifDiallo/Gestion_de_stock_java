package gn.stock.panels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestOracleConnection {

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String username = "c##koulibaly";  // Remplacez par votre nom d'utilisateur
        String password = "1234567890";  // Remplacez par votre mot de passe

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connexion réussie à la base de données Oracle !");
            } else {
                System.out.println("Échec de la connexion.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
