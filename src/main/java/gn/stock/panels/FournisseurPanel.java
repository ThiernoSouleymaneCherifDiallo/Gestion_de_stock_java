package gn.stock.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gn.stock.base.Kpanel;
import gn.stock.interfaces.IProduit;
import oracle.jdbc.proxy.annotation.Pre;

public class FournisseurPanel extends Kpanel implements IProduit {

    private JTextField nomPrenomField;
    private JTextField telField;
    private JTextField adresseField;
    private JTextField emailField;
    private JComboBox<String> txtProduit;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable fournisseurTable;
    private DefaultTableModel tableModel;
    private int selectedRow = -1; // Indice de la ligne sélectionnée

    public FournisseurPanel() {
        setLayout(new BorderLayout(20, 20)); // Utilisation de BorderLayout pour occuper tout l'espace
        setBackground(new Color(245, 245, 245)); // Couleur de fond

        // Configuration de la table
        String[] columnNames = { "ID", "Nom et Prénom", "Téléphone", "Adresse", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        fournisseurTable = new JTable(tableModel);
        loadFournisseurs();
        JScrollPane scrollPane = new JScrollPane(fournisseurTable);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        // Panneau pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal

        // Ajout des composants
        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 0; // Ligne 0
        gbc.gridwidth = 2; // Étiquette occupe deux colonnes
        JLabel titleLabel = new JLabel("Ajouter un Fournisseur");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204)); // Couleur du texte
        inputPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; // Réinitialisation à une colonne
        gbc.gridy = 1; // Ligne 1
        inputPanel.add(new JLabel("Nom et Prénom :"), gbc);
        gbc.gridx = 1; // Colonne 1
        nomPrenomField = new JTextField(20);
        inputPanel.add(nomPrenomField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 2; // Ligne 2
        inputPanel.add(new JLabel("Téléphone :"), gbc);
        gbc.gridx = 1; // Colonne 1
        telField = new JTextField(10);
        inputPanel.add(telField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 3; // Ligne 3
        inputPanel.add(new JLabel("Adresse :"), gbc);
        gbc.gridx = 1; // Colonne 1
        adresseField = new JTextField(30);
        inputPanel.add(adresseField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 4; // Ligne 4
        inputPanel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1; // Colonne 1
        emailField = new JTextField(30);
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 5; // Ligne 4
        inputPanel.add(new JLabel("Produit :"), gbc);
        gbc.gridx = 1; // Colonne 1
        txtProduit = new JComboBox<>(getIDsProduit());
        inputPanel.add(txtProduit, gbc);

        gbc.gridx = 0; // Colonne 0
        gbc.gridy = 6; // Ligne 5
        gbc.gridwidth = 2; // Le bouton occupe deux colonnes

        // Ajout de l'icône au bouton Ajouter
        ImageIcon addIcon = new ImageIcon(getClass().getResource("/add.png"));
        Image scaledAddIcon = addIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        addButton = new JButton("Ajouter", new ImageIcon(scaledAddIcon));
        addButton.setBackground(new Color(52, 152, 219)); // Couleur de fond du bouton Ajouter
        addButton.setForeground(Color.WHITE); // Couleur du texte
        inputPanel.add(addButton, gbc);
        addButton.addActionListener(e -> createProduit());

        // Nouveau bouton pour mettre à jour
        ImageIcon updateIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/update_icon.png")));
        Image scaledUpdateIcon = updateIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        updateButton = new JButton("Mettre à jour", new ImageIcon(scaledUpdateIcon));
        updateButton.setBackground(new Color(46, 204, 113)); // Couleur de fond du bouton Mettre à jour
        updateButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridy = 7; // Ligne 6
        inputPanel.add(updateButton, gbc);
        updateButton.addActionListener(e -> updateProduit());

        // Nouveau bouton pour supprimer
        ImageIcon deleteIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/delete_icon.png")));
        Image scaledDeleteIcon = deleteIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Redimensionner l'icône
        deleteButton = new JButton("Supprimer", new ImageIcon(scaledDeleteIcon));
        deleteButton.setBackground(new Color(231, 76, 60)); // Couleur de fond rouge pour le bouton Supprimer
        deleteButton.setForeground(Color.WHITE); // Couleur du texte
        gbc.gridy = 8; // Ligne 7
        inputPanel.add(deleteButton, gbc);
        deleteButton.addActionListener(e -> deleteProduit());

        // Ajout du panneau d'entrée à l'interface
        add(inputPanel, BorderLayout.NORTH); // Ajout du panneau d'entrée en haut

        // Ajout de la table à l'interface
        add(scrollPane, BorderLayout.CENTER); // Ajout du JScrollPane contenant la table

        // Écouteur de sélection de la table
        fournisseurTable.getSelectionModel().addListSelectionListener(event -> {
            selectedRow = fournisseurTable.getSelectedRow();
            if (selectedRow != -1) {
                fillFieldsWithSelectedFournisseur();
            }
        });

        // Ajustement de la taille du JPanel pour occuper 100% de la largeur
        setPreferredSize(new Dimension(800, 600)); // Ajustez la largeur selon vos besoins
        setMinimumSize(new Dimension(800, 600)); // Définir une taille minimale
    }

    private void loadFournisseurs() {
        tableModel.setRowCount(0);
        String sql = "select * from fournisseur_g1j ORDER BY id_p DESC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("ID_P") + "",
                            resultSet.getString("NOM_PRENOM_F"),
                            resultSet.getString("TEL_F"),
                            resultSet.getString("ADRESSE_F"),
                            resultSet.getString("EMAIL_F")
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Vector<String> getIDsProduit() {
        Vector<String> idsProduits = new Vector<>();
        String requete = "SELECT id_p from produit_g1j";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_p");
                    idsProduits.add(id + "");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return idsProduits;
    }

    private void fillFieldsWithSelectedFournisseur() {
        nomPrenomField.setText((String) tableModel.getValueAt(selectedRow, 0));
        telField.setText((String) tableModel.getValueAt(selectedRow, 1));
        adresseField.setText((String) tableModel.getValueAt(selectedRow, 2));
        emailField.setText((String) tableModel.getValueAt(selectedRow, 3));
    }

    @Override
    public void createProduit() {
        String nomPrenom = nomPrenomField.getText().trim();
        String tel = telField.getText().trim();
        String adresse = adresseField.getText().trim();
        String email = emailField.getText().trim();
        int idProduct = Integer.parseInt(txtProduit.getSelectedItem().toString());

        // Vérification que les champs ne sont pas vides
        if (nomPrenom.isEmpty() || tel.isEmpty() || adresse.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Insertion dans la base de données
        insertFournisseur(nomPrenom, tel, adresse, email, idProduct);

        // Vider les champs après l'ajout
        nomPrenomField.setText("");
        telField.setText("");
        adresseField.setText("");
        emailField.setText("");
    }

    private void insertFournisseur(String nomPrenom, String tel, String adresse, String email, int product_id) {
        String sql = "INSERT INTO FOURNISSEUR_G1J(ID_P, NOM_PRENOM_F,TEL_F , ADRESSE_F, EMAIL_F) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, product_id);
            pstmt.setString(2, nomPrenom);
            pstmt.setString(3, tel);
            pstmt.setString(4, adresse);
            pstmt.setString(5, email);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Fournisseur inseré avec succés", "Info", JOptionPane.INFORMATION_MESSAGE);
            loadFournisseurs();
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void updateProduit() {
        // Logique pour supprimer le fournisseur
        int selectedRow = fournisseurTable.getSelectedRow();
        if (selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Selectionner une ligne pour la supprimer !", " Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String requete = "UPDATE FOURNISSEUR_G1J SET NOM_PRENOM_F = ?, TEL_F=?, ADRESSE_F = ?, EMAIL_F = ? WHERE ID_P = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
            preparedStatement.setString(1, nomPrenomField.getText());
            preparedStatement.setString(2, telField.getText());
            preparedStatement.setString(3, adresseField.getText());
            preparedStatement.setString(4, emailField.getText());
            preparedStatement.setInt(5, Integer.parseInt(fournisseurTable.getValueAt(selectedRow, 0).toString()));

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Mise a jour reussie !");
            loadFournisseurs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }





    }

    public void listeners(){
        fournisseurTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = fournisseurTable.getSelectedRow();
                if (selectedRow != -1){
                    return;
                }

                nomPrenomField.setText((String) tableModel.getValueAt(selectedRow, 1));
                telField.setText((String) tableModel.getValueAt(selectedRow, 2));
                adresseField.setText((String) tableModel.getValueAt(selectedRow, 3));
                emailField.setText((String) tableModel.getValueAt(selectedRow, 4));
            }
        });
    }

    @Override
    public void deleteProduit() {
        // Logique pour supprimer le fournisseur
        int selectedRow = fournisseurTable.getSelectedRow();
        if (selectedRow == -1){
            JOptionPane.showMessageDialog(this, "Selectionner une ligne pour la supprimer !", " Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choix = JOptionPane.showConfirmDialog(this, "Etes vous sur de supprimer ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (choix == JOptionPane.YES_OPTION){
            String requete = "DELETE FROM FOURNISSEUR_G1J WHERE ID_P = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(requete)){
                preparedStatement.setInt(1, selectedRow);

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Suppression reussie avec succés", "Info", JOptionPane.INFORMATION_MESSAGE);
                loadFournisseurs();
            } catch (RuntimeException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        tableModel.removeRow(selectedRow);
        selectedRow = -1; // Réinitialiser la sélection
        // Réinitialiser les champs de texte
        nomPrenomField.setText("");
        telField.setText("");
        adresseField.setText("");
        emailField.setText("");
    }

    @Override
    public void viewProduit() {
        // Logique pour afficher les détails du fournisseur
    }
}
