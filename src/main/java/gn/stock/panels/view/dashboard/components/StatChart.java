package gn.stock.panels.view.dashboard.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StatChart extends JPanel {
    private Map<String, Integer> data;
    private final List<Color> barColors = List.of(
            new Color(0, 180, 255), new Color(255, 99, 132),
            new Color(255, 205, 86), new Color(75, 192, 192),
            new Color(153, 102, 255), new Color(255, 159, 64)
    );

    public StatChart(Map<String, Integer> data) {
        this.data = data;
        setPreferredSize(new Dimension(500, 300)); // Taille du graphique
        setBackground(new Color(40, 40, 40)); // Fond sombre
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
        repaint(); // Redessiner le graphique après mise à jour
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int barWidth = (width - 2 * padding) / data.size();
        int maxVal = data.values().stream().max(Integer::compare).orElse(1);

        int x = padding;
        Random rand = new Random();
        int colorIndex = 0;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int barHeight = (int) ((double) entry.getValue() / maxVal * (height - 2 * padding));

            // Sélection d'une couleur aléatoire parmi la liste
            g2.setColor(barColors.get(colorIndex % barColors.size()));
            g2.fillRect(x, height - padding - barHeight, barWidth - 10, barHeight);

            // Contour des barres pour une meilleure visibilité
            g2.setColor(Color.WHITE);
            g2.drawRect(x, height - padding - barHeight, barWidth - 10, barHeight);

            // Affichage des valeurs sur les barres
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2.drawString(String.valueOf(entry.getValue()), x + (barWidth - 10) / 2 - 5, height - padding - barHeight - 5);

            // Affichage des noms des produits
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            String label = entry.getKey().length() > 8 ? entry.getKey().substring(0, 6) + ".." : entry.getKey();
            g2.drawString(label, x + (barWidth - 10) / 2 - 10, height - 5);

            x += barWidth;
            colorIndex++;
        }
    }
}
