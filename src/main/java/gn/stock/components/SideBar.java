package gn.stock.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;

import gn.stock.panels.*;
import gn.stock.panels.view.dashboard.DashboardPanel;


class MainInterface extends JFrame {
    private JPanel sidePanel;
    private JPanel contentPanel;

    public MainInterface() {
        setTitle("Gestion des Stocks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bloquer le redimensionnement de la fenêtre
        // setResizable(false);

        // Centrer la fenêtre à l'écran
        setLocationRelativeTo(null);

        // Création du panneau latéral
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(36, 36, 38));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        // Ajout des boutons au panneau latéral avec icônes
        addButton("GESTION_STOCK", "/stock_icon.png");
        addButton("PRODUITS", "/product_icon.png");
        addButton("FOURNISSEURS", "/supplier_icon.png");
        addButton("TRANSACTION", "/transaction_icon.png");
        addButton("UTILISATEURS", "/user_icon_white.png");
        addButton("Deconnexion", "/logout_icon.png");

        // Création du panneau de contenu
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Initialisation avec le panneau de bienvenue
        contentPanel.add(new WelcomePanel(), BorderLayout.CENTER);

        // Ajout des panneaux à la fenêtre principale
        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addButton(String text, String iconPath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton button = new JButton(text, new ImageIcon(scaledIcon));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent(text);
            }
        });
        sidePanel.add(button);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void updateContent(String text) {
        contentPanel.removeAll();

        if (text.equals("PRODUITS")) {
            contentPanel.add(new ProduitPanel(), BorderLayout.CENTER);
        } else if (text.equals("FOURNISSEURS")) {
            contentPanel.add(new FournisseurPanel(), BorderLayout.CENTER);
        } else if (text.equals("UTILISATEURS")) {
            contentPanel.add(new UtilisateurPanel(), BorderLayout.CENTER);
        } else if (text.equals("TRANSACTION")) {
            contentPanel.add(new TransacPanel(), BorderLayout.CENTER);
        } else if (text.equals("GESTION_STOCK")) {
            contentPanel.add(new DashboardPanel(), BorderLayout.CENTER);
        } else if (text.equals("Deconnexion")) {
            // Fermer la fenêtre actuelle et ouvrir LoginPanel
            SwingUtilities.invokeLater(() -> {
                new LoginPanel(); // Ouvrir la fenêtre de connexion
                dispose(); // Fermer la fenêtre actuelle
            });
            return; // Sortir de la méthode pour éviter de revalider le contentPanel
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPanel();
            }
        });
    }
}