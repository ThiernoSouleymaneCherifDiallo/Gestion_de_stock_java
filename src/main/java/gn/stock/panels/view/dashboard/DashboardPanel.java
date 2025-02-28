package gn.stock.panels.view.dashboard;

import gn.stock.panels.view.dashboard.components.StatCard;
import gn.stock.panels.view.dashboard.components.StatChart;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

        gbc.gridx = 1; gbc.gridy = 0;
        contentPanel.add(new StatCard("Ventes", ventes, salesIcon), gbc);

        gbc.gridx = 2;
        contentPanel.add(new StatCard("Stock", stock, stockIcon), gbc);

        gbc.gridx = 3;
        contentPanel.add(new StatCard("Fournisseurs", fournisseurs, supplierIcon), gbc);

        gbc.gridx = 4;
        contentPanel.add(new StatCard("Caissiers", caissiers, cashierIcon), gbc);

        JPanel periodPanel = new JPanel();
        periodPanel.setBackground(new Color(30, 30, 30));
        periodComboBox = new JComboBox<>(new String[]{"Semaine", "Mois", "Année"});
        periodComboBox.setBackground(new Color(45, 45, 45));
        periodComboBox.setForeground(Color.WHITE);
        periodComboBox.addActionListener(e -> updateStatistics());
        periodPanel.add(periodComboBox);

        gbc.gridx = 0; gbc.gridy = 5;
        contentPanel.add(periodPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);

        statChart = new StatChart(new HashMap<>());
        add(statChart, BorderLayout.SOUTH);

        updateStatistics();
    }

    private int getVentesCount() {
        return executeCountQuery("SELECT COUNT(*) FROM TRANSACTIONS_G1J");
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
        String user =   "c##koulibaly";
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

    private Map<String, Integer> getTopSellingProducts(String period) {
        Map<String, Integer> salesData = new HashMap<>();
        String query = "";

        // Choix de la requête en fonction de la période sélectionnée
        switch (period) {
            case "Semaine":
                query = "SELECT NOM_PRODUIT, SUM(QUANTITE_T) AS TOTAL " +
                        "FROM TRANSACTIONS_G1J " +
                        "WHERE DATE_T >= SYSDATE - 7 " +  // Filtrage pour la semaine (7 derniers jours)
                        "GROUP BY NOM_PRODUIT ORDER BY TOTAL DESC";
                break;
            case "Mois":
                query = "SELECT NOM_PRODUIT, SUM(QUANTITE_T) AS TOTAL " +
                        "FROM TRANSACTIONS_G1J " +
                        "WHERE DATE_T >= ADD_MONTHS(SYSDATE, -1) " +  // Filtrage pour le mois (30 derniers jours)
                        "GROUP BY NOM_PRODUIT ORDER BY TOTAL DESC";
                break;
            case "Année":
                query = "SELECT NOM_PRODUIT, SUM(QUANTITE_T) AS TOTAL " +
                        "FROM TRANSACTIONS_G1J " +
                        "WHERE DATE_T >= ADD_MONTHS(SYSDATE, -12) " +  // Filtrage pour l'année (12 derniers mois)
                        "GROUP BY NOM_PRODUIT ORDER BY TOTAL DESC";
                break;
        }

        // Connexion à la base de données et exécution de la requête
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String user =   "c##koulibaly";
        String password = "1234567890";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Assurez-vous que le nom de la colonne est exactement "NOM_PRODUIT"
                salesData.put(rs.getString("NOM_PRODUIT"), rs.getInt("TOTAL"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }

        return salesData;
    }


    private void updateStatistics() {
        String selectedPeriod = (String) periodComboBox.getSelectedItem();
        Map<String, Integer> data = getTopSellingProducts(selectedPeriod);
        statChart.setData(data);
    }
}
