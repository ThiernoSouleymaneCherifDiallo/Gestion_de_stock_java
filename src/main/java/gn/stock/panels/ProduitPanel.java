package gn.stock.panels;

import gn.stock.base.Kpanel;
import gn.stock.interfaces.IProduit;

import javax.swing.*;

public class ProduitPanel extends Kpanel implements IProduit {


    public ProduitPanel() {
        add(new JLabel("PRODUIT"));
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
