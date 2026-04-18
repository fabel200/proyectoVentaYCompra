/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Marialyh
 */
public class WallpaperDesktopPane extends JDesktopPane {

    private Image background;
    
    public WallpaperDesktopPane(Image img) {
        this.background = img;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }
    }