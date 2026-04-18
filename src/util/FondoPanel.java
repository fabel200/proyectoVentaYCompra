package util;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FondoPanel extends JPanel implements Serializable {
    private String rutaImagen = "";

    public FondoPanel() {
        // Constructor vacío para la paleta
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
        repaint(); // Refresca el componente en el editor
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            try {
                Image img = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            } catch (Exception e) {
                // Si no hay imagen, el panel se mantiene con su color de fondo normal
            }
        }
    }
}