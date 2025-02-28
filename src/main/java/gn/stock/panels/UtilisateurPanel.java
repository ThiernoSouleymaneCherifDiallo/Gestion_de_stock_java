package gn.stock.panels;

import gn.stock.base.Kpanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurPanel extends Kpanel {
    private JTextField nomField, prenomField, telField, adresseField, emailField, searchField;
    private  JComboBox roleField;
    private JPasswordField passwordField, confirmPasswordField;
    private JTable userTable;
    private DefaultTableModel tableModel;

    // Les méthodes écrites par Big dans la classe Query.java
    public boolean insertUser(String nom, String prenom, String tel, String adresse, String email, String role, String password) {
        String sql = "INSERT INTO UTILISATEUR_G1J (nom, prenom, tel, adresse, email, role, mot_de_passe) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, tel);
            pstmt.setString(4, adresse);
            pstmt.setString(5, email);
            pstmt.setString(6, role);
            pstmt.setString(7, password);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getUsers(DefaultTableModel tableModel, String emailSearch) {
        tableModel.setRowCount(0);
        String sql = "SELECT id_utilisateur, nom, prenom, tel, adresse, email, role, mot_de_passe FROM UTILISATEUR_G1J " +
                "WHERE email LIKE ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + emailSearch + "%" );
            try(ResultSet rs = pstmt.executeQuery() ){
                while (rs.next()) {
                    int id = rs.getInt("id_utilisateur");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String tel = rs.getString("tel");
                    String adresse = rs.getString("adresse");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    String password = rs.getString("mot_de_passe");

                    tableModel.addRow(new Object[]{id, nom, prenom, tel, adresse, email, role, password});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMail(String email) {
        String sql = "SELECT email FROM UTILISATEUR_G1J WHERE email = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try(ResultSet resultSet = pstmt.executeQuery()){
                if( resultSet.next() ) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void modifUser(int id, String nom, String prenom, String tel, String adresse, String email, String role, String motdepasse) {
        String sql = "UPDATE UTILISATEUR_G1J SET nom = ?, prenom = ?, tel = ?, adresse = ?, email = ?, role = ?, mot_de_passe = ? WHERE id_utilisateur = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, tel);
            pstmt.setString(4, adresse);
            pstmt.setString(5, email);
            pstmt.setString(6, role);
            pstmt.setString(7, motdepasse);
            pstmt.setInt(8, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeUser(int id) {
        String sql = "DELETE FROM UTILISATEUR_G1J WHERE id_utilisateur = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Les méthodes définies par Big dans le fichier UserFram

    public UtilisateurPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nom:"), gbc);
        nomField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Prénom:"), gbc);
        prenomField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Téléphone:"), gbc);
        telField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(telField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Adresse:"), gbc);
        adresseField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(adresseField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Rôle:"), gbc);
        String option[] = {"admin", "employer"};
        roleField = new JComboBox<>(option); // Modification ici
        gbc.gridx = 1;
        panel.add(roleField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Mot de passe:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Confirmer Mot de passe:"), gbc);
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        JButton updateButton = new JButton("Modifier");
        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        JButton resetPasswordButton = new JButton("Réinitialiser MDP");
        resetPasswordButton.setBackground(new Color(155, 89, 182));
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.setHorizontalTextPosition(SwingConstants.RIGHT);

        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        panel.add(updateButton, gbc);
        gbc.gridx = 0; gbc.gridy = 9;
        panel.add(deleteButton, gbc);
        gbc.gridx = 1;
        panel.add(resetPasswordButton, gbc);

        String[] columnNames = {"ID", "Nom", "Prénom", "Téléphone", "Adresse", "Email", "Rôle", "Mot de passe"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        searchField = new JTextField(20);
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Rechercher:"));
        searchPanel.add(searchField);

        add(panel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        addButton.addActionListener(this::addUser);
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        resetPasswordButton.addActionListener(e -> resetPassword());
        getUsers(tableModel, "");
        listeners();
    }

    private void addUser(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String tel = telField.getText();
        String adresse = adresseField.getText();
        String email = emailField.getText();
        String role = (String) roleField.getSelectedItem(); // Modification ici
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Vérification des champs
        if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || adresse.isEmpty() || email.isEmpty() || role.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis !");
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "L'email est invalide !");
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas !");
            return;
        }

        // Crypter le mot de passe
        String encryptedPassword = encryptPassword(password);

        // Ajouter à la base de données
        if( insertUser(nom, prenom, tel, adresse, email, role, encryptedPassword) ) {
            getUsers(tableModel, "");
            JOptionPane.showMessageDialog(this, "Utilisateur ajouté !");
        }else{
            JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion !", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listeners(){
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = userTable.getSelectedRow();
                if( selectedRow == -1 ) return;

                String nom = (String) tableModel.getValueAt(selectedRow, 1);
                String prenom = (String) tableModel.getValueAt(selectedRow, 2);
                String telephone = (String) tableModel.getValueAt(selectedRow, 3);
                String addresse = (String) tableModel.getValueAt(selectedRow, 4);
                String email = (String) tableModel.getValueAt(selectedRow, 5);
                String role = (String) tableModel.getValueAt(selectedRow, 6);
                String password = (String) tableModel.getValueAt(selectedRow, 7);

                nomField.setText(nom);
                prenomField.setText(prenom);
                telField.setText(telephone);
                adresseField.setText(addresse);
                emailField.setText(email);
                roleField.setSelectedItem(role); // Modification ici
                passwordField.setText(password);
                confirmPasswordField.setText(password);
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                getUsers(tableModel, searchField.getText());
            }
        });
    }

    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            if( !checkMail(emailField.getText()) ){
                JOptionPane.showMessageDialog(this, "Ce mail est deja pris !");
                return;
            }
            else if( !passwordField.getText().equals(confirmPasswordField.getText()) ){
                JOptionPane.showMessageDialog(this, "les mot de passes ne correspondent pas !");
                return;
            }
            Object o = tableModel.getValueAt(selectedRow, 0);
            modifUser(
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 0) + ""),
                    nomField.getText(),
                    prenomField.getText(),
                    telField.getText(),
                    adresseField.getText(),
                    emailField.getText(),
                    (String) roleField.getSelectedItem(), // Modification ici
                    encryptPassword(passwordField.getText())
            );
            getUsers(tableModel, "");
            JOptionPane.showMessageDialog(this, "Mise a jour reussie !");
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur !");
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            removeUser(Integer.parseInt(tableModel.getValueAt(selectedRow, 0)+""));
            JOptionPane.showMessageDialog(this, "Suppression reussie !");
            getUsers(tableModel, "");
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur !");
        }
    }

    private void resetPassword() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.setValueAt(encryptPassword("0000"), selectedRow, 7);
        }
    }

    private String encryptPassword(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du chiffrement du mot de passe", e);
        }
    }
    private void searchUser(String text) {
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        userTable.setRowSorter(sorter);

        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 5));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UtilisateurPanel().setVisible(true));
    }
}
