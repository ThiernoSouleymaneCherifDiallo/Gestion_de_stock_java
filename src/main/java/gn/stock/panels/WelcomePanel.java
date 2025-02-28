package gn.stock.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WelcomePanel extends JPanel {

    public WelcomePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30)); // Couleur de fond sombre

        // Titre de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans GN Stock", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);

        // Sous-titre
        JLabel subtitleLabel = new JLabel("Votre solution de gestion de stock", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);

        // Image de bienvenue
        ImageIcon welcomeIcon = new ImageIcon(getClass().getResource("/welcome_image.png"));
        JLabel imageLabel = new JLabel(welcomeIcon, SwingConstants.CENTER);

        // Bouton pour commencer
        JButton startButton = new JButton("Commencer");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        startButton.setBackground(new Color(52, 152, 219));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panneau central pour le texte et l'image
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(welcomeLabel);
        centerPanel.add(subtitleLabel);
        centerPanel.add(imageLabel);

        // Ajout des composants au panneau principal
        add(centerPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }
} 