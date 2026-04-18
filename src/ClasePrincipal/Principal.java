package ClasePrincipal;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import controlador.*;
import javax.swing.UIManager;
import vista.*;

public class Principal {

    public static void main(String[] args) {
        try {
            // Esto activa el tema oscuro de FlatLaf
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FrmLogin lo = new FrmLogin();
        Ctrl_login con = new Ctrl_login(lo);

        con.iniciar();

    }

}
