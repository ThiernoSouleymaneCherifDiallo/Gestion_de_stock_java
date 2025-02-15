package gn.stock.main;

//import gn.stock.components.SideBar;
import gn.stock.panels.ProduitPanel;

import javax.swing.*;
import java.awt.*;

public class main extends JFrame {
    public main() throws HeadlessException {


        JPanel contentPanel = (JPanel) getContentPane();
        //contentPanel.add(new SideBar(),BorderLayout.WEST);
        contentPanel.add(new ProduitPanel());

        setSize(480,480);
        setVisible(true);
    }

    public static void main(String[] args) {
        new main();
    }
}
