/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.visualisation;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author geoagdt
 */
public class VectorImageManipulation {

    /**
     * Adapted from http://www.javalobby.org/articles/ultimate-image/
     * @param a_BufferedImage
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static BufferedImage resize(
            BufferedImage a_BufferedImage,
            int newWidth,
            int newHeight) {
        int width = a_BufferedImage.getWidth();
        int height = a_BufferedImage.getHeight();
        BufferedImage result = new BufferedImage(
                newWidth,
                newHeight,
                a_BufferedImage.TYPE_INT_RGB);
                //a_BufferedImage.getType());
        Graphics2D g = result.createGraphics();
//        g.setRenderingHint(
//                RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(
                a_BufferedImage,
                0,
                0,
                newWidth,
                newHeight,
                0,
                0,
                width,
                height,
                null);
        g.dispose();
        return result;
    }
}
