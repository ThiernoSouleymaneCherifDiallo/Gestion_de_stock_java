package gn.stock.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import gn.stock.base.Kpanel;
import gn.stock.interfaces.IProduit;

public class ProduitPanel extends Kpanel implements IProduit {

    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextField productQuantityField;
    private JTextField productStateField;
    private JTextField productCategoryField;
    private JTextField productCorbeilleField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private Dashboard dashboard; // Référence au tableau de bord
    private int selectedRow = -1; // Indice de la ligne sélectionnée

    public ProduitPanel(Dashboard dashboard) {
        this.dashboard = dashboard; // Initialisation de la référence
        setLayout(new BorderLayout(20, 20)); // Utilisation de BorderLayout pour occuper tout l'espace
        setBackground(new Color(245, 245, 245)); // Couleur de fond

        // Panneau pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal

        // Ajout des composants
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 0; // Ligne 0
        gbc.gridwidth = 2; // Étiquette occupe deux colonnes
        JLabel titleLabel = new JLabel("Ajouter un Produit");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204)); // Couleur du texte
        inputPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Réinitialisation à une colonne
        gbc.gridy = 1; // Ligne 1
        inputPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productNameField = new JTextField(20);
        inputPanel.add(productNameField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 2; // Ligne 2
        inputPanel.add(new JLabel("Prix :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productPriceField = new JTextField(10);
        inputPanel.add(productPriceField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 3; // Ligne 3
        inputPanel.add(new JLabel("Quantité :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productQuantityField = new JTextField(10);
        inputPanel.add(productQuantityField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 4; // Ligne 4
        inputPanel.add(new JLabel("État :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productStateField = new JTextField(10);
        inputPanel.add(productStateField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 5; // Ligne 5
        inputPanel.add(new JLabel("Catégorie :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productCategoryField = new JTextField(10);
        inputPanel.add(productCategoryField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 6; // Ligne 6
        inputPanel.add(new JLabel("Corbeille :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productCorbeilleField = new JTextField(3);
        inputPanel.add(productCorbeilleField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 7; // Ligne 7
        gbc.gridwidth = 2; // Le bouton occupe deux colonnes

        // Ajout de l'icône au bouton Ajouter
        ImageIcon addIcon = new ImageIcon(getClass().getResource("/add.png"));
        Image scaledAddIcon = addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        addButton = new JButton("Ajouter", new ImageIcon(scaledAddIcon));
        addButton.setBackground(new Color(52, 152, 219)); // Couleur de fond du bouton Ajouter
        addButton.setForeground(Color.WHITE); // Couleur du texte
        inputPanel.add(addButton, gbc);

        // Nouveau bouton pour mettre à jour
        ImageIcon updateIcon = new ImageIcon(getClass().getResource("/update_icon.png"));
        Image scaledUpdateIcon = updateIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        updateButton = new JButton("Mettre à jour", new ImageIcon(scaledUpdateIcon));
        updateButton.setBackground(new Color(46, 204, 113)); // Couleur de fond du bouton Mettre à jour
        updateButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridy = 8; // Ligne 8
        inputPanel.add(updateButton, gbc);

        // Nouveau bouton pour supprimer
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/delete_icon.png"));
        Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        deleteButton = new JButton("Supprimer", new ImageIcon(scaledDeleteIcon));
        deleteButton.setBackground(new Color(231, 76, 60)); // Couleur de fond rouge pour le bouton Supprimer
        deleteButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridy = 9; // Ligne 9
        inputPanel.add(deleteButton, gbc);

        // Ajout du panneau des champs de saisie avant le tableau
        add(inputPanel, BorderLayout.NORTH);

        // Configuration de la table
        String[] columnNames = {"Nom", "Prix", "Quantité", "État", "Catégorie", "Corbeille"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        // Ajout du tableau à la position centrale
        add(scrollPane, BorderLayout.CENTER);

        // Charger les données de la base de données
        loadProductsFromDatabase();

        // Écouteur de sélection de la table
        productTable.getSelectionModel().addListSelectionListener(event -> {
            selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                fillFieldsWithSelectedProduct();
            }
        });

        // Ajustement de la taille du JPanel pour occuper 100% de la largeur
        setPreferredSize(new Dimension(800, 600)); // Ajustez la largeur selon vos besoins
        setMinimumSize(new Dimension(800, 600)); // Définir une taille minimale

        // Ajouter des écouteurs d'événements pour les boutons
        addButton.addActionListener(e -> createProduit());
        updateButton.addActionListener(e -> updateProduit());
        deleteButton.addActionListener(e -> deleteProduit());

        // Ajouter les boutons au panneau
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadProductsFromDatabase() {
        String sql = "SELECT nom_p, prix_p, quantite_p, etat_p, category_p, corbeille_p FROM PRODUIT_G1J"; // Mise à jour du nom de la table

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Vider le modèle de la table avant de le remplir
            tableModel.setRowCount(0);

            // Remplir le modèle de la table avec les données de la base de données
            while (rs.next()) {
                String nom = rs.getString("nom_p");
                double prix = rs.getDouble("prix_p");
                int quantite = rs.getInt("quantite_p");
                String etat = rs.getString("etat_p");
                String category = rs.getString("category_p");
                String corbeille = rs.getString("corbeille_p");

                tableModel.addRow(new Object[]{nom, prix, quantite, etat, category, corbeille});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des produits depuis la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillFieldsWithSelectedProduct() {
        productNameField.setText((String) tableModel.getValueAt(selectedRow, 0));
        productPriceField.setText((String) tableModel.getValueAt(selectedRow, 1).toString());
        productQuantityField.setText((String) tableModel.getValueAt(selectedRow, 2).toString());
        productStateField.setText((String) tableModel.getValueAt(selectedRow, 3));
        productCategoryField.setText((String) tableModel.getValueAt(selectedRow, 4));
        productCorbeilleField.setText((String) tableModel.getValueAt(selectedRow, 5));
    }

    @Override
    public void createProduit() {
        String name = productNameField.getText().trim();
        String priceStr = productPriceField.getText().trim();
        String quantityStr = productQuantityField.getText().trim();
        String state = productStateField.getText().trim();
        String category = productCategoryField.getText().trim();
        String corbeille = productCorbeilleField.getText().trim();

        // Vérification que les champs ne sont pas vides
        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || state.isEmpty() || category.isEmpty() || corbeille.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        int quantity;
        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le prix doit être un nombre valide et la quantité doit être un entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insérer le produit dans la base de données
        insertProduct(name, price, quantity, state, category, corbeille);
        loadProductsFromDatabase(); // Recharger les produits
    }

    @Override
    public void updateProduit() {
        if (selectedRow != -1) {
            String name = productNameField.getText().trim();
            String priceStr = productPriceField.getText().trim();
            String quantityStr = productQuantityField.getText().trim();
            String state = productStateField.getText().trim();
            String category = productCategoryField.getText().trim();
            String corbeille = productCorbeilleField.getText().trim();

            // Vérification que les champs ne sont pas vides
            if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || state.isEmpty() || category.isEmpty() || corbeille.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price;
            int quantity;
            try {
                price = Double.parseDouble(priceStr);
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Le prix doit être un nombre valide et la quantité doit être un entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Mettre à jour le produit dans la base de données
            updateProduct(name, price, quantity, state, category, corbeille);
            loadProductsFromDatabase(); // Recharger les produits
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteProduit() {
        if (selectedRow != -1) {
            String name = (String) tableModel.getValueAt(selectedRow, 0); // Récupérer le nom du produit à supprimer

            // Supprimer le produit de la base de données
            deleteProduct(name);
            loadProductsFromDatabase(); // Recharger les produits
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void viewProduit() {
        // Logique pour afficher les détails du produit
    }
}
