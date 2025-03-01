package gn.stock.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import gn.stock.base.Kpanel;
import gn.stock.interfaces.ITransaction;

public class TransacPanel extends Kpanel implements ITransaction {

    private JComboBox<String> productComboBox;
    private JComboBox<String> userComboBox;
    private JComboBox<String> typeTransactionComboBox;
    private JTextField quantityField;
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private JButton createButton, updateButton, deleteButton, addButton;
    private JTextField idTransactionField;
    private JCheckBox receiptCheckBox;
    private int currentTransactionId; // Variable pour stocker l'ID de la transaction en cours
    private Timer refreshTimer;
    private JTextField transactionIdField;
    private JButton afficherTransactionsButton;
    private boolean showingTransactions = false; // Variable pour suivre l'état actuel

    public TransacPanel() {
        setLayout(new BorderLayout(20, 20)); // Utilisation de BorderLayout pour occuper tout l'espace
        setBackground(new Color(30, 30, 30)); // Couleur de fond sombre

        // Initialisation de la case à cocher
        receiptCheckBox = new JCheckBox("Panier");
        receiptCheckBox.setBackground(new Color(45, 45, 45)); // Couleur de fond
        receiptCheckBox.setForeground(Color.WHITE); // Couleur du texte

        // Table pour afficher les produits sélectionnés
        tableModel = new DefaultTableModel(
                new Object[]{"ID Produit", "Nom du Produit", "Quantité du Produit", "Nom Utilisateur"}, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setBackground(new Color(50, 50, 50)); // Couleur de fond du tableau
        transactionTable.setForeground(Color.WHITE); // Couleur du texte du tableau
        transactionTable.setGridColor(new Color(70, 70, 70)); // Couleur des lignes de grille

        JScrollPane tableScrollPane = new JScrollPane(transactionTable);
        tableScrollPane.setPreferredSize(new Dimension(400, 150));
        tableScrollPane.getViewport().setBackground(new Color(50, 50, 50)); // Couleur de fond du viewport

        // Panneau pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(45, 45, 45)); // Couleur de fond du panneau d'entrée
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal
        gbc.weightx = 1.0; // Permet aux composants de s'étendre horizontalement
        gbc.anchor = GridBagConstraints.WEST; // Alignement à gauche

        // Ajout des composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Ajouter une transaction");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Couleur du texte
        inputPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        inputPanel.add(createLabel("Type de Transaction :"), gbc);
        gbc.gridx = 1;
        typeTransactionComboBox = new JComboBox<>(new String[]{"Entrée", "Sortie"});
        inputPanel.add(typeTransactionComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(createLabel("Produit :"), gbc);
        gbc.gridx = 1;
        productComboBox = new JComboBox<>();
        inputPanel.add(productComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(createLabel("Quantité :"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(10);
        inputPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(createLabel("Utilisateur :"), gbc);
        gbc.gridx = 1;
        userComboBox = new JComboBox<>();
        inputPanel.add(userComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(receiptCheckBox, gbc);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(45, 45, 45));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        // Bouton pour ajouter un produit au tableau
        addButton = new JButton("Ajouter au panier");
        addButton.setBackground(new Color(52, 152, 219)); // Couleur de fond bleue
        addButton.setForeground(Color.WHITE); // Couleur du texte
        buttonPanel.add(addButton, gbc);
        addButton.addActionListener(e -> addProductToTable());

        // Bouton pour créer une transaction
        ImageIcon createIcon = new ImageIcon(getClass().getResource("/add.png"));
        Image scaledCreateIcon = createIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        createButton = new JButton("Créer", new ImageIcon(scaledCreateIcon));
        createButton.setBackground(new Color(52, 152, 219)); // Couleur de fond bleue
        createButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridx = 1;
        buttonPanel.add(createButton, gbc);
        createButton.addActionListener(e -> createTransation());

        // Bouton pour mettre à jour une transaction
        ImageIcon updateIcon = new ImageIcon(getClass().getResource("/update_icon.png"));
        Image scaledUpdateIcon = updateIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        updateButton = new JButton("Modifier", new ImageIcon(scaledUpdateIcon));
        updateButton.setBackground(new Color(46, 204, 113)); // Couleur de fond verte
        updateButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridx = 2;
        buttonPanel.add(updateButton, gbc);
        updateButton.addActionListener(e -> updateTransaction());

        // Bouton pour supprimer une transaction
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/delete_icon.png"));
        Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        deleteButton = new JButton("Supprimer", new ImageIcon(scaledDeleteIcon));
        deleteButton.setBackground(new Color(231, 76, 60)); // Couleur de fond rouge
        deleteButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridx = 3;
        buttonPanel.add(deleteButton, gbc);
        deleteButton.addActionListener(e -> deleteTransaction());

        // Initialisation du bouton "Afficher les transactions"
        afficherTransactionsButton = new JButton("Afficher");
        afficherTransactionsButton.setBackground(new Color(52, 152, 219)); // Couleur de fond bleue
        afficherTransactionsButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridx = 4; // Assurez-vous que cette position ne chevauche pas d'autres composants
        buttonPanel.add(afficherTransactionsButton, gbc);
        afficherTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingTransactions) {
                    // Revenir à l'affichage du panier
                    afficherPanier();
                    afficherTransactionsButton.setText("Afficher");
                    receiptCheckBox.setText("Panier");
                } else {
                    // Afficher les transactions
                    afficherTransactions();
                    afficherTransactionsButton.setText("Panier");
                    receiptCheckBox.setText("Transaction");
                }
                showingTransactions = !showingTransactions; // Inverser l'état
            }
        });

        // Ajout du panneau d'entrée et des boutons à l'interface
        add(inputPanel, BorderLayout.NORTH); // Ajout du panneau d'entrée en haut
        add(buttonPanel, BorderLayout.SOUTH); // Ajout du panneau de boutons en bas

        // Ajout de la table à l'interface
        add(tableScrollPane, BorderLayout.CENTER); // Ajout du JScrollPane contenant la table

        // Charger les données depuis la base de données
        loadProductsFromDatabase();
        loadUsersFromDatabase();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Charge les produits depuis la base de données
     */
    private void loadProductsFromDatabase() {
        productComboBox.removeAllItems(); // Effacer les anciens produits
        String query = "SELECT NOM_P FROM PRODUIT_G1J";

        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Connexion à la base de données non établie !");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productComboBox.addItem(rs.getString("NOM_P"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des produits !");
        }
    }

