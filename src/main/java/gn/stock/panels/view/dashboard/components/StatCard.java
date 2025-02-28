package gn.stock.panels.view.dashboard.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StatCard extends JPanel {
    private static final int ARC_SIZE = 20;  // Taille du border-radius

    public StatCard(String title, int value, Icon icon) {
        setPreferredSize(new Dimension(200, 140)); // Taille plus grande
        setBackground(new Color(45, 45, 45)); // Couleur de fond sombre
        setBorder(createRoundedBorder()); // Ajouter un border-radius
        setLayout(new BorderLayout());

        // Titre
        JLabel titleLabel = new JLabel(title, SwingConstants.LEFT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);

        // Valeur
        JLabel valueLabel = new JLabel(String.valueOf(value), SwingConstants.LEFT);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        valueLabel.setForeground(Color.CYAN);

        // Icône
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Panel du haut (Titre + Icône)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(iconLabel, BorderLayout.EAST);

        // Ajout au panel
        add(topPanel, BorderLayout.NORTH);
        add(valueLabel, BorderLayout.SOUTH);
    }

    // Méthode pour créer un border avec un border-radius
    private Border createRoundedBorder() {
        return new Border() {
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 10, 10, 10);  // Marge autour du border
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 60, 60)); // Couleur du border

                // Dessiner le bord arrondi
                g2.fill(new RoundRectangle2D.Double(x, y, width - 1, height - 1, ARC_SIZE, ARC_SIZE));
            }
        };
    }
}
