package gn.stock.components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanel extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, forgotPasswordButton;

    public LoginPanel() {
        // Configuration de la fenêtre
        setTitle("Connexion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre à l'écran
        setResizable(false);

        // Panneau principal avec couleur de fond bleue
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 204)); // Bleu
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(10)); // Espacement

        // Champs de saisie avec icônes
        usernameField = createTextField("Nom d'utilisateur", new ImageIcon("user_icon.png"));
        passwordField = createPasswordField("Mot de passe", new ImageIcon("lock_icon.png"));

        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(15));

        // Bouton Connexion
        loginButton = new JButton("Se connecter");
        stylizeButton(loginButton);
        panel.add(loginButton);

        // Bouton Mot de passe oublié
        forgotPasswordButton = new JButton("Mot de passe oublié ?");
        forgotPasswordButton.setForeground(Color.WHITE);
        forgotPasswordButton.setBackground(new Color(0, 102, 204));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        panel.add(forgotPasswordButton);

        // Ajout du panneau à la fenêtre
        add(panel);
        setVisible(true);

        // Gestion des événements
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = usernameField.getText(); // Utilisez l'email comme identifiant
                String password = new String(passwordField.getPassword());

                // Connexion à la base de données Oracle
                try (Connection connection = DriverManager.getConnection(
                        "jdbc:oracle:thin:@//localhost:1521/orcl", // URL de connexion Oracle
                        "c##gestionstock", // Nom d'utilisateur Oracle
                        "gesionstockpassword" // Mot de passe Oracle
                )) {
                    // Requête SQL pour vérifier les informations de connexion
                    String sql = "SELECT email, mot_de_passe FROM UTILISATEUR_G1J WHERE email = ?";
                    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                        pstmt.setString(1, email);
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                String storedPassword = rs.getString("mot_de_passe");

                                // Comparer le mot de passe saisi avec celui stocké (après chiffrement)
                                if (encryptPassword(password).equals(storedPassword)) {
                                    // Connexion réussie
                                    SwingUtilities.invokeLater(() -> {
                                        new MainInterface(); // Ouvrir l'interface principale
                                        dispose(); // Fermer la fenêtre de connexion
                                    });
                                } else {
                                    JOptionPane.showMessageDialog(LoginPanel.this,
                                            "Mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(LoginPanel.this,
                                        "Email non trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Veuillez contacter l'administrateur pour réinitialiser votre mot de passe.");
            }
        });
    }

    // Méthode pour chiffrer le mot de passe (identique à celle de votre classe UtilisateurPanel)
    private String encryptPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du chiffrement du mot de passe", e);
        }
    }

    // Méthodes utilitaires pour créer les champs de saisie et styliser les boutons
    private JTextField createTextField(String placeholder, ImageIcon icon) {
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(200, 15)); // Réduire la hauteur à 25 pixels
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)), // Bordure fine
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Marge intérieure
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        return textField;
    }

    private JPasswordField createPasswordField(String placeholder, ImageIcon icon) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 15)); // Réduire la hauteur à 25 pixels
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)), // Bordure fine
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Marge intérieure
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        return passwordField;
    }

    private void stylizeButton(JButton button) {
        button.setBackground(new Color(46, 204, 113));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPanel().setVisible(true));
    }
}