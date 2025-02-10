package gn.stock.components;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class SideBar extends JPanel {
    private JTree jTree;
    private JPanel contentPanel;

    public SideBar() {
        setLayout(new BorderLayout());

        // Créer le nœud racine
        DefaultMutableTreeNode stockNode = new DefaultMutableTreeNode("Stock");

        // Créer des nœuds pour les différentes fonctionnalités
        DefaultMutableTreeNode produitNode = new DefaultMutableTreeNode("Produits");
        DefaultMutableTreeNode utilisateurNode = new DefaultMutableTreeNode("Utilisateurs");
        DefaultMutableTreeNode transactionNode = new DefaultMutableTreeNode("Transactions");
        DefaultMutableTreeNode fournisseurNode = new DefaultMutableTreeNode("Fournisseurs");

        // Ajouter les nœuds à la racine
        stockNode.add(produitNode);
        stockNode.add(utilisateurNode);
        stockNode.add(transactionNode);
        stockNode.add(fournisseurNode);

        // Créer le modèle d'arbre avec le nœud racine
        DefaultTreeModel treeModel = new DefaultTreeModel(stockNode);
        jTree = new JTree(treeModel);

        // Configurer le JTree
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.setShowsRootHandles(true);
        jTree.setRootVisible(true);

        // Appliquer un renderer personnalisé
        jTree.setCellRenderer(new CustomTreeCellRenderer());

        // Ajouter un écouteur d'événements pour gérer les clics sur les nœuds
        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-clic
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
                    if (selectedNode != null) {
                        String nodeName = selectedNode.getUserObject().toString();
                        handleNodeClick(nodeName);
                    }
                }
            }
        });

        // Créer un JScrollPane pour le JTree
        JScrollPane treeScrollPane = new JScrollPane(jTree);
        add(treeScrollPane, BorderLayout.WEST);

        // Créer un panel pour le contenu à droite
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    // Méthode pour gérer les actions en fonction du nœud cliqué
    private void handleNodeClick(String nodeName) {
        contentPanel.removeAll(); // Nettoyer le contenu précédent

        JLabel contentLabel = new JLabel();
        contentLabel.setHorizontalAlignment(JLabel.CENTER);
        contentLabel.setFont(new Font("Serif", Font.BOLD, 24));

        switch (nodeName) {
            case "Produits":
                contentLabel.setText("Gestion des Produits");
                break;
            case "Utilisateurs":
                contentLabel.setText("Gestion des Utilisateurs");
                break;
            case "Transactions":
                contentLabel.setText("Gestion des Transactions");
                break;
            case "Fournisseurs":
                contentLabel.setText("Gestion des Fournisseurs");
                break;
            default:
                contentLabel.setText("Action non définie pour : " + nodeName);
                break;
        }

        contentPanel.add(contentLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Renderer personnalisé pour le JTree
    static class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        private final Color selectionBackground = new Color(52, 152, 219);
        private final Color selectionForeground = Color.WHITE;
        private final Color normalBackground = Color.WHITE;
        private final Color normalForeground = Color.BLACK;

        private final Icon stockIcon = new ImageIcon("icons/stock.png");
        private final Icon produitIcon = new ImageIcon("icons/product.png");
        private final Icon utilisateurIcon = new ImageIcon("icons/user.png");
        private final Icon transactionIcon = new ImageIcon("icons/transaction.png");
        private final Icon fournisseurIcon = new ImageIcon("icons/supplier.png");

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            String nodeName = node.getUserObject().toString();

            // Appliquer les icônes selon le nœud
            switch (nodeName) {
                case "Stock":
                    setIcon(stockIcon);
                    break;
                case "Produits":
                    setIcon(produitIcon);
                    break;
                case "Utilisateurs":
                    setIcon(utilisateurIcon);
                    break;
                case "Transactions":
                    setIcon(transactionIcon);
                    break;
                case "Fournisseurs":
                    setIcon(fournisseurIcon);
                    break;
                default:
                    setIcon(null);
                    break;
            }

            // Modifier les couleurs en fonction de la sélection
            if (selected) {
                setBackground(selectionBackground);
                setForeground(selectionForeground);
            } else {
                setBackground(normalBackground);
                setForeground(normalForeground);
            }

            return this;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestion de Stock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        SideBar sideBar = new SideBar();
        frame.add(sideBar);

        frame.setVisible(true);
    }
}