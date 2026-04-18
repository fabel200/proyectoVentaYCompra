package util;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonIcono extends JButton implements Serializable {
    private String rutaIcono = "";

    public BotonIcono() {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    public String getRutaIcono() {
        return rutaIcono;
    }

    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (rutaIcono != null && !rutaIcono.isEmpty()) {
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(rutaIcono));
                Image img = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(img));
            } catch (Exception e) {
                setText("Error Icon");
            }
        }
        super.paintComponent(g);
    }
}