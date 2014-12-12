package uk.ac.leeds.ccg.andyt.vector.visualisation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class VectorOverlayComponent extends JPanel {

    public BufferedImage _BufferedImage = null;
    //public Image _Image;

    public VectorOverlayComponent() {
    }

    public void readImage(URL imageURL) {
        try {
            _BufferedImage = ImageIO.read(imageURL);
            //_Image = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (_BufferedImage != null) {
            g.drawImage(_BufferedImage, 0, 0, this);
        }
        // Call out to all things to be overlayed...
    }


}
