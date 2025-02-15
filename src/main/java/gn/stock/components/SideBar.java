package gn.stock.components;

import gn.stock.panels.FournisseurPanel;
import gn.stock.panels.ProduitPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        addButton("GESTION_STOCK");
        addButton("PRODUITS");
        addButton("FOURNISSEURS");
        addButton("Deconnexion");

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
        if(text == "PRODUITS"){
            contentPanel.add(new ProduitPanel(), BorderLayout.CENTER);
        }
        else if(text == "FOURNISSEURS"){
            contentPanel.add(new FournisseurPanel(), BorderLayout.CENTER);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainInterface();
            }
        });
    }
}