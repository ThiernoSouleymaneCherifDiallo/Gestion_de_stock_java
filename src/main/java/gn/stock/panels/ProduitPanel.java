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
import javax.swing.JComboBox;
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
    private JComboBox<String> productStateField;
    private JTextField productCategoryField;
    private JComboBox<String> productCorbeilleField;
    private JTextField searchField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private int selectedRow = -1; // Indice de la ligne sélectionnée

    private JButton videchamp;// bouton pour vider les champs

    public ProduitPanel() {
        setLayout(new BorderLayout(20, 20)); // Utilisation de BorderLayout pour occuper tout l'espace
        setBackground(new Color(30, 30, 30)); // Couleur de fond sombre

        // Panneau pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(45, 45, 45)); // Couleur de fond du panneau d'entrée
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal

        // Ajout des composants
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 0; // Ligne 0
        gbc.gridwidth = 2; // Étiquette occupe deux colonnes
        JLabel titleLabel = new JLabel("Ajouter un Produit");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Couleur du texte
        inputPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Réinitialisation à une colonne
        gbc.gridy = 1; // Ligne 1
        inputPanel.add(createLabel("Nom :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productNameField = new JTextField(40);
        inputPanel.add(productNameField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 2; // Ligne 2
        inputPanel.add(createLabel("Prix :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productPriceField = new JTextField(10);
        inputPanel.add(productPriceField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 3; // Ligne 3
        inputPanel.add(createLabel("Quantité :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productQuantityField = new JTextField(10);
        inputPanel.add(productQuantityField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 4; // Ligne 4
        inputPanel.add(createLabel("État :"), gbc);
        gbc.gridx = 1; // Colonne 1
        String opt[] = {"Disponible", "En rupture de stock"};
        productStateField = new JComboBox<>(opt);
        inputPanel.add(productStateField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 5; // Ligne 5
        inputPanel.add(createLabel("Catégorie :"), gbc);
        gbc.gridx = 1; // Colonne 1
        productCategoryField = new JTextField(10);
        inputPanel.add(productCategoryField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 6; // Ligne 6
        inputPanel.add(createLabel("Corbeille :"), gbc);
        gbc.gridx = 1; // Colonne 1
        String choix[] = {"O", "N"};
        productCorbeilleField = new JComboBox<>(choix);
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

        // bouton qui vide les champs
        videchamp = new JButton("Nouveau");
        videchamp.setBackground(new Color(231, 76, 60)); // Couleur de fond rouge pour le bouton Supprimer
        videchamp.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        inputPanel.add(videchamp, gbc);

        videchamp.addActionListener(e -> {
            productNameField.setText("");
            productPriceField.setText("");
            productQuantityField.setText("");
            productCategoryField.setText("");
        });

        // Déplacer la section de recherche ici
        gbc.gridx = 0;
        gbc.gridy = 12; // Ligne après le bouton "Nouveau"
        gbc.gridwidth = 1;
        inputPanel.add(createLabel("Rechercher :"), gbc);
        gbc.gridx = 1;
        searchField = new JTextField("rechercher un produit", 20);
        inputPanel.add(searchField, gbc);
        gbc.gridx = 2;
        searchButton = new JButton("Rechercher");
        inputPanel.add(searchButton, gbc);

        // Ajout du panneau des champs de saisie avant le tableau
        add(inputPanel, BorderLayout.NORTH);

        // Configuration de la table
        String[] columnNames = {"Nom", "Prix", "Quantité", "État", "Catégorie", "Corbeille"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setBackground(new Color(50, 50, 50)); // Couleur de fond du tableau
        productTable.setForeground(Color.WHITE); // Couleur du texte du tableau
        productTable.setGridColor(new Color(70, 70, 70)); // Couleur des lignes de grille

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.getViewport().setBackground(new Color(50, 50, 50)); // Couleur de fond du viewport

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
        searchButton.addActionListener(e -> searchProduit());

        // Ajouter les boutons au panneau
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(45, 45, 45)); // Couleur de fond du panneau de boutons
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    public void loadProductsFromDatabase() {
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

    public void fillFieldsWithSelectedProduct() {
        productNameField.setText((String) tableModel.getValueAt(selectedRow, 0));
        productPriceField.setText((String) tableModel.getValueAt(selectedRow, 1).toString());
        productQuantityField.setText((String) tableModel.getValueAt(selectedRow, 2).toString());
        productStateField.setSelectedItem((String) tableModel.getValueAt(selectedRow, 3));
        productCategoryField.setText((String) tableModel.getValueAt(selectedRow, 4));
        productCorbeilleField.setSelectedItem((String) tableModel.getValueAt(selectedRow, 5));
    }

    @Override
    public void createProduit() {
        String name = productNameField.getText().trim();
        String priceStr = productPriceField.getText().trim();
        String quantityStr = productQuantityField.getText().trim();
        String state = productStateField.getSelectedItem().toString();
        String category = productCategoryField.getText().trim();
        String corbeille = productCorbeilleField.getSelectedItem().toString();

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

        // Vérifier si le produit existe déjà
        if (productExists(name)) {
            // Mettre à jour la quantité du produit existant
            updateProductQuantity(name, quantity);
        } else {
            // Insérer le produit dans la base de données
            insertProduct(name, price, quantity, state, category, corbeille);
        }
        loadProductsFromDatabase(); // Recharger les produits
    }

    @Override
    public void updateProduit() {
        if (selectedRow != -1) {
            String name = productNameField.getText().trim();
            String priceStr = productPriceField.getText().trim();
            String quantityStr = productQuantityField.getText().trim();
            String state = productStateField.getSelectedItem().toString();
            String category = productCategoryField.getText().trim();
            String corbeille = productCorbeilleField.getSelectedItem().toString();

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

    private boolean productExists(String name) {
        String sql = "SELECT COUNT(*) FROM PRODUIT_G1J WHERE nom_p = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateProductQuantity(String name, int quantity) {
        String sql = "UPDATE PRODUIT_G1J SET quantite_p = quantite_p + ? WHERE nom_p = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertProduct(String name, double price, int quantity, String state, String category, String corbeille) {
        String sql = "INSERT INTO PRODUIT_G1J (nom_p, prix_p, quantite_p, etat_p, category_p, corbeille_p) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, state);
            pstmt.setString(5, category);
            pstmt.setString(6, corbeille);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(String name, double price, int quantity, String state, String category, String corbeille) {
        String sql = "UPDATE PRODUIT_G1J SET prix_p = ?, quantite_p = ?, etat_p = ?, category_p = ?, corbeille_p = ? WHERE nom_p = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, state);
            pstmt.setString(4, category);
            pstmt.setString(5, corbeille);
            pstmt.setString(6, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String name) {
        String sql = "DELETE FROM PRODUIT_G1J WHERE nom_p = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchProduit() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadProductsFromDatabase();
            return;
        }

        String sql = "SELECT nom_p, prix_p, quantite_p, etat_p, category_p, corbeille_p FROM PRODUIT_G1J WHERE nom_p LIKE ? OR category_p LIKE ? OR etat_p LIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            pstmt.setString(3, "%" + searchText + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    String nom = rs.getString("nom_p");
                    double prix = rs.getDouble("prix_p");
                    int quantite = rs.getInt("quantite_p");
                    String etat = rs.getString("etat_p");
                    String category = rs.getString("category_p");
                    String corbeille = rs.getString("corbeille_p");

                    tableModel.addRow(new Object[]{nom, prix, quantite, etat, category, corbeille});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche des produits.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}