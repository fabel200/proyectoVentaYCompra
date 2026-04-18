package controlador;

import javax.swing.JTextField;
import conexion.Conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;
import static vista.InterGraficas.fecha_fin;
import static vista.InterGraficas.fecha_inicio;

public class Ctrl_graficas extends javax.swing.JInternalFrame {

  
    InterGraficas interGraficas;

    public Ctrl_graficas( InterGraficas interGraficas) {
        this.interGraficas = interGraficas;

        this.interGraficas.jButton_Guardar.addActionListener(e -> GaficarVentas());

       
    }

    private void GaficarVentas() {
InterGraficaVentas interGraficaVentas = new InterGraficaVentas();
        fecha_inicio = ((JTextField) interGraficas.jDateChooser_fecha_inicio.getDateEditor().getUiComponent()).getText();
        fecha_fin = ((JTextField) interGraficas.jDateChooser_fecha_fin.getDateEditor().getUiComponent()).getText();

        jDesktopPane_menu.add(interGraficaVentas);
        interGraficaVentas.setVisible(true);

    }

}