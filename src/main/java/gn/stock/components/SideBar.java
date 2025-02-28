package gn.stock.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

import gn.stock.panels.*;


class MainInterface extends JFrame {
    private JPanel sidePanel;
    private JPanel contentPanel;

    public MainInterface() {
        setTitle("Gestion des Stocks");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création du panneau latéral
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(240, 240, 240));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        // Ajout des boutons au panneau latéral
        if (getNbUsers()){
            addButton("GESTION_STOCK");
            addButton("PRODUITS");
            addButton("FOURNISSEURS");
            addButton("TRANSACTION");
            addButton("UTILISATEURS");

            addButton("Deconnexion");
        }else {
            addButton("UTILISATEURS");
        }


        // Création du panneau de contenu
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);


        // Ajout des panneaux à la fenêtre principale
        add(sidePanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }



    private void addButton(String text) {
        JButton button = new JButton(text);
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

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 24));
        
        if (text.equals("PRODUITS")) {
            contentPanel.add(new ProduitPanel(new Dashboard()), BorderLayout.CENTER);
        } else if (text.equals("FOURNISSEURS")) {
            contentPanel.add(new FournisseurPanel(), BorderLayout.CENTER);
        } else if (text.equals("GESTION_STOCK")) {
            contentPanel.add(new Dashboard(), BorderLayout.CENTER);
        }else if (text.equals("UTILISATEURS")) {
            contentPanel.add(new UtilisateurPanel(), BorderLayout.CENTER);
        }else if (text.equals("TRANSACTION")) {
            contentPanel.add(new TransacPanel(new Dashboard()), BorderLayout.CENTER);
        }


        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static boolean getNbUsers(){

        try (Connection connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/orcl", // URL de connexion Oracle
                "c##gestionstock", // Nom d'utilisateur Oracle
                "gesionstockpassword" // Mot de passe Oracle
        )) {
            // Requête SQL pour vérifier les informations de connexion
            String sql = "SELECT * FROM UTILISATEUR_G1J ";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                if (pstmt.executeUpdate() >0){
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

        if (getNbUsers()){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginPanel();
                }
            });
        }else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MainInterface();
                }
            });
        }
    }
}