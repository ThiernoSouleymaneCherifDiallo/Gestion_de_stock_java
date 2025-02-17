package gn.stock.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import gn.stock.base.Kpanel;

public class Dashboard extends Kpanel {
    private JLabel productCountLabel;
    private JLabel supplierCountLabel;
    private JLabel revenueLabel;

    public Dashboard() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Tableau de Bord");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(titleLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        statsPanel.setBackground(new Color(245, 245, 245));

        statsPanel.add(createCard("Revenus Totaux", "/revenue_icon.png", revenueLabel = new JLabel("0 GNF")));
        statsPanel.add(createCard("Commandes", "/orders_icon.png", productCountLabel = new JLabel("0")));
        statsPanel.add(createCard("Nouveaux Clients", "/customers_icon.png", supplierCountLabel = new JLabel("0")));

        add(statsPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String iconPath, JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(200, 80));

        // Chargement de l'icône
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(50, 50)); // Ajuster la taille de l'icône

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(iconLabel, BorderLayout.NORTH);
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);

        return panel;
    }

    public void updateProductCount(int count) {
        productCountLabel.setText(String.valueOf(count));
    }

    public void updateSupplierCount(int count) {
        supplierCountLabel.setText(String.valueOf(count));
    }

    public void updateRevenue(double revenue) {
        revenueLabel.setText(revenue + " GNF");
    }
}
