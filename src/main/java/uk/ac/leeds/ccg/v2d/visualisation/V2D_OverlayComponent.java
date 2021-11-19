/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v2d.visualisation;

import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class V2D_OverlayComponent extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The image
     */
    public BufferedImage bi = null;
    //public Image _Image;

    /**
     * Create a new instance
     */
    public V2D_OverlayComponent() {
    }

    /**
     * @param imageURL URL of image to read.
     */
    public void readImage(URL imageURL) {
        try {
            bi = ImageIO.read(imageURL);
            //_Image = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (bi != null) {
            g.drawImage(bi, 0, 0, this);
        }
        // Call out to all things to be overlayed...
    }


}
