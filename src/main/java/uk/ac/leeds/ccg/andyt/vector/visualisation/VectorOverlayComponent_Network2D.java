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

