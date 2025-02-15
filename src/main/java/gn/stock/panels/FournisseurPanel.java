package gn.stock.panels;

import gn.stock.base.Kpanel;
import gn.stock.interfaces.IProduit;

import javax.swing.*;

public class FournisseurPanel extends Kpanel implements IProduit {


    public FournisseurPanel() {
        add(new JLabel("Fournissseur"));
        add(new JTextField());

    }

    @Override
    public void createProduit() {


    }

    @Override
    public void updateProduit() {

    }

    @Override
    public void deleteProduit() {

    }

    @Override
    public void viewProduit() {

    }
}
