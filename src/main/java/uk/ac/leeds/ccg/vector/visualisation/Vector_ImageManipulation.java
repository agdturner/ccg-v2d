/**
 * Component of a library for handling spatial vector data.
 * Copyright (C) 2009 Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.vector.visualisation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author geoagdt
 */
public class Vector_ImageManipulation {

    /**
     * Adapted from http://www.javalobby.org/articles/ultimate-image/
     * @param bi The image to be resized.
     * @param newWidth The new width for the image.
     * @param newHeight The new height for the image.
     * @return A BufferedImage resized
     */
    public static BufferedImage resize(
            BufferedImage bi,
            int newWidth,
            int newHeight) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        BufferedImage result = new BufferedImage(
                newWidth,
                newHeight,
                bi.TYPE_INT_RGB);
                //a_BufferedImage.getType());
        Graphics2D g = result.createGraphics();
//        g.setRenderingHint(
//                RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(
                bi,
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
