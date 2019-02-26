package main;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

import res.resources.images.ResourceLoader;

public class LoadImage extends JFrame {

    Image img = ResourceLoader.loadImage("icon.png");

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null); // see javadoc for more info on the parameters
        repaint();
    }

    public static void main(String args[]) {
        LoadImage li = new LoadImage();
        li.setSize(300, 300);
        li.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        li.setVisible(true);
    }
}
