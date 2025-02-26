package gn.stock.panels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestOracleConnection {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Driver loaded");
        String url = "jdbc:oracle:thin:@" +
                "/FREEPDB1";
        String username = "Hasna";  // Remplacez par votre nom d'utilisateur
        String password = "664116362292";  // Remplacez par votre mot de passe
        System.out.println("Connecting to database...");
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
