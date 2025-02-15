package gn.stock.base;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Kpanel extends JPanel {

    // Menu lefts elements
    public static final String MENU_LEFT_ELEMENT_CLIENT = "Clients";
    public static final String MENU_LEFT_ELEMENT_RESERVATION = "Réservations";
    public static final String MENU_LEFT_ELEMENT_CHAMBRE = "Chambres";
    public static final String MENU_LEFT_ELEMENT_EMPLOYEE = "Employees";
    public static final String MENU_LEFT_ELEMENT_SERVICE = "Services";
    public static final String MENU_LEFT_ELEMENT_FACTURE = "Factures";
    public static final String MENU_LEFT_ELEMENT_STATISTIQUE = "Statistiques";
    public static final String MENU_LEFT_ELEMENT_SETTINGS = "Paramètres";
    public static final String MENU_LEFT_ELEMENT_LOGOUT = "Se Deconnecter";

    // Tailles des icones de  menu et du menu en question
    public static final int FLAT_SVG_ICON_SIZE = 25;
    public static final int K_MENU_LEFT_WIDTH = 55;

    // Icones de menus
    public static final FlatSVGIcon clientIcon = new FlatSVGIcon("svgs/client.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon reservationIcon = new FlatSVGIcon("svgs/reservation.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon chambreIcon = new FlatSVGIcon("svgs/chambre.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon employeeIcon = new FlatSVGIcon("svgs/employe.svg",FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon serviceIcon = new FlatSVGIcon("svgs/service.svg",FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon factureIcon = new FlatSVGIcon("svgs/facture.svg",FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon statistiqueIcon = new FlatSVGIcon("svgs/stats.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon settingsIcon = new FlatSVGIcon("svgs/settings.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);
    public static final FlatSVGIcon logoutIcon = new FlatSVGIcon("svgs/logout.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);

    // Instance de connexion a la base de donnée
    protected Connection connection;

    // Autres icones
    public static final FlatSVGIcon sendIcon = new FlatSVGIcon("svgs/send.svg", FLAT_SVG_ICON_SIZE, FLAT_SVG_ICON_SIZE);

    public Kpanel() {
       // connectToDatabase();
    }

    public void connectToDatabase() {
        Properties properties = new Properties();
        try(InputStream inputStream = getClass().getResourceAsStream("/database/databaseORACLE.properties")) {
            properties.load(inputStream);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }

        try{
            System.out.println(properties.getProperty("jdbc.driver.class"));
            Class.forName(properties.getProperty("jdbc.driver.class"));
            connection = DriverManager.getConnection(
                    properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.login"),
                    properties.getProperty("jdbc.password")
            );
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}