    /**
     * Charge les utilisateurs depuis la base de données
     */
    private void loadUsersFromDatabase() {
        userComboBox.removeAllItems(); // Effacer les anciens utilisateurs
        String query = "SELECT NOM, PRENOM FROM UTILISATEUR_G1J";

        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Connexion à la base de données non établie !");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String fullName = rs.getString("NOM") + " " + rs.getString("PRENOM");
                userComboBox.addItem(fullName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs !");
        }
    }

    /**
     * Ajoute un produit au tableau
     */
    private void addProductToTable() {
        String productName = (String) productComboBox.getSelectedItem();
        String quantityText = quantityField.getText();
        String selectedUserFullName = (String) userComboBox.getSelectedItem();

        if (productName == null || productName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit");
            return;
        }

        if (quantityText == null || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une quantité");
            return;
        }

        if (selectedUserFullName == null || selectedUserFullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La quantité doit être un nombre entier");
            return;
        }

        try {
            int productId = getProductIdByName(productName);
            tableModel.addRow(new Object[]{productId, productName, quantity, selectedUserFullName});
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du produit au tableau : " + e.getMessage());
        }
    }

    /**
     * Crée une nouvelle transaction à partir des produits dans le tableau
     */
    @Override
    public void createTransation() {
        try {
            String selectedUserFullName = (String) userComboBox.getSelectedItem();
            if (selectedUserFullName == null || selectedUserFullName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur");
                return;
            }

            int idUtilisateur = getUserIdByFullName(selectedUserFullName);
            String typeTransaction = typeTransactionComboBox.getSelectedItem().toString().equalsIgnoreCase("Entrée") ? "entree" : "sortie";
            boolean recus = receiptCheckBox.isSelected();

            connection.setAutoCommit(false);

            double montantTotal = 0;
            int idTransaction;

            String sqlTransaction = "INSERT INTO TRANSACTION_G1J (ID_T, ID_UTILISATEUR, MONTANT_T, TYPE_T, RECUS) " +
                    "VALUES (TRANSACTION_SEQ.NEXTVAL, ?, ?, ?, ?)";
            try (PreparedStatement stmtTransaction = connection.prepareStatement(sqlTransaction, new String[]{"ID_T"})) {
                stmtTransaction.setInt(1, idUtilisateur);
                stmtTransaction.setDouble(2, 0.0);
                stmtTransaction.setString(3, typeTransaction);
                stmtTransaction.setBoolean(4, recus);

                stmtTransaction.executeUpdate();

                // Récupérer l'ID généré
                try (ResultSet generatedKeys = stmtTransaction.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idTransaction = generatedKeys.getInt(1); // Récupérer l'ID en tant que nombre
                    } else {
                        throw new SQLException("Échec de la création de la transaction, aucun ID généré");
                    }
                }

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int productId = (int) tableModel.getValueAt(i, 0);
                    int quantity = (int) tableModel.getValueAt(i, 2);

                    double prixUnitaire = getProductPrice(productId);

                    // Vérification de la quantité disponible pour les sorties
                    if (typeTransaction.equalsIgnoreCase("sortie")) {
                        int availableQuantity = getAvailableQuantity(productId);
                        if (quantity > availableQuantity) {
                            JOptionPane.showMessageDialog(this, "Quantité demandée pour le produit " + tableModel.getValueAt(i, 1) + " dépasse la quantité disponible (" + availableQuantity + ").", "Erreur", JOptionPane.ERROR_MESSAGE);
                            connection.rollback();
                            return;
                        }
                    }

                    String sqlLigneTransaction = "INSERT INTO LIGNETRANSACTION (ID_T, ID_P, QUANTITE) VALUES (?, ?, ?)";
                    try (PreparedStatement stmtLigne = connection.prepareStatement(sqlLigneTransaction)) {
                        stmtLigne.setInt(1, idTransaction);
                        stmtLigne.setInt(2, productId);
                        stmtLigne.setInt(3, quantity);
                        stmtLigne.executeUpdate();
                    }

                    String sqlUpdateProduit = typeTransaction.equalsIgnoreCase("entree") ?
                            "UPDATE PRODUIT_G1J SET QUANTITE_P = QUANTITE_P + ? WHERE ID_P = ?" :
                            "UPDATE PRODUIT_G1J SET QUANTITE_P = QUANTITE_P - ? WHERE ID_P = ?";
                    try (PreparedStatement stmtUpdateProduit = connection.prepareStatement(sqlUpdateProduit)) {
                        stmtUpdateProduit.setInt(1, quantity);
                        stmtUpdateProduit.setInt(2, productId);
                        stmtUpdateProduit.executeUpdate();
                    }

                    montantTotal += prixUnitaire * quantity;
                }

                String sqlUpdateTransaction = "UPDATE TRANSACTION_G1J SET MONTANT_T = ? WHERE ID_T = ?";
                try (PreparedStatement stmtUpdateTransaction = connection.prepareStatement(sqlUpdateTransaction)) {
                    stmtUpdateTransaction.setDouble(1, montantTotal);
                    stmtUpdateTransaction.setInt(2, idTransaction);
                    stmtUpdateTransaction.executeUpdate();
                }

                connection.commit();
                JOptionPane.showMessageDialog(this, "Transaction ajoutée avec succès ! ID: " + idTransaction + ", Montant: " + montantTotal);

            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la transaction : " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la création de la transaction : " + e.getMessage());
        }
    }

    // Méthode pour obtenir la quantité disponible d'un produit
    private int getAvailableQuantity(int productId) throws SQLException {
        String query = "SELECT QUANTITE_P FROM PRODUIT_G1J WHERE ID_P = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("QUANTITE_P");
                } else {
                    throw new SQLException("Produit non trouvé avec l'ID : " + productId);
                }
            }
        }
    }

    // Les autres méthodes (getProductIdByName, getProductPrice, getUserIdByFullName, etc.) restent inchangées

    private double getProductPrice(int productId) throws SQLException {
        String query = "SELECT PRIX_P FROM PRODUIT_G1J WHERE ID_P = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("PRIX_P");
                } else {
                    throw new SQLException("Produit non trouvé avec l'ID : " + productId);
                }
            }
        }
    }

    private int getUserIdByFullName(String fullName) throws SQLException {
        String[] parts = fullName.split(" ");
        if (parts.length < 2) {
            throw new SQLException("Format de nom d'utilisateur invalide: " + fullName);
        }

        String nom = parts[0];
        String prenom = parts[1];

        String query = "SELECT ID_UTILISATEUR FROM UTILISATEUR_G1J WHERE NOM = ? AND PRENOM = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_UTILISATEUR");
                } else {
                    throw new SQLException("Utilisateur non trouvé: " + fullName);
                }
            }
        }
    }

    /**
     * Récupère l'ID d'un produit à partir de son nom
     */
    private int getProductIdByName(String productName) throws SQLException {
        String query = "SELECT ID_P FROM PRODUIT_G1J WHERE NOM_P = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String idValue = rs.getObject("ID_P").toString();

                    try {
                        return Integer.parseInt((String) idValue);
                    } catch (NumberFormatException e) {
                        throw new SQLException("Valeur de ID_P invalide : " + idValue);
                    }
                    /*if (idValue instanceof Integer) {
                        return (Integer) idValue;
                    } else if (idValue instanceof String) {
                        try {
                            return Integer.parseInt((String) idValue);
                        } catch (NumberFormatException e) {
                            throw new SQLException("Valeur de ID_P invalide : " + idValue);
                        }
                    } else {
                        throw new SQLException("Type de donnée non pris en charge pour ID_P : " + idValue);
                    }*/
                } else {
                    throw new SQLException("Produit non trouvé : " + productName);
                }
            }
        }
    }


    @Override
    public void updateTransaction() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité de mise à jour non encore implémentée");
    }

    @Override
    public void deleteTransaction() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité de suppression non encore implémentée");
    }

    @Override
    public void viewTransaction() {
        JOptionPane.showMessageDialog(this, "Fonctionnalité d'affichage non encore implémentée");
    }

    private void afficherPanier() {
        // Mettre à jour l'en-tête du tableau pour le panier initial
        tableModel.setColumnIdentifiers(new Object[]{"ID Produit", "Nom du Produit", "Quantité du Produit", "Nom Utilisateur"});

        // Effacer les lignes existantes dans le modèle de tableau
        tableModel.setRowCount(0);

        // Logique pour remplir le tableau avec les données du panier initial
        // Par exemple, vous pouvez réutiliser les données précédemment ajoutées au tableau
    }

    private void afficherTransactions() {
        // Mettre à jour l'en-tête du tableau pour inclure le produit et la quantité
        tableModel.setColumnIdentifiers(new Object[]{"ID Transaction", "ID Utilisateur", "Produit", "Quantité", "Montant", "Type", "Reçu"});

        // Effacer les lignes existantes dans le modèle de tableau
        tableModel.setRowCount(0);

        String query = "SELECT T.ID_T, T.ID_UTILISATEUR, P.NOM_P, LT.QUANTITE, T.MONTANT_T, T.TYPE_T, T.RECUS " +
                       "FROM TRANSACTION_G1J T " +
                       "JOIN LIGNETRANSACTION LT ON T.ID_T = LT.ID_T " +
                       "JOIN PRODUIT_G1J P ON LT.ID_P = P.ID_P";

        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Connexion à la base de données non établie !");
            return;
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idTransaction = rs.getInt("ID_T");
                int idUtilisateur = rs.getInt("ID_UTILISATEUR");
                String nomProduit = rs.getString("NOM_P");
                int quantite = rs.getInt("QUANTITE");
                double montant = rs.getDouble("MONTANT_T");
                String type = rs.getString("TYPE_T");
                boolean recus = rs.getBoolean("RECUS");

                // Ajoutez les données récupérées au modèle de tableau
                tableModel.addRow(new Object[]{idTransaction, idUtilisateur, nomProduit, quantite, montant, type, recus});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des transactions !");
        }
    }

    public static void main(String[] args) {
        new TransacPanel();
    }
}