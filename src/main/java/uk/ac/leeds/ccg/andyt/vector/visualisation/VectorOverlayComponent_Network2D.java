/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.leeds.ccg.andyt.vector.visualisation;

import java.awt.Graphics;

/**
 */
public class VectorOverlayComponent_Network2D extends VectorOverlayComponent {

    public VectorRenderNetwork2D _RenderNetwork2D;

    public VectorOverlayComponent_Network2D(
            VectorRenderNetwork2D _RenderNetwork2D) {
        this._RenderNetwork2D = _RenderNetwork2D;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (_BufferedImage != null) {
            g.drawImage(_BufferedImage, 0, 0, this);
        }
        _RenderNetwork2D.draw(g);
    }

}

