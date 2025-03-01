package gn.stock.panels.view.dashboard;

import gn.stock.panels.view.dashboard.components.StatCard;
import gn.stock.panels.view.dashboard.components.StatChart;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
public class DashboardPanel extends JPanel {
    private StatChart statChart;
    private JComboBox<String> periodComboBox;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 30, 30));
        JLabel titleLabel = new JLabel("Rapport", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ventes = getVentesCount();
        int stock = getStockQuantity();
        int fournisseurs = getFournisseursCount();
        int caissiers = getCaissiersCount();

        Icon salesIcon = UIManager.getIcon("OptionPane.informationIcon");
        Icon stockIcon = UIManager.getIcon("OptionPane.warningIcon");
        Icon supplierIcon = UIManager.getIcon("OptionPane.questionIcon");
        Icon cashierIcon = UIManager.getIcon("OptionPane.errorIcon");


        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(new StatCard("Ventes", ventes, salesIcon), gbc);

        gbc.gridx = 2;
        contentPanel.add(new StatCard("Stock", stock, stockIcon), gbc);

        gbc.gridx = 3;
        contentPanel.add(new StatCard("Fournisseurs", fournisseurs, supplierIcon), gbc);

        gbc.gridx = 4;
        contentPanel.add(new StatCard("Caissiers", caissiers, cashierIcon), gbc);

        /*
        JPanel periodPanel = new JPanel();
        periodPanel.setBackground(new Color(30, 30, 30));
        periodComboBox = new JComboBox<>(new String[]{"Semaine", "Mois", "Année"});
        periodComboBox.setBackground(new Color(45, 45, 45));
        periodComboBox.setForeground(Color.WHITE);
        periodComboBox.addActionListener(e -> updateStatistics());
        periodPanel.add(periodComboBox);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPanel.add(periodPanel, gbc);

         */

        add(contentPanel, BorderLayout.CENTER);

        statChart = new StatChart(new HashMap<>());
        add(statChart, BorderLayout.SOUTH);

        updateStatistics();
        setPreferredSize(new Dimension(800, 600)); // Ajustez la largeur selon vos besoins
        setMinimumSize(new Dimension(800, 600));
    }


    private int getVentesCount() {
        return executeCountQuery("SELECT COUNT(*) FROM TRANSACTION_G1J");
    }

    private int getStockQuantity() {
        return executeCountQuery("SELECT SUM(QUANTITE_P) FROM PRODUIT_G1J");
    }

    private int getFournisseursCount() {
        return executeCountQuery("SELECT COUNT(*) FROM FOURNISSEUR_G1J");
    }

    private int getCaissiersCount() {
        return executeCountQuery("SELECT COUNT(*) FROM UTILISATEUR_G1J");
    }


    private int executeCountQuery(String query) {
        int result = 0;
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String user = "c##koulibaly";
        String password = "1234567890";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
        return result;
    }
    private Map<String, Integer> getTopSellingProducts() {
        Map<String, Integer> salesData = new LinkedHashMap<>(); // Utilisation de LinkedHashMap pour garder l'ordre

        String query = """
    SELECT PRODUIT_G1J.NOM_P, SUM(LIGNETRANSACTION.QUANTITE) AS TOTAL
    FROM LIGNETRANSACTION
    JOIN PRODUIT_G1J ON LIGNETRANSACTION.ID_P = PRODUIT_G1J.ID_P
    GROUP BY PRODUIT_G1J.NOM_P
    ORDER BY TOTAL DESC
    """;

        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String user = "c##koulibaly";
        String password = "1234567890";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcours les résultats pour ajouter chaque produit et sa quantité à salesData
            while (rs.next()) {
                String productName = rs.getString("NOM_P");
                int total = rs.getInt("TOTAL");

                // Si le produit existe déjà dans salesData, on ne l'ajoute pas à nouveau
                salesData.put(productName, total);
            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }

        System.out.println("Produits récupérés : " + salesData); // Debugging pour voir les résultats
        return salesData;
    }



    private void updateStatistics() {
        Map<String, Integer> data = getTopSellingProducts();
        System.out.println("Données récupérées : " + data);
        statChart.setData(data);


    }
}